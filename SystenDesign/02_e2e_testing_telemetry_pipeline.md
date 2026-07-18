# E2E Testing Strategy: Endpoint Telemetry Pipeline

## 1. Testing Pyramid & Levels

```
                    ▲
                   / \
                  /   \  Manual Testing
                 /-----\  (Smoke, Demo)
                /       \ (< 1% of tests)
               /         \
              /---────────\  E2E Tests
             /             \ (UI, API, Data)
            /               \ (10-15% of tests)
           /─────────────────\  Integration Tests
          /                   \ (Services, Kafka, DB)
         /                     \ (25-35% of tests)
        /───────────────────────\
       /                         \ Unit Tests
      /                           \ (Code logic)
     /                             \ (60-70% of tests)
    /_____________________________/
```

### Test Level Definitions

| Level | Scope | Speed | Flakiness | %ofSuite |
|---|---|---|---|---|
| **Unit** | Single function; mocks all deps | <100ms | <1% | 65% |
| **Integration** | Service + one downstream (Kafka, DB) | 100ms-1s | 2-5% | 30% |
| **E2E** | Full pipeline end-to-end (ingestion → query) | 1-30s | 5-15% | 5% |
| **Smoke** | Critical path only (happy case) | 30-60s | 1-2% | - |

---

## 2. E2E Test Scenarios

### 2.1 Happy Path (Golden Flow)

**Scenario: Single event from endpoint to dashboard**

```
Test: event_happy_path_single_event
Steps:
  1. Generate valid event: {customer_id, endpoint_id, event_type, timestamp, tags}
  2. Send via HTTPS POST to /v1/events
  3. Assert: 202 response within 100ms
  4. Poll Kafka (telemetry-raw topic) for event; assert received within 2s
  5. Verify Flink processing: event appears in aggregation window
  6. Query VictoriaMetrics after 10s: check count metric increased
  7. Query raw events in S3: verify event stored in correct partition
  8. Dashboard query: select sum(event_count) for past 1min; assert matches
Expected: Event visible in all three paths (metrics, raw, dashboard) within 15s
```

**Scenario: Batch of 100 events**

```
Test: event_happy_path_batch_100
  1. Generate 100 unique events (different endpoint_ids or services)
  2. Send as single batch POST to /v1/events (payload 50KB)
  3. Assert: 202 response + batch ID in response
  4. Poll Kafka: verify all 100 arrive within partition assigned queue
  5. Check aggregation: 1-min bucket should have count=100
  6. Query dashboard: verify metric reflects batch
  7. Check S3 parquet file: verify all 100 rows present, correctly compressed
Expected: All events processed atomically; no partial loss
```

### 2.2 Error Scenarios

**Invalid Event Handling**

```
Test: malformed_event_rejected
  1. Send event with missing required field (e.g., no timestamp)
  2. Assert: 400 Bad Request with specific error message
  3. Verify: event routed to DLQ (telemetry-dlq topic)
  4. Monitor metric: validation_error_total incremented
  5. Check alerting: no false alert triggered (expected error)
Expected: Request rejected cleanly; system remains healthy
```

```
Test: clock_skew_too_large
  1. Send event with timestamp 2 days in the future
  2. Assert: 400 "Timestamp out of acceptable range" (-1 day to +1 day)
  3. Verify: dropped, not stored in raw events
Expected: Protect against malicious or broken clocks
```

```
Test: duplicate_event_deduplication
  1. Send same event (same event_id, endpoint_id, timestamp) twice, 10s apart
  2. Poll Kafka: both arrive (buffering layer doesn't deduplicate)
  3. Monitor Flink: dedup_count metric incremented by 1
  4. Query metrics: count=1 (not 2); deduplication worked
  5. Query raw events: both rows present (dedup happens at processing layer)
Expected: Metrics reflect deduplicated count; raw events retain both for audit
```

### 2.3 Load & Scale Scenarios

**Sustained Load**

```
Test: sustained_load_1m_events_per_sec
  Duration: 60 seconds
  Generate: 1M events/sec across 1000 endpoints
  Distribution: Poisson arrival; uniform across 10 event types
  
  Validation checkpoints (every 10s):
    1. Ingestion API: P99 latency < 100ms, error rate < 0.1%
    2. Kafka: all events land in assigned partitions, no backpressure
    3. Flink: lag (process_time - ingestion_time) < 5s
    4. Metrics DB: queries return results < 1s (includes aggregation)
    5. Memory growth: no unbounded growth; stable GC patterns
  
  Final validation:
    - Count ingested == count in Kafka == count in storage
    - No events lost
    - Metrics are within ±2% of expected (sampling tolerance)
Expected: System handles 1M evt/sec at designed SLOs
```

**Spike Load (10x spike, 30s)**

```
Test: spike_load_10m_events_burst
  Baseline: 1M evt/sec steady state for 20s
  Spike: Jump to 10M evt/sec for 30s
  Recovery: Back to 1M evt/sec for 20s
  
  Checkpoints:
    1. Ingestion API: gracefully return 429 if needed (circuit breaker)
    2. Sampling rate: auto-increase from 10% to 50% during spike
    3. No event loss for critical events (health, errors)
    4. Non-critical events: may drop or be sampled
    5. After spike: recovery to normal within 2 minutes
Expected: Graceful degradation; no cascading failures
```

### 2.4 Failure Scenarios

**Kafka Broker Down**

```
Test: kafka_broker_failure_single_node
  Setup: 3 Kafka brokers, RF=3
  Action: Kill broker-0 (node holds some partitions as leader)
  
  Immediate (0-5s):
    1. New ingestion requests: attempt to write, get broker unavailable error
    2. API returns 503 Service Unavailable (with retry hint)
    3. Failed requests buffered in client SDK (retry queue)
  
  Rebalance (5-15s):
    1. Kafka detects broker down
    2. Partitions rebalance; new leaders elected (from replicas)
    3. API retries succeed
    4. Events written to new brokers
  
  Validation:
    - No event loss (RF=3 means 2 replicas still exist)
    - Max downtime for new requests: < 30s
    - Existing lag clears within 1 min
Expected: Transparent failover; app continues
```

**Flink Crash (out-of-memory)**

```
Test: flink_taskmanager_oom_recovery
  Setup: Flink cluster with 5 taskmanagers, state size ~10GB (dedup window)
  Action: Inject heap exhaustion on taskmanager-2
  
  Failure (t=0):
    1. Taskmanager-2 crashes (JVM OOM)
    2. Pending events on that taskmanager are lost
    3. Lag accumulates
  
  Recovery (t=5-15s):
    1. Flink JobManager detects task failure
    2. Restores from latest checkpoint (saved 10s before failure)
    3. Rebalances tasks: remaining 4 taskmanagers take over
    4. Resume processing from checkpoint
  
  Validation:
    - Lag peak: < 10 min
    - Data loss: only events in-flight during crash (~50K events)
    - Full recovery: resume normal latency within 3 min
Expected: Fault-tolerant recovery via checkpointing
```

**Metrics DB Overload**

```
Test: timeseries_db_write_throttle
  Setup: VictoriaMetrics with low write capacity (constrained)
  Action: Send 5M evt/sec (high cardinality: 10K unique tag combinations)
  
  Response:
    1. First 30s: metric writes succeed, some latency
    2. 30-60s: write queue fills; metric ingestion backs off
    3. Flink detects backpressure; increases sampling of aggregation output
    4. After 60s: recovers to normal
  
  Validation:
    - Metrics eventually written (no loss of data)
    - No cascading failure (ingestion API still accepts events)
    - Metrics arrive with higher latency (acceptable: < 60s)
Expected: Backpressure propagation; graceful degradation
```

### 2.5 Data Correctness Scenarios

**Metric Accuracy**

```
Test: aggregation_accuracy_1min_window
  Scenario: Send 60,000 events over 60 seconds to 100 endpoints
  - 50% status=200, 50% status=500
  - Latencies: random [10, 500]ms
  
  Validation:
    1. Count: aggregate sum(count) == 60,000 ✓
    2. Error rate: (count where status=500) / total == 0.5 ±0.01 ✓
    3. Latency percentiles:
       - p50: in range [100, 200]ms
       - p99: in range [450, 500]ms
    4. Grouping by endpoint: ±1 error per endpoint (sampling/rounding)
Expected: Metrics computed correctly; acceptable rounding error
```

**Raw Event Storage Integrity**

```
Test: parquet_row_count_matches_input
  Scenario: Send 10,000 events in 5 batches over 10 seconds
  
  After processing:
    1. Count rows in S3 parquet file(s) for this minute
    2. Compare to:
       - Deduplicated ingested count (accounting for dedup)
       - Sampled count (if sampling applied)
    3. Verify schema: all columns present, types correct
    4. Verify compression: file size reasonable (target: 10:1)
Expected: Parquet file has correct row count and schema
```

**Deduplication Logic**

```
Test: dedup_window_boundary_cases
  Case 1: Identical events 1 hour apart
    - Send event at 14:00:00
    - Send same event at 15:00:00
    - Expected: both counted (outside dedup window)
  
  Case 2: Identical events 59 minutes apart
    - Send event at 14:00:00
    - Send same event at 14:59:59
    - Expected: second deduplicated (inside 1-hour window)
  
  Case 3: Events with same ID, different timestamp
    - event_id=A, timestamp=14:00:00 (ingested)
    - event_id=A, timestamp=14:00:01 (different timestamp)
    - Expected: not deduplicated (different events)
Expected: Dedup respects time window boundaries
```

---

## 3. Test Environment Setup

### 3.1 Local Development

**Docker Compose Stack**

```yaml
version: '3.8'
services:
  kafka:
    image: confluentinc/cp-kafka:7.5
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
  
  flink-jobmanager:
    image: flink:latest-java11
    command: jobmanager
    ports:
      - "8081:8081"
    environment:
      JOB_MANAGER_RPC_ADDRESS: flink-jobmanager
  
  flink-taskmanager:
    image: flink:latest-java11
    command: taskmanager
    depends_on:
      - flink-jobmanager
  
  victoria-metrics:
    image: victoriametrics/victoria-metrics
    ports:
      - "8428:8428"
  
  localstack:  # AWS S3 mock
    image: localstack/localstack
    ports:
      - "4566:4566"
    environment:
      SERVICES: s3
      AWS_ACCESS_KEY_ID: test
      AWS_SECRET_ACCESS_KEY: test
  
  api-server:
    build: ./api-server
    ports:
      - "8080:8080"
    depends_on:
      - kafka
```

**Startup**

```bash
docker-compose up -d
# Wait for services: ~30s

# Create Kafka topics
docker exec kafka kafka-topics --create --bootstrap-server localhost:9092 \
  --topic telemetry-raw --partitions 10 --replication-factor 1

# Deploy Flink job
flink run telemetry-processor.jar --kafka-broker localhost:9092
```

### 3.2 Staging Environment

**Infrastructure**

- Kubernetes cluster (3 nodes minimum)
- Kafka: 3 brokers, RF=3, 100 partitions
- Flink: 1 jobmanager + 5 taskmanagers
- VictoriaMetrics: 2 replicas (HA)
- S3: real AWS bucket (separate namespace: `telemetry-staging-*`)

**Deployment**

```bash
# Helm deploy
helm install telemetry ./helm/telemetry-pipeline \
  --values values-staging.yaml \
  --namespace telemetry-staging
```

### 3.3 Production-Like Testing (Pre-Production)

**Prod-parity Stack**

- Same K8s cluster topology as production
- Different cloud account / namespace isolation
- Real data volume: 10-20% of peak production traffic (replay from production logs)
- Same monitoring/alerting setup

**When to run: Before major releases, monthly scale tests**

---

## 4. Test Data Generation

### 4.1 Synthetic Event Generator

```python
# test_data_generator.py
import random, time, json
from datetime import datetime, timedelta

class EventGenerator:
    def __init__(self, customer_id, endpoint_id, num_endpoints=1):
        self.customer_id = customer_id
        self.endpoint_id = endpoint_id
        self.event_types = ['request', 'error', 'trace', 'log']
        self.statuses = [200, 201, 400, 404, 500, 503]
        self.services = ['api', 'worker', 'auth', 'db']
    
    def generate_event(self, event_type=None, status=None, latency_ms=None):
        return {
            'customer_id': self.customer_id,
            'endpoint_id': f'{self.endpoint_id}',
            'event_type': event_type or random.choice(self.event_types),
            'timestamp': datetime.utcnow().isoformat(),
            'event_id': f'{int(time.time()*1000)}-{random.randint(0, 999999)}',
            'status': status or random.choice(self.statuses),
            'latency_ms': latency_ms or int(random.expovariate(1/100)),  # avg 100ms
            'service': random.choice(self.services),
            'tags': {
                'region': random.choice(['us-west', 'eu-central']),
                'version': f'1.{random.randint(0, 10)}',
            }
        }
    
    def generate_batch(self, count=10):
        return [self.generate_event() for _ in range(count)]

# Usage in test
gen = EventGenerator('acme', 'host-001', num_endpoints=100)
events = gen.generate_batch(100)
```

### 4.2 Realistic Distributions

```python
# Poisson arrival (requests don't come uniformly)
def poisson_arrival(rate_per_sec, duration_sec):
    import numpy as np
    times = np.sort(np.random.exponential(1/rate_per_sec, int(rate_per_sec * duration_sec)))
    return times

# Latency distribution (most fast, few slow)
latency = np.random.lognormal(mean=3, sigma=1.5)  # log-normal

# Error rate spike (simulating incidents)
error_rate = 0.01 if time.time() % 60 < 30 else 0.50

# Cardinality distribution (Zipfian: few endpoints send lots, many send little)
endpoint_weights = np.random.zipf(a=1.5, size=1000)
```

### 4.3 Replaying Production Traffic

```bash
# Export events from prod (last 1 hour)
aws s3 cp s3://telemetry-prod/2025-07-17/14/ ./prod-replay/ --recursive

# Replay in staging at 10% speed (compressed time)
python replay_events.py \
  --source ./prod-replay \
  --target http://staging-api:8080/v1/events \
  --speed-factor 0.1 \
  --duration 1h
```

---

## 5. Validation & Assertions

### 5.1 Data Consistency Validation

```python
class TelemetryValidator:
    def __init__(self, kafka_client, tsdb_client, s3_client):
        self.kafka = kafka_client
        self.tsdb = tsdb_client
        self.s3 = s3_client
    
    def validate_event_flow(self, event_id, customer_id, max_wait_sec=60):
        """Trace a single event through all layers"""
        start = time.time()
        
        # 1. Check Kafka
        kafka_event = self._find_in_kafka(event_id, timeout=10)
        assert kafka_event is not None, f"Event {event_id} not found in Kafka"
        
        # 2. Check metrics (after 5-10s aggregation)
        time.sleep(10)
        metrics = self.tsdb.query(f'event_count{{event_id="{event_id}"}}', end=time.time())
        assert metrics['value'] >= 1, "Event not aggregated in metrics"
        
        # 3. Check S3 (after batch flush, ~5min)
        time.sleep(300)
        s3_events = self.s3.scan_partition(customer_id, date=today(), event_id=event_id)
        assert len(s3_events) >= 1, "Event not found in S3 Parquet"
        
        elapsed = time.time() - start
        print(f"Event tracing completed in {elapsed:.1f}s")
    
    def validate_aggregation_accuracy(self, event_count, expected_metric):
        """Verify aggregated metrics match input"""
        time.sleep(15)  # Wait for aggregation window
        
        metric_value = self.tsdb.query(expected_metric)['value']
        error_pct = abs(metric_value - event_count) / event_count * 100
        
        assert error_pct < 5, f"Metric {expected_metric} off by {error_pct}%"
        return error_pct
    
    def validate_no_data_loss(self, ingested_count, deduplicated_count):
        """Ensure all non-duplicate events are stored"""
        time.sleep(300)  # Wait for batch flush
        
        stored_count = self.s3.count_rows(date=today())
        assert stored_count == deduplicated_count, \
            f"Data loss: ingested {ingested_count}, deduped {deduplicated_count}, stored {stored_count}"
```

### 5.2 Latency SLO Validation

```python
def validate_latency_slo(events_sent, slo_p99_ms=100):
    """Measure end-to-end latency: send → API receipt"""
    latencies = []
    
    for event in events_sent:
        start = time.time()
        response = requests.post('http://api:8080/v1/events', json=event)
        latency_ms = (time.time() - start) * 1000
        latencies.append(latency_ms)
        assert response.status_code == 202, f"Failed: {response.status_code}"
    
    latencies.sort()
    p99 = latencies[int(len(latencies) * 0.99)]
    
    assert p99 < slo_p99_ms, f"P99 latency {p99}ms exceeds SLO {slo_p99_ms}ms"
    print(f"Latency: P50={latencies[len(latencies)//2]:.1f}ms, P99={p99:.1f}ms")
```

### 5.3 System Health Checks

```python
class HealthValidator:
    def check_all_systems(self):
        checks = [
            ('Kafka', self.check_kafka),
            ('Flink', self.check_flink),
            ('VictoriaMetrics', self.check_tsdb),
            ('S3', self.check_s3),
            ('API Server', self.check_api),
        ]
        
        results = {}
        for name, check_fn in checks:
            try:
                check_fn()
                results[name] = 'PASS'
            except Exception as e:
                results[name] = f'FAIL: {e}'
        
        failed = [k for k, v in results.items() if v != 'PASS']
        assert len(failed) == 0, f"System health check failed: {failed}"
        return results
    
    def check_kafka(self):
        """Verify Kafka broker health, partition distribution"""
        admin = KafkaAdminClient(...)
        metadata = admin.describe_cluster()
        brokers = metadata.brokers
        assert len(brokers) >= 3, "Insufficient brokers"
        
        topics = admin.list_topics()
        for topic in ['telemetry-raw', 'telemetry-events', 'telemetry-dlq']:
            assert topic in topics, f"Topic {topic} missing"
    
    def check_flink(self):
        """Verify Flink job running, lag acceptable"""
        job_status = requests.get('http://flink:8081/api/v1/jobs').json()
        assert len(job_status['jobs']) > 0, "No Flink job running"
        
        job_id = job_status['jobs'][0]['id']
        lag = requests.get(f'http://flink:8081/api/v1/jobs/{job_id}/metrics', 
                          params={'get': 'taskmanager.lag'}).json()
        lag_ms = lag['taskmanager.lag']
        assert lag_ms < 60000, f"Flink lag {lag_ms}ms exceeds threshold"
    
    def check_tsdb(self):
        """Verify metrics DB responding, cardinality reasonable"""
        response = requests.get('http://victoria:8428/api/v1/labels')
        assert response.status_code == 200, "Metrics DB not responding"
        
        cardinality = requests.get('http://victoria:8428/api/v1/cardinality/label_values',
                                  params={'label': '__name__'}).json()['value']
        assert cardinality < 1_000_000, f"Cardinality explosion: {cardinality} series"
```

---

## 6. Test Implementation

### 6.1 pytest-based E2E Suite

```python
# tests/e2e/test_happy_path.py
import pytest
from fixtures import kafka_client, tsdb_client, api_client, validator

@pytest.mark.e2e
class TestHappyPath:
    @pytest.fixture(autouse=True)
    def setup(self, kafka_client, tsdb_client, api_client):
        self.kafka = kafka_client
        self.tsdb = tsdb_client
        self.api = api_client
        # Clear state
        kafka_client.delete_topic('telemetry-raw')
        kafka_client.create_topic('telemetry-raw', partitions=10)
        time.sleep(2)
    
    def test_single_event_e2e(self, validator, event_generator):
        """Event flows from ingestion → Kafka → Flink → metrics → S3"""
        event = event_generator.generate_event()
        
        # Send
        response = self.api.post('/v1/events', json=[event])
        assert response.status_code == 202
        
        # Validate E2E
        validator.validate_event_flow(event['event_id'], event['customer_id'])
    
    def test_batch_100_events(self, validator, event_generator):
        """Batch of 100 events processed atomically"""
        events = event_generator.generate_batch(100)
        
        # Send
        response = self.api.post('/v1/events', json=events)
        assert response.status_code == 202
        
        # Validate
        time.sleep(15)  # Aggregation window
        error = validator.validate_aggregation_accuracy(100, 'event_count')
        assert error < 2  # < 2% deviation

@pytest.mark.e2e
class TestErrorHandling:
    def test_invalid_event_returns_400(self, api_client):
        """Malformed event rejected at API"""
        event = {'missing': 'timestamp'}  # Missing required field
        
        response = api_client.post('/v1/events', json=[event])
        assert response.status_code == 400
        assert 'timestamp' in response.text.lower()
    
    def test_duplicate_event_deduplicated(self, validator, event_generator):
        """Duplicate events counted once in metrics"""
        event = event_generator.generate_event()
        
        # Send same event twice
        self.api.post('/v1/events', json=[event])
        self.api.post('/v1/events', json=[event])
        
        time.sleep(15)
        
        # Metrics should show count=1 (deduped)
        metric = self.tsdb.query('event_count')
        assert metric['value'] == 1

@pytest.mark.e2e
@pytest.mark.load
class TestSustainedLoad:
    def test_1m_events_per_second_60_seconds(self, validator, event_generator):
        """System handles 1M evt/sec for 1 minute"""
        duration_sec = 60
        rate = 1_000_000
        events_per_batch = 1000
        
        start = time.time()
        event_count = 0
        
        while time.time() - start < duration_sec:
            events = event_generator.generate_batch(events_per_batch)
            response = self.api.post('/v1/events', json=events)
            assert response.status_code == 202
            event_count += events_per_batch
            
            # Check SLO every 10s
            if int(time.time() - start) % 10 == 0:
                health = validator.check_all_systems()
                assert all(v == 'PASS' for v in health.values()), health
        
        print(f"Sent {event_count} events in {duration_sec}s ({event_count/duration_sec:.0f} evt/sec)")
        validator.validate_no_data_loss(event_count, event_count)
```

### 6.2 Chaos Testing with Chaos Toolkit

```yaml
# chaos/experiments/kafka_broker_down.yaml
title: "Kill Kafka broker and verify recovery"
description: "Ensure system handles broker failure gracefully"

steady-state-hypothesis:
  title: "System is healthy"
  probes:
    - name: "Check API responding"
      type: "probe"
      provider:
        type: "http"
        url: "http://api:8080/health"
      tolerance: 200

method:
  - type: "action"
    name: "Kill Kafka broker"
    provider:
      type: "process"
      path: "pkill -f kafka.Kafka"
      arguments: "-KILL"
  
  - type: "probe"
    name: "API returns 503"
    provider:
      type: "http"
      url: "http://api:8080/v1/events"
    tolerance: [503, 504]  # Expected during failure
    timeout: 5
  
  - type: "wait"
    value: 15
    unit: second  # Time for Kafka failover
  
  - type: "probe"
    name: "API recovers"
    provider:
      type: "http"
      url: "http://api:8080/health"
    tolerance: 200

rollback:
  - type: "action"
    name: "Restart Kafka broker"
    provider:
      type: "process"
      path: "docker restart kafka"
```

**Run chaos test:**

```bash
chaos run kafka_broker_down.yaml --journal-out=chaos-report.json
```

### 6.3 Load Testing with k6

```javascript
// load-tests/sustained_load.js
import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  scenarios: {
    sustained_load: {
      executor: 'constant-vus',
      vus: 100,        // 100 concurrent users
      duration: '1m',   // 1 minute
      rampUp: '10s',    // 10s ramp
    }
  },
  thresholds: {
    'http_req_duration': ['p(99)<100'],  // P99 < 100ms
    'http_req_failed': ['rate<0.1'],      // < 0.1% error
  },
};

export default function() {
  let event = {
    customer_id: 'acme',
    endpoint_id: `endpoint-${__VU}`,  // Each VU has unique endpoint
    event_type: 'request',
    timestamp: new Date().toISOString(),
    event_id: `${Date.now()}-${Math.random()}`,
    status: Math.random() < 0.1 ? 500 : 200,  // 10% error rate
    latency_ms: Math.random() * 500,
  };
  
  let response = http.post(
    'http://api:8080/v1/events',
    JSON.stringify([event]),
    { headers: { 'Content-Type': 'application/json' } }
  );
  
  check(response, {
    'status is 202': (r) => r.status === 202,
    'response time < 100ms': (r) => r.timings.duration < 100,
  });
  
  sleep(0.01);  // 10ms between requests per VU (10 req/sec × 100 VUs = 1000 evt/sec)
}
```

**Run:**

```bash
k6 run load-tests/sustained_load.js --out json=results.json
```

---

## 7. CI/CD Integration

### 7.1 GitHub Actions Workflow

```yaml
# .github/workflows/e2e-tests.yml
name: E2E Tests

on:
  push:
    branches: [main]
  pull_request:
  schedule:
    - cron: '0 2 * * *'  # Nightly at 2 AM

jobs:
  e2e-unit:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up Python
        uses: actions/setup-python@v4
        with:
          python-version: '3.10'
      
      - name: Run unit tests
        run: pytest tests/unit -v --cov=src --cov-report=xml
      
      - name: Upload coverage
        uses: codecov/codecov-action@v3

  e2e-integration:
    runs-on: ubuntu-latest
    services:
      kafka:
        image: confluentinc/cp-kafka:7.5
        ports:
          - 9092:9092
      victoria:
        image: victoriametrics/victoria-metrics
        ports:
          - 8428:8428
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Build API server
        run: docker build -t telemetry-api:test ./api-server
      
      - name: Start services
        run: docker-compose -f docker-compose.test.yml up -d
      
      - name: Wait for services
        run: sleep 30
      
      - name: Run integration tests
        run: pytest tests/integration -v --tb=short
      
      - name: Collect logs
        if: failure()
        run: docker-compose -f docker-compose.test.yml logs > test-logs.txt
      
      - name: Upload logs
        if: failure()
        uses: actions/upload-artifact@v3
        with:
          name: docker-logs
          path: test-logs.txt

  e2e-full:
    runs-on: ubuntu-latest
    if: github.event_name == 'push' || github.event_name == 'schedule'
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Deploy to staging
        run: |
          helm upgrade --install telemetry ./helm/telemetry-pipeline \
            --values values-staging.yaml \
            --namespace telemetry-staging
      
      - name: Run E2E tests
        run: |
          pytest tests/e2e \
            --tb=short \
            --maxfail=3 \
            -v \
            --junitxml=results.xml
        env:
          API_ENDPOINT: http://staging-api:8080
      
      - name: Run load tests
        run: k6 run load-tests/sustained_load.js --out json=k6-results.json
      
      - name: Publish results
        if: always()
        uses: EnricoMi/publish-unit-test-result-action@v2
        with:
          files: results.xml
      
      - name: Performance regression check
        run: python scripts/check_perf_regression.py k6-results.json

  e2e-chaos:
    runs-on: ubuntu-latest
    if: github.event_name == 'schedule'  # Only nightly
    
    steps:
      - uses: actions/checkout@v3
      
      - name: Install Chaos Toolkit
        run: pip install chaostoolkit
      
      - name: Deploy to chaos environment
        run: helm upgrade --install telemetry ./helm/telemetry-pipeline --values values-chaos.yaml
      
      - name: Run chaos experiments
        run: |
          chaos run chaos/experiments/kafka_broker_down.yaml --journal-out=chaos-kafka.json
          chaos run chaos/experiments/flink_crash.yaml --journal-out=chaos-flink.json
          chaos run chaos/experiments/tsdb_overload.yaml --journal-out=chaos-tsdb.json
      
      - name: Analyze chaos results
        run: python scripts/analyze_chaos.py chaos-*.json
      
      - name: Upload reports
        uses: actions/upload-artifact@v3
        with:
          name: chaos-reports
          path: chaos-*.json
```

### 7.2 Test Reporting Dashboard

```python
# scripts/generate_test_report.py
import json, datetime
from pathlib import Path

def summarize_test_results():
    results = {
        'timestamp': datetime.datetime.utcnow().isoformat(),
        'unit': parse_pytest_xml('results-unit.xml'),
        'integration': parse_pytest_xml('results-integration.xml'),
        'e2e': parse_pytest_xml('results-e2e.xml'),
        'load': parse_k6_json('k6-results.json'),
        'chaos': parse_chaos_json('chaos-reports/*.json'),
    }
    
    summary = {
        'total_passed': sum(r['passed'] for r in results.values() if isinstance(r, dict)),
        'total_failed': sum(r['failed'] for r in results.values() if isinstance(r, dict)),
        'coverage': results['unit']['coverage'],
        'performance_regressed': results['load']['perf_check']['regressed'],
        'chaos_experiments_failed': len([e for e in results['chaos'] if not e['steady_state_met']]),
    }
    
    # Print to console
    print(f"✓ Passed: {summary['total_passed']}")
    print(f"✗ Failed: {summary['total_failed']}")
    print(f"📊 Coverage: {summary['coverage']}")
    
    # Write to artifact
    with open('test-summary.json', 'w') as f:
        json.dump(summary, f, indent=2)
    
    return summary
```

---

## 8. Test Data Management

### 8.1 Fixture Cleanup Strategy

```python
@pytest.fixture(scope='session')
def test_customer_id():
    """Unique customer ID per test session"""
    return f'test-{uuid.uuid4().hex[:8]}'

@pytest.fixture(autouse=True)
def cleanup_after_test(kafka_client, s3_client, test_customer_id):
    """Clean up test data after each test"""
    yield  # Test runs here
    
    # Cleanup: delete all topics, S3 partitions for this test_customer_id
    topics = kafka_client.list_topics()
    for topic in topics:
        if topic.startswith('telemetry-'):
            kafka_client.delete_messages(topic, 
                filter={'customer_id': test_customer_id})
    
    s3_client.delete_objects(
        Bucket='telemetry-test',
        Prefix=f'{test_customer_id}/'
    )
```

### 8.2 Data Retention Policies for Testing

```python
# Clean up old test data after 7 days
s3_lifecycle_policy = {
    "Rules": [
        {
            "Id": "DeleteOldTestData",
            "Filter": {"Prefix": "test-"},
            "Expiration": {"Days": 7},
            "Status": "Enabled"
        }
    ]
}
```

---

## 9. Test Monitoring & Alerts

### 9.1 Test Flakiness Tracking

```python
# Track flaky tests
test_flakiness = {
    'test_duplicate_event_deduplicated': 0.08,  # 8% flake rate (⚠️ warning)
    'test_kafka_broker_down': 0.15,             # 15% flake rate (🚨 critical)
}

# Alert on flakiness > 5%
flaky_tests = {name: rate for name, rate in test_flakiness.items() if rate > 0.05}
if flaky_tests:
    print("🚨 FLAKY TESTS DETECTED:")
    for test, rate in flaky_tests.items():
        print(f"  {test}: {rate*100:.1f}% flake rate")
    # Create issue
```

### 9.2 Test Metrics

**Track per CI run:**
- Test duration (unit: 5-10s, integration: 30-60s, E2E: 5-10m)
- Success rate per suite
- Flakiness trend
- Coverage trend

**Dashboard (Grafana):**

```
Telemetry Pipeline Test Health
├─ Unit Test Pass Rate: 99.5%
├─ Integration Test Pass Rate: 97.2% ⚠️ (trending down)
├─ E2E Test Pass Rate: 95.8%
├─ Flaky Tests: kafka_broker_down (15%), duplicate_dedup (8%)
├─ Code Coverage: 82% (target: 85%)
└─ Avg Test Duration: 8m 45s (target: 5m)
```

---

## 10. Checklist for Test Coverage

- [ ] **Happy path:** Single event, batch of 100, high volume (1M evt/sec)
- [ ] **Validation:** Invalid JSON, missing fields, clock skew, malformed schema
- [ ] **Deduplication:** Same event 1 min apart, 1 hour apart, boundary cases
- [ ] **Metrics accuracy:** Count, error rate, percentiles, grouping
- [ ] **Raw storage:** Row count, compression, schema, partitioning
- [ ] **Failure modes:** Kafka broker down, Flink crash, metrics DB overload, S3 throttle
- [ ] **Graceful degradation:** Sampling under load, backpressure propagation
- [ ] **SLOs:** Ingestion P99 < 100ms, metrics latency < 5s, query P95 < 5s
- [ ] **Multi-tenancy:** Data isolation, RBAC compliance
- [ ] **Replay & idempotency:** Reprocessing old events, duplicate detection
- [ ] **Compliance:** GDPR deletion, audit logging
- [ ] **Performance regression:** Load test comparison to baseline
- [ ] **Chaos experiments:** Broker down, task crash, network partition, resource exhaustion

---

## 11. References

- **pytest** (unit/integration): https://docs.pytest.org/
- **k6** (load testing): https://k6.io/docs/
- **Chaos Toolkit**: https://chaostoolkit.org/
- **testcontainers** (Docker for tests): https://testcontainers.com/
- **Locust** (alternative load testing): https://locust.io/

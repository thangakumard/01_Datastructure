# Endpoint Telemetry Pipeline: System Design

## 1. Problem Statement

An endpoint telemetry pipeline ingests, processes, aggregates, and stores event data from distributed client endpoints (agents, SDKs, browser extensions, mobile apps). It must handle high-volume, real-time streams while maintaining data quality, supporting complex queries, and enabling fast incident response.

**Key challenges:**
- Millions of endpoints generating events continuously
- Network unreliability and endpoint intermittency
- Balancing raw data retention vs. storage costs
- Supporting both real-time dashboards and historical analysis
- Strict latency requirements (milliseconds to seconds)
- Ensuring data integrity and deduplication

---

## 2. Requirements

### Functional Requirements

| Requirement | Details |
|---|---|
| **Event ingestion** | Accept structured events from heterogeneous endpoints (varying SDK versions, platforms) |
| **Real-time aggregation** | Compute metrics (counts, percentiles, rates) with sub-second latency for dashboards |
| **Historical retention** | Store raw events (30–90 days) for forensics, replay, compliance |
| **Dimensionality** | Support arbitrary tags/attributes per event without schema enforcement |
| **Sampling & filtering** | Dynamically adjust sampling rates per endpoint or event type to manage volume |
| **Anomaly detection** | Identify baseline deviations (spike detection, sudden drop-offs) with acceptable false-positive rates |
| **Multi-tenancy** | Isolate customers' data at ingestion, query, and storage layers |
| **Query API** | Support time-series queries (filtering, grouping, arithmetic, percentiles) |
| **Backpressure handling** | Gracefully degrade (drop, buffer, sample) under overload rather than lose critical data |

### Non-Functional Requirements

| Category | Requirement |
|---|---|
| **Throughput** | 10+ million events/second peak; 1-5M steady state |
| **Latency** | P99 ingestion latency ≤100ms; metric delivery ≤5s (dashboard) / ≤60s (long-term storage) |
| **Availability** | 99.9% uptime; tolerate single-machine, rack, or regional failures |
| **Data retention** | 30–90 days raw; 2+ years aggregated metrics |
| **Durability** | No loss of critical events (health, errors, transactions); lossy sampling OK for high-volume traces |
| **Cost** | Sub-$1/GB storage (compressed); efficient compute utilization |
| **Operational complexity** | Minimal manual intervention; self-healing, auto-scaling |

---

## 3. High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                          ENDPOINTS                              │
│  (Agents, SDKs, Browsers, Mobile) ──→ Event streams             │
└────────────────┬────────────────────────────────────────────────┘
                 │
                 ▼
        ┌────────────────┐
        │   Ingestion    │
        │   Layer (DLQ)  │
        └────────┬───────┘
                 │
      ┌──────────┴──────────┐
      ▼                     ▼
   Kafka                  gRPC
  (buffer)           (direct stream)
      │                     │
      └──────────┬──────────┘
                 ▼
        ┌─────────────────┐
        │ Stream Processor │
        │ (Flink/Kafka ST)│
        └────────┬────────┘
                 │
    ┌────────────┼────────────┐
    ▼            ▼            ▼
Real-time   Aggregation  Sampling
Window      & Rollup     & Filter
    │            │            │
    └────────────┴────────────┘
                 ▼
        ┌─────────────────┐
        │  Time-Series DB │
        │  (InfluxDB/VictoriaMetrics)
        │  (metrics only)  │
        └────────┬────────┘
                 │
        ┌────────┴────────┐
        ▼                 ▼
    Dashboards       Alerts
                     
        ┌─────────────────┐
        │  Object Storage │
        │  (S3/GCS/Blob)  │
        │  (raw events)   │
        └────────┬────────┘
                 │
        ┌────────┴────────┐
        ▼                 ▼
   Query Engine      Long-term
   (Trino/BigQuery)  Archive
```

---

## 4. Detailed Component Design

### 4.1 Ingestion Layer

**Purpose:** Accept events from endpoints, validate, and route them with minimal latency.

**Components:**

- **API Gateway (reverse proxy)**
  - Load balancer (geo-distributed)
  - Protocol: HTTPS (events) + gRPC (high-throughput agents)
  - Endpoint: `/v1/events` (batched), `/v1/event` (single)
  - Payload: 1–100KB per request; up to 1000 events per batch
  - Returns: 202 Accepted immediately (async processing)
  - Circuit breaker: Reject with 429 if backend overloaded

- **Request Validation**
  - Schema validation (allow lenient mode for new SDK versions)
  - Authentication: API key or mTLS per customer
  - Rate limiting: Per API key, per endpoint, global
  - Compression: gzip, snappy support

- **DLQ (Dead Letter Queue)**
  - Invalid events → Kafka topic `telemetry-dlq`
  - Retain for 7 days for debugging
  - Route to error tracking system

**Scaling:** Horizontal scale API servers; add geo-distributed ingestion points (PoPs) for latency.

**Failure modes:**
- Overload → gRPC backpressure, HTTP 429
- Validation failure → DLQ + metric
- Auth failure → 401/403 + block endpoint

### 4.2 Buffering & Routing

**Kafka Cluster**

Topics:
- `telemetry-raw` (partitioned by endpoint ID or customer ID)
  - Retention: 48 hours
  - Replication factor: 3
  - Partition count: 100–1000 (scale with throughput)
  
- `telemetry-events` (post-sampling, sent to storage)
  - Retention: 3 days
  - Used for durable writes to object storage

- `telemetry-dlq` (validation failures)
  - Retention: 7 days

**Why Kafka:**
- Durable buffering if downstream processing lags
- Enables replay without re-ingesting from endpoints
- Decouples ingestion from processing
- Built-in partitioning and scaling

**Alternative:** Use a managed streaming service (AWS Kinesis, Azure Event Hubs) if preferring operational simplicity over control.

### 4.3 Stream Processing

**Framework:** Apache Flink or Kafka Streams

**Primary responsibilities:**

1. **Parsing & Normalization**
   - Deserialize JSON events
   - Extract standard fields (timestamp, endpoint_id, customer_id, event_type)
   - Normalize timestamps to UTC; reject clock-skew > 1 day
   - Tag with ingestion timestamp

2. **Deduplication**
   - By (customer_id, endpoint_id, event_id, timestamp) within a 1-hour window
   - Use bloom filter or state store for efficiency
   - Drop duplicates; emit dedup_count metric

3. **Sampling & Filtering**
   - Read dynamic sampling config from config service
   - Sample events probabilistically per endpoint/type
   - Filter out low-value spam (logs, verbose traces)
   - Emit sampling_applied metric

4. **Aggregation Windows**
   - 1-minute tumbling windows
   - Compute per-dimension counts, error rates, latency percentiles (p50, p95, p99)
   - Support multiple aggregation keys (endpoint, service, region, status code)

5. **Anomaly Detection**
   - Sliding 5-minute baseline of rates and latencies
   - Flag if current window > baseline + 3σ
   - Emit anomaly scores for alerting

**Output streams:**
- `metrics-1m-raw` → Time-series DB (InfluxDB, VictoriaMetrics)
- `events-sampled` → Kafka, then to object storage
- `anomalies` → Alert system

**Scaling:** Use Flink parallelism > partition count; auto-scale based on lag.

**Failure handling:**
- State snapshots (checkpoints) every 10s; restore from latest on restart
- Idempotent writes to downstream (use dedup windows)
- Monitor backpressure; trigger sampling if lag > threshold

### 4.4 Time-Series Database (Metrics Store)

**Choice: VictoriaMetrics or InfluxDB**

**Schema (example):**
```
measurement=event_rate
timestamp=2025-07-17T14:30:00Z
tags:
  customer_id=acme
  endpoint_id=host-001
  event_type=request_complete
  status_code=200
  service=api
fields:
  count=523
  error_rate=0.02
  latency_p50=45
  latency_p99=250
```

**Retention policies:**
- Raw 1-min metrics: 30 days
- 5-min rollup: 90 days
- 1-hour rollup: 1 year
- Archive older to cold storage

**Queries:**
- Range: `metric{customer_id="acme"} [5m]` (Prometheus-style)
- Aggregation: `sum(rate(request_total[5m])) by (service)`
- Percentiles: `histogram_quantile(0.99, latency_ms)`

**Scaling:**
- Sharding by customer_id or hash(endpoint_id)
- Replication factor ≥2
- Compaction to manage cardinality (high-cardinality tags = storage explosion)

**Cost optimization:**
- Downsampling old series (drop low-volume time series)
- Compression (typically 10:1 ratio)

### 4.5 Raw Event Storage (Object Store)

**Medium: AWS S3, GCS, or Azure Blob Storage**

**Organization:**
```
s3://telemetry-bucket/{customer_id}/{date}/{hour}/{minute}/{partition}.parquet
```

**Format: Parquet**
- Columnar: compress well, support pushdown filtering
- Nested schema for dynamic fields
- Encoding: Snappy or Zstd

**Partitioning strategy:**
- Time (date/hour) for retention policies and cleanup
- Customer for isolation and access control
- Optional: event_type for query optimization

**Retention:**
- 30 days: hot (frequent access, no archival)
- 30–90 days: warm (monthly queries, archive after 90d)
- Archive: Glacier or equivalent for 2-year compliance hold

**Deduplication:**
- At write time: Spark/dataflow deduplicate by event_id before writing
- Or accept duplicates; deduplicate at query time

**Writing:**
- Buffered: Collect 5–10min of events (or 100MB), flush to object store
- Use Kafka connect sink (Confluent) or Spark streaming job
- Idempotent writes: use event_id + version as object key; overwrite if re-written

### 4.6 Query Layer (Analytics)

**Engine: Trino (PrestoSQL) or BigQuery**

**Responsibilities:**
- Federated queries over time-series DB + object store
- Support SQL (SELECT, WHERE, GROUP BY, JOIN)
- Materialized views for common queries
- Row-level access control (customer isolation)

**Example query:**
```sql
SELECT 
  endpoint_id,
  COUNT(*) as error_count,
  COUNT(DISTINCT user_id) as affected_users
FROM telemetry.raw_events
WHERE timestamp >= NOW() - INTERVAL 1 HOUR
  AND customer_id = 'acme'
  AND event_type = 'error'
GROUP BY endpoint_id
ORDER BY error_count DESC
LIMIT 10;
```

**Optimizations:**
- Partition pruning (by date, customer)
- Column pruning (Parquet advantage)
- Cached results for dashboards
- Explain plan + cost estimation before execution

**Access control:**
- Query isolation by customer (row-level filters)
- Query timeouts (max 5 min)
- Resource quotas per customer

### 4.7 Anomaly Detection Service

**Approach: Unsupervised + Rule-based**

**Algorithm:**
1. Establish baseline (e.g., p50, p95, error rate) over rolling 7-day window
2. For each new metric point, compute z-score: `z = (value - baseline_mean) / baseline_std`
3. Flag if `z > threshold` (e.g., 3.0 = 3 sigma, ~0.1% false positive rate)
4. Suppress consecutive alerts (deduplicate within 5 min)

**Enhancements:**
- Adjust baseline for day-of-week and time-of-day seasonality
- Use seasonal decomposition (STL) for series with strong periodicity
- Model-based: Train lightweight ARIMA or Prophet model per metric
- Correlation analysis: Link spikes across related metrics

**Output:**
- Anomaly events → Kafka topic `anomalies`
- Anomaly score (0–1) and metadata (baseline, current, z-score)
- Routed to alerting system (PagerDuty, Slack webhook)

**False positive control:**
- Require sustained anomaly (N consecutive points) before firing alert
- Exclude known maintenance windows
- A/B test sensitivity; target < 5% false positive rate

---

## 5. Data Flow (End-to-End Example)

**Scenario:** An endpoint sends 100 events; 2 are duplicates, 50 are sampled out, 48 are stored.

```
1. Endpoint sends batch of 100 events via HTTPS POST
   └─→ API Gateway validates, assigns ingestion_timestamp
   
2. Events placed in Kafka topic `telemetry-raw`
   └─→ Topic has 100-partition sharding; load-balanced
   
3. Flink consumer (parallelism 100) reads
   └─→ Parser extracts customer_id, endpoint_id, event_id, timestamp
   └─→ Deduplication: finds 2 duplicates (same event_id, timestamp), drops them (98 remain)
   └─→ Sampling: applies 50% rate per endpoint, keeps 49
   
4. Aggregation operator:
   └─→ 1-min tumbling window collects 49 events
   └─→ Groups by (customer_id, service, status_code)
   └─→ Emits: count=49, error_rate=0.02, latency_p99=250ms
   
5. Outputs fanned:
   └─→ Metrics → VictoriaMetrics (1-min window)
   └─→ Events (raw, sampled) → Kafka topic `events-sampled` → Spark job
   └─→ Anomalies → Kafka topic `anomalies` (if spike detected)
   
6. Spark job batches 5 min of sampled events, writes Parquet to S3
   └─→ Path: s3://bucket/acme/2025-07-17/14/30/events_0.parquet (1.2 MB)
   └─→ Compression: 45 events → 30KB (parquet snappy)
   
7. Dashboard queries VictoriaMetrics for 1-hour metric time-series; renders in < 1s

8. EOD job: archive raw events from S3 hot tier to Glacier (> 30 days old)
```

---

## 6. Scaling Strategies

### Horizontal Scaling

| Component | Scaling lever | Limits |
|---|---|---|
| **Ingestion API** | Add servers behind LB; geo-distribute PoPs | DNS propagation time; geo routing complexity |
| **Kafka brokers** | Increase broker count; add partitions | Per-broker bandwidth (~500Mbps typical) |
| **Flink taskmanagers** | Increase parallelism & task slots | State size (dedup window), JVM memory |
| **VictoriaMetrics** | Shard by customer/endpoint; multi-node cluster | Cardinality (high-cardinality tags = explosion) |
| **Spark jobs** | Increase executors/partitions | Scheduling queue, S3 rate limits |
| **Object storage** | N/A (cloud-managed); tune prefix key distribution | Request rate per prefix; use distributed keys |

### Vertical Scaling

- Kafka brokers: Increase memory for page cache (OS buffers)
- Flink taskmanagers: Increase heap for state store
- Query engines: More CPU cores for parallel scans

### Practical scaling roadmap

**Stage 1 (1M events/sec):**
- 3 Kafka brokers, 30 partitions
- 5-node Flink cluster (parallelism 30)
- Single VictoriaMetrics node + replication standby

**Stage 2 (5M events/sec):**
- 6 Kafka brokers, 100 partitions
- 10-node Flink cluster (parallelism 100)
- 3-node VictoriaMetrics cluster (sharded)
- Multiple Spark job executors (20+)

**Stage 3 (10M+ events/sec):**
- 9 Kafka brokers, 200 partitions
- 20-node Flink cluster (parallelism 200)
- Multi-region VictoriaMetrics (sharded + replicated)
- Spark on YARN or K8s for elasticity

---

## 7. Resilience & Fault Tolerance

### Failure Modes & Mitigation

| Failure | Impact | Mitigation |
|---|---|---|
| **Ingestion API down** | Events unable to submit | Geo-failover; client-side retry + backoff |
| **Kafka broker down** | Partition unavailable; producer errors | RF=3; rebalance; monitor under-replicated partitions |
| **Flink crash** | Processing stops; lag accumulates | Checkpointing (10s interval); restore from latest ckpt |
| **Metrics DB full** | Write failures; query slowdown | Compaction; downsampling; cardinality limits |
| **S3 throttle** | Writes fail; events lost if unretried | Exponential backoff; distribute by key prefix; queue retry job |
| **Time-sync drift** | Events out of order; metric gaps | NTP on all hosts; reject clock skew > 1 day |
| **Duplicate events** | Over-counting; incorrect metrics | Dedup state; 1-hour retention; idempotent sinks |

### Circuit Breaker & Graceful Degradation

```
Incoming load > threshold
  ├─→ Increase sampling rate (drop non-critical events)
  ├─→ Reduce aggregation window latency (trade accuracy for throughput)
  ├─→ Return HTTP 429 to non-critical clients
  └─→ Alert operators to scale up
```

### Backup & Restore

- **Metrics:** VictoriaMetrics snapshot every 6 hours → S3; restore to new cluster in < 15 min
- **Raw events:** Kafka retention + S3 parquet files = dual redundancy
- **Config:** Store sampling rules, schema in versioned Git; auto-sync to services every 1 min

---

## 8. Monitoring & Observability

### KPIs to track

| Metric | Target | Tool |
|---|---|---|
| **Ingestion latency (P99)** | < 100ms | Prometheus + alerting on SLO breach |
| **Event loss rate** | < 0.01% | Count ingested vs. stored; DLQ rate |
| **Deduplication ratio** | 0.5–2% (expected) | Monitor dedup_count in Flink metrics |
| **Processing lag** | < 5 min | Kafka consumer lag metric; Flink lag |
| **Query latency (P95)** | < 5s (dashboard) / < 60s (raw) | Query engine logs + sampling |
| **Availability** | > 99.9% | Uptime tracking per component |
| **Data freshness** | < 5s (metrics) / < 60s (raw) | E2E latency from endpoint to queryable |

### Instrumentation

- **Kafka:** Confluent monitoring; JMX metrics
- **Flink:** Prometheus sink; lag monitoring; checkpoint duration/size
- **VictoriaMetrics:** Built-in metrics endpoint; cardinality tracking
- **Object storage:** CloudWatch / Stackdriver; request rate, errors
- **Custom:** Application-level metrics (events_ingested, sampling_rate, anomalies_detected)

### Dashboards

1. **Ingestion health:** Request rate, latency, errors by endpoint
2. **Pipeline health:** Kafka lag, Flink processing time, checkpoint duration
3. **Storage health:** S3 write rate, parquet file sizes, query latency
4. **Alerts:** Recent anomalies, false positive rate, alert firing trend

### Alerting Rules

```yaml
- alert: HighIngestionLatency
  expr: histogram_quantile(0.99, ingestion_latency_ms) > 150
  for: 5m
  
- alert: KafkaLagTooHigh
  expr: kafka_consumer_lag > 1000000
  for: 10m
  
- alert: DeduplicationRateLow
  expr: dedup_rate < 0.001
  for: 1h
  
- alert: QueryLatencyHigh
  expr: histogram_quantile(0.95, query_latency_ms) > 10000
  for: 5m
```

---

## 9. Security & Compliance

### Authentication & Authorization

- **Ingestion:** API key (Bearer token) or mTLS certificate per customer
- **Queries:** RBAC; customer can only query own data (row-level filters in Trino)
- **Internal:** mTLS between services; network policies (NetworkPolicy in K8s)

### Data Protection

- **In transit:** TLS 1.3 (HTTPS, gRPC)
- **At rest:** Encryption at S3/Parquet level (KMS key per customer)
- **Deletion:** Automated cleanup after retention period; audit log of deletions

### Compliance

- **GDPR right-to-be-forgotten:** Purge customer data from all tiers (Kafka, metrics DB, S3) within 30 days
- **Audit logging:** All queries and config changes logged with user/timestamp
- **Data residency:** Route customer data to geo-specific clusters (EU data → EU storage)
- **Multi-tenancy:** Cryptographic isolation (prefix keys, customer_id in every query filter)

### Rate Limiting & DDoS Mitigation

- Per-API-key rate limits: 10K req/sec per key (configurable)
- Per-endpoint rate limits: 100K events/sec per endpoint
- Geo IP-based blocking for known bad actors
- Graceful degradation: drop lowest-priority events under extreme load

---

## 10. Deployment & Operations

### Infrastructure (Kubernetes-based example)

```yaml
# Ingestion API
Deployment: api-server (3 replicas, HPA up to 10)
Service: LoadBalancer (external)

# Kafka
StatefulSet: kafka-broker (3 replicas, persistent volumes)
Service: headless (for broker discovery)

# Flink
Deployment: jobmanager (1 replica)
StatefulSet: taskmanagers (5+ replicas, HPA)
ConfigMap: flink-config

# VictoriaMetrics
StatefulSet: victoria-metrics (2 replicas + replication)
PVC: 500GB storage

# Query engine (Trino)
StatefulSet: coordinator + workers (3+ workers, HPA)

# Spark jobs
CronJob: run daily archive job
Pod: adhoc queries
```

### CI/CD

- **Image builds:** Push Docker images to registry on each commit
- **Helm charts:** Version deployments; rollback if needed
- **Canary deployments:** Run new processing version on 10% of data; compare metrics before full rollout
- **Testing:** Integration tests (end-to-end event flow); load tests (5M events/sec); chaos tests (kill broker, taskmanager)

### Runbook Examples

**Alert: High ingestion latency**
1. Check API server CPU/memory; HPA status
2. Check Kafka broker load (network I/O)
3. Check GC logs on Flink taskmanagers
4. If persistent: manually scale up; investigate root cause

**Alert: Event loss detected**
1. Check DLQ size (validation failures?)
2. Check Flink checkpoint duration (if > 5s, increase parallelism)
3. Check S3 write errors (CloudWatch logs)
4. Reprocess from Kafka if needed (replay feature)

---

## 11. Cost Optimization

### Storage

- Parquet compression: ~50:1 ratio (1TB raw → 20GB stored)
- Tiering: Hot (S3) → Warm (Glacier) → Archive (Deep Archive)
- Retention pruning: Aggressive cleanup of old, low-value data

### Compute

- Right-size: Flink parallelism = partition count (no idle slots)
- Spot instances: Use for Spark jobs (87 interrupted, 13 interrupted → resume)
- Batch vs. streaming: Use batch Spark for non-urgent aggregations
- Reserved capacity: Buy annual commitment for baseline load

### Network

- Minimize data movement: Colocate processing with storage (same region/cloud)
- Compression: Always compress in transit (gzip ingestion, snappy Parquet)
- Multi-tier: Edge caching for common queries

### Total Cost Model

| Component | Volume | Unit cost | Monthly |
|---|---|---|---|
| **Storage** | 100TB raw (30-day retention) | $20/TB | $2,000 |
| **Kafka brokers** | 6 c5.2xlarge instances | $0.34/hr | $1,500 |
| **Flink cluster** | 10 m5.xlarge instances | $0.19/hr | $1,400 |
| **VictoriaMetrics** | 3 r5.2xlarge instances | $0.50/hr | $1,100 |
| **Trino query cluster** | 5 i3.2xlarge instances | $1.67/hr | $1,200 |
| **Data transfer (egress)** | 50TB/month | $0.09/GB | $4,500 |
| **Total** | — | — | **~$11.7k/month** |

**Per-event cost:** 11.7k / (5M events/sec * 30 days * 86400s) ≈ $0.0009 per event (**sub-$1/GB**)

---

## 12. Alternative Architectures

### When to use Kafka Streams instead of Flink
- Simpler topologies (< 3 stages of processing)
- Tighter coupling with Kafka preferred
- Smaller operational footprint acceptable

### When to use Pulsar instead of Kafka
- Multi-tenancy built-in (isolation at broker level)
- Replication factor changes desired post-publication
- Geo-replication across regions critical

### When to use ClickHouse for analytics
- Extremely high cardinality (billions of metric combinations)
- Ad-hoc SQL queries more important than time-series specifics
- ClickHouse typically 3–5× cheaper than Trino for OLAP

### Lambda architecture (hybrid batch + streaming)
- Use if batch reprocessing (e.g., late-arriving corrections) is common
- Add Spark batch layer that recomputes daily aggregates
- Merge streaming + batch results in serving layer

---

## 13. Key Implementation Checklist

- [ ] **Ingestion:** API server (Go/Java), gRPC support, rate limiting, DLQ
- [ ] **Buffering:** Kafka cluster (3+ brokers), topic design, retention policies
- [ ] **Processing:** Flink job (with checkpointing), dedup state, aggregation windows, sampling
- [ ] **Storage (Metrics):** VictoriaMetrics cluster (3 nodes), retention policies, dashboards
- [ ] **Storage (Raw):** S3 lifecycle rules, Parquet schema, partitioning, deduplication job
- [ ] **Query:** Trino federation setup, access control, materialized views
- [ ] **Monitoring:** Prometheus + Grafana, alerting rules, E2E latency tracking
- [ ] **Resilience:** Backup/restore procedures, chaos testing, runbook documentation
- [ ] **Security:** Encryption at rest/transit, RBAC, audit logging, compliance validation
- [ ] **Deployment:** Helm charts, CI/CD pipeline, canary deployments, rollback procedures

---

## 14. References & Further Reading

- **Streaming:** Apache Kafka documentation, Flink SQL cookbook
- **Time-series:** VictoriaMetrics architecture blog, InfluxDB best practices
- **Analytics:** Trino federation patterns, BigQuery cost optimization
- **Observability:** Prometheus alerting rules, SLO error budgets (SRE book)
- **Resilience:** Chaos engineering (Chaos Toolkit), distributed systems (Designing Data-Intensive Applications)

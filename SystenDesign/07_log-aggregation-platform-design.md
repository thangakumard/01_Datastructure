# Design a Log Aggregation / Search Platform

## 1. System Design

### Requirements

**Functional**
- Collect logs from thousands of hosts/services (structured + unstructured)
- Parse, enrich (add host/service/pod metadata), and index logs
- Full-text search + structured filtering (by service, level, time range, trace ID)
- Real-time tailing ("live tail") and historical search
- Alerting on log patterns (e.g., error rate spikes)
- Retention policies, tiered storage (hot/warm/cold)

**Non-functional**
- High write throughput: millions of log lines/sec at scale (think 10M+ events/sec for a large fleet)
- Low ingestion latency (log visible in search within seconds)
- Search latency: sub-second for recent/hot data, seconds for cold/archived
- Durability — no silent log loss (log pipelines are often used for compliance/audit)
- Horizontal scalability, multi-tenant isolation
- Cost efficiency — logs are huge in volume, storage cost dominates

### Back-of-envelope capacity estimate

- 10,000 hosts × 500 log lines/sec × 500 bytes/line ≈ 2.5 GB/sec raw ingestion
- ~200 TB/day raw before compression; 5-10x compression with columnar/inverted index storage → ~20-40 TB/day stored
- This single number drives almost every architectural decision below (why you need Kafka as a buffer, why storage must be tiered, why indexing must be async).

### High-Level Architecture

```
[Log Agents/Sidecars] → [Ingestion Gateway/LB] → [Buffer: Kafka/Kinesis]
        │                                                │
        │                                     ┌──────────┴───────────┐
        │                                 [Stream Processors]   [Cold Storage Sink]
        │                              (parse/enrich/route)     (S3 raw, for replay)
        │                                        │
        │                            ┌───────────┴────────────┐
        │                       [Indexing Tier]          [Metrics/Alerting]
        │                     (Lucene/inverted index          (derive counters,
        │                      shards, hot/warm/cold)          trigger alerts)
        │                            │
        │                     [Query/Search Service]
        │                            │
        └──────────────────→ [Query Router / API Gateway] → [UI / API clients]
```

### Component Deep Dive

**1. Log Agents (Fluentd / Fluent Bit / Filebeat / custom sidecar)**
- Tail files or read from stdout, batch, compress, retry with local disk buffering to survive network blips
- Backpressure-aware: if the ingestion tier is saturated, agents buffer locally rather than drop
- Add basic metadata at source: hostname, pod/container ID, timestamp (agent-side timestamping avoids clock-skew ordering issues later)

**2. Ingestion Gateway**
- Stateless HTTP/gRPC fleet behind a load balancer, does auth, rate limiting, basic validation, and writes to the buffer
- This is your natural horizontal-scaling point — scale gateway replicas independently of everything downstream

**3. Buffer / Log of Record — Kafka (or Kinesis/Pulsar)**
- This is the critical design decision: **never write directly from agents into the search index**. Kafka decouples ingestion rate from indexing rate, absorbs traffic spikes, and gives you replay capability (crucial for reprocessing after a schema/parser bug, or for testing).
- Partition by tenant/service so a noisy tenant doesn't create hot partitions elsewhere
- Retention on the Kafka topic itself (e.g., 24-72h) acts as a safety buffer independent of your final storage retention

**4. Stream Processing (Flink/Kafka Streams/Spark Streaming)**
- Parse (grok/regex or structured JSON), enrich (geo-IP, service metadata lookups via a side cache), extract structured fields for indexing, drop/redact PII per policy
- Route to different downstream sinks: indexing tier, cold storage (S3, for cheap long-term archive + reprocessing), and a metrics extraction path for alerting (e.g., count(level=ERROR) per service per minute → feeds an alerting engine without touching the full-text index)

**5. Indexing Tier (Elasticsearch/OpenSearch-style, or a custom Lucene-based service)**
- Inverted index for full-text terms + a columnar/doc-values structure for structured fields (fast filtering/aggregation on level, service, status code)
- **Sharding**: typically by time (daily/hourly index) + hash within tenant, so you can age out and delete whole shards cheaply instead of doing expensive per-document deletes
- **Hot/warm/cold tiers**: hot = SSD, fully indexed, recent (e.g., last 24-48h); warm = fewer replicas, cheaper disks; cold = index closed/archived to object storage, rehydrated on-demand for old queries
- Replication factor ~2-3 in hot tier for availability; warm/cold can drop to 1 replica + rely on object storage durability

**6. Query/Search Service**
- Query router scatter-gathers across relevant shards (only touches shards whose time range overlaps the query — critical optimization), merges/ranks results
- Supports both full-text (search terms) and structured (trace_id=X AND service=Y) queries
- Live tail is a separate path — essentially a filtered subscription directly off the stream processing layer (bypassing the index) so it's real-time rather than index-latency-bound

**7. Alerting**
- Runs continuously against the stream (not the index) for low-latency detection — e.g., sliding-window error-rate thresholds — because waiting for indexing round-trip is too slow for paging

### Key Trade-offs to Discuss

- **Push vs. pull ingestion**: push (agents send) is simpler and lower-latency; pull (server scrapes) gives better backpressure control but adds discovery complexity. Most log platforms use push with agent-side buffering.
- **Indexing everything vs. selective indexing**: full-text indexing every field is expensive; many systems only fully index a "message" field and treat other fields as structured/doc-values-only to save index size.
- **Consistency**: log search is generally fine with eventual consistency (seconds of lag) in exchange for throughput — this is very different from an OLTP system.
- **Kafka as buffer** is the single highest-leverage design choice — it turns a synchronous fan-out problem into decoupled async pipelines and gives you replay for free, which directly enables a lot of the testing strategies below.

---

## 2. E2E Cloud Distributed Testing Approach

For a system this decomposed, "E2E testing" isn't one thing — it's a layered strategy.

### A. Test Pyramid Adapted for Distributed Systems

| Layer | What it covers | Tooling |
|---|---|---|
| Unit | Parsers, grok patterns, enrichment logic | JUnit/pytest, table-driven tests |
| Component/integration | Single service against a real dependency (e.g., stream processor against a real Kafka topic) | Testcontainers (spin up Kafka, ES in Docker) |
| Contract | Schema compatibility between producer (agent) and consumer (indexer) | Schema registry compatibility checks, Pact-style contract tests |
| System/E2E | Full pipeline, log-in-to-search-out, on real cloud infra | Custom harness, described below |
| Chaos/resilience | Failure injection at every hop | Chaos Mesh/Gremlin/Litmus |
| Performance/load | Throughput, latency SLOs under load | k6/Locust/Gatling + custom Kafka producers |

### B. End-to-End Functional Testing (log-in → search-out)

- **Golden-path test**: inject a known, uniquely-tagged log line (e.g., a UUID marker) through a real agent into a real (ephemeral, per-test-run) cluster; poll the search API until the marker is searchable; assert on latency (ingestion-to-searchable SLA) and correctness (all fields parsed correctly).
- **Idempotency/exactly-once checks**: replay the same batch twice (simulating agent retry after a network blip) and assert no duplicate documents in the index — this is a very common real bug in these systems.
- **Ordering/deduplication under multi-agent write**: send interleaved logs from multiple simulated agents with the same trace_id, verify correct ordering by agent-side timestamp survives the pipeline.
- **Schema evolution test**: push a log with a new field/type, verify it doesn't break indexing (mapping conflict) for the whole shard.

### C. Environment Strategy for Cloud E2E

- **Ephemeral per-PR environments**: spin up a scoped-down but topologically real stack (small Kafka cluster, small ES cluster, real cloud LB) via Terraform/Pulumi + Kubernetes namespace per test run, run the E2E suite, tear down. This catches real cloud networking/IAM issues that Testcontainers-in-CI can't.
- **Shared staging environment**: longer-lived, closer to prod scale, used for chaos/load tests and nightly soak tests rather than per-PR (too expensive to spin up at scale per PR).
- **Data isolation**: tag all test-injected logs with a synthetic tenant ID so cleanup/assertions never touch real traffic in a shared staging cluster.

### D. Chaos / Resilience Testing

- **Broker failure**: kill a Kafka broker mid-ingestion (Chaos Mesh `PodChaos`), verify producers failover to remaining replicas and no logs are lost (check offsets/counts before/after).
- **Network partition**: partition stream processors from Kafka or from the index tier (`NetworkChaos` — latency/packet loss/partition), verify backpressure kicks in on agents rather than silent drops, and verify recovery/catch-up behavior once partition heals.
- **Index node loss**: kill an ES/OpenSearch data node holding primary shards, verify replica promotion and that in-flight queries degrade gracefully (partial results + clear indication) rather than erroring outright.
- **Disk pressure / slow disk**: inject I/O latency on a shard's underlying volume, verify the query router's timeout/circuit-breaker logic routes around the slow shard.
- **Clock skew injection**: skew a subset of agents' clocks, verify time-based sharding/queries still behave sanely (this is a very underrated failure mode in log systems).
- Automate these as scheduled chaos experiments in staging (GameDay-style) with automated pass/fail on SLO dashboards, not just manual runs.

### E. Load / Performance Testing

- **Ingestion throughput test**: ramp synthetic agents (k6/Locust with custom protocol, or a purpose-built Kafka producer harness) to target sustained rate (e.g., simulate 2.5 GB/sec) and beyond, find the breaking point and the component that breaks first (usually stream processing CPU or ES indexing threads).
- **Query latency under load**: run realistic query mix (recent-time-range full text, structured aggregations, live tail subscriptions) concurrently with ingestion load, assert p50/p95/p99 stay within SLA — query and ingestion contend for the same cluster resources, so isolated tests of each alone are misleading.
- **Backfill/replay load test**: since Kafka retains raw data, test replaying a day's worth of data into a fresh index to validate reprocessing throughput (relevant for schema migrations or disaster recovery).
- **Soak test**: run at 70-80% of max sustained throughput for 24-48h to catch slow leaks (heap growth, file descriptor leaks, segment merge backlog in Lucene) that short load tests miss.

### F. Correctness / Data-Quality Checks (continuous, not just pre-release)

- **Reconciliation job**: periodically compare count of documents indexed vs. count of messages produced to the Kafka topic (per partition/time window) — a canary for silent data loss.
- **Synthetic canary logs**: continuously inject known-pattern logs from every region and assert they're searchable within SLA — this doubles as a production health check, not just a test.
- **Cross-region tests** (if multi-region): verify a query issued in region B correctly federates/replicates data ingested in region A per the consistency model you've designed (async replication lag bounds, etc.)

### G. CI/CD Pipeline Shape

```
PR → unit + component tests (Testcontainers, fast)
   → merge → ephemeral cloud E2E (golden path + schema/idempotency tests)
   → nightly → full load test + chaos GameDay on staging
   → pre-release → soak test + cross-region correctness
   → production → synthetic canaries + reconciliation jobs running continuously
```

---

## Interview Framing

Testing a system like this is really testing the guarantees claimed in the design — durability (no loss under broker/node failure), latency SLA (ingestion-to-searchable), and correctness under concurrency/replay (idempotency, ordering). Structure the answer around "here's the guarantee, here's how I'd break it, here's how I'd automate proving it holds" rather than a generic tool list — that's the senior/staff signal.

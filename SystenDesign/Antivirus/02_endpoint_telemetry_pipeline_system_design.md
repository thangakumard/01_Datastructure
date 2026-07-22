# Endpoint Telemetry Pipeline — System Design Interview Guide
### Senior & Staff Engineer Level

---

## Table of Contents

1. [Problem Statement & Scope](#1-problem-statement--scope)
2. [Requirements](#2-requirements)
3. [Capacity Estimation](#3-capacity-estimation)
4. [High-Level Architecture](#4-high-level-architecture)
5. [Component 1: Agent / Edge Layer](#5-component-1-agent--edge-layer)
6. [Component 2: Ingestion Layer](#6-component-2-ingestion-layer)
7. [Component 3: Buffering & Routing (Kafka)](#7-component-3-buffering--routing-kafka)
8. [Component 4: Stream Processing](#8-component-4-stream-processing)
9. [Component 5: Cardinality Management](#9-component-5-cardinality-management)
10. [Component 6: Time-Series DB (Metrics Store)](#10-component-6-time-series-db-metrics-store)
11. [Component 7: Raw Event Storage](#11-component-7-raw-event-storage)
12. [Component 8: Query Layer](#12-component-8-query-layer)
13. [Component 9: Anomaly Detection](#13-component-9-anomaly-detection)
14. [Component 10: Config Service & Schema Registry](#14-component-10-config-service--schema-registry)
15. [CAP Theorem Positioning](#15-cap-theorem-positioning)
16. [Failure Modes & Mitigations](#16-failure-modes--mitigations)
17. [Scalability, Sharding & Multi-Region](#17-scalability-sharding--multi-region)
18. [Security & Compliance](#18-security--compliance)
19. [Cost Model](#19-cost-model)
20. [Alternative Architectures](#20-alternative-architectures)
21. [Senior vs Staff Answer Differentiators](#21-senior-vs-staff-answer-differentiators)
22. [Interview Time Allocation](#22-interview-time-allocation)
23. [Quick-Reference Cheatsheet](#23-quick-reference-cheatsheet)

---

## 1. Problem Statement & Scope

An **endpoint telemetry pipeline** ingests, processes, aggregates, and stores event data from millions of distributed client endpoints (agents, SDKs, browser extensions, mobile apps). It must handle high-volume, real-time streams while preserving data quality, supporting complex historical queries, and enabling fast incident response — all while the sources themselves are unreliable, intermittent, and only loosely time-synchronized.

### What the interviewer is really testing

- Can you design a system that treats **the edge as untrusted and unreliable**, not just the backend?
- Do you understand that **not all events are equal** — durability guarantees must be priority-aware, not uniform?
- Can you reconcile **streaming correctness** (late data, watermarks, exactly-once) rather than hand-waving it?
- Do you make **explicit CAP theorem decisions** per component, the way you would for any distributed store?

---

## 2. Requirements

### Functional Requirements

| Requirement | Notes |
|---|---|
| Event ingestion | Heterogeneous endpoints, varying SDK versions/platforms |
| Real-time aggregation | Sub-second-to-seconds latency for dashboards |
| Historical retention | Raw events, 30–90 days, for forensics/replay/compliance |
| Dimensionality | Arbitrary tags per event, no rigid schema — but cardinality must stay bounded |
| Sampling & filtering | Dynamic, **priority-aware**, per endpoint/event type |
| Anomaly detection | Baseline deviation detection with bounded false-positive rate |
| Multi-tenancy | Isolation at ingestion, processing, storage, and query |
| Query API | Time-series filtering, grouping, arithmetic, percentiles |
| Backpressure handling | Degrade gracefully, and degrade **the right things first** |

### Non-Functional Requirements

| Requirement | Target |
|---|---|
| Throughput | 10M+ events/sec peak; 1–5M steady state |
| Latency | P99 ingestion ≤100ms; metrics ≤5s; raw queryable ≤60s |
| Availability | 99.9%; tolerate machine, rack, and regional failure |
| Retention | 30–90 days raw; 2+ years aggregated |
| Durability | **Zero loss of P0 events** (errors, health, transactions); lossy sampling acceptable for high-volume/debug traces only |
| Cost | Sub-$1/GB storage compressed; efficient compute utilization |
| Operational complexity | Self-healing, auto-scaling, minimal manual intervention |

**The load-bearing requirement most designs get wrong:** durability is not uniform across the event space. "No loss of critical events" and "lossy sampling OK for high-volume traces" are two different SLAs living in the same pipeline. Every downstream decision — sampling, backpressure, dedup, storage tiering — has to know which class an event belongs to.

---

## 3. Capacity Estimation

```
Target: 5M events/sec steady state, 10M peak

Payload: ~1KB/event average (structured JSON, pre-compression)
  Steady:  5M × 1KB  = 5 GB/s ingest bandwidth
  Peak:    10M × 1KB = 10 GB/s

Kafka partitions (target ~10MB/s/partition):
  10 GB/s / 10MB/s ≈ 1,000 partitions at peak

Flink parallelism: ≥ partition count → 1,000 parallel subtasks at peak scale

Dedup state store (1hr window, ~200 bytes/key incl. overhead):
  5M events/sec × 3600s × 200B ≈ 3.6 TB state
  → must be partitioned across taskmanagers (RocksDB state backend), not single-node

Raw storage (Parquet, ~50:1 compression from 1KB JSON):
  5M/s × 1KB × 86400s ≈ 432 TB/day raw → ~8.6 TB/day compressed on disk

Metrics cardinality (worst case, unbounded tags):
  If each of 5M endpoints emits 20 unique tag combinations/min,
  that's 100M active series/min — this WILL blow up a time-series DB
  without cardinality limits (see Component 5).
```

**Key insight:** the bottleneck isn't raw ingestion throughput (that's a solved horizontal-scaling problem) — it's **state size in the streaming layer** (dedup windows) and **cardinality in the metrics store**. Both need explicit bounding strategies, not just "add more nodes."

---

## 4. High-Level Architecture

```
┌────────────────────────────────────────────────────────────────────┐
│  ENDPOINTS (Agents / SDKs / Browsers / Mobile)                     │
│  OS hooks → normalize → resource governor → RocksDB queue →         │
│  priority tagging → batch flush → retry w/ backoff                  │
└───────────────────────────────┬──────────────────────────────────┘
                                 │  Protobuf/gRPC+mTLS (agent) or
                                 │  HTTPS/JSON batched (lightweight SDKs)
                                 ▼
                    ┌─────────────────────────┐
                    │   Ingestion API          │
                    │   Auth · Validate · DLQ  │
                    │   Schema Registry check  │
                    └────────────┬─────────────┘
                                 ▼
                    ┌─────────────────────────┐
                    │   Kafka (telemetry-raw) │
                    │   Partition: endpoint_id│
                    └────────────┬─────────────┘
                                 ▼
              ┌──────────────────────────────────────┐
              │        Stream Processor (Flink)        │
              │  Parse → Dedup → Watermark → Priority   │
              │  Sampling → Cardinality Limiter →        │
              │  Windowed Aggregation → Anomaly Scoring  │
              └───────┬──────────────┬──────────────┬───┘
                      ▼              ▼              ▼
              ┌──────────────┐ ┌───────────┐ ┌──────────────┐
              │ Time-Series  │ │  Kafka:   │ │   Kafka:     │
              │ DB (metrics) │ │ events-   │ │  anomalies   │
              │ VictoriaMet. │ │ sampled   │ │              │
              └──────┬───────┘ └─────┬─────┘ └──────┬───────┘
                     ▼               ▼              ▼
              Dashboards      Spark → S3/Parquet   Alerting
              & Alerts        (raw event archive)  (PagerDuty)
                                     │
                              ┌──────┴──────┐
                              ▼             ▼
                       Query Engine   Cold Archive
                       (Trino)        (Glacier)

  Config Service (sampling rules, cardinality budgets) — pushed to
  Flink + Ingestion API on a bounded staleness window (see §14)
```

**Kafka** remains the durable spine between every stage — replay, decoupling, and back-pressure isolation all depend on it. What changes from a naive design: **every event carries a priority tag from the moment it leaves the device**, and every downstream stage (sampling, backpressure, dedup, storage tiering) makes decisions conditioned on that tag rather than treating the stream as homogeneous.

---

## 5. Component 1: Agent / Edge Layer

This is the layer most telemetry-pipeline designs skip — and the one that actually answers "network unreliability and endpoint intermittency" from the problem statement. It is also, quietly, the highest-risk component in the whole system: it's code you're deploying onto millions of devices you don't control, sitting between kernel-level data sources and the network. A well-designed backend can't compensate for an agent that crashes hosts or saturates a laptop's CPU.

### 5.1 Architecture

A production agent (Osquery-style, eBPF-based, or a proprietary EDR sensor) is a modular, multi-threaded pipeline **inside the endpoint itself** — collection is deliberately decoupled from transport so a network stall never blocks data capture:

```
┌────────────────────────────────────────────────────────────┐
│                        ENDPOINT HOST                        │
│                                                               │
│  OS Kernel / Subsystems (eBPF, Auditd, ETW, Endpoint         │
│  Security Framework, network drivers)                        │
│                          │                                    │
│                          ▼                                    │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                        AGENT                          │   │
│  │  [ Sensor / Collector Modules ]                        │   │
│  │   ├── Process Monitor                                 │   │
│  │   ├── Network Monitor                                 │   │
│  │   └── File System Watcher                              │   │
│  │                          │                              │   │
│  │                          ▼                              │   │
│  │  [ Event Normalizer → ECS/OCSF schema + priority tag ] │   │
│  │                          │                              │   │
│  │                          ▼                              │   │
│  │  [ Resource Governor (CPU/RAM check) ]                 │   │
│  │                          │                              │   │
│  │                          ▼                              │   │
│  │  [ Local Disk Queue — RocksDB, capped e.g. 500MB ]      │   │
│  │                          │                              │   │
│  │                          ▼                              │   │
│  │  [ Network Shipper — Protobuf / gRPC / mTLS ]           │   │
│  └──────────────────────────┬───────────────────────────┘   │
└─────────────────────────────┼───────────────────────────────┘
                               ▼   mTLS, HTTP/2
                    Ingestion Load Balancers (§6)
```

### 5.2 Hooking Into the OS

Agents don't poll for activity when they can avoid it — polling means either missed events between intervals or wasted CPU on unchanged state. They tap kernel-level hooks that emit events as they happen:

| OS | Hooking mechanism | Captures |
|---|---|---|
| Linux | eBPF, or Auditd where eBPF isn't available | `execve`, socket open/close, file read/write, privilege escalation |
| Windows | ETW (Event Tracing for Windows) + minifilter drivers | Process/thread creation, registry edits, handle creation, file modification |
| macOS | Endpoint Security Framework | Process execution, filesystem activity, volume mounts, dylib loading |

**Staff-level nuance on Osquery specifically:** the traditional model is *polling* — scheduled SQL-style queries (`SELECT * FROM processes;`) run on an interval. That's fine for inventory/compliance snapshots but wrong for real-time telemetry: a process that starts and exits between polls is invisible. Production deployments pair Osquery's query interface with **event-driven extensions** (eBPF/ETW-backed) so the event stream is push-based, and reserve polling for low-frequency state checks. If a candidate proposes pure polling for a real-time pipeline, that's a Senior-level gap.

### 5.3 Normalization at the Source

Raw OS hooks are noisy and platform-specific — a Staff answer doesn't ship raw `Event ID 4688` strings downstream, it normalizes at the agent into a common schema (ECS or OCSF) before anything leaves the host. This is also where the pipeline's `event_id` and priority tag get assigned, tying directly into the dedup and sampling design in §8:

```json
{
  "event_id": "hash(endpoint_id + boot_id + monotonic_seq)",
  "event_type": "process_start",
  "priority": "P1",
  "client_timestamp": "2026-07-21T17:28:00.123Z",
  "endpoint_id": "agent-usr-9921",
  "process": {
    "pid": 4812, "name": "cmd.exe",
    "path": "C:\\Windows\\System32\\cmd.exe",
    "ppid": 1024, "parent_name": "explorer.exe"
  }
}
```

`event_id` is deliberately **not** a fresh UUID per event — on a device that can crash and replay a local queue, `boot_id + monotonic_seq` gives you a collision-resistant ID that survives restarts without duplicating, the same problem unique-ID-generation designs solve with `machine_id + sequence`. Normalizing to ECS/OCSF at the edge, rather than downstream in the stream processor, also means schema compatibility (§14) is enforced closer to the source — malformed or drifted events get caught before they consume any network or Kafka capacity.

### 5.4 Resource Guardrails (the constraint most designs ignore entirely)

Deploying to millions of endpoints you don't operationally control means the agent's failure mode isn't "the pipeline loses data" — it's "a customer's laptop battery drains" or "a database server's CPU gets stolen by your monitoring agent." That reframes the sampling/backpressure conversation from §8: **the first backpressure signal often originates on the device itself**, not in Kafka consumer lag.

```
Resource Governor loop (runs continuously on-device):
  if cpu_usage > 2–5% of one core OR rss_memory > 150–200MB:
      → throttle collection (increase local sampling of P2)
      → drop non-critical (P2) events at the source
      → log a throttle warning event (itself P1 — you want to know
        when and where this is happening, fleet-wide)
  else:
      → stream normally

Hard caps, not soft targets: these are enforced limits, not
aspirational ones — a governor that can be exceeded under load is
exactly the failure mode ("agent drops the host OS") this exists
to prevent.
```

**Local buffering:** telemetry queues in an embedded on-disk store (RocksDB is the common choice — same reasoning as Flink's state backend in §8: fast sequential writes, bounded by a hard size cap) rather than an in-memory-only buffer, so an app/agent crash doesn't lose the queue. Priority still governs eviction order once the cap is hit: P2 drops before P1, P0 is protected until the cap is truly exhausted. On reconnect, the agent drains the queue sequentially — feeding directly into the thundering-herd concern below.

**Crash resilience:** the agent runs as a supervised daemon (`systemd` on Linux, Windows SCM) so a crash restarts the process automatically rather than taking the host down or silently going dark. A silently-dead agent is worse than a noisy one — it should emit a last-gasp heartbeat-missed signal the backend can alert on (an agent that stops reporting looks identical to "endpoint is offline" unless you distinguish the two).

### 5.5 Serialization & Transport

- **Protobuf over gRPC (HTTP/2)** rather than JSON over HTTP/1.1 for the high-throughput path — binary encoding cuts bandwidth ~60–80% versus JSON and meaningfully reduces on-device CPU spent on serialization, which matters directly against the resource caps in §5.4. (Lower-throughput or simpler SDKs can still fall back to batched HTTPS/JSON — see §6 — but the primary agent path should be gRPC.)
- **mTLS**: every agent carries a unique client certificate; the server and agent authenticate each other during the handshake. This isn't just transport security — it's what prevents a rogue or compromised endpoint from injecting fake telemetry into the pipeline, which matters a lot for a security-relevant telemetry stream (EDR-style data) versus a purely observability-oriented one.
- **Source-side filtering/dedup**: on very high-throughput nodes (a database server generating millions of file-change events/sec), the agent applies its own local filtering and dedup *before* anything hits the wire — pushing the §8.2 dedup concept one hop earlier for the highest-volume sources, rather than relying entirely on the backend to absorb the full unfiltered rate.

### Flush & Retry Policy

```
Flush triggers:
  - Batch size threshold (e.g., 100 events or 100KB)
  - Time threshold (e.g., every 10s)
  - Lifecycle events (backgrounding, graceful shutdown)
  - Immediate flush for P0 events (bypass batching delay)

Retry policy:
  - Exponential backoff with jitter (avoid thundering herd on
    network recovery — see below)
  - Bounded retry queue; P2 dropped locally before P0 if the
    buffer is full and the network stays down
```

### Staff-Level Gotcha: Thundering Herd on Reconnect

If a regional network blip takes down connectivity for 500K endpoints simultaneously, naive retry-on-reconnect means 500K agents hammering the ingestion API at once when connectivity returns — each also trying to drain a backlog of locally-queued events. Mitigation: jittered backoff **and** a server-side admission control layer that can shed load by returning `429` with a `Retry-After` header the agent respects — this is why the ingestion API's circuit breaker (§6) has to be a first-class part of the agent's contract, not just a backend implementation detail.

### Staff-Level Gotcha: The Resource Governor Is Itself a Backpressure Signal

Most designs treat backpressure as something that originates in the backend (Kafka lag, Flink checkpoint duration) and propagates outward to sampling config. But the resource governor in §5.4 means individual devices can independently decide to drop P2 events under local CPU/memory pressure — with no coordination from the Config Service at all. That's *correct* behavior (a struggling laptop shouldn't wait for a round-trip to a config service before protecting itself), but it means your fleet-wide sampling-rate metrics need to account for **both** centrally-configured sampling (§8.4) and this decentralized, per-device throttling — otherwise "why is our P2 volume down 15% today" becomes a hard debugging question with two independent, uncorrelated causes.

**Time to spend in interview:** ~4 minutes. Most candidates start at the API gateway; explicitly starting here is a strong Staff signal.

---

## 6. Component 2: Ingestion Layer

**Purpose:** Accept events from endpoints, authenticate, validate against schema, and route with minimal latency.

### Components

- **API Gateway** — geo-distributed load balancer; HTTPS for standard SDKs, gRPC for high-throughput agents; `/v1/events` (batched, up to 1000/request) and `/v1/event` (single); returns `202 Accepted` immediately (async).
- **Request Validation**
  - Schema check against the **Schema Registry** (§14) — reject or quarantine on incompatible schema, don't silently accept malformed structure ("lenient mode" needs a defined boundary, not an open door)
  - Auth: API key or mTLS per customer; per-device identity/attestation for agent-based SDKs (prevents endpoint spoofing)
  - Rate limiting: per API key, per endpoint, global
- **DLQ** — invalid events routed to `telemetry-dlq` (7-day retention), surfaced to error tracking, **with sampling of DLQ contents surfaced to SDK owners** so schema drift gets fixed instead of silently accumulating.

### Failure Modes

| Failure | Response |
|---|---|
| Backend overloaded | Circuit breaker → `429` + `Retry-After`; SDK backs off (see §5) |
| Validation failure | DLQ + metric; does not block the rest of the batch |
| Auth failure | `401`/`403`; repeated failures → temporary endpoint block |
| Schema incompatible | Quarantine (not silent drop) — routed to a dead-schema topic for investigation |

---

## 7. Component 3: Buffering & Routing (Kafka)

### Topics

| Topic | Partition key | Retention | Purpose |
|---|---|---|---|
| `telemetry-raw` | `endpoint_id` | 48h, RF=3 | Durable buffer; preserves per-endpoint ordering |
| `telemetry-dlq` | — | 7 days | Validation failures |
| `dead-schema` | — | 30 days | Schema-incompatible payloads for SDK-team triage |
| `events-sampled` | `customer_id` | 3 days | Post-processing durable writes to object storage |
| `anomalies` | `customer_id` | 7 days | Feeds alerting |

### Why Partition by `endpoint_id`?

Same reasoning as domain-routing in a crawler frontier: **ordering guarantees are local, not global.** Partitioning by `endpoint_id` guarantees per-device event order is preserved through the pipeline (critical for correct dedup and for causally-ordered event replay), at the cost of no global ordering guarantee across endpoints — which you don't need.

**Tradeoff:** a small number of extremely high-volume endpoints (e.g., a misbehaving agent) can create hot partitions. Mitigation: detect and split by `hash(endpoint_id + event_type) % k` sub-partitioning once a partition's throughput crosses a threshold, same pattern as splitting a hot domain queue in a crawler.

**Alternative:** managed streaming (Kinesis, Event Hubs) trades operational control for simplicity — reasonable if the team doesn't want to own Kafka operations, but you lose fine control over partition-level backpressure signals used in §8's degradation ladder.

---

## 8. Component 4: Stream Processing

**Framework:** Apache Flink (stateful operators, exactly-once checkpointing, and native watermark support make it the stronger choice over Kafka Streams once you need windowed correctness under out-of-order arrival — see below).

### 8.1 Parsing & Normalization

Deserialize, validate against Schema Registry, extract standard fields, tag with `ingestion_timestamp` (kept **alongside**, not instead of, the client's `event_timestamp` — you need both to reconcile skew and detect intermittency patterns per endpoint).

### 8.2 Deduplication — Single Canonical Layer

Dedup happens **exactly once**, here, and every downstream sink trusts this layer rather than re-implementing it:

```
Key: (customer_id, endpoint_id, event_id) — timestamp is not part of
     the key; event_id is already collision-resistant per §5.

State: RocksDB-backed keyed state, 1-hour TTL window
       (bounds the 3.6TB estimate in §3 — TTL eviction keeps it finite)

On duplicate: drop, emit dedup_count metric, do NOT re-forward
On new: mark seen, forward

Downstream sinks (metrics DB, events-sampled, S3) are NOT expected
to dedup again — they trust the Flink output. This removes the
three-different-dedup-strategies problem: one source of truth for
"has this event been counted."
```

**Why not query-time dedup as a fallback?** It's tempting ("dedup at query time if storage dedup is skipped") but it silently produces different answers depending on which query path you take — a real-time dashboard reading from VictoriaMetrics and a forensic query reading from S3 would disagree if dedup logic diverges. One canonical layer, trusted everywhere, is the Staff-level answer.

### 8.3 Watermarks & Late Data (the gap most designs skip)

Endpoints are explicitly intermittent — a mobile device buffering locally for 20 minutes and then reconnecting is expected behavior, not an edge case.

```
Watermark strategy: bounded out-of-orderness on event_timestamp
  watermark = max(event_timestamp seen) - allowed_lateness

allowed_lateness: tuned per event priority
  P0 (errors/health): 15 min  — worth waiting for correctness
  P1 (business metrics): 5 min
  P2 (debug/traces): 1 min — not worth holding windows open

On-time events: aggregated into the 1-min tumbling window normally.

Late events (arrive after watermark passes):
  Option A — emit a correction: re-open and re-emit an updated
             aggregate for the affected window (requires the
             time-series DB to support upsert/overwrite, which
             VictoriaMetrics does via re-ingestion at the same
             timestamp)
  Option B — route to a "late-arrivals" stream, reconciled by a
             daily batch job (Lambda-architecture pattern, §20) —
             cheaper, but dashboards are eventually-consistent for
             up to a day on the affected metric

Recommendation: Option A for P0 (correctness matters more than
compute cost at that volume), Option B for P1/P2.
```

Without an explicit watermark policy, a 1-minute tumbling window over intermittent mobile data silently either (a) closes too early and undercounts, or (b) stays open indefinitely waiting for stragglers and blows up state size. Naming this tradeoff explicitly is one of the highest-value things you can do in this interview.

### 8.4 Priority-Aware Sampling & Filtering

This directly fixes the contradiction between "no loss of critical events" and uniform sampling:

```
Sampling decision = f(priority, current_system_load)

P0: never sampled. Full pass-through regardless of load.
P1: sampled only once sustained backpressure crosses threshold 1
    (e.g., consumer lag > 30s sustained for 2 min)
P2: sampled proactively at a baseline rate (e.g., 10%) always,
    increased further under any backpressure signal

Config source: pulled from Config Service (§14), refreshed every
60s, with a safe local default if the config service is unreachable
(fail toward MORE sampling of P2, never toward dropping P0).
```

### 8.5 Aggregation Windows

1-minute tumbling windows (extended per §8.3 lateness rules), grouped by `(customer_id, service, endpoint, status_code)`, computing count / error rate / latency percentiles (p50/p95/p99).

### 8.6 Output Streams

`metrics-1m-raw` → VictoriaMetrics · `events-sampled` → S3 archive · `anomalies` → alerting.

### Failure Handling

Checkpoints every 10s (RocksDB incremental checkpointing at this state size — full checkpoints would be too slow); restore from latest on restart. Because dedup state and watermark position are both part of checkpointed state, a restart doesn't reintroduce duplicates or replay already-closed windows — this is what makes the pipeline **effectively-once** end to end (at-least-once delivery from Kafka + idempotent, checkpointed dedup state).

---

## 9. Component 5: Cardinality Management

Named as a risk in most designs, rarely solved. "Arbitrary tags without schema enforcement" is in direct tension with time-series database cardinality limits — this needs a concrete mechanism, not a warning.

```
Per-customer cardinality budget (e.g., 1M active series):
  Tracked via a probabilistic cardinality estimator (HyperLogLog)
  per customer, updated as new tag combinations are seen.

Enforcement, in order of preference:
  1. Tag allow-listing — customers declare which tags are safe to
     index; unlisted tags are stored in the raw event (S3) but
     NOT promoted to time-series labels.
  2. Automatic high-cardinality tag detection — if a tag's distinct
     value count crosses a threshold (e.g., a tag that's
     accidentally set to a per-request UUID), auto-demote it out
     of the metrics path and alert the customer.
  3. Bucketing — for tags that are numeric but high-cardinality
     (e.g., latency_ms as a label instead of a field), force into
     histogram buckets rather than raw label values.

This is the same instinct as Bloom filter fill-ratio monitoring in
a crawler dedup layer: don't just note that a data structure can
saturate — instrument it and act before it does.
```

---

## 10. Component 6: Time-Series DB (Metrics Store)

**Choice:** VictoriaMetrics (or InfluxDB) — Prometheus-compatible query surface, good compression, horizontal sharding.

```
Sharding: by customer_id (isolation) or hash(endpoint_id) (even load)
Replication factor: ≥2

Retention tiers:
  1-min raw    → 30 days
  5-min rollup → 90 days
  1-hr rollup  → 1 year
  → archived beyond that

Compression: ~10:1 typical
```

Cardinality limits from §9 are enforced **before** data reaches this layer — the store itself should never be the last line of defense against an cardinality explosion, only a backstop with hard limits that reject writes (loudly, with alerting) rather than degrading silently.

---

## 11. Component 7: Raw Event Storage

**Medium:** S3/GCS/Blob, Parquet format, Snappy/Zstd encoding.

```
s3://telemetry-bucket/{customer_id}/{date}/{hour}/{minute}/{partition}.parquet
```

Partitioned by time (retention/cleanup) and customer (isolation, access control). Written via a Spark job consuming the deduplicated `events-sampled` topic — **no re-dedup here**, trusting Flink's canonical dedup layer (§8.2). Retention: 30 days hot → 30–90 days warm → Glacier/Deep Archive beyond that for compliance hold.

---

## 12. Component 8: Query Layer

**Engine:** Trino (federated over the time-series DB and S3) or BigQuery.

```sql
SELECT endpoint_id, COUNT(*) AS error_count,
       COUNT(DISTINCT user_id) AS affected_users
FROM telemetry.raw_events
WHERE timestamp >= NOW() - INTERVAL 1 HOUR
  AND customer_id = 'acme' AND event_type = 'error'
GROUP BY endpoint_id ORDER BY error_count DESC LIMIT 10;
```

Row-level customer isolation, partition/column pruning, query timeouts (5 min), per-customer resource quotas. **Consistency caveat worth stating explicitly:** a federated query spanning both the time-series store and S3 is not point-in-time consistent across the two sources — the metrics store reflects the last flush, S3 reflects the last Spark batch. For most dashboard/investigation use cases this is fine; call it out anyway, it's the kind of caveat a Staff engineer surfaces before being asked.

---

## 13. Component 9: Anomaly Detection

**Approach:** rolling 7-day baseline (mean/std per metric), z-score threshold (3σ ≈ 0.1% false-positive rate), seasonal adjustment (day-of-week/time-of-day via STL decomposition), sustained-anomaly requirement (N consecutive windows) before firing.

```
Output: anomaly score (0–1) + baseline/current/z-score metadata
        → Kafka `anomalies` → alerting (PagerDuty/Slack)

False-positive control: suppress during known maintenance windows;
require sustained deviation, not a single spike; target <5% FP rate.
```

**Staff-level extension:** correlate anomalies across related metrics before alerting (a latency spike on `service=api` and an error-rate spike on `service=db` in the same window are probably one incident, not two pages).

---

## 14. Component 10: Config Service & Schema Registry

Two supporting services referenced throughout, both under-specified in most designs — worth naming explicitly.

### Config Service (sampling rules, cardinality budgets)

```
Model: push-based propagation to Flink + Ingestion API, polling
       every 60s as a fallback if push fails.
Storage: versioned config in a strongly-consistent store (etcd/
         ZooKeeper — this is a CP component; see §15) with Git as
         the source of truth for auditability.
Staleness bound: 60s max — bounded, and documented, because every
         consumer of this config needs to know how stale a
         "safe default" might be.
Fail-safe default: if unreachable, fail toward MORE sampling of
         P2 and normal handling of P0/P1 — never fail toward
         silently dropping critical events.
```

### Schema Registry

```
Format: Avro or Protobuf with backward/forward compatibility
        enforcement (Confluent Schema Registry or equivalent).
Purpose: replaces ad hoc "lenient mode" parsing with an actual
        compatibility contract — new SDK versions can add optional
        fields without breaking old consumers, and the registry
        rejects genuinely incompatible schema changes at ingestion
        time rather than downstream in Flink.
```

---

## 15. CAP Theorem Positioning

| Component | CAP Choice | Reasoning |
|---|---|---|
| Kafka (`telemetry-raw`) | **AP** (tunable via `acks`) | Producers keep writing under partition; `acks=all`+RF=3 gives strong durability without blocking on full consistency |
| Flink dedup/watermark state | **CP**-leaning | Checkpointed state must be internally consistent — a split-brain here double-counts or misses events; correctness > availability for this specific state |
| VictoriaMetrics | **AP** | Stale dashboard reads are acceptable; never block ingestion waiting for read consistency |
| S3 raw store | **AP** | Append-only, writes never conflict |
| Trino query layer | **Mixed, per-source** | No cross-source transactional consistency; document this rather than imply it |
| Config Service (etcd/ZK) | **CP** | Sampling/cardinality rules must be consistent across all Flink taskmanagers — a stale/conflicting config causes uneven sampling decisions across partitions |
| Schema Registry | **CP** | Compatibility decisions must be globally agreed; a registry split-brain could let two taskmanagers accept incompatible schemas simultaneously |

---

## 16. Failure Modes & Mitigations

| Failure | Symptom | Mitigation |
|---|---|---|
| Ingestion API down | Clients can't submit | Geo-failover PoPs; client-side buffering absorbs the outage (§5) |
| Kafka broker down | Partition unavailable | RF=3; rebalance; monitor under-replicated partitions |
| Flink crash | Processing stalls, lag grows | Checkpointing (10s); restore from latest; dedup/watermark state recovers with it |
| Dedup state explosion | OOM on taskmanagers | TTL-bounded state (1hr); state partitioned by key, not centralized |
| Cardinality explosion | Metrics DB write failures/slowdown | HLL-based budget enforcement + auto-demotion (§9) before writes reach the store |
| Late/out-of-order events | Undercounted windows or unbounded state growth | Explicit watermark + allowed-lateness policy (§8.3), priority-tiered |
| Thundering herd on reconnect | Ingestion API overload after regional network recovery | Jittered client backoff + server-side `429`/`Retry-After` admission control |
| Duplicate events | Over-counted metrics | Single canonical dedup layer (§8.2) — no re-dedup downstream |
| Schema drift from new SDK version | Parsing failures, DLQ growth | Schema Registry compatibility enforcement, quarantine not silent-accept |
| S3 throttling | Writes fail | Exponential backoff; distribute by key prefix; retry queue |
| Clock skew | Ordering/window errors | Track `client_timestamp` and `ingestion_timestamp` separately; reject skew > 1 day; watermark logic uses event time |
| Config Service unreachable | Sampling/cardinality rules stale | Bounded staleness (60s) + fail-safe defaults biased toward not dropping P0 |

---

## 17. Scalability, Sharding & Multi-Region

### Horizontal Scaling (as throughput grows)

| Component | Lever | Constraint |
|---|---|---|
| Ingestion API | Add nodes behind LB; geo-PoPs | DNS propagation, geo-routing complexity |
| Kafka | More brokers/partitions | ~10MB/s/partition target; per-broker bandwidth ceiling |
| Flink | Parallelism ≥ partition count | Dedup state size, JVM heap |
| VictoriaMetrics | Shard by customer/endpoint hash | Cardinality (§9), not raw throughput, is the real ceiling |
| S3 | Cloud-managed | Request rate per prefix — distribute keys |

### Multi-Region Topology (missing from most first-pass designs)

```
Regional ingestion PoPs → regional Kafka clusters → regional Flink
  - Keeps raw PII-bearing event data within its region of origin
    (serves data-residency requirements directly, not as an
    afterthought bolted onto compliance)
  - Only aggregated, already-anonymized metrics cross regions,
    into a global read-replica view for cross-region dashboards

Cross-region replication: MirrorMaker 2 (or equivalent) for the
  aggregated-metrics topic only — not for raw event topics.

Failure isolation: a regional outage degrades that region's
  ingestion/processing independently; it does not cascade globally,
  because there's no cross-region synchronous dependency in the
  hot path.
```

### Practical Scaling Roadmap

| Stage | Throughput | Kafka | Flink | Metrics DB |
|---|---|---|---|---|
| 1 | 1M/s | 3 brokers, 30 partitions | 5 nodes, parallelism 30 | Single node + standby |
| 2 | 5M/s | 6 brokers, 100 partitions | 10 nodes, parallelism 100 | 3-node sharded cluster |
| 3 | 10M+/s | 9 brokers, 200 partitions, multi-region | 20 nodes, parallelism 200 | Multi-region, sharded + replicated |

---

## 18. Security & Compliance

- **Auth:** API key or mTLS per customer at ingestion; per-device identity for agent-based SDKs; internal service mTLS.
- **In transit:** TLS 1.3. **At rest:** KMS encryption, per-customer keys.
- **GDPR right-to-be-forgotten:** purge across Kafka, metrics DB, and S3 within 30 days — note that Kafka's topic retention makes "purge on demand" nontrivial; a tombstone/compaction strategy or a per-customer encryption key you can simply discard (crypto-shredding) is the practical answer for data still inside the retention window.
- **Data residency:** solved structurally by the regional topology in §17, not just by a routing rule layered on top.
- **Rate limiting / DDoS:** per-key and per-endpoint limits; geo-IP blocking; graceful degradation follows the priority ladder from §8.4, not a flat drop.

---

## 19. Cost Model (corrected)

| Component | Volume | Unit cost | Monthly |
|---|---|---|---|
| Storage | 100TB raw, 30-day retention | $20/TB | $2,000 |
| Kafka brokers | 6× c5.2xlarge | $0.34/hr | $1,500 |
| Flink cluster | 10× m5.xlarge | $0.19/hr | $1,400 |
| VictoriaMetrics | 3× r5.2xlarge | $0.50/hr | $1,100 |
| Trino cluster | 5× i3.2xlarge | $1.67/hr | $1,200 |
| Data transfer (egress) | 50TB/month | $0.09/GB | $4,500 |
| **Total** | | | **~$11.7k/month** |

**Per-event cost, corrected:**
```
5M events/sec × 86,400s/day × 30 days ≈ 1.296 × 10^13 events/month

$11,700 / 1.296×10^13 events ≈ $9.0 × 10^-10 per event
                              ≈ $0.0009 per MILLION events
                              (the original doc mislabeled this as
                               "per event" — off by 10^6)
```
Stated another way: **$0.90 per billion events**, comfortably inside the sub-$1/GB storage target once you account for ~50:1 Parquet compression on the raw volume.

---

## 20. Alternative Architectures

- **Kafka Streams over Flink:** simpler topologies (<3 processing stages), tighter Kafka coupling, smaller operational footprint — but you lose Flink's more mature watermark/lateness handling, which matters given §8.3.
- **Pulsar over Kafka:** built-in multi-tenancy isolation at the broker level, easier post-publication replication-factor changes, native geo-replication — worth it if multi-region (§17) is a day-one requirement rather than a later addition.
- **ClickHouse over Trino:** better for extremely high-cardinality ad hoc SQL analytics; typically 3–5× cheaper than Trino for OLAP-style queries, at the cost of weaker federation across heterogeneous sources.
- **Lambda architecture (batch + streaming):** the direct answer to §8.3's late-data problem for P1/P2 events — a daily Spark batch job recomputes corrected aggregates, merged with the streaming layer's near-real-time (but occasionally-approximate) view.

---

## 21. Senior vs Staff Answer Differentiators

### Senior-level expectations

- Correctly identify all major components (ingestion, buffering, stream processing, storage, query)
- Explain Kafka's role as a durable buffer and describe basic dedup by event ID
- Mention sampling and backpressure as general concepts
- Handle obvious failure modes (broker down, consumer crash)
- Provide basic capacity math

### Staff-level expectations (additional)

| Topic | What Staff-level adds |
|---|---|
| Agent/edge layer | OS-level hooking (eBPF/ETW/Endpoint Security) over polling; on-device resource governor as a *second, decentralized* backpressure source; RocksDB-backed local queue; mTLS + Protobuf/gRPC transport; crash-supervised daemon with heartbeat-missed alerting |
| Durability | Explicit priority tiers (P0/P1/P2) threaded through sampling, backpressure, and storage — not uniform treatment |
| Deduplication | One canonical dedup layer with a stated reason downstream sinks don't re-implement it; collision-resistant `event_id` design at the source |
| Streaming correctness | Explicit watermark strategy, allowed lateness tuned per priority, a stated policy for late-arriving corrections |
| Cardinality | Concrete enforcement mechanism (HLL budgets, allow-listing, auto-demotion) — not just naming the risk |
| CAP theorem | Explicit AP vs CP decision for every component, including supporting services (config, schema registry) |
| Multi-region | Data-residency and failure-isolation solved structurally in the topology, not bolted on in the compliance section |
| Failure modes | Proactively enumerate thundering herd, cardinality explosion, dedup state OOM, config-service staleness |
| Cost math | Actually re-derive the per-unit cost rather than trust a pasted number |

### The single most important Staff differentiator here

**Treating "no loss of critical events" as a constraint that has to survive contact with every other design decision** — sampling, backpressure, dedup, storage tiering, even the client-side buffer eviction policy. A Senior answer designs the pipeline and then bolts sampling on top. A Staff answer designs sampling, backpressure, and durability together, from the same priority model, from the start.

---

## 22. Interview Time Allocation

For a 45-minute system design session:

| Phase | Time | Focus |
|---|---|---|
| Requirements & scope | 5 min | Surface the priority-tiered durability requirement explicitly |
| Capacity estimation | 5 min | Throughput, dedup state size, cardinality risk |
| High-level architecture | 5 min | Draw all components; name Kafka as the spine |
| Edge/SDK + Ingestion (if time) | 4 min | Local buffering, priority tagging, backoff |
| Stream Processing (deep dive) | 12 min | Dedup layer, watermarks/lateness, priority sampling |
| Cardinality & Storage | 6 min | HLL budgets, tiered storage, retention |
| CAP positioning | 3 min | Table, component by component |
| Failure modes | 5 min | Thundering herd, cardinality explosion, state OOM |

**What to cut if short on time:** query-layer SQL detail, cost-model derivation. **Never cut:** the priority-tiered durability story, watermark/lateness handling, or the canonical dedup layer — these are where Staff signal concentrates.

---

## 23. Quick-Reference Cheatsheet

```
KEY DECISIONS
─────────────
Dedup:          ONE canonical layer, in Flink, keyed on
                (customer_id, endpoint_id, event_id), 1hr TTL state
Watermarks:     bounded out-of-orderness, allowed_lateness tuned
                by priority (P0: 15min, P1: 5min, P2: 1min)
Sampling:       priority-aware — P0 never sampled, P1 sampled only
                under sustained backpressure, P2 sampled baseline
Cardinality:    HLL budget per customer + tag allow-listing +
                auto-demotion of runaway tags
Partitioning:   by endpoint_id (preserves per-device order,
                enables local politeness/dedup state)

KEY NUMBERS
───────────
5M events/sec steady, 10M peak
~1,000 Kafka partitions at peak
~3.6TB dedup state (bounded by 1hr TTL)
432TB/day raw → ~8.6TB/day compressed (50:1 Parquet)
$11.7k/month → ~$0.0009 per MILLION events (not per event)

CAP DECISIONS
─────────────
AP:  Kafka, VictoriaMetrics, S3
CP:  Flink dedup/watermark state, Config Service, Schema Registry
Mixed: Trino (no cross-source transactional consistency)

STAFF-LEVEL SIGNALS TO HIT
───────────────────────────
✓ Priority tiers (P0/P1/P2) threaded through sampling AND backpressure
✓ Single canonical dedup layer, not three divergent strategies
✓ Explicit watermark/lateness policy for intermittent endpoints
✓ Cardinality budget enforcement, not just risk-naming
✓ Event-driven OS hooks (eBPF/ETW/Endpoint Security), not polling
✓ On-device resource governor as a second, decentralized backpressure source
✓ RocksDB-backed local queue, priority-aware eviction, jittered backoff
✓ mTLS + Protobuf/gRPC transport for the high-throughput agent path
✓ Thundering-herd mitigation on reconnect
✓ CAP table including supporting services (config, schema registry)
✓ Multi-region topology that solves data residency structurally
✓ Re-derived (not pasted) cost math
```

---

*Guide compiled for Senior & Staff-level system design interview preparation.*
*Topics: Endpoint Telemetry, Stream Processing, Watermarks, Priority-Aware Sampling, Cardinality Management, CAP Theorem.*

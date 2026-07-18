# Real-Time Threat Detection Platform — System Design

## 1. Requirements

### Functional
- Ingest security events (network flows, auth logs, endpoint telemetry, cloud audit logs) from many sources
- Detect threats in near-real-time using rules (signatures) and behavioral/ML models
- Alert on matches with enough context for a human analyst to triage
- Support retroactive/forensic querying (e.g. "show me everything this IP touched in the last 30 days")

### Non-functional
- High throughput, bursty (DDoS, scan storms cause spikes)
- Low detection latency (seconds, not minutes) for critical threats
- High availability — a gap in detection is a security gap
- Tunable precision/recall — false positives burn analyst time; false negatives are breaches
- Data retention for compliance (often 90 days hot, 1+ year cold)

### Scale estimate
Mid-size enterprise / MSSP scale: 500K–2M events/sec peak, ~2–5 KB/event → roughly 5–10 GB/s ingest at peak, tens of PB/year retained.

---

## 2. High-Level Architecture

```
                    ┌───────────────────┐
                    │   Event sources    │
                    │ Logs, flows,       │
                    │ telemetry          │
                    └─────────┬──────────┘
                              │
                    ┌─────────▼──────────┐
                    │  Ingestion layer    │
                    │ Kafka, schema       │
                    │ registry            │
                    └─────────┬──────────┘
                              │
                    ┌─────────▼──────────┐
                    │ Stream processing   │
                    │ Flink jobs,         │
                    │ windowing           │
                    └────┬──────────┬─────┘
                         │          │
              ┌──────────▼───┐  ┌───▼───────────────┐
              │ Rule engine   │  │ ML anomaly         │
              │ Signature     │  │ detection           │
              │ matching      │  │ Behavioral models   │
              └──────┬────────┘  └──────────┬──────────┘
                     │                       │
                     └──────────┬────────────┘
                                │
                     ┌──────────▼───────────┐
                     │   Alert manager        │
                     │ Dedup, enrich, score   │
                     └──────────┬─────────────┘
                                │
                  ┌─────────────┴───────────────┐
                  │                              │
        ┌─────────▼─────────┐         ┌──────────▼──────────┐
        │   SOC analyst       │         │   Cold storage        │
        │ Triage & response   │         │ Data lake, forensic    │
        │                      │         │ query                  │
        └──────────────────────┘         └────────────────────────┘
```

---

## 3. Component Deep-Dives

### Ingestion layer (Kafka)
- Each source type (netflow, EDR, auth, cloud audit) gets its own topic, partitioned by entity (host ID, user ID, IP) so all events for one entity land on the same partition — critical because most detections need per-entity ordering/state.
- Schema registry (Avro/Protobuf) enforces contract so downstream jobs don't break on field drift.
- Kafka also acts as your durability buffer: if stream processing falls behind or crashes, events aren't lost — you replay from offset.

### Stream processing (Flink, or Kafka Streams for simpler cases)
- Stateful operators keyed by entity, using sliding/tumbling windows (e.g., "failed logins per user per 5 min", "unique ports touched per host per min").
- State backend: RocksDB with periodic checkpointing to S3, so a node failure doesn't lose in-flight aggregates — this is the piece most people under-engineer and it's where you'll get probed hardest in an interview.
- Exactly-once semantics matter here: double-counting a failed-login burst could trigger (or suppress) an alert incorrectly.

### Rule engine (deterministic detection)
- Sigma-style rules or a custom DSL compiled into the streaming job (or run as a side rules-engine like a CEP library) — e.g., "5 failed SSH logins from the same IP within 60s" or "process spawns from Word that weren't seen in baseline."
- Fast, explainable, low false-positive *tuning cost* once written — but blind to novel attacks (only catches known patterns).

### ML/behavioral layer
- Two tiers is typical:
  1. Lightweight per-entity statistical baselining (rolling z-scores, EWMA) computed in the stream job itself for cheap, low-latency anomaly scoring.
  2. Heavier models (isolation forest, autoencoders, graph-based lateral-movement detection) run on a slower cadence (minutes) over aggregated features, since they're too expensive to run per-event.
- Feature store needed here so training and serving use identical feature definitions — a classic source of train/serve skew bugs.

### Alert manager
- Dedup/correlate: a single attack often trips 20 rules — you want one incident, not 20 alerts. Group by entity + time window + kill-chain stage.
- Score/prioritize (severity × confidence × asset criticality) so analysts see the worst first.
- Enrich with context (asset owner, recent vuln scan, threat intel feed lookup) before it ever reaches a human.

### Cold storage / forensic query
- Raw events also get written (via a sink connector, not through the hot path) to a columnar data lake (Parquet/Iceberg on S3), queryable with something like Trino for retroactive investigation — "what did this IP do in the last 30 days" needs to scan cold data cheaply, which is a different access pattern than the hot streaming path.

---

## 4. Key Tradeoffs

| Decision | Option A | Option B | Tradeoff |
|---|---|---|---|
| Detection latency vs. accuracy | Score every event immediately | Batch/window before scoring | Lower latency catches faster, but window-based aggregation catches patterns single events can't |
| Rules vs. ML | Rule engine only | Add ML layer | Rules are explainable and fast to deploy; ML catches unknowns but needs training data, drifts, and is harder to explain to an analyst ("why did this fire?") |
| State management | In-memory only | RocksDB + checkpoint to S3 | In-memory is faster but a crash loses all baselines; checkpointing survives failure at the cost of I/O overhead |
| Storage | Single hot store for everything | Hot (stream state) + warm (recent search, e.g. OpenSearch) + cold (data lake) | Tiering controls cost — full-fidelity retention for a year in a hot store is prohibitively expensive |
| Alerting precision | Alert on every rule match | Correlate + dedup before alerting | Raw alerting causes analyst fatigue (alert fatigue is the #1 reason real breaches get missed) |

---

## 5. Likely Follow-Up Questions

- How do you handle a burst of 10x normal traffic without dropping detection accuracy (backpressure, load shedding, sampling strategies)?
- How do you avoid alert storms from a single incident tripping many correlated rules?
- How would you design the entity-state store so lateral movement across hosts is detectable (this usually pulls in a graph model)?
- How do you evaluate detection quality (precision/recall) when you don't have labeled ground truth for novel attacks?

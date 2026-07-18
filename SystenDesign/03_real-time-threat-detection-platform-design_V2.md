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

## 5. Follow-Up Questions and Answers

### 5.1 Handling a 10x traffic burst without losing detection accuracy

The instinct is "just autoscale," but autoscaling has a lag (30s–2min to spin up new Flink task managers or Kafka consumers), and a burst can hit hard within seconds. So you need a layered defense:

**Absorb first, shed second.**
- Kafka itself is your primary shock absorber — partitions buffer the spike, and as long as your retention window is long enough (hours, not minutes), you don't lose data even if processing falls behind for a while.
- Autoscaling consumers/Flink task managers kicks in on lag metrics (consumer lag, not just CPU) — CPU can look fine while lag balloons because the bottleneck is often stateful join/window operations, not raw compute.

**When you can't scale fast enough, degrade deliberately, not randomly:**
- **Tiered processing**: route events through cheap, stateless rule checks first (pattern match on a single event — no window state needed). Only events that pass a first filter go into the expensive stateful/ML path. This means a SYN flood gets caught by a cheap rule before it ever touches your windowed aggregation state.
- **Load shedding by priority, not volume**: never drop uniformly. Tag sources/rule types by criticality (auth events > general netflow noise) and shed low-priority event types first when backpressure crosses a threshold. This is a policy decision, not just an engineering one — worth surfacing to stakeholders.
- **Sampling with fidelity floors**: for very high-volume, low-signal sources (e.g. raw netflow from a scan storm), sample at high volume but never sample known-sensitive event types (auth, privilege escalation, EDR alerts). A common pattern: 1-in-N sampling for volumetric detections (which only need approximate counts anyway — a SYN flood is still detectable from 1% of packets) while keeping 100% fidelity on discrete security-relevant events.
- **Adaptive windowing**: some systems widen aggregation windows under load (compute "failed logins per 5 min" as "per 10 min" temporarily) to trade timeliness for throughput rather than dropping data outright.

**The interview-worthy insight**: burst handling isn't one mechanism, it's a cascade — buffer → autoscale → tiered filtering → priority shedding → sampling — and you want to be explicit that *what* gets shed is a security decision (you'd rather lose netflow volume metrics than lose a single auth failure event).

### 5.2 Avoiding alert storms from one incident tripping many rules

This is fundamentally a **correlation/grouping problem**, solved in three layers:

**a) Entity + time-window grouping (cheapest, catches most cases)**
Group alerts that share the same entity (host, user, IP) within a rolling time window (e.g. 5–15 min). If "port scan," "failed SSH," and "new process spawn" all fire for the same host within 10 minutes, they become one incident with three sub-signals rather than three tickets. This is a simple key-based aggregation, easy to implement as a stateful stream operator downstream of the rule engine.

**b) Kill-chain / MITRE ATT&CK stage correlation**
Tag every rule/model with which stage of the attack lifecycle it corresponds to (reconnaissance, initial access, lateral movement, exfiltration...). If multiple alerts on the same entity map to *sequential* stages, that's strong evidence of one coordinated incident — worth escalating as a single high-confidence case rather than several. If they map to the *same* stage repeatedly (e.g. 50 failed logins in a burst), that's more likely one noisy signal, not 50 separate incidents — dedupe on rule-id + entity, not just entity.

**c) Graph-based incident clustering (for the sophisticated case)**
Build an incident graph where nodes are entities/alerts and edges represent relationships (same source IP, same process tree, same time window, shared destination). Run connected-components or community detection periodically over the alert graph — everything in one connected component becomes one incident. This catches incidents that a simple entity-key grouping misses, e.g. a compromised account moving from host A to host B to host C, where each hop looks like a separate entity if you only group by single-entity key.

**Operational levers that matter as much as algorithms:**
- Suppress duplicate alerts from the same rule+entity within a cooldown window (classic "don't page on-call 50 times for the same fire").
- Confidence-weighted escalation: low-confidence individual signals that co-occur can *raise* combined confidence (Bayesian-ish scoring) rather than just being counted as separate low-priority alerts.
- Give analysts a feedback loop (mark "false positive" / "not related") that retrains the correlation weights over time — this is the piece that turns a static correlation engine into one that actually improves.

### 5.3 Detecting lateral movement — the entity-state store as a graph

Single-entity windowed state (what's described in the base design) is *necessary but not sufficient* for lateral movement — because the signal isn't "this host did something anomalous," it's "an identity/session moved between hosts in a pattern that doesn't match normal behavior." That's inherently a **graph traversal problem**, not a per-entity aggregation problem.

**Design:**
- Maintain a **session/identity graph**, not just per-host counters: nodes = {host, user, service account, process}, edges = {authentication event, RDP/SSH session, process spawn, service ticket request (Kerberos)}, each edge timestamped and weighted.
- This graph is built incrementally in the stream processor — every auth event or session event adds/updates an edge. Store it in a graph-capable state store (in Flink, this can be a custom keyed state with adjacency lists; at bigger scale, a dedicated graph DB like Neo4j or JanusGraph fed asynchronously from the stream, since real-time full-graph algorithms are expensive).
- **Real-time signal**: cheap, local graph properties computed incrementally — "this account authenticated to 8 distinct hosts in 10 minutes" (fan-out), or "this session chain crosses a network segment boundary it's never crossed before" (using a baseline of normal host-to-host adjacency per account).
- **Near-real-time signal (minute-scale batch)**: heavier graph algorithms — shortest path from a known-compromised entry point, centrality changes, or community detection to spot an account suddenly bridging two previously disconnected clusters of hosts (a classic lateral-movement tell).
- **Baseline matters more than anomaly-detection cleverness here**: most of the value comes from a solid "normal adjacency" baseline per identity (which hosts does this service account normally touch?) — deviation from *that*, not generic outlier detection, is what catches lateral movement with low false positives.

Worth noting explicitly in an interview: this is the one part of the system where you'd justify a separate, slower analytical path alongside the hot streaming path — trying to run full graph algorithms in the sub-second hot path isn't realistic at scale, so you accept a few-minutes detection latency for this specific class of threat in exchange for much better precision.

### 5.4 Evaluating detection quality without labeled ground truth for novel attacks

This is the hardest and most honest question in the whole design — worth naming that upfront in an interview rather than hand-waving it.

**What you *can* measure directly:**
- **Known-attack precision/recall**: for signature-based rules and any attack simulated via red-team/purple-team exercises or breach-and-attack-simulation (BAS) tools (e.g. running MITRE ATT&CK technique simulations), you get real labels — did the platform catch the simulated technique? This gives a legitimate, if narrow, recall number against *known* techniques.
- **Analyst feedback as weak labels**: every alert an analyst marks true-positive/false-positive becomes a label, even without knowing ground truth for undetected attacks. This lets you track precision (of what you alert on) reliably over time, even though it says nothing about recall for what you miss.

**What you approximate, because true recall on unknowns is unmeasurable:**
- **Proxy metrics for the ML/anomaly layer**: track model drift (feature distribution shift), alert volume stability (a sudden drop in alert volume from a model is often a bug, not an improvement), and score distribution over time — not accuracy, but *is the model still behaving the way it did when validated*.
- **Adversarial validation**: periodically inject known-bad synthetic sequences (constructed from public threat intel — CTI reports of real breach TTPs) into a staging pipeline and confirm detection. This isn't "novel" attack coverage, but it's the closest practical substitute — it validates the pipeline still catches yesterday's novel attacks, which is a reasonable proxy for confidence in catching tomorrow's.
- **Time-to-detect for post-hoc confirmed incidents**: when an incident is *eventually* found (via external report, audit, or a delayed signal), reconstruct whether earlier signals existed in the data that weren't surfaced. This retroactive analysis ("we had the data 6 hours before we alerted") is one of the few ways to estimate real missed-detection cost, and it directly feeds back into rule/model tuning.

**The framing to give an interviewer**: you don't solve the ground-truth problem — you triangulate. Known-attack recall (from simulation) tells you the floor. Analyst-labeled precision tells you the noise level. Drift monitoring tells you when the model is silently degrading. Post-hoc incident reconstruction tells you what you're actually missing in production. No single metric is trustworthy alone; the maturity of a threat detection program is largely measured by how disciplined it is about running all four of these continuously rather than picking one comfortable number to report.

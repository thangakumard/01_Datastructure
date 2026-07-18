# E2E Testing Approach — Real-Time Threat Detection Platform

This document lays out how to test the platform end-to-end: from raw event ingestion through to an alert landing in front of a SOC analyst (or into cold storage for forensic query). It complements, and assumes, the architecture in `real-time-threat-detection-platform-design.md`.

---

## 1. Why this system is hard to test

Before the approach, it's worth naming what makes E2E testing here different from a typical CRUD service:

- **The pipeline is stateful and stream-based.** A test isn't "call an API, check the response" — it's "inject an event sequence over time, wait for windowed state to accumulate, then check an alert appeared." Timing and windowing are part of correctness.
- **Correctness is probabilistic for the ML layer.** A rule either fires or doesn't; a model produces a score. You can't assert exact equality the way you would for the rule engine.
- **Failure modes are as important as happy paths.** A gap in detection during a Kafka rebalance or a Flink checkpoint recovery is a security incident, not just a bug — so E2E testing has to include failure injection, not just golden-path validation.
- **There's no ground truth for "did we miss something."** Unlike a typical E2E suite where you know the expected output, part of the test strategy has to be built around *known* attack sequences precisely because *unknown* ones can't be asserted against.

---

## 2. Test pyramid for this system

```
                     ┌─────────────────────────┐
                     │   Chaos / soak / DR       │   (lowest volume, highest realism)
                     ├─────────────────────────┤
                     │   E2E scenario tests       │
                     ├─────────────────────────┤
                     │   Integration tests         │
                     ├─────────────────────────┤
                     │   Unit tests                 │   (highest volume, fastest feedback)
                     └─────────────────────────┘
```

- **Unit tests**: individual rule logic, feature extraction functions, scoring functions, dedup key generation — pure functions, no Kafka/Flink runtime needed.
- **Integration tests**: a single stream job tested against an embedded/local Kafka and Flink MiniCluster — verifies windowing, state, and checkpointing logic in isolation, without the full pipeline.
- **E2E scenario tests**: full pipeline, real (or realistic staging) infrastructure, event-in to alert-out. This is the focus of this document.
- **Chaos / soak / DR tests**: full pipeline under induced failure or sustained load, run less frequently (pre-release, or continuously in a dedicated environment) rather than on every commit.

---

## 3. Environment strategy

- **Staging environment mirrors production topology**: same Kafka partition counts (or a scaled-down but proportional version), same Flink parallelism ratios, same rule/model versions as production — configuration drift between staging and prod is the single biggest cause of "worked in staging, silent in prod."
- **Shadow / dark traffic**: mirror a sample of real production events into the staging pipeline (or into a canary version of the pipeline running alongside production) so E2E tests run against realistic volume and data shape, not just synthetic data.
- **Canary deployments for rule/model changes**: new rule versions or model versions run in parallel with the production version on the same traffic, and their outputs are diffed before cutover — this catches regressions (a rule that used to fire on X and doesn't anymore) before they reach analysts.

---

## 4. Test data strategy

Three tiers of test data, each serving a different purpose:

### a) Synthetic event generators
- Programmatic generators that produce realistic-shaped events (auth logs, netflow, EDR telemetry) with controllable parameters — volume, entity cardinality, time distribution.
- Used for deterministic, fast-running E2E tests: "generate 5 failed SSH events for host X within 30 seconds, assert exactly one alert with rule_id=ssh_bruteforce fires within 10 seconds of the 5th event."

### b) Replayed historical / breach datasets
- Publicly available labeled datasets (e.g. CICIDS, LANL authentication dataset) or your own anonymized historical incident data, replayed through the ingestion layer at controllable speed.
- Used to validate detection against realistic background noise — synthetic generators are too clean; real traffic has the false-positive-inducing weirdness that matters.

### c) Attack-technique simulations (MITRE ATT&CK-mapped)
- Breach-and-attack-simulation (BAS) tooling or custom scripts that execute (in an isolated range) real attack techniques — credential stuffing, lateral movement via PsExec, C2 beaconing patterns — so the resulting telemetry is genuinely attack-shaped, not just a description of an attack.
- This is the closest thing to ground truth the system gets, and it's the basis for the regression suite described in Section 6.

---

## 5. Core E2E scenario categories

### 5.1 Detection correctness (rule engine)
- Inject a known-bad event sequence → assert the expected rule fires, with the expected entity, severity, and within the expected latency budget.
- Inject a known-benign sequence that's *close* to a rule's threshold (e.g. 4 failed logins when the rule threshold is 5) → assert no alert fires. This is as important as the positive case; threshold-boundary tests catch off-by-one and windowing bugs.
- Inject events out of order or with clock skew → assert the windowing logic still produces the correct result (or fails predictably), since real-world event delivery is never perfectly ordered.

### 5.2 Detection correctness (ML/anomaly layer)
- Since exact output can't be asserted, test against **score bands** and **relative ranking** instead: a known-anomalous sequence should score above a known-normal baseline sequence, and above a fixed threshold.
- Feature-store consistency test: run the same raw event through the online (streaming) and offline (batch/training) feature pipelines and assert identical feature vectors — this directly targets train/serve skew, the most common silent failure mode in this layer.
- Model version regression: run the previous and new model versions against the same replayed traffic and diff the alert sets — flag any large unexplained shift for manual review before promoting.

### 5.3 Correlation and alert manager
- Inject a multi-signal attack sequence designed to trip several rules on the same entity within the correlation window → assert exactly one incident is produced, containing all sub-signals, rather than multiple separate alerts.
- Inject the same rule firing repeatedly (e.g. a sustained brute-force) → assert dedup suppresses repeats within the cooldown window and doesn't spam.
- Inject a sequence spanning multiple entities that should be graph-correlated (lateral movement scenario: host A → host B → host C under one identity) → assert the incident graph clustering links them into one incident.

### 5.4 Latency / SLA validation
- Measure event-ingested-timestamp to alert-emitted-timestamp for each scenario category, and assert against defined SLA budgets (e.g. sub-5-second for stateless rules, sub-2-minute for windowed aggregations, sub-10-minute for batch ML scoring).
- Run this continuously against production-shaped shadow traffic, not just in isolated test runs, since latency is heavily influenced by real load.

### 5.5 Forensic query path
- After events flow through the hot path, assert they also land correctly in cold storage within the expected freshness window, and that a representative set of forensic queries (e.g. "all events for entity X in time range Y") return complete, correct results against the data lake.

### 5.6 Delivery / notification path
- Assert an alert that reaches the alert manager actually results in the expected downstream action — ticket created, page fired, dashboard updated — including verifying idempotency (re-delivery of the same alert doesn't create duplicate tickets).

---

## 6. Regression suite (the safety net for every change)

- Maintain a curated, versioned library of attack-technique scenarios (from Section 4c) mapped to MITRE ATT&CK techniques, each with an expected detection outcome.
- Run this suite automatically against every rule change, model deployment, and pipeline code change before it's allowed to promote to production — this is the primary defense against silently losing detection coverage as the system evolves.
- Track detection coverage over time as a first-class metric (percentage of the technique library still detected) — a coverage regression should block a release the same way a failing unit test would.

---

## 7. Non-functional E2E testing

### Load / burst testing
- Replay traffic at multiples of peak production volume (2x, 5x, 10x) against a staging environment sized like production, and validate the degradation behavior described in the design doc (tiered filtering, priority shedding, sampling) actually engages correctly rather than silently dropping high-priority events.
- Explicitly assert that critical event types (auth, privilege escalation) are never sampled or shed under load, only lower-priority volumetric data.

### Chaos engineering
- Kill a Flink task manager mid-window and verify checkpoint recovery restores state correctly with no lost or duplicated detections (exactly-once semantics under real failure, not just in theory).
- Trigger a Kafka broker failure / partition rebalance during active traffic and verify no consumer group gets stuck or drops messages.
- Simulate downstream failure (alert manager or notification sink unavailable) and verify the pipeline buffers/retries rather than silently dropping alerts.

### Soak testing
- Run the full pipeline under sustained realistic load for an extended period (24–72 hours) to catch slow leaks — state store growth, memory leaks in long-lived windows, gradual checkpoint slowdown — that short test runs won't surface.

### Disaster recovery
- Periodically exercise full region/cluster failover and measure actual recovery time and data-loss window against the documented RTO/RPO targets, rather than trusting the failover mechanism was configured correctly.

---

## 8. CI/CD integration

- **Schema contract tests**: run on every change to event schemas — verify producers and consumers stay compatible (this catches the ingestion-layer breakage class before it reaches staging).
- **Fast E2E subset on every PR**: a curated, fast-running slice of Section 5 scenarios (synthetic data, small volume) runs on every pull request for quick feedback.
- **Full E2E + regression suite pre-merge/pre-release**: the full attack-technique regression suite and broader scenario set run before a release is promoted, using staging infrastructure sized close to production.
- **Canary + shadow diffing post-deploy**: after deployment, new pipeline versions run alongside the previous version against live shadow traffic for a bake period, with automated diffing of alert output before full cutover.

---

## 9. Exit criteria / what "tested" means for a release

A release is considered E2E-validated when:
- 100% of the attack-technique regression suite still detects as expected (or any regression is explicitly reviewed and accepted).
- Latency SLAs hold under both normal and 2x-burst shadow traffic.
- No unexplained shift greater than an agreed threshold in canary vs. production alert volume/distribution.
- Chaos scenarios (task manager failure, broker failure, downstream outage) recover without data loss or duplicate alerting.
- Cold storage forensic queries return complete results for the validation time window.

# Secure Agent Update Service — System Design & E2E Distributed Testing Approach

---

# 1. System Design: Secure Agent Update Service

## Problem Framing

A fleet of agents (endpoint agents, IoT devices, monitoring agents, sidecars) running across untrusted/semi-trusted networks need to receive software updates that are:
- **Authentic** (came from us, not tampered)
- **Fresh** (not a replayed old/vulnerable version)
- **Available** (rollout doesn't brick the fleet)
- **Auditable** (who shipped what, to whom, when)

Assumptions: millions of agents, heterogeneous OS/arch, intermittent connectivity, update sizes 10MB–2GB, multi-tenant.

## Requirements

**Functional**
- Publish/version/stage new agent builds
- Agents can discover, download, verify, and apply updates
- Staged rollouts (canary → ring-based → GA), rollback
- Delta/binary-diff updates to save bandwidth
- Per-tenant/per-cohort targeting (OS, region, agent version, feature flags)

**Non-functional**
- Security: integrity, authenticity, non-repudiation, protection against downgrade/rollback/mix-and-match attacks (TUF-like guarantees)
- Availability: 99.95%+, must survive CDN/region failures
- Scale: millions of agents polling, spiky during rollout
- Observability: rollout health, adoption %, failure telemetry

## High-Level Architecture

```
                        ┌─────────────────────┐
                        │   Release Pipeline   │
                        │ (CI, sign, publish)  │
                        └──────────┬───────────┘
                                   │ signed artifacts + manifest
                                   ▼
        ┌───────────────────────────────────────────────┐
        │            Update Control Plane                │
        │  - Metadata service (versions, targets, rings) │
        │  - Rollout orchestrator (canary %, health gate) │
        │  - Signing service (KMS/HSM-backed)             │
        │  - Audit log                                    │
        └───────────────────┬─────────────────────────────┘
                             │ publishes signed manifests
                             ▼
        ┌───────────────────────────────────────────────┐
        │           Global Distribution Layer             │
        │   CDN (edge cached manifests + binaries)         │
        │   Object storage (S3/GCS, versioned, immutable)  │
        └───────────────────┬─────────────────────────────┘
                             │ HTTPS pull (poll or long-poll)
                             ▼
                     ┌───────────────┐
                     │     Agents     │  (millions, edge)
                     │ - poll manifest│
                     │ - verify sig   │
                     │ - stage/apply  │
                     │ - report status│
                     └───────┬────────┘
                             │ telemetry
                             ▼
                  ┌─────────────────────┐
                  │  Telemetry/Health    │
                  │  ingestion + alerts  │
                  └─────────────────────┘
```

## Key Components

**1. Release & Signing Pipeline**
- CI builds artifact → generates SBOM → computes hash → sends to Signing Service backed by KMS/HSM (offline root key, online timestamp/targets keys — the TUF model: root, targets, snapshot, timestamp roles with separate keys and expiries).
- Signed manifest includes: version, hash (SHA-256), size, min-agent-version, target cohorts, signature, expiry timestamp.

**2. Metadata/Manifest Service**
- Source of truth for "what version should cohort X be on."
- Manifests are versioned and immutable in object storage; a small, frequently-changing "current pointer" (like TUF's timestamp role) tells agents what's latest — this pointer has short-lived signatures to prevent replay of stale manifests.
- Rollout state modeled as rings: `canary (1%) → early (10%) → broad (50%) → GA (100%)`, each gated by health signals (crash rate, error rate) before auto-promoting.

**3. Distribution Layer**
- Binaries + manifests served via CDN, backed by multi-region object storage with cross-region replication.
- Agents use exponential backoff + jitter polling (never a synchronized thundering herd) or a lightweight pub/sub push hint (e.g., MQTT/webhook "check now") for faster propagation without breaking the pull-based trust model.
- Delta updates (bsdiff/courgette-style) computed server-side between consecutive versions to cut bandwidth.

**4. Agent-Side Update Client**
- Verifies: signature against pinned root-of-trust public key, hash of downloaded binary, version monotonicity (reject downgrade unless explicitly signed rollback), manifest expiry (anti-replay/freeze attack).
- Staged apply: download → verify → stage in isolated dir → atomic swap (symlink flip) → health self-check → auto-rollback to previous known-good binary if health check fails post-update (watchdog timer).
- Reports status (success/fail/version) to telemetry endpoint, itself authenticated (mTLS or signed agent identity token).

**5. Rollout Orchestrator**
- Watches telemetry aggregates per ring; auto-pauses/rolls back rollout if error/crash rate crosses threshold; supports manual kill-switch.

**6. Audit/Compliance**
- Every publish, promotion, and rollback event immutably logged (who, what, when) — important for enterprise/regulated customers.

## Security Model (TUF-inspired)

| Threat | Mitigation |
|---|---|
| Tampered binary in transit/storage | Hash + signature verification client-side |
| Compromised signing key | Key hierarchy — offline root key signs delegated online keys; rotate + revoke via root |
| Replay of old vulnerable manifest | Short-lived "timestamp" signature + monotonic version check |
| Rollback attack | Agent rejects versions older than currently installed unless signed rollback manifest |
| Mix-and-match (serve valid-but-wrong artifact) | Manifest binds exact hash per target; agent checks hash matches manifest, not just "a valid signature" |
| Single CDN/region compromise or outage | Multi-CDN + multi-region storage, agents can fall back to secondary source |
| Fleet-wide bad update ("bricking") | Canary rings, automated health-gated promotion, client-side auto-rollback watchdog |

---

# 2. E2E Cloud Distributed Testing Approach

Goal: validate correctness, security, and resilience of the whole pipeline — not just unit-test the signing library — across realistic network/scale conditions.

## Test Environment Topology

- Multi-region test clusters (3+ cloud regions, e.g., us-west, eu-central, ap-south) via IaC (Terraform), each with:
  - A scaled-down but architecturally identical control plane
  - Simulated CDN/edge (or real CDN in a staging account)
- **Agent fleet simulation**: containerized (Kubernetes Jobs or ECS tasks) — thousands of lightweight agent-client containers, each running the real agent binary/client logic, distributed across regions and AZs to mimic geo/network diversity.
- Network chaos injection (Toxiproxy, Chaos Mesh, tc/netem) to simulate: high latency, packet loss, partial connectivity, DNS failures, CDN region outage.

## Test Layers

**A. Functional correctness (per component, cross-service)**
- Publish a signed release → assert manifest propagates to all regions within SLA.
- Agent pull → verify signature/hash validation logic against known-good and deliberately corrupted artifacts (should reject).
- Version monotonicity: attempt to serve an old manifest to an up-to-date agent → must be rejected (rollback-attack test).
- Delta update correctness: apply delta patch on N prior versions, diff resulting binary hash against full download.

**B. Security/adversarial testing**
- Key compromise simulation: rotate a "compromised" delegated key via root, verify old signatures immediately rejected fleet-wide.
- Replay attack: replay an expired timestamp manifest → agents must reject.
- MITM simulation: intercept and swap payload at proxy layer → signature check must catch it before apply.
- Fuzz the manifest parser with malformed inputs.

**C. Scale & load**
- Ramp simulated fleet to target concurrency (e.g., 100K simulated agents polling) using a distributed load driver (k6, Locust, or Gatling workers spread across regions) to avoid the load generator itself being a bottleneck/single point of geo bias.
- Measure: control-plane p99 latency, CDN cache hit ratio, origin fetch rate, propagation time to X% of fleet.
- Thundering-herd test: force synchronized poll (disable jitter) to validate backoff/jitter logic prevents origin overload.

**D. Rollout/health-gate logic**
- Inject synthetic "bad build" that fails self-check on a subset of canary agents → assert orchestrator halts promotion to next ring automatically within defined time window.
- Validate auto-rollback: agents in the bad cohort revert to previous version and report recovery.

**E. Chaos/resilience**
- Kill a region's control plane / CDN PoP mid-rollout → verify agents in that region fail over to secondary source without stalling indefinitely.
- Partition network between control plane and one region for N minutes, then heal → verify eventual consistency and no split-brain in rollout state (e.g., two rings both thinking they're "current").

**F. Observability validation**
- Assert telemetry from simulated agents (success/fail/version) aggregates correctly and triggers alerting thresholds in the test environment (deliberately push failure rate over threshold, confirm pipeline pauses).

## Test Orchestration & CI Integration

- Wrap the above as a staged pipeline: unit → integration (single region) → E2E multi-region (nightly, using ephemeral Terraform-provisioned environments) → chaos suite (weekly or pre-release gate).
- Ephemeral environments: spin up via IaC per run, tear down after — avoids drift and cost bleed.
- Golden-path + fault-injection scenarios both codified as versioned test scenarios (e.g., YAML-defined chaos experiments) so they're reviewable and repeatable, not ad hoc scripts.
- Release gate: no promotion past canary ring in production until the E2E suite passes against that exact build/manifest in the staging multi-region environment.

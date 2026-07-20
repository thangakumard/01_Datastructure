# Global Configuration Management Service (GCMS) — System Design

## 1. Requirements

### Functional
- CRUD on config entries, scoped hierarchically (global → region → environment → service → tenant → instance), with override/inheritance resolution.
- Versioning + audit trail; rollback to any prior version.
- Real-time propagation to consumers (push, not just poll).
- Conditional/targeted configs (e.g., feature flags by tenant %, region, user segment).
- Validation against schemas before publish.
- Approval workflows for sensitive configs (RBAC + change review).

### Non-functional
- Read-heavy, write-light: expect 10,000:1 to 100,000:1 read:write ratio. Reads dominate the design.
- Read availability > write availability. A read outage breaks every dependent service; a write outage just blocks config changes.
- Read latency: p99 < 10ms for local SDK/cache reads, < 100ms for centralized reads.
- Global scale: multi-region active-active, must survive full region loss.
- Strong consistency for a single config key's *write* ordering; eventual consistency is acceptable for global propagation, bounded by an SLA (e.g., "all regions converge within 5s").
- Config service must never be a single point of failure for the services depending on it — its own crash cannot crash config consumers ("fail static," not "fail closed").

## 2. High-Level Architecture

```
                         ┌────────────────────────┐
                         │   Admin UI / CLI / API   │
                         └───────────┬─────────────┘
                                     │ writes (gRPC/REST)
                         ┌───────────▼─────────────┐
                         │   Control Plane (per-     │
                         │   region, leader region   │
                         │   for a given namespace)  │
                         │  - Validation              │
                         │  - Versioning              │
                         │  - Approval workflow       │
                         └───────────┬─────────────┘
                                     │ commit
                         ┌───────────▼─────────────┐
                         │  Source of Truth Store    │
                         │  (e.g. etcd/FoundationDB/  │
                         │   Spanner-like, strongly   │
                         │   consistent per key)      │
                         └───────────┬─────────────┘
                                     │ change stream (CDC / watch)
                    ┌────────────────┼────────────────┐
                    ▼                ▼                ▼
             ┌───────────┐   ┌───────────┐    ┌───────────┐
             │ Region A   │   │ Region B   │    │ Region C   │
             │ Replicator │   │ Replicator │    │ Replicator │
             └─────┬─────┘   └─────┬─────┘    └─────┬─────┘
                   ▼               ▼                ▼
             ┌───────────┐   ┌───────────┐    ┌───────────┐
             │ Regional   │   │ Regional   │    │ Regional   │
             │ Read Cache │   │ Read Cache │    │ Read Cache │
             │ (Redis/    │   │ (Redis/    │    │ (Redis/    │
             │  local KV) │   │  local KV) │    │  local KV) │
             └─────┬─────┘   └─────┬─────┘    └─────┬─────┘
                   ▼               ▼                ▼
             Push (gRPC stream/SSE) or SDK long-poll to
             consuming services, each with an embedded
             local cache + fallback snapshot on disk
```

### Key architectural decisions

**Write path is centralized, read path is fully decentralized.** All writes go through one logical control plane (can be leader-elected per config namespace to allow regional ownership, e.g., a EU team owns EU-only configs). Writes hit a strongly consistent store. This is the classic pattern (Consul, etcd, Spanner-backed configs) — you don't need global write availability, you need global write *correctness*, because writes are rare.

**Change propagation via CDC/watch, not polling fan-out.** The source-of-truth store emits an ordered change stream (like etcd's watch API or a Kafka changelog topic). Regional replicators subscribe to this stream and hydrate their local read caches. This gives you an audit-friendly, replayable log and bounds propagation lag deterministically.

**Consumers never talk to the source of truth directly.** They talk to a regional cache/edge layer, and ideally embed an SDK that:
- Maintains a local in-memory snapshot.
- Long-polls or holds a streaming (gRPC/SSE) connection to the regional layer for push updates.
- Persists the last-known-good snapshot to local disk, so a fresh process boot with no network still gets a usable (if stale) config rather than blocking or crashing.

This "fail static" design is the single most important property: **a configuration service outage should degrade to staleness, never to unavailability of the consuming service.**

## 3. Data Model

```
ConfigKey {
  namespace: string        // e.g. "workflow-data-fabric"
  scope_path: string        // hierarchical: /global/us-west/prod/svc-x/tenant-42
  key: string
  value: bytes (JSON/proto, schema-validated)
  version: int64 (monotonic per key)
  content_hash: string
  created_by, created_at
  ttl / expiry (optional, for time-boxed rollouts)
  targeting_rules: [ { condition, value, rollout_pct } ]  // for flag-style configs
}
```

**Scope resolution** is a merge/override walk: instance-level config overrides service-level, which overrides environment, which overrides global. Resolve this at write-cache-build time (in the regional replicator), not on every read — precompute the flattened, resolved view per scope so reads are O(1) key lookups, not O(depth) merges.

**Versioning**: every write creates a new immutable version; the "current" pointer moves. This gives free audit log and trivial rollback (just move the pointer, don't mutate).

## 4. Consistency Model

- **Within a key, writes are linearizable** (single-writer-per-key via the strongly consistent store, e.g. etcd/FoundationDB/Spanner). No lost updates, no split-brain on a single config value.
- **Across regions, propagation is eventually consistent** with a bounded staleness SLA. Use vector/version stamps so consumers can detect "I'm N versions behind" and surface that as an observability metric, not silently serve stale data forever.
- **Read-your-writes for the admin who just made the change**: the control plane's write response should include the new version, and the admin UI should poll/wait on that specific region's replicator until it reflects that version (this avoids the classic "I just changed it and it looks unchanged" support ticket).
- Provide a `wait_for_version` API for consumers who need synchronous confirmation (e.g., a deploy pipeline gating on a config rollout).

## 5. APIs

```
POST /v1/configs/{scope_path}/{key}      — write, requires approval token if governed
GET  /v1/configs/{scope_path}/{key}      — resolved read (with inheritance applied)
GET  /v1/configs/{scope_path}/{key}/history
POST /v1/configs/{scope_path}/{key}/rollback?version=N
STREAM /v1/watch?scope_prefix=...        — gRPC/SSE push stream for changes
```

SDK-level API (what services actually call) is just `config.get(key, default)` — synchronous, backed by local cache, never blocks on network.

## 6. Multi-Region & Disaster Recovery

- Source-of-truth store: multi-region strongly-consistent (Spanner-style) or single-leader-region-per-namespace with async cross-region replication if you want to avoid the cost/latency of synchronous global consensus. For a config service, single-leader-per-namespace with regional failover is usually the better cost/complexity tradeoff — configs don't need cross-region synchronous writes, they need cross-region synchronous *reads with bounded staleness*.
- Each region's replicator can operate fully independently of other regions — a US region control plane outage doesn't stop EU reads, only US writes.
- Regional read caches are the actual availability boundary: as long as one is warm, that region's consumers are fine even if the entire control plane and cross-region network is down.

## 7. Security & Governance

- RBAC on scope_path prefixes (namespace/team ownership).
- Mandatory schema validation pre-commit (reject malformed configs before they ever propagate — this is your biggest outage-prevention lever).
- Approval workflow + audit log for "blast radius" configs (anything scoped at /global).
- Canary/staged rollout: percentage-based targeting so a bad config hits 1% of traffic before 100%.
- Kill switch: every config change should be revertible in under the propagation SLA, and there should be an emergency "freeze propagation" control independent of normal write path.

---

# E2E Distributed Testing Approach

Given the architecture above, testing has to validate three different things separately: **correctness of resolution logic**, **propagation/consistency guarantees under failure**, and **consumer-side resilience (fail-static behavior)**.

## 1. Unit / Component Level (fast, deterministic)
- Scope resolution & inheritance merge logic — property-based tests (e.g., generate random scope hierarchies + overrides, assert deterministic resolved output).
- Schema validation rejects malformed payloads.
- Versioning/rollback logic — pure logic, no network.

## 2. Integration Testing (single region, real dependencies)
- Spin up the real store (etcd/FoundationDB testcontainer) + control plane + one replicator + SDK, in Docker Compose or Testcontainers.
- Assert: write → change stream event → regional cache update → SDK push, all within SLA, using real timing (not mocked clocks).
- Contract tests between control plane and SDK (schema/version compatibility) so client and server evolve independently without breaking each other — this matters a lot once you have SDKs embedded in many services with different upgrade cadences.

## 3. Multi-Region E2E in a Real Distributed Testbed
This is the core of validating the actual design goals, and it needs to run against real network topology, not mocks:

- **Deploy to 3+ real (or realistically simulated) regions** in a staging environment, with actual cross-region network latency (don't fake this — use real cloud regions or at least `tc netem` to inject realistic RTT, e.g. 80–150ms US↔EU).
- **Propagation SLA test**: write in region A, poll all regions, assert p99 convergence time < SLA (e.g. 5s) across N consecutive runs under normal load, and again under 10x write burst load.
- **Read-your-writes test**: admin writes, then immediately reads from the same region — must reflect the write; from a remote region — must reflect it within SLA, and the version-staleness metric must be observable in the interim.
- **Ordering test**: rapid sequential writes to the same key (write A, B, C within milliseconds) — every region must converge to C, never regress to A or B (test against replicator reordering bugs, an easy class of bug in CDC-fan-out systems).

## 4. Chaos / Fault-Injection Testing (the part people skip and shouldn't)
This is where you actually prove the "fail static" property, which is the whole point of the design:
- **Kill the control plane entirely** mid-test — assert existing regional caches keep serving reads unaffected, and consuming services report no errors (only "config service degraded" metric).
- **Partition a region from the source-of-truth store** — that region should keep serving its last-known cache; writes to that region should fail loudly (not silently accept and lose data).
- **Kill the source-of-truth store's quorum** (e.g., take down majority of etcd/Spanner nodes) — writes should be rejected cleanly; reads from regional caches should continue.
- **Kill a consuming service's network entirely, then restart the process** — assert it boots from its on-disk snapshot within X ms and serves stale-but-valid config rather than blocking startup or crash-looping. This is the test most teams forget and the one that causes real production incidents.
- **Inject corrupted/malformed change-stream events** — replicator should reject and alert, not propagate garbage to caches.
- **Clock skew injection** across regions — verify version-based (not wall-clock-based) ordering holds.

Tooling for this tier: Chaos Mesh or Litmus on Kubernetes if you're running there (kill pods, inject network partitions/latency/packet loss declaratively), or Gremlin if you want a managed option. For a JVM/Java-based stack, Chaos Monkey-style fault injection at the service level pairs well with Chaos Mesh at the infra level.

## 5. Load & Soak Testing
- Sustained high read QPS against SDK local caches (should be near-zero network calls — verify with network call counters, not just latency, since a caching bug that silently falls back to network-per-read is a classic failure mode that only shows up under load).
- Write-storm test: burst of hundreds of writes/sec to validate the control plane's queuing/backpressure doesn't silently drop or reorder events downstream.
- Long-running soak (24–72h) watching for replicator memory leaks, watch-connection churn, and cache staleness creep — CDC/watch-stream based systems are notorious for slow connection-leak degradation that short tests miss.

## 6. Canary Validation in Production Topology
- Before global rollout of the config service itself (not the configs it serves — the *service* itself), canary a new control-plane/replicator version into one region behind the existing one, shadow-comparing resolved config output between old and new for a sample of real traffic before cutover.
- Synthetic canary configs: a dedicated always-on test key written on a fixed interval from a synthetic prober, with an alert if any region's read of that key falls outside the propagation SLA — this becomes a permanent production health-check, not just a pre-release test.

---

**General principle:** unit/integration tests prove logical correctness; chaos and multi-region tests prove the operational guarantees (fail-static, bounded staleness, ordering) that are the actual reason this system is hard — and those guarantees are exactly the ones that can't be verified without real distributed infrastructure and real fault injection, so that tier shouldn't be treated as optional or "nice to have later."

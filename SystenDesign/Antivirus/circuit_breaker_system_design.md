# Circuit Breaker Pattern — System Design Interview Guide
### Senior & Staff Engineer Level

---

## Table of Contents

1. [What Problem It Solves](#1-what-problem-it-solves)
2. [Where Should the Circuit Breaker Live](#2-where-should-the-circuit-breaker-live)
3. [The State Machine](#3-the-state-machine)
4. [Where the State Actually Lives](#4-where-the-state-actually-lives)
5. [How Fast-Fail Works (Mechanics)](#5-how-fast-fail-works-mechanics)
6. [How to Verify a Circuit Breaker Has Triggered](#6-how-to-verify-a-circuit-breaker-has-triggered)
7. [Sample API Response When OPEN](#7-sample-api-response-when-open)
8. [Trip Conditions — Good vs Naive](#8-trip-conditions--good-vs-naive)
9. [Characteristics of a Good Circuit Breaker](#9-characteristics-of-a-good-circuit-breaker)
10. [Circuit Breaker vs Related Patterns](#10-circuit-breaker-vs-related-patterns)
11. [Failure Modes & Mitigations](#11-failure-modes--mitigations)
12. [Senior vs Staff Answer Differentiators](#12-senior-vs-staff-answer-differentiators)
13. [Interview Time Allocation](#13-interview-time-allocation)
14. [Quick-Reference Cheatsheet](#14-quick-reference-cheatsheet)

---

## 1. What Problem It Solves

A **circuit breaker** is a client-side stateful guard that stops a caller from repeatedly invoking a dependency that is failing or degraded, so that:

- The caller doesn't waste threads/connections/memory waiting on doomed calls
- The struggling dependency isn't hammered further while it's trying to recover
- Failure in one dependency doesn't cascade into an outage of the entire calling fleet

### What the interviewer is really testing

- Do you understand this is a **local, in-process** protection mechanism, not a remote service?
- Can you explain the **mechanics of fast-fail**, not just recite "it opens after N failures"?
- Do you know **where** in the architecture it belongs, and where it *doesn't*?
- Can you describe **observability** — how an operator or a caller actually knows it tripped?

---

## 2. Where Should the Circuit Breaker Live

**Rule of thumb: at every trust boundary where you cross a network hop to a dependency that can degrade independently of you.**

| Layer | Should it have a CB? | Why |
|---|---|---|
| **Service client / SDK layer** | ✅ Yes — primary location | The caller suffers cascading failure if it keeps calling a dying dependency; it has the local stats to decide. |
| **API Gateway / Service Mesh sidecar** (Envoy, Istio, Linkerd) | ✅ Yes — often preferred in practice | Centralizes the logic as infrastructure (Envoy `outlier_detection`) instead of reimplementing per-language libraries. |
| **Load balancer** | Related but different | LB does *endpoint ejection* (removing one unhealthy instance from rotation) — not the same as breaking a call to a whole dependency. |
| **Server side (the callee)** | ❌ No | The callee can't tell if it's failing broadly or just for one caller. Use **rate limiting / load shedding / bulkheads** there instead. |
| **DB driver layer** | ✅ Often | Same rationale as service client. |

**Applied to a web crawler:** Fetcher → target website, Parser → Storage layer, Frontier coordinator → Redis/Cassandra, any service → Kafka producer are all valid CB boundaries. The per-domain politeness/backoff logic in the frontier is functionally a specialized circuit breaker already — CB generalizes that idea to *any* dependency.

---

## 3. The State Machine

A good breaker has **three states**, not two:

```
                    failure rate ≥ threshold
        ┌──────────────────────────────────────┐
        │                                        ▼
   ┌─────────┐                             ┌──────────┐
   │ CLOSED  │                             │   OPEN   │
   │ (normal)│                             │(fail fast)│
   └────┬────┘                             └────┬─────┘
        ▲                                        │
        │                              wait_duration elapsed
        │                                        │
        │                                        ▼
        │                              ┌──────────────────┐
        └──────success on probe────────┤    HALF_OPEN     │
             (limited N probes allowed)│  (trial traffic)  │
                                        └─────────┬─────────┘
                                                  │
                                       probe fails │
                                                  ▼
                                             back to OPEN
                                          (reset/backoff timer)
```

- **CLOSED** — normal operation; calls pass through; failures are counted in a sliding window.
- **OPEN** — calls are rejected immediately, no network I/O attempted; a timer (`open_until`) governs when to try again.
- **HALF_OPEN** — a small number of trial requests are allowed through to test recovery. All succeed → CLOSED. Any fail → back to OPEN with backoff.

A binary on/off breaker either recovers too slowly (never re-tests) or floods a recovering dependency (no limited trial phase) — HALF_OPEN is the detail that separates a toy implementation from a production one.

---

## 4. Where the State Actually Lives

The breaker is **not a separate service the client asks**; it's an in-memory object wrapping the call path.

```
Client code
    │
    ▼
┌─────────────────────────┐
│  CircuitBreaker.call()  │  ← wraps every outbound call
│  state: CLOSED/OPEN/    │  ← in-memory field, checked
│         HALF_OPEN       │     BEFORE any network I/O
└──────────┬───────────────┘
           │  (only if CLOSED or HALF_OPEN-and-probe-slot-available)
           ▼
   Actual network call to dependency
```

| Pattern | State location | Consistency across instances |
|---|---|---|
| **Library-embedded** (resilience4j, Polly, pybreaker) | In-process memory, per pod/instance | Independent per instance — no coordination needed. |
| **Sidecar/mesh** (Envoy outlier detection) | Local to the node's proxy | Same as above, language-agnostic. |
| **Shared/distributed** (Redis-backed) | External store, shared across instances | All instances flip together — useful for protecting very fragile downstreams from a synchronized fleet-wide recovery storm, but adds a network hop to the breaker itself. |

**Staff-level nuance:** most systems intentionally keep state **per-instance and uncoordinated**. It's cheaper (no extra call just to check the breaker) and self-limiting — many pods tripping independently based on their own local failure signal produces graceful, staggered degradation rather than a synchronized all-or-nothing flip.

---

## 5. How Fast-Fail Works (Mechanics)

Fast-fail is simply an early return/throw **before any I/O is attempted**:

```python
class CircuitBreaker:
    def call(self, dependency_fn, *args):
        if self.state == OPEN:
            if time.now() < self.open_until:
                raise CircuitBreakerOpenError()   # fast fail: no network call made
            self.state = HALF_OPEN                # timeout elapsed, allow a probe

        if self.state == HALF_OPEN and self.probes_in_flight >= self.max_probes:
            raise CircuitBreakerOpenError()       # still fast fail during probing

        try:
            result = dependency_fn(*args)          # ONLY path that touches the network
            self.record_success()
            return result
        except (Timeout, ConnectionError) as e:
            self.record_failure()
            raise
```

**Key point:** when OPEN, `dependency_fn` is never invoked. The call returns in microseconds instead of blocking on a multi-second timeout. That avoidance of the network call *is* the entire fast-fail mechanism — its value is in preventing thread/connection pool exhaustion, not in any clever failure detection.

**Caveat to flag in interview:** fast-fail protects *your own process* from waiting. It does **not** reduce load on the downstream dependency below a single health-check probe's worth — it's not a substitute for the downstream having its own rate limiting / load shedding.

---

## 6. How to Verify a Circuit Breaker Has Triggered

Verification happens at three levels — state, causal evidence, and blast-radius confirmation.

### a) Expose state directly (don't infer it)
```
circuit_breaker_state{dependency="payment-service"} = 0|1|2   // CLOSED / OPEN / HALF_OPEN
```
- **Metrics**: emit a gauge on every state transition (Prometheus/StatsD).
- **Structured logs**: log `circuit_opened`, `circuit_half_open_probe`, `circuit_closed` events with the trigger reason.
- **Listeners/hooks**: resilience4j/Polly support `onStateTransition` — wire these into alerting, not just logs.

### b) Corroborate with call-level evidence
- OPEN calls should raise a **distinct exception type** (`CircuitBreakerOpenError`), not the underlying `TimeoutException`/`ConnectionRefused` — otherwise you can't distinguish CB-rejected calls from real failures.
- Track **separate counters**: `circuit_breaker_rejected_total` vs `dependency_call_failed_total`. A spike in the former with the latter flat is the signature of "tripped and now protecting us."
- **Latency should cliff-drop** for rejected calls (near-zero, no network hop) — a good dashboard shows p99 latency falling sharply at the same moment error rate holds steady.

### c) Confirm blast-radius / recovery behavior
- Log/metric every HALF_OPEN probe and its outcome — a breaker flapping OPEN → HALF_OPEN → OPEN repeatedly is itself worth alerting on.
- Dashboard: overlay state transitions as an annotated timeline on top of the dependency's error-rate/latency graph.
- **Chaos/game-day testing** is the only way to truly verify: inject a fault (kill dependency, add latency, force 5xx) and confirm (1) state flips to OPEN within threshold, (2) callers fast-fail instead of hanging, (3) it self-heals when the dependency recovers.

---

## 7. Sample API Response When OPEN

### Recommended status code: `503 Service Unavailable`

Not `500` (implies your own code broke) and not `504` (implies you tried and timed out — you explicitly didn't try). `503` correctly signals "temporarily unavailable, retry later."

```http
HTTP/1.1 503 Service Unavailable
Content-Type: application/json
Retry-After: 15
X-Circuit-Breaker: OPEN
X-Circuit-Breaker-Dependency: payment-service
X-Request-Id: 8f3e9a2c-...

{
  "error": {
    "code": "DEPENDENCY_UNAVAILABLE",
    "message": "The payment service is temporarily unavailable. Please retry shortly.",
    "type": "CIRCUIT_BREAKER_OPEN",
    "dependency": "payment-service",
    "retry_after_seconds": 15
  }
}
```

| Element | Purpose |
|---|---|
| `503` | Correct semantics — transient, not client-caused (4xx) or a genuine bug (500). |
| `Retry-After` | RFC 9110 standard header — tells well-behaved clients exactly when to retry; should match `open_until`. Prevents retry storms. |
| `X-Circuit-Breaker: OPEN` | Lets you grep/alert on CB-caused failures separately from genuine 503s. |
| `type: CIRCUIT_BREAKER_OPEN` in body | Lets calling *code* branch to a fallback path, not just a human reading logs. |
| `X-Request-Id` | Standard correlation ID — even fast-failed requests should be traceable. |

### Prefer graceful degradation over a bare error, when a fallback exists

```http
HTTP/1.1 200 OK
Content-Type: application/json
X-Circuit-Breaker: OPEN
X-Data-Source: cache-fallback

{
  "recommendations": [ /* stale cached data */ ],
  "meta": {
    "source": "fallback_cache",
    "warning": "Live recommendation service unavailable; showing cached results.",
    "cached_at": "2026-07-21T09:12:00Z"
  }
}
```

### Internal vs external context

| Context | Status code | Notes |
|---|---|---|
| Internal gRPC | `UNAVAILABLE` (status code 14) | Dedicated gRPC status; conceptually maps to HTTP 503. |
| Internal REST | `503` + rich debug headers | Internal tooling can key on breaker internals safely. |
| Public API | `503` with a **sanitized** body | Never leak internal dependency names or breaker internals externally — information disclosure smell. |

```http
HTTP/1.1 503 Service Unavailable
Retry-After: 15
Content-Type: application/json

{
  "error": "service_unavailable",
  "message": "We're experiencing temporary issues. Please try again in a few seconds."
}
```

### Common mistake: don't use `429 Too Many Requests`

`429` means *the client* is sending too much traffic (rate limiting). CB-open means *the dependency* is unhealthy. Returning `429` misleads the client into throttling itself when the real problem is downstream. Keep `429` for actual rate limiting; use `503` for circuit-breaker/dependency-unavailable.

---

## 8. Trip Conditions — Good vs Naive

| Approach | Description | Problem |
|---|---|---|
| **Naive**: consecutive failure count | Trip after N consecutive failures (e.g. 5 in a row) | A single blip can trip it; doesn't catch a low-but-steady failure rate interspersed with successes |
| **Good**: sliding window error rate | Trip when ≥ X% of calls fail over a rolling window (e.g., last 20 calls, or last 10s) | Requires a **minimum volume threshold** first — don't evaluate rate on 2–3 calls at low traffic, or you get false trips |
| **Better**: multiple trigger types | Error rate, timeout rate, **and slow-call rate** each independently able to trip | Slow-but-successful calls are often a leading indicator of impending failure; ignoring them means you react late |

---

## 9. Characteristics of a Good Circuit Breaker

**State machine**
- Three states (CLOSED / OPEN / HALF_OPEN), not binary on/off.

**Trip conditions**
- Sliding-window error rate, not simple consecutive-failure counting.
- Minimum call volume before evaluating rate (avoids false trips on low traffic).
- Multiple trigger types: error rate, timeout rate, slow-call rate.

**Recovery behavior**
- Exponential backoff on repeated OPEN → HALF_OPEN → OPEN cycles, not a fixed wait.
- Limited concurrent probes in HALF_OPEN (e.g., 5, not 1 and not unlimited) — 1 probe is noisy, unlimited recreates the thundering herd.

**Isolation**
- Per-dependency, ideally per-endpoint — a global breaker conflates unrelated failures (DB down shouldn't trip the cache breaker).
- Pairs with **bulkheads** — separate thread/connection pools per dependency so one exhausted dependency can't starve calls to a healthy one.

**Observability**
- State exposed as metrics + structured logs + transition events (not just internal).
- Distinct exception/error type for CB-rejected calls vs real dependency failures.

**Graceful degradation**
- Pairs with a fallback (cached/stale data, default value, degraded feature) so failures don't always propagate as hard errors.

**Configuration**
- Thresholds (error rate, window size, timeout, probe count) externally tunable per dependency without redeploy — a flaky third-party API and a low-latency internal service need very different settings.

---

## 10. Circuit Breaker vs Related Patterns

| Pattern | What it protects | How it differs from CB |
|---|---|---|
| **Retry with backoff** | A single transient failure | Retries *increase* load on a struggling dependency; CB *reduces* it. Often combined: retry a few times, then let CB trip. |
| **Timeout** | A single hung call | Timeout still pays the full wait cost once; CB skips the wait entirely once tripped. |
| **Bulkhead** | Resource exhaustion across dependencies | Isolates thread/connection pools per dependency; CB decides *whether* to call at all. Complementary, not overlapping. |
| **Rate limiting** | The dependency, from overload by too much traffic | Rate limiting is usually server-side and proactive; CB is client-side and reactive to observed failures. |
| **Load shedding** | Server-side self-protection under overload | Server decides to drop requests based on its own load; CB is the caller deciding to stop sending. |
| **Endpoint ejection (LB-level)** | One unhealthy instance among many | Removes a single bad instance from rotation; CB breaks the call to a dependency (or endpoint) entirely. |

---

## 11. Failure Modes & Mitigations

| Failure | Symptom | Mitigation |
|---|---|---|
| Breaker never trips (threshold too high) | Cascading failure still occurs | Tune thresholds per dependency; use multiple trigger types (error + timeout + slow-call rate) |
| Breaker trips too eagerly (threshold too low) | Healthy dependency gets cut off during a minor blip | Minimum call volume before evaluating rate; sliding window instead of single-failure trip |
| Flapping (OPEN → HALF_OPEN → OPEN repeatedly) | Dependency partially recovers, then falls back over | Exponential backoff on retry interval; alert specifically on flap frequency |
| Thundering herd on recovery | All instances probe simultaneously the moment `open_until` elapses | Jitter the open-timer per instance; limit concurrent HALF_OPEN probes |
| Global breaker masks per-endpoint health | One bad endpoint trips calls to all endpoints of a multi-instance dependency | Scope breakers per-endpoint, not just per-service |
| Shared/distributed breaker state itself becomes a dependency | CB check adds latency or becomes a new single point of failure | Prefer per-instance uncoordinated state unless fleet-wide coordination is a hard requirement |
| CB-rejected calls indistinguishable from real failures | Alerting/logging can't tell fast-fail from actual downstream errors | Distinct exception type + separate metrics (`rejected_total` vs `failed_total`) |
| No fallback defined | OPEN state always surfaces as a hard error to the end user | Pair with cached/stale-data fallback or degraded-feature response where business logic allows |

---

## 12. Senior vs Staff Answer Differentiators

### Senior-level expectations
- Know the pattern exists and roughly what it does ("stops calling a failing service")
- Name the three states
- Know it prevents cascading failure

### Staff-level expectations (additional)

| Topic | What Staff-level adds |
|---|---|
| Placement | Explicitly reasons about client-side vs mesh-level vs why *not* server-side |
| State location | Explains per-instance in-memory state vs distributed state, and the tradeoff (coordination cost vs synchronized behavior) |
| Fast-fail mechanics | Describes it as *avoidance of a network call*, not just "returns an error faster" |
| Trip conditions | Sliding window + minimum volume threshold + multiple trigger types, not naive consecutive-count |
| Verification | Distinguishes CB-rejected vs real failures via distinct metrics/exception types; proposes chaos testing to *prove* it works |
| Response design | Chooses `503` deliberately over `500`/`504`/`429`, with reasoning; designs a fallback response, not just an error |
| Related patterns | Cleanly distinguishes CB from retry, timeout, bulkhead, rate limiting, load shedding — and shows how they compose |
| Failure modes | Proactively raises flapping, thundering herd on recovery, and per-endpoint vs per-service scoping |

### The single most important Staff differentiator

**Reasoning about the fast-fail mechanism precisely** — that OPEN state means the network call is *never attempted*, and that this is what actually stops thread/connection pool exhaustion. Many Senior-level answers describe circuit breakers correctly at a conceptual level but can't explain *mechanically* why fast-fail prevents cascading failure, or how an operator would confirm the breaker is actually doing its job in production rather than just assuming it.

---

## 13. Interview Time Allocation

For a circuit breaker deep-dive within a larger system design session (~10–15 min):

| Phase | Time | Focus |
|---|---|---|
| Problem & placement | 2 min | Cascading failure problem; where in the architecture it belongs |
| State machine | 2 min | Three states, transitions, why HALF_OPEN matters |
| Fast-fail mechanics | 2 min | In-memory check before I/O; per-instance vs shared state |
| Trip conditions | 2 min | Sliding window, minimum volume, multiple trigger types |
| Observability/verification | 3 min | Metrics, distinct exception types, chaos testing |
| Response contract | 2 min | Status code choice, headers, fallback response |
| Failure modes (if time) | 2 min | Flapping, thundering herd, per-endpoint scoping |

**Never cut:** state machine, fast-fail mechanics, and trip conditions — these are the core of the pattern. **Cut first if short on time:** related-pattern comparisons and failure mode enumeration.

---

## 14. Quick-Reference Cheatsheet

```
STATE MACHINE
─────────────
CLOSED    → normal; calls pass; failures counted in sliding window
OPEN      → fast fail; no network I/O attempted; timer governs retry
HALF_OPEN → limited trial calls; success→CLOSED, failure→OPEN (backoff)

WHERE IT LIVES
──────────────
✓ Client/SDK layer wrapping each outbound call
✓ Service mesh sidecar (Envoy outlier_detection) — preferred at scale
✗ NOT on the server/callee side (use rate limiting/load shedding there)
State: in-memory, per-instance, uncoordinated (usually — not shared)

FAST FAIL MECHANICS
────────────────────
if state == OPEN: raise CircuitBreakerOpenError()   // network call SKIPPED
→ value is in avoiding the call, not in clever detection

TRIP CONDITIONS (good practice)
────────────────────────────────
Sliding window error rate (not consecutive-failure count)
+ minimum call volume before evaluating
+ multiple triggers: error rate, timeout rate, slow-call rate

VERIFICATION
────────────
1. State exposed as metric/gauge + structured log on transition
2. Distinct exception type (CircuitBreakerOpenError) for rejected calls
3. Separate counters: rejected_total vs dependency_failed_total
4. Latency cliff-drop for rejected calls (near-zero, no I/O)
5. Chaos/game-day test to prove it end-to-end

API RESPONSE WHEN OPEN
───────────────────────
Status:  503 Service Unavailable   (NOT 500, 504, or 429)
Headers: Retry-After, X-Circuit-Breaker: OPEN, X-Request-Id
Body:    { "type": "CIRCUIT_BREAKER_OPEN", "retry_after_seconds": N }
Public API → sanitize body, don't leak dependency names
Prefer fallback (200 + stale/cached data) over bare error when possible

STAFF-LEVEL SIGNALS TO HIT
───────────────────────────
✓ Client-side placement reasoning (not server-side)
✓ Per-instance vs distributed state tradeoff
✓ Fast-fail = network call avoidance, explained mechanically
✓ Sliding window + min volume + multi-trigger trip conditions
✓ Distinct rejected-vs-failed metrics for verification
✓ 503 + Retry-After + fallback response design
✓ Flapping and thundering-herd-on-recovery as explicit failure modes
✓ Clean distinction from retry, bulkhead, rate limiting, load shedding
```

---

*Guide compiled for Senior & Staff-level system design interview preparation.*
*Topics: Circuit Breaker, Resilience Patterns, Fault Tolerance, Distributed Systems.*

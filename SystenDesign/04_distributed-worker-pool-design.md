# Distributed Worker Pool / Thread Pool — Design & Testing

## 1. Requirements

**Functional**
- Submit tasks, execute them across many machines, return results
- Support both fire-and-forget and request/response jobs

**Non-functional**
- Horizontal scalability
- Fault tolerance — a worker or the coordinator can die mid-task
- At-least-once (or exactly-once, if you pay for it) execution
- Backpressure when producers outrun capacity
- Low task-assignment latency

## 2. Architecture

```
 Client            Job queue            Coordinator            Registry
 ------  --------> ---------  -------->  -----------  <------> ---------
Submits           Kafka /              Assigns &                etcd /
 jobs              SQS                  tracks tasks           ZooKeeper
                                             |                      ^
                                             |                      | heartbeats
                                             v                      |
                                      +----------------+            |
                                      |  Worker pool    | <----------+
                                      |  (N nodes, each |
                                      |  running a      |
                                      |  local thread    |
                                      |  pool)           |
                                      +----------------+
                                             |
                                             v
                                       Result store
                                     (task outputs, status)
```

Solid arrows = data/control path. Dashed (registry) links = heartbeats for liveness and leader election.

### Components

**Job queue** — durable, ordered (or partitioned) buffer between producers and the coordinator. Kafka gives partitioned ordering and replay; SQS/RabbitMQ give simpler point-to-point delivery with visibility timeouts. This decouples producer rate from consumer rate.

**Coordinator** — the brain. Pulls (or has jobs pushed to it), decides which worker gets which task, and tracks task state (`pending → assigned → running → done/failed`). Runs as a leader-elected singleton (via the registry) so two coordinators never double-assign work.

**Registry / heartbeat service** — ZooKeeper or etcd, providing: leader election for the coordinator, worker liveness (ephemeral nodes that vanish if a worker dies), and dynamic worker discovery for scaling.

**Worker nodes** — each is a process running a **local thread pool** underneath a distributed shell. The worker's own concurrency is a classic bounded thread pool; the distributed layer feeds that pool across the fleet.

**Result store** — a table/DB keyed by task ID holding output and status, so clients can poll or get notified.

### Push vs. pull task assignment

- **Push (coordinator assigns directly):** lower latency, but the coordinator needs live knowledge of each worker's queue depth/capacity, and a slow worker can accumulate a backlog nobody rebalances.
- **Pull (workers request work when free):** naturally load-balances — a fast worker asks for more work more often — and the coordinator stays simple, but adds a poll-interval floor to latency and needs long-polling or streaming to avoid busy-waiting.

Most production systems (Celery, Cadence/Temporal, Sidekiq) lean pull, with push reserved for latency-critical paths.

### The local thread pool (per worker)

```java
ThreadPoolExecutor pool = new ThreadPoolExecutor(
    corePoolSize,       // threads kept alive even when idle
    maxPoolSize,         // ceiling under load
    keepAliveTime, TimeUnit.SECONDS,          // idle timeout for threads above core
    new LinkedBlockingQueue<>(queueCapacity),  // bounded! unbounded queues hide backpressure
    new ThreadPoolExecutor.CallerRunsPolicy()  // rejection policy when queue is full
);
```

- **Bounded queue is non-negotiable** — an unbounded queue means `maxPoolSize` never kicks in, and you OOM under sustained overload instead of applying backpressure.
- **Rejection policy** is your backpressure signal: `CallerRunsPolicy` throttles the producer thread itself (self-limiting, no drops); `AbortPolicy` throws and lets the caller retry/reroute; `DiscardOldestPolicy` trades correctness for freshness.
- Dispatch cost is O(1) amortized (queue push/pop); the pool contributes no algorithmic overhead beyond that.

### Fault tolerance

- **Worker crash mid-task:** the registry's ephemeral node disappears (session timeout, typically a few seconds); the coordinator sees the liveness gap and reassigns the task. Requires **idempotent task handlers**, or retries cause double-execution.
- **Coordinator crash:** leader election promotes a standby from the registry; in-flight assignments are recoverable from the durable queue plus a write-ahead assignment log, or by treating "assigned but no heartbeat within N seconds" as failed.
- **At-least-once vs. exactly-once:** at-least-once is the default (retry on ambiguous failure); exactly-once needs dedup — a task ID plus a "processed set" (Redis set, or a DB unique constraint) checked before commit.

### Scaling

Autoscale worker count off queue depth / consumer lag, not CPU alone — CPU is a lagging indicator for I/O-bound task pools. Each worker also autoscales its local pool size within `[core, max]` based on its own queue depth — two independent scaling loops: fleet size (coordinator/registry-driven) and per-worker concurrency (local, fast to react).

---

## 3. End-to-End Testing Approach

E2E here means testing the whole cluster — queue, coordinator, registry, workers, result store — as a black box. You assert on cluster-level outcomes (task got done exactly/at-least once, results are consistent) under conditions unit tests can't reach: real network delay, real process death.

### 3.1 Environment: build a disposable mini-cluster

Use **Testcontainers** to spin up the real dependencies per test run — mocking Kafka or etcd hides exactly the failure modes you're testing for.

```java
@Testcontainers
class WorkerPoolE2ETest {
    @Container static KafkaContainer queue = new KafkaContainer(...);
    @Container static GenericContainer<?> etcd = new GenericContainer<>("bitnami/etcd")...;
    @Container static PostgreSQLContainer<?> resultStore = new PostgreSQLContainer<>(...);
    // N worker containers, built from the actual worker image
    List<GenericContainer<?>> workers = IntStream.range(0, 3)
        .mapToObj(i -> new GenericContainer<>(WORKER_IMAGE)...)
        .toList();
}
```

Run the **actual coordinator and worker binaries** in containers, not in-process fakes — the point of E2E is exercising real process boundaries, real serialization, real timeouts.

### 3.2 Correctness scenarios (happy path first)

- Submit N tasks → assert all N appear exactly once in the result store, within a bounded time window (`Awaitility.await().atMost(...)`, never a fixed `Thread.sleep`).
- Submit tasks faster than one worker can drain → assert they distribute across ≥2 workers.
- Submit a task with a deliberately failing handler → assert it lands in a dead-letter/failed state after the configured retry count, not silently dropped.

### 3.3 Fault injection

| Fault | How to inject | What to assert |
|---|---|---|
| Worker dies mid-task | `docker kill` on a worker container while a task is in-flight | Task gets reassigned and completed within (heartbeat timeout + assignment latency) |
| Coordinator dies | Kill the leader, watch registry | A standby is promoted; in-flight assignments aren't lost or duplicated beyond the at-least-once contract |
| Network partition | **Toxiproxy** in front of the queue/registry to inject latency, jitter, or a full cut | Cluster degrades gracefully — no split-brain, no silent data loss |
| Registry unavailable | Pause the registry container | Coordinator stalls safely or fails over per design — not undefined behavior |
| Slow/flaky worker | Toxiproxy latency on one worker's connection | Coordinator (push) or the worker itself (pull) doesn't let it become a bottleneck |

Toxiproxy sits between components and injects latency, bandwidth caps, and connection resets on demand — the difference between "unit test with a mock" and "does this survive a bad network."

### 3.4 Idempotency / exactly-once verification

The test most designs skip. Deliberately force a duplicate delivery — kill a worker *after* it completes a task but *before* it acks the queue — and assert the task's side effect happened exactly once (e.g. a counter incremented once, not twice), not just that the message was redelivered. Testing "the message was retried" is easy; testing "the retry didn't double-charge" is the actual requirement.

### 3.5 Load and backpressure

- Ramp submission rate past total worker capacity, hold it there, then release. Assert: queue depth grows and drains predictably, no OOM, and the chosen rejection policy behaves as designed rather than silently dropping tasks.
- Kill and restart a worker under sustained load — assert the fleet absorbs the loss without the queue growing unbounded.

### 3.6 Observability as a test target

Assert on metrics/traces, not just final state — e.g. after a worker-crash scenario, assert a "task reassigned" event/metric fired. Relying only on "the task eventually completed" can't distinguish "worked as designed" from "got lucky," and misses the case where the system self-heals in an unintended way.

### 3.7 CI practicalities

- **No fixed sleeps** — every wait is `Awaitility` with a timeout tied to the SLA (heartbeat interval × N), or tests get flaky exactly when the real system is slow.
- **Isolate test runs** — unique topic/queue names or consumer groups per test class, so parallel CI runs don't cross-contaminate.
- Keep this suite separate from the fast unit/integration suite and run it less frequently (pre-merge or nightly) — full-cluster spin-up with fault injection takes minutes, not seconds, per run.

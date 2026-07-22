# Apache Kafka — System Design Interview Guide
### Senior & Staff Engineer Level

---

## Table of Contents

1. [Why Kafka](#1-why-kafka)
2. [Kafka Architecture](#2-kafka-architecture)
3. [Topics & Partitions](#3-topics--partitions)
4. [Producers](#4-producers)
5. [Consumers](#5-consumers)
6. [Consumer Groups](#6-consumer-groups)
7. [Replication](#7-replication)
8. [Leader Election](#8-leader-election)
9. [Offsets](#9-offsets)
10. [Delivery Guarantees](#10-delivery-guarantees)
11. [Ordering](#11-ordering)
12. [Scaling](#12-scaling)
13. [Fault Tolerance](#13-fault-tolerance)
14. [Performance](#14-performance)
15. [Kafka Internals](#15-kafka-internals)
16. [Transactions](#16-transactions)
17. [Exactly Once Semantics](#17-exactly-once-semantics)
18. [Kafka Connect](#18-kafka-connect)
19. [Kafka Streams](#19-kafka-streams)
20. [Monitoring & Troubleshooting](#20-monitoring--troubleshooting)
21. [Handling Consumer Crashes](#21-handling-consumer-crashes)
22. [Handling Producer Crashes](#22-handling-producer-crashes)
23. [Handling Broker Crashes](#23-handling-broker-crashes)
24. [CAP Theorem Positioning](#24-cap-theorem-positioning)
25. [Senior vs Staff Differentiators](#25-senior-vs-staff-differentiators)
26. [Interview Time Allocation](#26-interview-time-allocation)
27. [Quick-Reference Cheatsheet](#27-quick-reference-cheatsheet)

---

## Core Vocabulary (read this first)

| Term | One-line definition |
|---|---|
| **Producer** | Client that publishes (writes) records to a topic |
| **Broker** | A single Kafka server; stores partition data and serves reads/writes |
| **Topic** | A named, logical stream of records (like a table or a category) |
| **Partition** | An ordered, append-only log; a topic is split into N partitions for parallelism |
| **Consumer** | Client that subscribes to and reads records from topics |
| **Consumer Group** | A set of consumers that cooperatively divide up partitions to parallelize consumption |
| **KRaft Controller** | Quorum of broker nodes running the Raft protocol to manage cluster metadata (replaces ZooKeeper since Kafka 3.3+/4.0) |
| **ZooKeeper** (legacy) | External coordination service that older Kafka clusters (pre-KRaft) used for metadata and controller election |

---

## 1. Why Kafka

### The problem Kafka solves

Before Kafka, systems typically integrated point-to-point: Service A calls Service B calls Service C. This creates an **O(N²) integration problem** — N services need up to N(N-1)/2 direct connections, each with its own retry logic, backpressure handling, and failure mode.

```
Without a message bus (point-to-point):        With Kafka (hub-and-spoke log):

  A ──▶ B                                          A ──┐
  A ──▶ C                                          B ──┤
  A ──▶ D                                          C ──┼──▶ Kafka ──▶ X
  B ──▶ C                                          D ──┘         ──▶ Y
  B ──▶ D                                                        ──▶ Z
  C ──▶ D
  (6 connections for 4 services,               (N producers + M consumers,
   grows quadratically)                         fully decoupled)
```

### What makes Kafka different from a traditional message queue (RabbitMQ, SQS, ActiveMQ)

| Property | Traditional MQ | Kafka |
|---|---|---|
| Message lifecycle | Deleted after ack/consume | Retained for a configured period regardless of consumption |
| Consumption model | Destructive (message removed once consumed) | Non-destructive (consumers track their own offset; log is untouched) |
| Replay | Not supported (or requires DLQ tricks) | Native — reset offset and re-read history |
| Fan-out | Requires multiple queues/exchanges | Native — many consumer groups can independently read the same topic |
| Ordering | Per-queue, often lost with multiple consumers | Per-partition, guaranteed |
| Throughput model | Optimized for low-latency small messages | Optimized for high-throughput sequential I/O (batching, zero-copy) |
| Storage model | In-memory / transient disk queue | Append-only distributed commit log (durable by design) |

### The core abstraction: the distributed commit log

Kafka is fundamentally a **distributed, partitioned, replicated commit log**. Everything else — pub/sub semantics, streaming, event sourcing — is built on top of this one primitive: an ordered, immutable sequence of records that can be replayed from any point.

### Canonical use cases

- **Event-driven microservices** — decouple services; each publishes domain events, others react
- **Log aggregation** — centralize logs from thousands of hosts into one durable stream
- **Stream processing** — real-time ETL, fraud detection, clickstream analytics (Kafka Streams / Flink)
- **Event sourcing / CQRS** — the topic *is* the source of truth; rebuild state by replaying
- **Change Data Capture (CDC)** — Debezium streams DB row changes into Kafka topics
- **Metrics/telemetry pipelines** — high-volume ingestion buffer in front of storage/analytics
- **Commit log for distributed systems** — e.g., backing store for a materialized cache

### Senior-level answer
"Kafka decouples producers and consumers with a durable, replayable log."

### Staff-level answer
Frame it as **buying you three specific properties simultaneously that are normally in tension**: high write throughput (sequential disk I/O + batching), durability (replication), and multi-consumer fan-out with independent replay — and explain *why* the log abstraction, rather than a queue abstraction, is what makes all three possible at once.

---

## 2. Kafka Architecture

### High-Level Diagram

```
                    ┌────────────────────────────────────────┐
                    │              KAFKA CLUSTER              │
                    │                                          │
┌───────────┐       │   ┌──────────┐  ┌──────────┐  ┌────────┐│      ┌───────────┐
│ Producer 1│──────▶│   │ Broker 0 │  │ Broker 1 │  │Broker 2││─────▶│ Consumer  │
├───────────┤       │   │          │  │          │  │        ││      │ Group A   │
│ Producer 2│──────▶│   │ P0-lead  │  │ P1-lead  │  │P2-lead ││─────▶│ (3 cons.) │
├───────────┤       │   │ P1-repl  │  │ P2-repl  │  │P0-repl ││      └───────────┘
│ Producer N│──────▶│   │ P2-repl  │  │ P0-repl  │  │P1-repl ││      ┌───────────┐
└───────────┘       │   └──────────┘  └──────────┘  └────────┘│─────▶│ Consumer  │
                    │                                          │      │ Group B   │
                    │        ┌────────────────────┐            │      │ (1 cons.) │
                    │        │  KRaft Controller  │            │      └───────────┘
                    │        │  Quorum (3-5 nodes)│            │
                    │        │  (Raft protocol)   │            │
                    │        └────────────────────┘            │
                    └────────────────────────────────────────┘
```

### The moving parts

- **Producers** push records to topics; they decide which partition each record lands in.
- **Brokers** are the servers that store partition data on local disk and serve produce/fetch requests. A cluster is typically 3+ brokers.
- **The Controller** (a broker elected via KRaft/Raft, or historically via ZooKeeper) owns cluster-level metadata: which broker leads which partition, topic creation/deletion, broker liveness.
- **Consumers** pull records from partitions, tracking their position (offset) as they go.
- **ZooKeeper vs KRaft**: pre-3.3, Kafka depended on an external ZooKeeper ensemble for controller election, broker registration, and topic/ACL metadata. **KRaft (Kafka Raft)** — production-ready by 3.3 and mandatory by Kafka 4.0 — removes this external dependency. Metadata now lives in an internal Kafka topic (`__cluster_metadata`) replicated among a small quorum of controller nodes using the Raft consensus protocol.

### Why KRaft matters (Staff-level talking point)

```
ZooKeeper era problems:
  • Two systems to operate, patch, and monitor (ZK + Kafka)
  • Metadata propagation to brokers was pull-based and eventually consistent
    (controller pushes to ZK, brokers watch ZK — extra hop, extra latency)
  • Controller failover could take seconds to tens of seconds on large clusters
    (full metadata reload from ZK)
  • Partition count scaling was practically capped (~200K partitions/cluster)
    because ZK znode overhead grows linearly with partition count

KRaft fixes:
  • Single system; metadata is itself a replicated Kafka log
  • Controller failover in the sub-second range (Raft log replay, not full ZK dump)
  • Event-driven metadata propagation instead of polling
  • Scales to millions of partitions per cluster (tested to ~2M)
```

### Physical layout on a broker's disk

```
/kafka-logs/
  orders-0/                      ← topic "orders", partition 0
    00000000000000000000.log     ← segment file (records)
    00000000000000000000.index   ← offset → byte-position index
    00000000000000000000.timeindex
  orders-1/
    ...
  __consumer_offsets-12/         ← internal topic storing committed offsets
    ...
```

Each partition is a directory; each partition is broken into **segment files** (default 1 GB or a time-based roll), which is what enables efficient deletion of old data (drop whole segment files past the retention window, not record-by-record).

---

## 3. Topics & Partitions

### Topic

A topic is a logical category/feed name (e.g., `orders`, `payments.completed`). It has no inherent ordering guarantee across the whole topic — ordering is a **per-partition** property.

### Partition

A partition is the actual unit of storage, parallelism, and ordering.

```
Topic: orders (3 partitions)

Partition 0: [msg0][msg1][msg2][msg3][msg4] ...   offset increases →
Partition 1: [msg0][msg1][msg2] ...
Partition 2: [msg0][msg1][msg2][msg3] ...
```

- Each partition is an **ordered, immutable, append-only log**.
- Records within a partition are strictly ordered by **offset** (a monotonically increasing integer).
- There is **no ordering guarantee across partitions** of the same topic.
- Partition count determines the **maximum consumer parallelism** for a consumer group (see Section 6).

### Choosing the number of partitions

This is one of the most common Staff-level interview questions: "How many partitions would you create?"

```
Considerations:
  Throughput target ÷ per-partition throughput  →  lower bound on partition count
  Target consumer parallelism                    →  partitions ≥ max consumers
  Downstream fan-out (multiple consumer groups)   →  doesn't require more partitions
                                                       (each group tracks its own offsets)
  Metadata overhead                              →  more partitions = more replication
                                                       traffic, more open file handles,
                                                       slower controller failover (pre-KRaft)
  Key cardinality (if keyed)                     →  partitions should roughly match
                                                       or exceed distinct key count for
                                                       even distribution

Rule of thumb: start with partitions = max(desired_parallelism, throughput_target / ~10MB/s per partition),
then round up modestly for headroom. Over-provisioning is cheaper than a repartitioning migration later.
```

**Staff-level gotcha:** partition count can only be **increased**, never decreased, without deleting and recreating the topic. Increasing partition count also **breaks the key → partition mapping** for existing keyed data (see below), so consumers relying on "same key always same partition" for ordering will see a discontinuity at the moment you repartition.

### Partition assignment for a record (producer side)

```
If a key is provided:
    partition = hash(key) % num_partitions      (murmur2 hash, sticky within a key)
    → Guarantees all records with the same key land in the same partition
    → This is how you get per-key ordering (e.g., all events for order_id=42
      always go to the same partition, hence are always in order)

If no key is provided:
    Sticky partitioner (Kafka ≥ 2.4): batches records to one partition until
    that batch is full or lingerMs expires, then switches partitions.
    → Maximizes batch size → better throughput than pure round-robin,
      while still spreading load roughly evenly over time.
```

### Skewed partitions (hot partition problem)

If your key distribution is skewed (e.g., `tenant_id` where one tenant is 40% of traffic), that tenant's partition becomes a bottleneck — a single partition is served by a single broker for writes and consumed sequentially by a single consumer within a group.

**Mitigations:**
- **Salting the key**: `key = f"{tenant_id}#{random.randint(0,9)}"` spreads a hot key across 10 partitions, at the cost of losing strict per-tenant ordering (acceptable if you only need ordering per sub-key, or you re-aggregate downstream).
- **Custom partitioner**: override default hashing with business-aware logic.
- **Increase partitions + isolate hot keys**: route known-hot keys to a dedicated topic with a partitioner tuned specifically for them.

---

## 4. Producers

### Send Path

```
1. Application calls producer.send(topic, key, value)
2. Serializer converts key/value to bytes
3. Partitioner determines target partition (hash(key) or sticky)
4. Record appended to an in-memory RecordAccumulator batch for that partition
5. Sender thread flushes batches to the broker when:
     • batch.size reached, OR
     • linger.ms elapsed (whichever first)
6. Broker (partition leader) appends to its local log
7. Broker replicates to followers (per acks setting — see below)
8. Broker sends ack back to producer
9. Producer invokes the callback / completes the Future
```

### Key Producer Configs

| Config | Purpose | Staff-level nuance |
|---|---|---|
| `acks` | Durability vs latency tradeoff | `0` = fire-and-forget; `1` = leader ack only; `all`/`-1` = all in-sync replicas ack. `all` is required for no-data-loss guarantees |
| `retries` | Auto-retry transient failures | Combine with `max.in.flight.requests.per.connection=5` and idempotence to avoid reordering on retry |
| `linger.ms` | Batch delay for throughput | Trading a few ms of latency for dramatically larger batches (fewer, bigger network round trips) |
| `batch.size` | Max bytes per batch per partition | Larger batches = better compression ratio and throughput, at the cost of per-record latency |
| `compression.type` | `none`/`gzip`/`snappy`/`lz4`/`zstd` | `lz4` or `zstd` are the modern default choice — good ratio, low CPU cost |
| `enable.idempotence` | Prevents duplicate writes on retry | Default `true` since Kafka 3.0; assigns a Producer ID (PID) + sequence number per partition |
| `max.in.flight.requests.per.connection` | Pipelining depth | With idempotence on, can safely be up to 5 while preserving ordering (broker rejects out-of-order sequence numbers) |
| `buffer.memory` | Total memory for unsent batches | If exceeded, `send()` blocks or throws `BufferExhaustedException` |

### The acks Tradeoff (a favorite interview probe)

```
acks=0   Producer doesn't wait for any acknowledgment.
         Fastest, but records can be silently lost (broker crash before write).

acks=1   Leader writes to its local log and acks immediately —
         doesn't wait for followers to replicate.
         Records can be lost if the leader crashes before followers replicate.

acks=all Leader waits until all in-sync replicas (ISR) have the record.
         Combined with min.insync.replicas=2 (replication factor 3), tolerates
         one broker failure with zero data loss.
         Highest durability, highest latency.
```

**Staff-level framing:** `acks=all` alone is not sufficient for durability — it must be paired with `min.insync.replicas` on the topic. If `min.insync.replicas=1` and `acks=all`, you only actually need 1 replica to ack, which defeats the purpose. The real durability guarantee is `acks=all` **and** `min.insync.replicas ≥ 2` **and** `replication.factor ≥ 3`.

### Idempotent Producer (mechanism)

```
On first connect, broker assigns producer a unique PID (Producer ID).
Every batch sent carries: {PID, partition, sequence_number}

Broker tracks the last committed sequence number per (PID, partition).
  • If incoming seq == last_seq + 1  → accept, append
  • If incoming seq <= last_seq      → duplicate, drop silently, ack anyway
  • If incoming seq > last_seq + 1   → out-of-order gap, reject

This makes retries safe: a retried batch after a network timeout (where the
original write actually succeeded) is deduplicated at the broker, not the app.
```

---

## 5. Consumers

### Pull-based model

Kafka consumers **pull** records via `poll()`, unlike push-based systems. This is a deliberate design choice:

```
Push model (e.g., RabbitMQ push):
  Broker decides delivery rate → risk of overwhelming a slow consumer
  Requires complex flow-control/backpressure protocol between broker and consumer

Pull model (Kafka):
  Consumer decides its own rate by calling poll() when ready
  Consumer can batch-fetch efficiently and fall behind / catch up on its own schedule
  Same primitive naturally supports both real-time and batch consumers
```

### The poll loop

```java
while (running) {
    ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(500));
    for (ConsumerRecord<K, V> record : records) {
        process(record);
    }
    consumer.commitSync();   // or commitAsync(), or rely on auto-commit
}
```

### Key Consumer Configs

| Config | Purpose | Staff-level nuance |
|---|---|---|
| `fetch.min.bytes` | Min data before broker responds | Larger = fewer, more efficient fetches, added latency |
| `fetch.max.wait.ms` | Max time broker waits to satisfy `fetch.min.bytes` | Bounds worst-case latency when traffic is low |
| `max.poll.records` | Records returned per `poll()` call | Tune down if per-record processing is slow, to avoid `max.poll.interval.ms` timeout |
| `max.poll.interval.ms` | Max time between polls before consumer is considered dead | Must exceed your worst-case processing time for a batch, or the group coordinator evicts you mid-processing |
| `session.timeout.ms` | Heartbeat-based liveness timeout | Shorter = faster failure detection, more false-positive rebalances under GC pauses |
| `enable.auto.commit` | Auto-commit offsets periodically | Convenient but risks committing offsets for records not yet fully processed — **avoid for at-least-once/exactly-once workloads** |
| `auto.offset.reset` | Behavior when no committed offset exists | `earliest` (replay from start) vs `latest` (only new records) |
| `isolation.level` | Whether to see uncommitted transactional records | `read_committed` for exactly-once pipelines (see Section 16) |

### Deserialization & Schema Evolution

Production Kafka consumers almost always pair with a **schema registry** (Avro/Protobuf/JSON Schema via Confluent Schema Registry or Apicurio) rather than raw JSON:

```
Why: raw JSON has no contract — a producer can silently change a field type
     and break every downstream consumer with no compile-time or even
     runtime-until-crash signal.

Schema Registry gives you:
  • Compatibility checking on publish (BACKWARD/FORWARD/FULL modes)
  • Compact binary encoding (Avro/Protobuf are far smaller than JSON)
  • Schema ID embedded in each record header → consumer resolves schema
    dynamically, supporting rolling upgrades of producers and consumers
    independently
```

---

## 6. Consumer Groups

### The core parallelism mechanism

A **consumer group** is a set of consumer instances that share a `group.id` and cooperatively consume a topic — Kafka guarantees **each partition is assigned to exactly one consumer within a group at a time**.

```
Topic "orders" — 6 partitions

Consumer Group "billing-service" (3 consumer instances):

  Consumer A  ←  Partition 0, Partition 1
  Consumer B  ←  Partition 2, Partition 3
  Consumer C  ←  Partition 4, Partition 5

Consumer Group "analytics-service" (2 consumer instances) — SAME topic, independent:

  Consumer X  ←  Partition 0, 1, 2
  Consumer Y  ←  Partition 3, 4, 5

Both groups read all the same data independently — this is Kafka's native
fan-out. Neither group affects the other's offsets or lag.
```

### The golden rule

```
num_partitions determines MAX useful consumers in a single group.

6 partitions, 3 consumers → each gets 2 partitions (balanced)
6 partitions, 6 consumers → each gets 1 partition (max parallelism)
6 partitions, 8 consumers → 6 active, 2 sit completely IDLE (wasted)
```

**Staff-level gotcha:** adding more consumer instances than partitions doesn't increase throughput — it just leaves consumers idle. If you need more parallelism than your partition count allows, you must increase the partition count (which, as noted, is a one-way, non-trivial operation for keyed topics).

### Partition Assignment Strategies

| Strategy | Behavior | Tradeoff |
|---|---|---|
| `RangeAssignor` (legacy default) | Assigns contiguous partition ranges per topic per consumer | Can create imbalance across multiple topics (same consumer always gets the "leftover" range) |
| `RoundRobinAssignor` | Spreads all partitions round-robin across all consumers | Better balance, but a full rebalance reassigns everything |
| `StickyAssignor` | Round-robin-like balance, but **minimizes partition movement** on rebalance | Reduces the "stop the world" cost of rebalancing |
| `CooperativeStickyAssignor` (modern default) | Incremental cooperative rebalancing — only reassigns the *specific* partitions that need to move | Avoids the classic "stop-the-world" rebalance entirely |

### Rebalancing (the most operationally painful part of Kafka)

```
Triggers:
  • Consumer joins the group
  • Consumer leaves (graceful shutdown or crash/timeout)
  • Topic partition count changes
  • Consumer fails to poll() within max.poll.interval.ms (considered dead)

Eager rebalancing (old default):
  1. ALL consumers in the group revoke ALL their partitions
  2. Group coordinator computes new assignment
  3. ALL consumers receive new assignment
  → "Stop the world": entire group pauses consumption during rebalance,
    even if only one consumer joined/left.

Cooperative (incremental) rebalancing (KIP-429, modern default via
CooperativeStickyAssignor):
  1. Coordinator computes new assignment
  2. Only consumers whose partitions actually changed revoke/reassign
  3. Unaffected consumers keep consuming without interruption
  → May take 2 rebalance rounds internally, but only pauses partitions
    that must move.
```

### Static Group Membership (Staff-level scaling knob)

```
Problem: in Kubernetes/rolling-deploy environments, a pod restart is a
new consumer instance from Kafka's point of view → triggers a full
rebalance for what is really just a brief blip.

Fix: set group.instance.id to a stable identifier per pod (e.g., derived
from a StatefulSet ordinal). Kafka then treats a reconnect within
session.timeout.ms as the SAME member rejoining, not a new member —
no rebalance triggered at all.
```

---

## 7. Replication

### Why replication

A partition's data lives on one broker (the **leader**) plus N-1 **followers** that passively replicate it. This is what makes Kafka durable against broker failure.

```
Topic: orders, Partition 0, replication.factor = 3

  Broker 0: [LEADER]    ← serves all reads/writes for this partition
  Broker 1: [FOLLOWER]  ← replicates from leader, can serve as failover
  Broker 2: [FOLLOWER]  ← replicates from leader, can serve as failover

Followers pull from the leader exactly like consumers do (same fetch
protocol) — they are not a separate replication mechanism, they reuse
the log-fetch machinery.
```

### In-Sync Replica (ISR) Set

```
ISR = {leader} ∪ {followers that have fully caught up within
                   replica.lag.time.max.ms (default 30s)}

A follower is removed from the ISR if it falls behind for longer than
that window (e.g., due to GC pause, disk I/O saturation, network partition).

acks=all means: wait for ack from every member CURRENTLY in the ISR —
not necessarily all replication.factor replicas. If a follower drops
out of the ISR, acks=all still succeeds with fewer replicas, trading
some durability for continued availability.
```

### Unclean Leader Election (a critical durability knob)

```
Scenario: Leader crashes. Only followers OUTSIDE the ISR are alive
          (they were lagging when the leader died).

unclean.leader.election.enable = false (default, recommended):
  → Cluster refuses to elect a non-ISR replica as leader.
  → Partition becomes UNAVAILABLE until an ISR member returns.
  → Zero data loss, but availability sacrificed. (CP choice)

unclean.leader.election.enable = true:
  → Cluster elects the most-caught-up non-ISR replica anyway.
  → Partition stays AVAILABLE, but any records the old leader had
    that never replicated are PERMANENTLY LOST, and consumers may
    see silent data loss / duplicate delivery of already-consumed
    offsets that get overwritten.
  → (AP choice)

This single config is the textbook Kafka CAP theorem lever — the
interviewer wants to hear you name it explicitly.
```

---

## 8. Leader Election

### Partition leader election

Handled by the **Controller**, not by the replicas themselves negotiating directly:

```
1. Controller watches broker liveness (via KRaft heartbeats, formerly
   ZooKeeper ephemeral nodes)
2. On leader broker failure, controller selects a new leader from the
   ISR (preferring the "preferred leader" — the first replica in the
   assignment list — when available and in-sync)
3. Controller updates partition metadata (LeaderAndIsr) and propagates
   to all brokers
4. Producers/consumers refresh metadata (via a NotLeaderForPartition
   error triggering a metadata refresh) and redirect to the new leader
```

### Preferred Leader & Leader Skew

```
Kafka assigns a "preferred leader" (first replica in the replica list)
at partition-creation time to spread leadership evenly across brokers.

Over time, leadership can drift (e.g., after a broker restart, its
partitions get new leaders elected elsewhere and don't automatically
move back) → leader skew → uneven load across brokers.

Fix: auto.leader.rebalance.enable=true (periodic background job that
rebalances leadership back to preferred leaders when safe), or trigger
manually via kafka-leader-election.sh.
```

### The Controller's own election (cluster-level, not partition-level)

```
KRaft mode:
  Controller nodes form a Raft quorum (typically 3 or 5 dedicated nodes).
  One is elected Raft leader (the "active controller") via the standard
  Raft leader-election protocol (randomized election timeouts, majority
  vote, term numbers).
  If the active controller dies, remaining quorum members re-elect within
  the Raft timeout window — typically sub-second, since it's just a
  Raft leader election, not a full ZK-metadata reload.

ZooKeeper mode (legacy):
  The first broker to successfully create an ephemeral /controller znode
  in ZooKeeper becomes the controller. If it dies, the znode disappears
  (ZK session timeout), triggering a watch-based notification storm, and
  the next broker to win the race becomes the new controller — then must
  reload the FULL metadata for every partition in the cluster from ZK,
  which is what made large-cluster failover slow.
```

---

## 9. Offsets

### What an offset is

A **per-partition, monotonically increasing integer** identifying a record's position in the log. Offsets are meaningful only within a single partition — offset 5 in partition 0 and offset 5 in partition 1 are unrelated records.

```
Partition 0:  [off:0][off:1][off:2][off:3][off:4] ← next write appends at off:5
                                    ▲
                        consumer's "current position" (next to fetch)
                                ▲
                        consumer's "committed offset" (last confirmed processed)
```

### Committed offset vs current position — the gap matters

```
current position: where the consumer's poll() cursor is (in-memory, client-side)
committed offset:  what has been durably recorded (in __consumer_offsets topic)
                    as "processed" — this is what a NEW consumer instance
                    resumes from after a rebalance or restart.

The gap between these two is exactly the set of records that would be
RE-DELIVERED if the consumer crashed right now. Minimizing or managing
this gap is the entire game of delivery-guarantee engineering (Section 10).
```

### Where offsets are stored

Not in ZooKeeper (a common outdated assumption) — since Kafka 0.9, committed offsets live in an **internal, compacted Kafka topic**: `__consumer_offsets` (50 partitions by default), keyed by `(group.id, topic, partition)`. This makes offset storage itself durable, replicated, and horizontally scalable — the same log-based mechanism as everything else in Kafka.

### Commit strategies

| Strategy | Mechanism | Tradeoff |
|---|---|---|
| Auto-commit | Background thread commits every `auto.commit.interval.ms` | Simplest, but can commit offsets for records not yet finished processing → data loss risk on crash |
| Sync commit (`commitSync`) | Explicit, blocking call after processing a batch | Safe, but adds latency (blocks until broker acks) |
| Async commit (`commitAsync`) | Explicit, non-blocking, with callback | Higher throughput; must handle out-of-order commit callbacks carefully |
| Manual per-record commit | Commit after every single record | Strongest guarantee, worst throughput — rarely justified |
| External offset store | Store offset in the same transaction as the side-effect (e.g., a DB row) | Enables true exactly-once for "consume → write to DB" pipelines (see Section 17) |

### Resetting offsets (operational tool, not just a config)

```bash
# Replay an entire topic from the beginning for a given group
kafka-consumer-groups.sh --bootstrap-server broker:9092 \
  --group billing-service --topic orders \
  --reset-offsets --to-earliest --execute

# Replay from a specific timestamp (e.g., "redo the last hour")
kafka-consumer-groups.sh --bootstrap-server broker:9092 \
  --group billing-service --topic orders \
  --reset-offsets --to-datetime 2026-07-21T10:00:00.000 --execute
```

This is the operational superpower that traditional queues don't give you: **bugs in a consumer can be fixed and the historical data reprocessed**, as long as it's still within the topic's retention window.

---

## 10. Delivery Guarantees

### The three levels

```
AT-MOST-ONCE
  Commit offset BEFORE processing.
  If consumer crashes mid-processing → record is lost (never reprocessed).
  Fastest, simplest, rarely what you actually want.

AT-LEAST-ONCE (most common in practice)
  Process record, THEN commit offset.
  If consumer crashes after processing but before commit → record is
  reprocessed on restart → DUPLICATE delivery, never loss.
  Requires idempotent downstream processing (see below) to be safe.

EXACTLY-ONCE
  Each record affects downstream state exactly once, even across
  producer retries, consumer crashes, and rebalances.
  Achieved via Kafka transactions (Section 16) for Kafka-to-Kafka
  pipelines, or via idempotent writes / transactional outbox patterns
  for Kafka-to-external-system pipelines.
```

### Making at-least-once safe: idempotent consumers

Since Kafka's default realistic guarantee for most pipelines is at-least-once, the standard Staff-level answer to "how do you prevent duplicate processing" is: **make the consumer's side effect idempotent**, not chase perfect exactly-once everywhere.

```
Techniques:
  • Natural idempotency key: use the record's own key + offset (or a
    business ID like order_id) as a de-dup key in the downstream store
    — "INSERT ... ON CONFLICT DO NOTHING" / upsert semantics
  • Dedup table with TTL: track (partition, offset) or a business dedup
    key seen in the last N hours, skip reprocessing
  • Design writes to be naturally idempotent: "set balance = 500"
    instead of "add 10 to balance" — replaying a duplicate is a no-op
```

### Producer → Consumer guarantee matrix

| Producer config | Consumer config | End-to-end guarantee |
|---|---|---|
| `acks=0` | any | Possible loss at producer, possible duplicate at consumer |
| `acks=all`, no idempotence | commit-after-process | At-least-once (retries can duplicate at broker) |
| `acks=all` + idempotence | commit-after-process | At-least-once, no broker-level dupes from retries, app-level dupes possible on consumer crash |
| `acks=all` + idempotence + transactions | `isolation.level=read_committed` + transactional consume-transform-produce | Exactly-once (within Kafka topology) |

---

## 11. Ordering

### The fundamental rule

**Kafka guarantees ordering only within a single partition.** There is no cross-partition ordering guarantee, by design — enforcing it would require serializing all writes through one point, destroying the parallelism that is Kafka's whole value proposition.

```
Want: all events for a given entity processed in order (e.g., order_id=42
      status transitions: CREATED → PAID → SHIPPED must never be seen
      out of order)

Solution: use order_id as the partition key.
  hash(order_id) % num_partitions → same partition, every time
  → All events for order_id=42 are strictly ordered relative to each
    other (they're all in one partition's log).
  → Events for order_id=42 and order_id=99 have NO ordering relationship
    to each other (likely different partitions) — and that's fine,
    because they're independent entities.
```

### Things that silently break ordering (Staff-level gotchas)

```
1. Retries without idempotence + max.in.flight.requests > 1:
     Batch 2 can be acked before a retried Batch 1 — records land
     out of order within the partition.
     Fix: enable.idempotence=true (broker enforces sequence order,
     rejecting out-of-order retries) — default since Kafka 3.0.

2. Repartitioning a topic (increasing partition count):
     hash(key) % num_partitions changes for EVERY key the moment
     partition count changes. All "same key → same partition" history
     is broken at that point — new records for order_id=42 may land
     in a different partition than historical ones.
     Fix: plan partition count upfront for keyed topics; if you must
     repartition, treat it as a versioned migration (new topic, dual-write
     transition period, cutover).

3. Multiple producers writing the same key from different processes
   with no coordination + acks=0 or low acks:
     Network reordering between producer and broker is possible under
     retries. Idempotent producer protects this per-producer-session,
     but two independent producer instances writing the same key have
     no ordering guarantee relative to EACH OTHER — only within each
     producer's own retry sequence.

4. Consumer-side reordering via parallel processing:
     A consumer that pulls a batch and then fans it out to a thread
     pool for parallel processing can finish processing out of order,
     even though it READ them in order. If downstream ordering matters,
     you must either process sequentially per key within the consumer,
     or shard the thread pool by key.
```

---

## 12. Scaling

### Levers, in the order a Staff engineer should reach for them

```
1. Increase partition count
   → more parallel consumers, more parallel disk I/O across brokers
   → one-way door (can't decrease); breaks key→partition mapping if keyed

2. Add brokers + reassign partitions
   → horizontal scale-out of storage and I/O capacity
   → use kafka-reassign-partitions.sh, throttle reassignment traffic
     (reassignment itself is a full replica copy — can saturate network
     if unthrottled)

3. Tune batching (producer: batch.size, linger.ms)
   → fewer, larger network round trips = higher throughput per partition
   → the single highest-leverage producer-side throughput knob

4. Compression (lz4/zstd)
   → reduces network AND disk I/O simultaneously — the rare optimization
     that's nearly free (small CPU cost, large I/O savings)

5. Tiered storage (Kafka 3.6+, KIP-405)
   → offload older log segments to object storage (S3/GCS) while
     keeping recent "hot" segments on local broker disk
   → decouples storage capacity from broker compute/local-disk sizing
   → dramatically cheaper long-retention topics (e.g., 1-year audit logs)

6. Follower fetching (KIP-392, rack-aware reads)
   → consumers in a given AZ/rack can fetch from the NEAREST in-sync
     replica instead of always the leader → cuts cross-AZ network
     transfer cost significantly in cloud deployments
```

### Scaling consumer-side throughput without adding partitions

If you're capped on partition count (or don't want to repartition) but need more consumer-side parallelism, decouple **I/O parallelism from partition count**: have each consumer thread pull from Kafka and hand off work to an internal worker pool, as long as you don't need strict ordering across that pool (or you shard the pool by key to preserve it).

### Multi-cluster / geo scaling

```
Active-Passive (MirrorMaker 2 / Confluent Replicator):
  Primary cluster serves all traffic; secondary is a replicated
  standby for disaster recovery. Simple, but secondary capacity
  is idle most of the time.

Active-Active:
  Both clusters serve local read/write traffic; changes replicate
  bidirectionally. Lower latency for geo-distributed users, but
  requires careful handling of conflicting writes / offset translation
  (MM2 rewrites offsets, they are NOT preserved 1:1 across clusters).

Stretch cluster (single cluster spanning regions):
  Generally discouraged — Kafka's replication protocol is
  latency-sensitive (ISR membership depends on replica.lag.time.max.ms);
  cross-region latency causes constant ISR churn.
```

---

## 13. Fault Tolerance

### What Kafka tolerates natively

| Failure | Kafka's built-in mitigation |
|---|---|
| Broker crash | Replicas elsewhere in the ISR; controller elects new leader for affected partitions |
| Disk failure on a broker | Same as above — replication factor absorbs it, replace + rebuild the broker |
| Network partition (broker isolated) | Isolated broker drops out of ISR; controller (on the majority side) elects new leaders; isolated broker rejoins/catches up once healed |
| Controller crash (KRaft) | Raft quorum re-elects new active controller in sub-second range |
| Consumer crash | Group coordinator detects missed heartbeats, triggers rebalance, reassigns partitions to surviving consumers |
| Producer crash mid-send | In-flight records may be lost from the app's perspective if not yet acked; already-acked records are safely persisted regardless |

### What Kafka does NOT protect you from (interview traps)

```
• Data loss from acks=0 or acks=1 + unclean leader election enabled
  → Kafka gives you the KNOBS for durability; it doesn't force safe
    defaults on you at every layer. You must configure for it.

• Application-level bugs producing bad data
  → Kafka faithfully replicates and retains whatever you write,
    including garbage. Schema registry + validation at the produce
    boundary is your responsibility.

• Full-cluster or full-region outage
  → Requires cross-cluster replication (MirrorMaker 2) for true DR;
    a single Kafka cluster, however well replicated internally, is
    still one blast radius if the whole region goes down.

• Slow consumers causing unbounded retention growth
  → Kafka retains data per its retention policy REGARDLESS of consumer
    lag. A stuck consumer doesn't stop the topic from expiring old
    segments — it just means that consumer will hit
    OffsetOutOfRangeException and skip forward (data loss for that
    consumer) once retention catches up to it.
```

### Replication factor and fault-tolerance math

```
replication.factor = 3, min.insync.replicas = 2

Tolerates: 1 broker failure with zero data loss and continued availability
           (2 remaining ISR members still satisfy min.insync.replicas)

Does NOT tolerate: 2 simultaneous broker failures without either
  (a) unavailability (writes blocked, NotEnoughReplicasException), or
  (b) manually lowering min.insync.replicas (reduces durability further)

This is directly analogous to RF=3 in Cassandra or a 3-node Raft/Paxos
group — 3 is the minimum for surviving a single failure while keeping
majority-based durability semantics.
```

---

## 14. Performance

### Why Kafka is fast — the core mechanisms

```
1. Sequential disk I/O
   Even on spinning disks, sequential writes/reads can rival RAM
   random-access speeds. Kafka's append-only log design means every
   write is sequential — no seeks. Same for reads: consumers reading
   in offset order get sequential reads too.

2. Zero-copy transfer (sendfile syscall)
   Normally: disk → kernel buffer → application buffer → socket buffer
             → NIC (4 copies, 2 context switches)
   Kafka:    disk → kernel buffer → NIC directly via sendfile()
             (2 copies, far fewer context switches)
   This is why Kafka can serve consumer fetches at near network-line-rate
   without CPU becoming the bottleneck.

3. Page cache reliance (not a custom cache)
   Kafka deliberately does NOT maintain an in-process JVM cache of
   recent records — it relies on the OS page cache. Sequential writes
   populate the page cache "for free," so recent-data reads (the most
   common consumer pattern — near-real-time consumers reading close
   to the tail) are served from RAM without ever touching disk.

4. Batching everywhere
   Producer batches records per partition before sending; brokers
   append batches; consumers fetch batches. Every layer amortizes
   per-request overhead (network round trip, syscall, fsync) across
   many records instead of paying it per-record.

5. Partition-level parallelism
   Throughput scales roughly linearly with partition count (up to
   broker/disk/network saturation), because each partition can be
   independently written, replicated, and read.
```

### Producer-side performance tuning cheat sheet

```
High-throughput batch pipeline:
  linger.ms=20-100, batch.size=256KB-1MB, compression.type=zstd,
  acks=1 (if some loss tolerable) or acks=all (if not)

Low-latency (e.g., trading, alerting):
  linger.ms=0-5, smaller batch.size, acks=1,
  accept lower throughput per connection, scale via more partitions
  and more producer instances instead
```

### Consumer-side performance tuning

```
fetch.min.bytes=~50KB-1MB (batch more per network round trip)
max.partition.fetch.bytes tuned to avoid one huge partition starving
  fetch bandwidth for others in the same fetch request
Deserialize + process in a tight loop; avoid blocking I/O per record
  inside the poll loop (push to an internal queue/thread pool instead,
  respecting max.poll.interval.ms)
```

---

## 15. Kafka Internals

### The Log Segment structure

```
Each partition is physically a sequence of segment files:

  00000000000000000000.log        ← record data (batches of records)
  00000000000000000000.index      ← sparse index: offset → byte position
  00000000000000000000.timeindex  ← sparse index: timestamp → offset

A new segment rolls when EITHER:
  • log.segment.bytes reached (default 1 GB), OR
  • log.roll.ms/hours elapsed (default 7 days)

Why sparse indexes? A dense index (every offset mapped) would be huge.
A sparse index (every log.index.interval.bytes, default 4KB) trades a
small linear scan within the interval for a much smaller index that
fits comfortably in the page cache.
```

### Record batch format (v2, since Kafka 0.11)

```
RecordBatch header includes:
  • base offset, batch length, magic byte (format version)
  • CRC (integrity check)
  • attributes (compression codec, timestamp type, is-transactional,
    is-control-batch)
  • producer ID + producer epoch + base sequence  ← idempotence/transactions
  • record count

Individual records within a batch are DELTA-ENCODED against the batch
header (offset delta, timestamp delta) — this is a major space saving
over the old per-record format and is what enables efficient batch-level
compression (compress once per batch, not per record).
```

### Log compaction (an alternate retention strategy)

```
Standard retention: delete records older than retention.ms/bytes
                     regardless of key (good for event streams)

Compacted topics (cleanup.policy=compact): retain only the LATEST
  record for each key; older records with the same key are eventually
  removed by a background compaction thread.

  Use case: __consumer_offsets (only the latest offset per group/
  partition matters), or a topic acting as a changelog / KTable
  materialization for Kafka Streams (only current state per key
  matters, not full history).

  Tombstones: a record with a null value for a key signals "delete
  this key" — compaction removes the key entirely after
  delete.retention.ms.
```

### Request handling architecture

```
Broker uses a Reactor-style multi-threaded architecture:

  Acceptor thread(s)     → accept new socket connections
  Network (processor)
  threads (num.network.
  threads)                → read requests off the wire, deserialize,
                             hand off to a shared request queue
  I/O threads (num.io.
  threads)                → pull from request queue, do the actual
                             log append/fetch work, produce response
  Purgatory                → holds requests waiting on a condition
                             (e.g., a produce request with acks=all
                             waiting for ISR acks, or a fetch request
                             waiting for fetch.min.bytes to accumulate)
                             — implemented as a time-wheel data structure
                             for efficient timeout handling at scale
```

---

## 16. Transactions

### The problem transactions solve

A "consume-transform-produce" pipeline (read from topic A, process, write to topic B, commit offset on A) has **three separate operations** that need to succeed or fail atomically. Without transactions, a crash between steps can produce either duplicate output or lost output.

```
Without transactions:
  1. Consume record from topic A
  2. Process it
  3. Produce result to topic B     ← crash here...
  4. Commit offset on topic A      ← ...before this happens
  → On restart: record is re-consumed from A (offset wasn't committed)
    and re-produced to B → DUPLICATE in B.

With transactions:
  Steps 3 and 4 are wrapped in a single Kafka transaction — the offset
  commit itself becomes a write to the (transactional) __consumer_offsets
  topic, included in the SAME atomic unit as the produce to topic B.
  Either both happen or neither does, visible to downstream consumers
  in read_committed mode.
```

### Mechanics

```
producer.initTransactions()     → registers a unique transactional.id,
                                   broker (Transaction Coordinator)
                                   fences off any older "zombie" producer
                                   instance with the same transactional.id
                                   (bumps producer epoch)

producer.beginTransaction()
producer.send(topicB, result)
producer.sendOffsetsToTransaction(offsets, consumerGroupMetadata)
producer.commitTransaction()    → Transaction Coordinator writes a
                                   COMMIT marker to every partition
                                   involved (2-phase-commit-like protocol)

If the producer crashes mid-transaction:
  Transaction Coordinator's transaction timeout
  (transaction.timeout.ms, default 60s) fires, aborts the transaction,
  writes ABORT markers. read_committed consumers never see the
  half-written data.
```

### Zombie fencing (Staff-level nuance)

```
Scenario: producer instance P1 hangs (e.g., long GC pause), the
orchestration layer (e.g., Kubernetes) assumes it's dead and starts
P2 with the SAME transactional.id to take over. Then P1 wakes up and
tries to keep writing.

Fix: every producer with a given transactional.id gets a monotonically
increasing "producer epoch" from the Transaction Coordinator on
initTransactions(). P2's init bumps the epoch. Any subsequent request
from P1 (old epoch) is rejected by the coordinator — P1 is "fenced."
This is what actually prevents the zombie-writer split-brain problem,
and it's a direct analog to fencing tokens in distributed locks
(e.g., Chubby/ZooKeeper lease patterns).
```

---

## 17. Exactly Once Semantics

### Scoping the claim precisely (the single most important thing to say in an interview)

"Exactly-once" in Kafka means **exactly-once processing semantics within Kafka-to-Kafka topologies** (idempotent producer + transactions + `read_committed` consumers) — it does **not** mean "exactly-once delivery" across an arbitrary network in the abstract sense (which the Two Generals' Problem proves is impossible in general). Be precise here — this is a well-known Staff-level tell.

### Exactly-once within Kafka (Kafka Streams / consume-transform-produce)

```
Achieved by combining:
  1. Idempotent producer (dedupes retries at the broker level)
  2. Transactions (atomic multi-partition writes, including offset commits)
  3. read_committed isolation level on consumers downstream
  → Kafka Streams sets processing.guarantee=exactly_once_v2 to enable
    this automatically for its entire topology.
```

### Exactly-once when an EXTERNAL system is involved (Kafka → DB, Kafka → API)

Kafka's transaction protocol can't extend into a system it doesn't control. Two standard patterns:

```
Pattern 1: Idempotent external writes (most common, simplest)
  Design the external write to be a no-op on duplicate delivery:
  UPSERT keyed on a business ID, or a dedup table with (partition,
  offset) as the idempotency key checked in the same DB transaction
  as the actual write.
  → Effectively "exactly-once" from the OUTCOME's perspective, even
    though delivery itself is at-least-once.

Pattern 2: Transactional outbox (for the OTHER direction — DB → Kafka)
  Write the business change AND an "event to publish" row to the DB
  in the same local DB transaction. A separate CDC process (e.g.,
  Debezium reading the DB's write-ahead log) publishes that outbox
  row to Kafka. This avoids the classic "dual write" problem (DB
  commit succeeds, Kafka publish fails, or vice versa) by making the
  DB transaction the single source of atomicity, and CDC read of the
  WAL the reliable bridge to Kafka.
```

### Performance cost of exactly-once

Transactions add overhead: extra round trip for `initTransactions`, control (marker) records written to every involved partition, and consumers must buffer records until they see the commit/abort marker (added latency, especially with long `transaction.timeout.ms`). **Staff framing:** exactly-once is not a free upgrade over at-least-once — it's a deliberate tradeoff of throughput/latency for correctness guarantees, and idempotent-at-least-once is usually the pragmatic default unless the business logic genuinely can't tolerate any duplicate-handling logic downstream.

---

## 18. Kafka Connect

### What it is

A framework for moving data **into and out of** Kafka without hand-writing producer/consumer code — a plugin ecosystem of pre-built, configuration-driven connectors.

```
                Source Connectors                Sink Connectors
  ┌──────────┐      │                                 │      ┌──────────┐
  │ Postgres │──────▶│                                 │─────▶│ S3       │
  │ (via CDC/│      │                                 │      │ Snowflake│
  │ Debezium)│      │            KAFKA                │      │ Elastic  │
  └──────────┘      │                                 │      │ BigQuery │
  ┌──────────┐      │                                 │      └──────────┘
  │ MySQL,   │──────▶│                                 │
  │ MongoDB  │      └─────────────────────────────────┘
  └──────────┘
```

### Architecture

```
Connect Worker (JVM process, runs in distributed or standalone mode)
  │
  ├── Distributed mode: workers form a group (like a consumer group)
  │   coordinated via Kafka itself (config/offset/status stored in
  │   internal Kafka topics) — connectors and their tasks are
  │   rebalanced across workers automatically, horizontally scalable
  │   and fault tolerant, same underlying group-coordination protocol
  │   as consumer groups.
  │
  └── Standalone mode: single process, offsets stored in a local file
      — simple, but no fault tolerance; fine for dev/test.

Connector = configuration + class defining HOW to connect to a system
Task = the actual unit of parallel work (a connector splits into N tasks,
       e.g., one task per DB table, or one task per Kafka partition for
       a sink)
```

### Source vs Sink

| Type | Direction | Example | Key concern |
|---|---|---|---|
| Source | External system → Kafka | Debezium (CDC from Postgres/MySQL WAL) | Exactly-once source offset tracking (source-specific: e.g., WAL LSN) |
| Sink | Kafka → External system | S3 Sink, JDBC Sink, Elasticsearch Sink | Idempotent writes to handle at-least-once redelivery from Kafka's side |

**Staff-level note:** CDC via Debezium is the standard, production-grade solution to the "dual write" problem mentioned in Section 17 — instead of your application writing to both a DB and Kafka (which can fail independently), you write only to the DB, and Debezium tails the DB's replication log to reliably publish changes, guaranteeing the DB is always the single source of truth.

---

## 19. Kafka Streams

### What it is

A client library (not a separate cluster/service — it runs *inside your application process*) for building stream-processing applications directly on top of Kafka consumer/producer APIs, with higher-level abstractions for stateful processing.

### Core abstractions

```
KStream<K,V>   — an unbounded stream of independent events (record stream)
KTable<K,V>    — a changelog / continuously-updated table (latest value
                 per key) — conceptually the "compacted topic" view
GlobalKTable   — a KTable replicated in FULL to every stream task
                 (for small reference/lookup data, avoids the need for
                 co-partitioning on joins)

Stream-Table duality: a stream of updates IS a table (apply each update
in order → get current state); a table's changes over time ARE a stream.
This duality is why joins between the two are so natural in the API.
```

### Stateful processing & local state stores

```
For aggregations/joins, Kafka Streams maintains LOCAL state
(RocksDB-backed key-value store) per stream task, partitioned the same
way as the input topics.

  Input topic partition 0 ──▶ Stream Task 0 ──▶ RocksDB state store 0
  Input topic partition 1 ──▶ Stream Task 1 ──▶ RocksDB state store 1

Fault tolerance for state: every local state store is backed by a
CHANGELOG TOPIC (compacted) in Kafka. If a task migrates to another
instance (rebalance) or a machine dies, the new owner rebuilds its
RocksDB store by replaying the changelog topic from scratch (or from
a local disk-cached copy + delta replay if using standby replicas).

Standby replicas (num.standby.replicas > 0): pre-warm a copy of the
state store on another instance BEFORE it's needed, so failover doesn't
require a full changelog replay — dramatically reduces recovery time
for large stateful topologies.
```

### Co-partitioning requirement for joins

```
To join KStream A with KStream/KTable B, both MUST have:
  • the same number of partitions
  • the same partitioning key/scheme

Why: Kafka Streams joins happen locally within a task — task N only
ever sees partition N of every input. If keys aren't co-located in the
same partition number across both topics, matching records would never
end up on the same task to be joined. If inputs aren't co-partitioned,
Streams requires an explicit repartition step (which writes to an
intermediate topic, adding latency and storage cost) before the join.
```

---

## 20. Monitoring & Troubleshooting

### The metrics that actually matter (Staff-level triage list)

| Metric | What it tells you | Alert threshold guidance |
|---|---|---|
| **Consumer lag** (`records-lag` / `kafka-consumer-groups.sh --describe`) | Gap between latest produced offset and last committed offset | Alert on sustained growth, not absolute value (some lag is normal under bursty load) |
| **Under-replicated partitions** | Partitions where ISR < replication factor | Any sustained non-zero value = a broker is struggling or down |
| **Offline partitions** | Partitions with NO leader (unavailable for writes) | Must be zero; non-zero is a hard outage |
| **Active controller count** | Should always be exactly 1 cluster-wide | 0 = no controller (cluster metadata frozen); >1 = split-brain bug |
| **Request handler / network thread idle %** | Broker CPU/thread saturation | Sustained near-0% idle = broker is the bottleneck, add capacity |
| **`BytesInPerSec` / `BytesOutPerSec`** | Throughput per broker/topic | Compare against known NIC/disk ceiling |
| **Purgatory size** | Requests waiting on ISR acks or fetch.min.bytes | Growing purgatory = slow followers or under-provisioned fetch settings |
| **ISR shrink/expand rate** | Frequency of replicas falling in/out of sync | High churn = network instability or broker overload, not just a one-off blip |
| **Producer/consumer error rates by exception type** | `NotLeaderForPartition`, `RequestTimedOut`, etc. | Spikes correlate with leader elections/rebalances — cross-reference timing |

### Common failure signatures and root causes

```
Symptom: Consumer lag growing steadily, CPU on consumer host is low
  → Likely I/O-bound downstream call (DB write, external API) per record;
    the poll loop is blocked on business logic, not on Kafka itself.

Symptom: Frequent, unexplained rebalances
  → Check max.poll.interval.ms vs actual processing time per poll batch;
    check for GC pauses exceeding session.timeout.ms;
    check for consumer pods being killed/restarted (K8s liveness probes
    too aggressive) — consider static membership (Section 6).

Symptom: Under-replicated partitions spike during peak traffic
  → Followers can't keep up with leader's write rate — check follower
    disk I/O, network saturation between brokers, or
    replica.lag.time.max.ms being too tight for your workload's
    natural jitter.

Symptom: Producer throughput far below expected, acks=all
  → Check min.insync.replicas isn't forcing waits on a struggling
    broker; check batch.size/linger.ms are tuned (default linger.ms=0
    is a common silent throughput killer); check compression is enabled.

Symptom: OffsetOutOfRangeException on consumers
  → Consumer fell behind further than the topic's retention window;
    Kafka deleted data the consumer hadn't read yet. Either the
    consumer was down too long, or retention.ms is too short for the
    realistic worst-case consumer downtime you need to tolerate.
```

### Tooling

```
kafka-consumer-groups.sh   — lag, group state, reset offsets
kafka-topics.sh            — describe partitions, leader/ISR state
kafka-reassign-partitions.sh — move partitions between brokers, throttle
kafka-log-dirs.sh          — per-broker disk usage by topic-partition
JMX metrics + Prometheus/Grafana (via JMX exporter) — the production
  standard for dashboards/alerting
Cruise Control (LinkedIn OSS) — automated partition rebalancing and
  broker capacity-aware load balancing at scale
```

---

## 21. Handling Consumer Crashes

### What happens automatically

```
1. Consumer stops sending heartbeats to the Group Coordinator
2. After session.timeout.ms with no heartbeat (or a poll() gap
   exceeding max.poll.interval.ms), the coordinator marks it dead
3. Coordinator triggers a rebalance
4. The dead consumer's partitions are reassigned to surviving members
   (cooperative rebalancing minimizes disruption to the healthy ones)
5. New owner resumes from the LAST COMMITTED offset for those partitions
   → any records processed-but-not-yet-committed by the crashed
     consumer will be RE-DELIVERED to the new owner (at-least-once)
```

### Designing for it (what a Staff engineer actually builds)

```
• Commit offsets only AFTER successful processing (never before), and
  prefer manual commit over interval-based auto-commit for anything
  where duplicate processing has real cost.

• Make processing idempotent (Section 10) so redelivery after a crash
  is a correctness non-event, not an incident.

• Set max.poll.interval.ms comfortably above your true worst-case
  per-batch processing time (including any downstream retries/backoff)
  — otherwise a SLOW consumer looks identical to a CRASHED one to the
  coordinator, and gets evicted mid-work, causing a self-inflicted
  rebalance storm under load.

• For long-running per-record work, do it asynchronously off the poll
  thread and call consumer.pause()/resume() on assigned partitions
  rather than blocking poll() itself — keeps heartbeats flowing.

• Use static group membership (group.instance.id) in containerized
  deployments so routine pod restarts don't trigger full rebalances.

• Monitor consumer lag AND processing exceptions separately — a
  consumer that's "alive" (heartbeating) but throwing on every record
  looks healthy to Kafka while silently failing its actual job; this
  needs application-level alerting, not just Kafka-level.
```

---

## 22. Handling Producer Crashes

### What happens automatically

```
Records already ACKED by the broker (per the acks setting) before the
crash are safe — durably persisted per the durability guarantee that
acks level implies. They are unaffected by the producer's death.

Records still in the producer's local send buffer (RecordAccumulator)
at the moment of the crash are LOST from the application's perspective
— they never left the process. This is not a Kafka failure; it's
inherent to any client-side buffering.

If using transactions: an in-flight, uncommitted transaction from the
crashed producer will sit open until transaction.timeout.ms expires,
at which point the Transaction Coordinator aborts it — read_committed
consumers never see the partial data, so no corruption, just delay.
```

### Designing for it

```
• Choose acks based on how much "in-flight loss on crash" is
  acceptable: acks=all minimizes the at-risk window to essentially
  "whatever hasn't been sent to the broker yet," at the cost of
  per-request latency.

• Keep linger.ms/batch.size reasonable, not maximal — the at-risk
  window on a crash is roughly proportional to how much unsent data
  you're willing to buffer client-side. Trading a LOT of durability
  risk for a marginal throughput gain from huge batches is usually
  the wrong trade for anything business-critical.

• For "must not lose this record" producers (e.g., financial events),
  call get() on the Future synchronously (or use a bounded set of
  in-flight callbacks) so the application KNOWS a record failed to
  send before considering its own unit of work complete — rather than
  fire-and-forget send() that can silently drop on crash before the
  callback ever fires.

• On restart, a NEW producer instance with idempotence enabled gets a
  fresh Producer ID — it has no memory of what the crashed instance
  sent. If exactly-once matters across restarts, that's precisely what
  transactional.id + zombie fencing (Section 16) is for: consistent
  transactional.id across restarts lets the new instance safely resume
  and the coordinator fences out any lingering zombie from the old
  process.

• Client-side outbox pattern for critical produces: write the
  "to-be-sent" record to local durable storage (or the same DB
  transaction as the business action, per the transactional outbox
  pattern in Section 17) BEFORE calling send(), so a crash before
  send() completes doesn't lose the business intent — a recovery
  process can replay anything not confirmed sent.
```

---

## 23. Handling Broker Crashes

*(This is the "queue crash" scenario in Kafka's architecture — there's no single central queue process, but the broker hosting a partition is the closest analog, so this section covers both a single broker crash and cluster-wide considerations.)*

### Single broker crash — what happens automatically

```
1. Controller detects the broker is unreachable (missed KRaft
   heartbeats / lost ZK session)
2. For every partition where the dead broker was LEADER:
     Controller elects a new leader from the remaining ISR members
     (Section 8) — sub-second in KRaft mode
3. For every partition where the dead broker was a FOLLOWER:
     No action needed immediately — replication factor just
     temporarily drops by one for those partitions (under-replicated,
     but still available and durable via remaining replicas)
4. Producers/consumers get NotLeaderForPartition / connection errors,
   refresh their metadata, and transparently reconnect to the new
   leader — this is built into the client library, not something
   application code needs to handle manually
5. When the broker comes back online, it rejoins as a follower,
   catches up from the current leader, and re-enters the ISR once
   caught up within replica.lag.time.max.ms
```

### Designing for it

```
• replication.factor=3, min.insync.replicas=2 as the standard baseline
  for anything durability-sensitive — tolerates one broker failure
  with zero data loss and continued availability.

• Rack/AZ awareness (broker.rack config): ensure replicas for a
  partition are spread across FAILURE DOMAINS (availability zones),
  not just across brokers that could all be in the same rack/AZ and
  fail together. Kafka's rack-aware replica placement handles this
  automatically once brokers report their rack.

• Decide your unclean.leader.election.enable stance EXPLICITLY
  (Section 7) — this is the single biggest "did we mean to lose data
  or not" decision in the whole cluster, and it should be a deliberate
  choice per-environment (often false in production, occasionally
  true in a lower environment where availability trumps perfect
  durability).

• Throttle partition reassignment traffic when rebuilding a replaced
  broker's data (kafka-reassign-partitions.sh --throttle) — an
  unthrottled full-broker rebuild can saturate the network and degrade
  live traffic on the surviving brokers.

• For total CLUSTER loss (not just one broker) — e.g., a whole-region
  outage — no amount of intra-cluster replication factor helps; this
  requires cross-cluster replication (MirrorMaker 2 / Confluent
  Cluster Linking) to a standby cluster in a different region, with a
  documented failover runbook (client bootstrap server cutover, offset
  translation caveats, and a clear RPO/RTO target).

• Capacity-plan for N-1 (or N-2) broker availability, not N: if your
  cluster is provisioned to be exactly at capacity with ALL brokers
  healthy, losing one broker means the survivors must absorb its
  leadership AND replication load — undersized clusters cascade-fail
  under a single broker loss precisely when you need them most robust.
```

---

## 24. CAP Theorem Positioning

| Component/Config | CAP Choice | Reasoning |
|---|---|---|
| Partition leader (default: `unclean.leader.election.enable=false`) | **CP** | Refuses to elect a non-ISR leader; sacrifices availability to guarantee no silent data loss |
| Partition leader with `unclean.leader.election.enable=true` | **AP** | Elects best-available replica even if lagging; sacrifices durability to stay available |
| Producer `acks=all` + `min.insync.replicas≥2` | **CP**-leaning | Write blocks/fails rather than risk under-durable acknowledgment |
| Producer `acks=0`/`acks=1` | **AP**-leaning | Prioritizes write availability/latency over durability guarantee |
| Consumer offset commits (`__consumer_offsets`, itself a replicated Kafka topic) | **AP**, tunable | Same replication/ISR mechanics as any topic; benefits from the same acks tradeoffs |
| KRaft controller quorum | **CP** | Raft is a majority-consensus protocol by construction — cannot make progress without a quorum, by design |
| Kafka Streams state stores (RocksDB + changelog) | **AP** | Local reads always available; changelog replication catches up asynchronously after failover |

**Staff-level framing:** Kafka isn't monolithically CP or AP — it's a system that exposes CAP as an **explicit, per-write, per-topic configuration surface** (`acks`, `min.insync.replicas`, `unclean.leader.election.enable`) rather than baking in one global answer. The interview-winning move is naming these three specific knobs, not just citing "CAP theorem" abstractly.

---

## 25. Senior vs Staff Differentiators

### Senior-level expectations

- Explain topics, partitions, producers, consumers, consumer groups correctly
- Know that ordering is per-partition, not per-topic
- Know replication factor and the basic idea of a leader/follower
- Explain at-least-once vs at-most-once at a high level
- Describe a basic rebalance
- Get the capacity/throughput math roughly right

### Staff-level expectations (additional)

| Topic | What Staff-level adds |
|---|---|
| Producers | Idempotent producer mechanics (PID + sequence numbers); acks + min.insync.replicas as a *combined* durability contract, not acks alone |
| Consumer groups | Cooperative vs eager rebalancing; static group membership to avoid rebalance storms in K8s |
| Replication | Unclean leader election as the explicit CAP lever; ISR shrink/expand dynamics |
| Leader election | Distinguish partition-leader election (per-partition, controller-driven) from controller election (cluster-level, Raft-driven) |
| Offsets | Committed offset vs current position gap as the precise mental model for "what gets redelivered on crash" |
| Delivery guarantees | Precisely scope "exactly-once" claims; default to idempotent-at-least-once as the pragmatic real-world answer |
| Ordering | Name the specific ways ordering silently breaks (repartitioning, parallel consumer-side fan-out, non-idempotent retries) |
| Transactions | Zombie fencing via producer epoch, not just "transactions make it exactly-once" |
| Kafka Streams | Co-partitioning requirement for joins; standby replicas for fast stateful failover |
| Performance | Zero-copy transfer and page-cache reliance as the *specific* mechanisms behind Kafka's throughput, not just "it's fast" |
| Fault tolerance | Explicitly separate what Kafka protects against (broker loss) from what it doesn't (app bugs, region loss, acks=0 misconfiguration) |
| CAP | Name the three specific configuration knobs rather than asserting Kafka is "CP" or "AP" globally |

### The single most important Staff differentiator

**Precision about guarantees, not just mechanisms.** Anyone can describe what a partition or a consumer group is. What separates Staff is being exact about *what is and isn't guaranteed* under a given configuration — especially around durability (`acks`/ISR/unclean election), ordering (partition scope, repartitioning risk), and "exactly-once" (correctly scoped to Kafka-internal transactions, with idempotent-at-least-once as the honest default for external systems). Vague confidence ("Kafka guarantees exactly-once") reads as Senior; precise scoping ("exactly-once within a Kafka-to-Kafka topology using transactions + read_committed; for external systems we get that via idempotent writes, not Kafka's guarantee alone") reads as Staff.

---

## 26. Interview Time Allocation

For a 45-minute system design session where Kafka is a component (not the whole question):

| Phase | Time | Focus |
|---|---|---|
| Why Kafka here (vs alternative) | 2 min | Name the specific properties you need (durability, replay, fan-out, ordering-by-key) that justify Kafka over a simpler queue |
| Topic/partition design | 5 min | Partition count reasoning, key choice for ordering, hot-key mitigation if relevant |
| Producer config | 3 min | acks + min.insync.replicas as a pair; idempotence; batching tradeoff |
| Consumer/consumer-group design | 5 min | Parallelism via partitions; rebalancing behavior; static membership if relevant to the scenario |
| Delivery guarantee | 5 min | State the guarantee precisely; idempotent-consumer design if at-least-once |
| Failure modes | 5 min | Broker crash, consumer crash, producer crash — proactively, not just when asked |
| CAP tradeoffs | 3 min | Name `acks`, `min.insync.replicas`, `unclean.leader.election.enable` explicitly |

If Kafka **is** the whole question (a dedicated "design a message queue" or "design Kafka" prompt), expect this to expand to the full 45 minutes, with proportionally more time on Sections 2 (architecture), 7–9 (replication/leader election/offsets), and 16–17 (transactions/exactly-once) as the deep-dive core.

**Never skip:** the acks/min.insync.replicas durability pairing, and precise scoping of any "exactly-once" claim — these are the two most common places Staff candidates lose points by being vague.

---

## 27. Quick-Reference Cheatsheet

```
CORE VOCAB
──────────
Producer          → writes records to a topic
Broker            → a Kafka server; stores partition data
Topic             → logical named stream
Partition         → ordered, append-only log; unit of parallelism & ordering
Consumer          → reads records from partitions
Consumer Group    → cooperatively divides partitions among its members
KRaft Controller  → Raft quorum managing cluster metadata (replaces ZooKeeper)

KEY GUARANTEES
──────────────
Ordering:      guaranteed WITHIN a partition only
Durability:    acks=all + min.insync.replicas≥2 + replication.factor≥3
Delivery:      at-least-once is the practical default; make consumers idempotent
Exactly-once:  Kafka-to-Kafka only, via idempotent producer + transactions +
               read_committed; external systems need idempotent writes or outbox

KEY CONFIGS TO NAME IN AN INTERVIEW
────────────────────────────────────
acks                              → 0 / 1 / all
min.insync.replicas               → paired with acks=all for real durability
enable.idempotence                → dedupes retries at broker (default true)
unclean.leader.election.enable    → the explicit CAP lever (default false = CP)
max.poll.interval.ms              → slow ≠ dead; tune to avoid false rebalances
group.instance.id                 → static membership, avoids rebalance storms
isolation.level=read_committed    → required for exactly-once consumers

FAILURE HANDLING AT A GLANCE
─────────────────────────────
Consumer crash → coordinator detects missed heartbeat → rebalance →
                 new owner resumes from last COMMITTED offset (redelivery risk)
Producer crash → unacked in-buffer records lost; acked records safe;
                 open transactions abort after transaction.timeout.ms
Broker crash   → controller elects new leader from ISR (sub-second in KRaft);
                 followers absorb load; replication factor determines
                 how many simultaneous broker losses are tolerated

CAP KNOBS
─────────
CP:  unclean.leader.election.enable=false, acks=all + min.insync.replicas≥2,
     KRaft controller quorum (Raft is inherently CP)
AP:  unclean.leader.election.enable=true, acks=0/1, Kafka Streams local
     state store reads

STAFF-LEVEL SIGNALS TO HIT
───────────────────────────
✓ Pair acks=all with min.insync.replicas — never cite acks alone
✓ Scope "exactly-once" precisely (Kafka-internal vs external systems)
✓ Name the specific ordering-breaking scenarios (repartition, parallel
  fan-out, non-idempotent retries)
✓ Distinguish partition-leader election from controller election
✓ Cooperative/incremental rebalancing vs classic stop-the-world
✓ Zero-copy + page-cache as the concrete mechanism behind throughput
✓ Zombie fencing via producer epoch for transactional correctness
✓ Proactively cover consumer/producer/broker crash handling unprompted
✓ Name unclean.leader.election.enable as the explicit CAP lever
✓ Rack-aware replica placement for real failure-domain isolation
```

---

*Guide compiled for Senior & Staff-level system design interview preparation.*
*Topics: Apache Kafka, Distributed Systems, Event Streaming, Replication, Consensus, Exactly-Once Semantics.*

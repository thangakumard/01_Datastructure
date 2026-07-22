# System Design: Caching

> Based on a system design interview prep video by Evan (former Meta Staff Engineer, co-founder of Hello Interview), with additional supplementary topics commonly probed in interviews but not covered in the source material.

---

## Table of Contents

1. [Introduction & Fundamentals](#1-introduction--fundamentals)
2. [Where to Cache: Layers in Your System](#2-where-to-cache-layers-in-your-system)
   - [2.1 External Caching](#21-external-caching)
   - [2.2 In-Process Caching](#22-in-process-caching)
   - [2.3 CDN (Content Delivery Network)](#23-cdn-content-delivery-network)
   - [2.4 Client-Side Caching](#24-client-side-caching)
3. [Cache Architectures (Read/Write Patterns)](#3-cache-architectures-readwrite-patterns)
   - [3.1 Cache-Aside (Lazy Loading)](#31-cache-aside-lazy-loading)
   - [3.2 Write-Through](#32-write-through)
   - [3.3 Write-Behind (Write-Back)](#33-write-behind-write-back)
   - [3.4 Read-Through](#34-read-through)
   - [3.5 Pattern Comparison Table](#35-pattern-comparison-table)
4. [Cache Eviction Policies](#4-cache-eviction-policies)
5. [Common Caching Problems & Failure Modes](#5-common-caching-problems--failure-modes)
   - [5.1 Cache Stampede (Thundering Herd)](#51-cache-stampede-thundering-herd)
   - [5.2 Cache Consistency](#52-cache-consistency)
   - [5.3 Hot Keys](#53-hot-keys)
6. [When to Bring Up Caching in an Interview](#6-when-to-bring-up-caching-in-an-interview)
7. [How to Introduce Caching: The 5-Step Framework](#7-how-to-introduce-caching-the-5-step-framework)
8. [Supplementary Topics Not Covered in the Video](#8-supplementary-topics-not-covered-in-the-video)
   - [8.1 Negative Caching](#81-negative-caching)
   - [8.2 Cache Sizing & Capacity Planning](#82-cache-sizing--capacity-planning)
   - [8.3 Sharding & Consistent Hashing for Distributed Caches](#83-sharding--consistent-hashing-for-distributed-caches)
   - [8.4 Redis-Specific Considerations](#84-redis-specific-considerations)
   - [8.5 Cache Invalidation via Pub/Sub](#85-cache-invalidation-via-pubsub)
   - [8.6 Multi-Level (Tiered) Caching](#86-multi-level-tiered-caching)
   - [8.7 Cache Security](#87-cache-security)
   - [8.8 Monitoring & Observability](#88-monitoring--observability)
   - [8.9 Serialization Format Choices](#89-serialization-format-choices)
9. [Interview Checklist](#9-interview-checklist)
10. [Quick Reference Summary](#10-quick-reference-summary)

---

## 1. Introduction & Fundamentals

A **cache** is a temporary storage layer that keeps recently or frequently used data close at hand so it can be retrieved faster than going back to the original (slower) source.

**Why it matters — the latency gap:**

| Storage Medium | Typical Access Latency |
|---|---|
| Disk (SSD, e.g. a database) | ~1 millisecond |
| Memory (RAM, e.g. a cache) | ~100 nanoseconds (~10,000x faster) |

At scale (thousands of requests/sec), this latency gap compounds dramatically. Caching trades a bit of storage and architectural complexity for significant speed gains by keeping copies of frequently accessed data in a faster layer (usually memory, though not exclusively).

---

## 2. Where to Cache: Layers in Your System

There are four primary layers where caching can live, each with distinct trade-offs.

### 2.1 External Caching

- **Most common pattern in system design interviews.**
- A dedicated caching service (e.g., **Redis**, **Memcached**) runs on its own server, independent of the application and database.
- Flow: App checks cache → **hit** → return instantly. **Miss** → fetch from DB → write to cache → return to client.
- Key benefit: in a horizontally scaled system, **all application servers share the same external cache**. Once one server populates the cache, every other server benefits — avoiding redundant database hits.

### 2.2 In-Process Caching

- Data is cached directly within the application server's own memory (no external hop like Redis).
- **Fastest possible cache** — no network round trip; data lives in the same memory space as the application.
- **Trade-off:** Each server has its own isolated memory — no sharing across instances. This causes potential inconsistency between servers and wasted/duplicated memory.
- **When to use:** Rarely the default in interviews. Reach for it only for low-level optimizations, ultra-low-latency requirements, or small, mostly-static data like config values or lookup tables used on every request.
- **Default recommendation:** External caching remains the default; in-process caching is a secondary optimization.

### 2.3 CDN (Content Delivery Network)

- A geographically distributed network of edge servers that cache content physically closer to end users.
- Optimizes for **network latency**, not memory-vs-disk speed.
- **Example:** Origin server in Virginia, user in Australia → round trip without CDN: ~300–350ms. With a nearby edge server: ~20–40ms.
- Flow mirrors cache-aside/read-through: request hits nearest edge → **hit** → return immediately. **Miss** → edge server fetches from origin (e.g., S3) → caches it → returns to client.
- Modern CDNs can also cache public API responses, HTML pages, and run edge logic for personalization — but in interviews, the highest-impact use case to mention is **media delivery** (images, video, static assets) for globally distributed users.

### 2.4 Client-Side Caching

- Data stored directly on the user's device (browser or app), avoiding network calls entirely.
- Examples: browser HTTP cache, `localStorage`; on mobile, in-memory or on-disk storage.
- **Pros:** Extremely fast, data never leaves the device.
- **Cons:** Least control over freshness/staleness/validation.
- **When relevant in interviews:** Rare — mainly for offline functionality or client-heavy workloads (e.g., a browser reusing already-downloaded images, or an app like Strava caching run data locally while offline and syncing on reconnect).
- Of the four layers, this is the **least important** to know deeply for interview purposes.

---

## 3. Cache Architectures (Read/Write Patterns)

Cache architectures define the order in which reads/writes happen across the application, cache, and database.

### 3.1 Cache-Aside (Lazy Loading)

**The default pattern — if you remember only one, make it this one.**

- Application checks cache first.
- **Hit:** return data.
- **Miss:** fetch from database → write to cache → return to client.
- **Pros:** Keeps the cache lean — only data that's actually requested gets cached.
- **Cons:** Cache misses add latency (extra DB round trip + cache write) before the response can return.

### 3.2 Write-Through

- Application writes directly to the cache; the cache **synchronously** writes through to the database before acknowledging the write.
- Requires a caching library/framework that supports this behavior (e.g., Spring Cache, Hazelcast) — Redis/Memcached don't natively support it, so you'd otherwise need to implement dual-write logic yourself.
- **Cons:**
  - Slower writes (must wait on both cache and DB).
  - Cache pollution — data gets cached even if it's never read again.
  - **Dual-write problem:** if the cache write succeeds but the DB write fails (or vice versa), the two fall out of sync, requiring complex retry/error-handling logic.
- **When to use:** Only when reads must always be fresh and the system can tolerate somewhat slower writes. Confirm that cache-aside can't already satisfy the requirement before reaching for this.

### 3.3 Write-Behind (Write-Back)

- Similar to write-through, but the cache flushes to the database **asynchronously**, typically in batches.
- **Pros:** Much faster writes than write-through.
- **Cons:** Risk of **data loss** if the cache crashes before the flush completes.
- **When to use:** High write throughput matters more than immediate consistency — e.g., analytics/metrics pipelines where occasional data loss is acceptable.
- **Interview guidance:** Useful if you're an expert with a strong justification, but for most candidates this invites more scrutiny than it's worth. Generally **avoid** unless you can defend it strongly.

### 3.4 Read-Through

- Nearly identical to cache-aside, except the **cache itself** (acting as a proxy) handles the database lookup on a miss, rather than the application doing it.
- Flow: app reads from cache → miss → cache fetches from DB → cache stores it → returns to app.
- This is essentially how **CDNs** work.
- **When to use:** Mostly relevant in the context of CDNs/edge caching. For general application-level caching, cache-aside remains preferable since it doesn't require a special caching framework/adapter.

**Interview tip:** Interviewers don't require you to recall the exact terminology — describing the *behavior* clearly ("I'll check the cache first, and if it's missing, fetch from the database and update the cache") is what actually matters.

### 3.5 Pattern Comparison Table

| Pattern | Write Path | Read Path | Consistency | Write Speed | Complexity | Interview Default? |
|---|---|---|---|---|---|---|
| Cache-Aside | App → DB | App checks cache → DB on miss | Eventual (until invalidated) | Fast (writes untouched) | Low | ✅ Yes — default |
| Write-Through | App → Cache → DB (sync) | App checks cache | Strong | Slow | Medium–High | Only if freshness is critical |
| Write-Behind | App → Cache → DB (async) | App checks cache | Weak (until flush) | Fastest | High | Rarely — niche use cases only |
| Read-Through | App → DB | Cache proxies DB fetch | Eventual | N/A (read-focused) | Medium (needs framework) | Mostly for CDNs/edge |

---

## 4. Cache Eviction Policies

Since memory is limited, an eviction policy decides what stays and what gets removed as new data arrives.

| Policy | Behavior | Best For | Interview Relevance |
|---|---|---|---|
| **LRU** (Least Recently Used) | Evicts items not accessed recently | General-purpose, most common default | High — most common choice |
| **LFU** (Least Frequently Used) | Evicts items accessed least often, regardless of recency | Highly skewed access patterns (few items read far more than others) | Medium — mention when access is skewed |
| **FIFO** (First In, First Out) | Removes the oldest item to make room for the newest | Simple queues; rarely optimal | Low — good to know, rarely the right choice |
| **TTL** (Time To Live) | Each entry expires after a set duration (e.g., 5 minutes) | Data that must go stale predictably (sessions, API responses) | High — used constantly for freshness control |

> Implementation details (e.g., linked-list-based LRU) are almost always **out of scope** for system design interviews — focus on *when* to use each policy, not how to implement it.

---

## 5. Common Caching Problems & Failure Modes

There's a well-known saying: *"There are only two hard problems in computer science: naming things and cache invalidation."*

### 5.1 Cache Stampede (Thundering Herd)

**What happens:** A popular cache entry expires (TTL), and a flood of concurrent requests all try to rebuild it simultaneously, overwhelming the database.

**Example:** A homepage feed cached with a 60-second TTL, serving 100,000 requests/sec. The moment the TTL expires, all 100,000 requests miss simultaneously and hammer the database at once — potentially causing cascading failure.

**Mitigations:**
1. **Request Coalescing / Single Flight** — When multiple requests try to rebuild the same key, only the first proceeds to the database; the rest wait and then read the freshly populated cache.
2. **Cache Warming** — Proactively refresh popular keys *before* they expire (e.g., refresh at the 55-second mark of a 60-second TTL) so the entry effectively never goes stale or expires under load.

### 5.2 Cache Consistency

**The most commonly probed caching issue in interviews.** Occurs because most systems **read from cache** but **write to the database**, creating a window where the two disagree.

**Example:** A user updates their profile picture. The database is updated immediately, but the cache still serves the old image until it's invalidated or expires.

**Mitigation strategies (case-by-case, depends on freshness requirements):**
1. **Invalidate on write** — Delete the cache key when the underlying data changes; the next read will miss, fetch fresh data, and repopulate the cache. Best when consistency matters most.
2. **Short TTLs** — Accept some staleness, bounded by a short expiration window (e.g., 60 seconds for a frequently changing feed).
3. **Accept eventual consistency** — Valid for data like feeds, analytics, or metrics where brief staleness is fine (e.g., "profile images may be stale for up to 5 minutes — acceptable trade-off").

### 5.3 Hot Keys

**What happens:** A single cache entry receives disproportionately more traffic than everything else, even if the overall cache hit rate is healthy — creating a bottleneck on a single cache node/shard.

**Example:** On a Twitter/X-like system, a viral user's profile data could receive millions of requests/sec, overwhelming the single Redis node/shard hosting that key — even though the cache is technically "working as designed."

> Note: This isn't unique to caching — databases can suffer from hot rows/partitions too. It's a very common interview follow-up once caching is introduced to scale reads.

**Mitigations:**
1. **Replicate hot keys** — Duplicate the hot key across multiple shards/nodes in the cache cluster so the application can load-balance requests across all of them instead of hammering one.
2. **Local fallback cache** — Add an in-process cache layer (Section 2.2) for extremely hot values so repeated requests never even reach the external cache (e.g., Redis).

---

## 6. When to Bring Up Caching in an Interview

Don't add caching just to add it — always justify it. Bring it up when **one of these four conditions** applies:

1. **Read-heavy workload draining the database.**
   *Example: "100M DAU × 20 requests/day = 2B reads/day — more than our database can handle. Let's add a cache in front of it."*
2. **Expensive queries.**
   *Example: Computing a personalized newsfeed requires joining posts, followers, and likes across multiple tables — expensive to compute repeatedly, so cache the result with a short TTL.*
3. **High database CPU** (mostly a real-world signal, less applicable in interviews since you won't have live metrics).
4. **Latency requirements.**
   *Example: A non-functional requirement specifies a 100ms response time, but the underlying query is too slow to meet that on its own — caching bridges the gap.*

**Pattern:** Identify the bottleneck → quantify it with rough numbers → explain how caching solves it.

---

## 7. How to Introduce Caching: The 5-Step Framework

Once justified, walk through introducing caching in this order:

1. **Identify the bottleneck** — what specifically is slow or overloaded.
2. **Decide what to cache** — not everything should be cached. Focus on data that's read frequently, doesn't change often, or is expensive to fetch/compute. Be explicit about **cache keys** and **cached values** — interviewers frequently probe on this, especially at junior/mid levels ("What exactly are you caching? What's your key?").
3. **Choose the cache architecture** — e.g., "I'll use cache-aside: check Redis first, fall back to the database on a miss, populate the cache, return to the user."
4. **Choose the eviction policy** — LRU, LFU, and/or TTL, with justification tied to your specific system.
5. **Address potential downsides** — Does a popular key on a TTL risk a thundering herd? Is stale data acceptable for this use case? Are hot keys a concern?

> **Timing tip:** Caching most naturally comes up during deep dives when discussing scale or latency in your non-functional requirements.

---

## 8. Supplementary Topics Not Covered in the Video

The following are commonly probed in senior/staff-level system design interviews but weren't part of the source material. Worth having in your back pocket.

### 8.1 Negative Caching

Caching the *absence* of data (e.g., "this user ID doesn't exist") to avoid repeatedly hitting the database for lookups that will keep failing — common defense against cache-penetration attacks where an attacker repeatedly queries for known-nonexistent keys to bypass the cache and hammer the DB. Usually paired with a short TTL so legitimately-created data becomes visible quickly.

### 8.2 Cache Sizing & Capacity Planning

Worth being able to reason about, at a high level, in a deep dive:
- Estimate working-set size (how much "hot" data needs to fit in memory) vs. total data size.
- Rule of thumb: cache the top N% of data responsible for the majority of traffic (Pareto-style access skew) rather than trying to cache everything.
- Discuss node memory limits and when horizontal sharding (Section 8.3) becomes necessary as working-set size grows.

### 8.3 Sharding & Consistent Hashing for Distributed Caches

When a single cache node can't hold the full working set or handle the request volume, data is partitioned (sharded) across multiple cache nodes.
- **Consistent hashing** is the standard technique to map keys to nodes while minimizing re-distribution when nodes are added/removed (avoids a full cache-wide reshuffle, which would otherwise cause a mass cache-miss event).
- Directly relevant to the hot-key mitigation discussed in Section 5.3 — replication across shards assumes a sharded cluster already exists.

### 8.4 Redis-Specific Considerations

Since Redis is the most commonly referenced caching technology in interviews, a few implementation-level facts are useful to have ready if pressed:
- **Persistence options:** RDB snapshots vs. AOF (append-only file) logs — relevant if discussing durability trade-offs for write-behind caching.
- **Redis Cluster** for native sharding, vs. **Redis Sentinel** for high availability/failover of a single logical dataset.
- **Data structures beyond simple key-value:** Sorted sets (e.g., leaderboards), hashes, lists — occasionally relevant if a deep dive touches on using Redis for more than pure caching.
- **Atomicity:** Redis commands are single-threaded per operation, useful when discussing race conditions (e.g., single-flight cache stampede protection is often implemented with Redis `SETNX`/locks).

### 8.5 Cache Invalidation via Pub/Sub

For multi-node or multi-region caching setups (including a mix of in-process + external caching), a common pattern is publishing an invalidation event (e.g., via Redis Pub/Sub or a message queue) when data changes, so all interested caches — including in-process ones on different servers — can invalidate their local copies rather than relying purely on TTL expiration.

### 8.6 Multi-Level (Tiered) Caching

Combining multiple cache layers from Section 2 in a single request path — e.g., in-process cache → external cache (Redis) → database, or CDN → external cache → database. Each tier absorbs load before it reaches the next, slower layer. Useful to mention when discussing hot-key mitigation or extreme scale requirements, since it directly composes the "local fallback cache" idea from Section 5.3 with the standard external cache.

### 8.7 Cache Security

Rarely central to a system design interview, but worth a sentence if asked about production concerns:
- Sensitive data cached in a shared external cache should be encrypted at rest/in transit if compliance requires it.
- Access control on the cache layer itself (e.g., Redis AUTH, network isolation via VPC) to prevent unauthorized reads of cached data.

### 8.8 Monitoring & Observability

Metrics worth mentioning if a deep dive goes toward operational maturity:
- **Cache hit ratio** — the primary health signal; a declining hit ratio often signals TTLs that are too short, a working set that's outgrown the cache, or a hot-key/stampede problem.
- **Eviction rate** — high eviction rate can indicate the cache is undersized for its working set.
- **Latency (p50/p99) per cache tier** — useful for spotting a degraded shard or node.

### 8.9 Serialization Format Choices

When caching complex objects (not just primitive values), the serialization format affects both cache size and (de)serialization latency — e.g., JSON (human-readable, larger) vs. a binary format like Protocol Buffers or MessagePack (smaller, faster, less flexible). Rarely a focus, but a good one-liner if optimizing cache memory footprint comes up.

---

## 9. Interview Checklist

- [ ] Justify *why* caching is needed (one of the four triggers in Section 6) — don't add it by default.
- [ ] Specify **what** you're caching and the **cache key** structure explicitly.
- [ ] Name the **architecture pattern** you're using (default: cache-aside) and describe the read/write flow in plain language.
- [ ] Name the **eviction policy** (default: LRU, or TTL for freshness-sensitive data) with justification.
- [ ] Proactively address **at least one** relevant failure mode: stampede, consistency, or hot keys — pick whichever is actually relevant to your system.
- [ ] Know the **layer** you're caching at (external vs. in-process vs. CDN vs. client) and why.
- [ ] Be ready to reason about **trade-offs**, not just describe mechanisms — interviewers grade judgment, not vocabulary recall.

---

## 10. Quick Reference Summary

| Concept | Interview Default | When to Deviate |
|---|---|---|
| Cache layer | External (Redis/Memcached) | In-process for ultra-low-latency/small static data; CDN for media; client-side rarely |
| Architecture | Cache-Aside | Write-Through if freshness is critical and writes can be slower; Read-Through for CDN/edge contexts; Write-Behind only with strong justification |
| Eviction policy | LRU | LFU for highly skewed access; TTL for data that must go stale predictably |
| Stampede fix | Request coalescing or cache warming | Use both together for very hot, high-QPS keys |
| Consistency fix | Invalidate-on-write or short TTL | Accept eventual consistency for feeds/analytics/non-critical data |
| Hot key fix | Replicate across shards | Add local (in-process) fallback cache for extreme cases |

# Proximity Service — System Design Interview Guide
### Senior & Staff Engineer Level
### (Yelp Nearby / Uber Driver Matching / Tinder Nearby / DoorDash Dispatch)

---

## Table of Contents

1. [Problem Statement & Scope](#1-problem-statement--scope)
2. [Requirements](#2-requirements)
3. [Capacity Estimation](#3-capacity-estimation)
4. [The Two Flavors of Proximity Service (Staff-Level Fork)](#4-the-two-flavors-of-proximity-service-staff-level-fork)
5. [High-Level Architecture](#5-high-level-architecture)
6. [Component 1: Geospatial Indexing Strategies](#6-component-1-geospatial-indexing-strategies)
7. [Component 2: The Nearby Query Algorithm](#7-component-2-the-nearby-query-algorithm)
8. [Component 3: Storage Layer](#8-component-3-storage-layer)
9. [Component 4: Location Ingestion (Write Path)](#9-component-4-location-ingestion-write-path)
10. [Component 5: Sharding the Index](#10-component-5-sharding-the-index)
11. [Component 6: Matching / Assignment Service](#11-component-6-matching--assignment-service)
12. [Component 7: Live Tracking (Push Updates)](#12-component-7-live-tracking-push-updates)
13. [Component 8: Caching Layer](#13-component-8-caching-layer)
14. [CAP Theorem Positioning](#14-cap-theorem-positioning)
15. [Failure Modes & Mitigations](#15-failure-modes--mitigations)
16. [Freshness & Staleness Strategy](#16-freshness--staleness-strategy)
17. [Scalability & Sharding](#17-scalability--sharding)
18. [Senior vs Staff Answer Differentiators](#18-senior-vs-staff-answer-differentiators)
19. [Interview Time Allocation](#19-interview-time-allocation)
20. [Quick-Reference Cheatsheet](#20-quick-reference-cheatsheet)

---

## 1. Problem Statement & Scope

A **proximity service** answers one deceptively simple question at scale: *"What entities are near this point?"* — nearby restaurants (Yelp), nearby drivers (Uber/Lyft), nearby daters (Tinder), nearby delivery couriers (DoorDash), nearby friends (Snap Map).

The deceptive part: the underlying data has two radically different personalities depending on the product, and conflating them is the single most common mistake candidates make in this interview.

### What the interviewer is really testing

- Can you pick and justify a **geospatial indexing structure** (not just say "use a database with a geo index")?
- Do you recognize that **write pattern determines architecture** here more than almost any other system design question?
- Can you handle the **matching/assignment race condition** (two riders, one driver) as a distinct CP problem embedded inside an otherwise AP system?
- Do you proactively separate **"search near me"** from **"track this moving object"**?

---

## 2. Requirements

### Functional Requirements

| Requirement | Notes |
|---|---|
| Given a lat/lon + radius, return nearby entities | Core query — the "GET /nearby" endpoint |
| Given a lat/lon, return the K nearest entities | KNN variant — used for matching |
| Support entity movement | Only for the dynamic flavor (drivers, couriers) |
| Support filters combined with location | e.g., "vegan restaurants within 2km", "SUV drivers within 5 min" |
| Support live tracking of a specific entity | Rider watching driver's dot move on the map |

### Non-Functional Requirements

| Requirement | Target |
|---|---|
| Query latency | p99 < 100ms for nearby search |
| Write throughput (dynamic) | Sustain continuous location pings from millions of moving entities |
| Freshness (dynamic) | Location shown to a rider should be ≤ 5–10s stale |
| Availability | Prefer availability over consistency for search; the matching step is the one exception |
| Geographic isolation | A regional outage in one city should not affect other cities |

---

## 3. Capacity Estimation

```
── STATIC FLAVOR (Yelp-style POI search) ──────────────────

Total businesses worldwide:        ~100 million
Business updates/day:              ~1 million  (owners editing hours, menus)
                                    → ~12 writes/sec (trivially low)

Search QPS:
  Assume 50M DAU, 2 "near me" searches/day/user
  50,000,000 × 2 / 86,400 ≈ 1,157 QPS average → design for 10× peak ≈ 11,500 QPS

Read:write ratio ≈ 1000:1  →  this is a READ-DOMINANT system.
Implication: optimize for query fan-out and caching, not write throughput.


── DYNAMIC FLAVOR (Uber-style driver tracking) ────────────

Active drivers globally (peak):     ~1,000,000
Location ping interval:             4 seconds

Write throughput:
  1,000,000 / 4 ≈ 250,000 location writes/sec  (sustained, not peak)

Ride requests:
  100,000 requests/min ≈ 1,667 nearby-driver queries/sec

Read:write ratio ≈ 1:150  →  this is a WRITE-DOMINANT system.
Implication: the bottleneck is ingesting and indexing location updates,
not serving reads. Opposite bottleneck from the static flavor.

Per-driver payload: {driver_id, lat, lon, heading, speed, ts} ≈ 100 bytes
Ingestion bandwidth: 250,000 × 100B ≈ 25 MB/s  (trivial bandwidth;
the challenge is write amplification into the spatial index, not network)
```

**Key insight:** the two flavors have *inverted* read:write ratios. A design that's correct for Yelp (optimize reads, cache aggressively, batch-rebuild the index) is catastrophically wrong for Uber (the index must absorb 250K writes/sec continuously), and vice versa. This is the estimation section where you should explicitly call out which flavor you're optimizing for — or design for both and say so.

---

## 4. The Two Flavors of Proximity Service (Staff-Level Fork)

> Proactively raising this fork — before the interviewer steers you toward it — is the single highest-leverage move in this interview.

| Dimension | Static (Yelp/Google Places) | Dynamic (Uber/DoorDash/Tinder) |
|---|---|---|
| Entity movement | Never (or rarely — a restaurant doesn't move) | Constantly (every few seconds) |
| Write volume | Very low | Very high, continuous |
| Read volume | Very high | Moderate |
| Index rebuild strategy | Batch rebuild acceptable (hourly/daily) | Must be updated in near-real-time |
| Staleness tolerance | Hours/days fine | Seconds — stale driver location = failed pickup |
| Storage | Durable disk-based (Postgres/PostGIS, Elasticsearch) | Primarily in-memory (Redis, custom grid service) |
| Extra concern | Text + geo combined search (name, category) | Matching/assignment race conditions; entity expiry (TTL) |
| CAP lean | AP, strongly | AP for search, but **CP for the assignment step** |

**Why this matters architecturally:** a static POI index can be built once with a batch job (Spark job computes geohash buckets, writes to Elasticsearch, done) and read millions of times before the next rebuild. A dynamic index has no such luxury — the structure itself must support cheap, frequent point updates, which immediately rules out approaches that are great for static data (e.g., a bulk-loaded R-tree) and rules in approaches optimized for update throughput (in-memory hash grid keyed by cell ID).

Many real systems need **both** — Uber shows nearby *restaurants* (static-ish) and nearby *drivers* (dynamic) in the same app, backed by architecturally different subsystems under one product surface.

---

## 5. High-Level Architecture

```
                          ┌───────────────────────┐
                          │   Client (mobile app)  │
                          └───────────┬────────────┘
                                      │  GET /nearby?lat=&lon=&radius=
                                      ▼
                          ┌───────────────────────┐
                          │   API Gateway / LB     │
                          │   (routes by region)   │
                          └───────────┬────────────┘
                                      │
                ┌─────────────────────┼─────────────────────┐
                │                                            │
   ┌────────────▼────────────┐                  ┌────────────▼────────────┐
   │  STATIC PATH             │                  │  DYNAMIC PATH            │
   │  Query Service           │                  │  Query Service           │
   │  (nearby POIs)           │                  │  (nearby drivers)        │
   └────────────┬─────────────┘                  └────────────┬─────────────┘
                │                                              │
   ┌────────────▼─────────────┐                  ┌─────────────▼────────────┐
   │  POI Index                │                  │  In-Memory Geo Grid       │
   │  Elasticsearch / PostGIS  │                  │  Redis GEO / custom H3    │
   │  (geohash / R-tree index) │                  │  index, sharded by region │
   └────────────▲─────────────┘                  └─────────────▲────────────┘
                │  batch rebuild                                │  continuous writes
   ┌────────────┴─────────────┐                  ┌─────────────┴────────────┐
   │  Offline Indexing Job     │                  │  Location Ingestion Svc   │
   │  (Spark, hourly/daily)    │                  │  (WebSocket / gRPC        │
   │                            │                  │   stream from devices)   │
   └────────────────────────────┘                  └─────────────┬────────────┘
                                                                   │
                                                     ┌─────────────▼────────────┐
                                                     │  Kafka: location-updates  │
                                                     │  partitioned by region    │
                                                     └─────────────┬────────────┘
                                                                   │
                                          ┌────────────────────────┼───────────────────────┐
                                          │                        │                         │
                             ┌────────────▼──────────┐ ┌───────────▼──────────┐ ┌────────────▼──────────┐
                             │ ETA / Surge Pricing    │ │ Matching Service      │ │ Live Tracking          │
                             │ Service (async)        │ │ (CP — driver lock)    │ │ (WebSocket fan-out)    │
                             └────────────────────────┘ └───────────────────────┘ └────────────────────────┘
```

**Regional partitioning happens at the load balancer.** Because proximity is inherently local, the router can send a request from a user in Austin straight to the Austin regional cluster — this is a structural advantage proximity services have that most other distributed systems (e.g., a web crawler or a global feed system) don't get for free.

---

## 6. Component 1: Geospatial Indexing Strategies

This is the component interviewers spend the most time probing. You need one primary structure and a clear justification, plus awareness of the alternatives.

### Geohash

```
Encodes (lat, lon) into a base32 string by interleaving bits of
latitude and longitude, recursively halving the bounding box.

"9q8yy" → a ~1.2km × 0.6km cell in San Francisco

Precision table:
  Length 1 → ~5,000km cell     Length 6 → ~1.2km cell
  Length 4 → ~40km cell        Length 8 → ~40m cell
  Length 5 → ~5km cell         Length 9 → ~5m cell

Sharding: trivial — shard by geohash prefix (range-based).
Query: find cell for query point, plus its 8 neighbors, filter by
       actual distance (rules out points in the "corner" of neighbors).
```

**Boundary problem:** two points 5 meters apart can fall into completely different geohash cells if they straddle a cell boundary (they may not even share a common prefix at all, due to the interleaving). **This is why you always query the center cell plus all 8 neighbors, never just the matching cell.**

### H3 (Uber's hexagonal hierarchical index)

```
Tessellates the earth into hexagonal cells instead of rectangles.
Hierarchical: each hex has exactly 7 children at the next resolution.

Why hexagons over squares:
  • All 6 neighbors are equidistant from the center (a square has
    4 edge-adjacent neighbors at distance d and 4 corner-adjacent
    neighbors at distance d√2 — non-uniform, adds edge cases)
  • Better approximates a circle → fewer false negatives at radius edges
  • No pole/antimeridian distortion issues that plague raw lat/lon grids
```

This is what Uber actually uses in production for driver/rider matching — a strong real-world citation to drop in the interview.

### Quadtree

```
Recursive spatial subdivision: split a cell into 4 quadrants when
it exceeds a point-count threshold. Adapts naturally to density —
Manhattan gets deeply subdivided, rural Montana stays coarse.

Pros: handles non-uniform density gracefully (avoids the hot-cell
      problem that fixed grids have in dense city centers)
Cons: harder to shard (tree structure, not a flat keyspace);
      typically lives in-memory on a single node or a
      custom-sharded service, not a natural fit for a
      distributed KV store
```

### S2 (Google's library)

```
Projects the sphere onto a cube, then a Hilbert space-filling curve
within each cube face → 64-bit cell IDs with strong locality
(nearby points → numerically close IDs).

Pros: excellent locality preservation, handles poles/antimeridian
      correctly, used by MongoDB's geo indexes internally
Cons: steeper conceptual overhead to explain/implement from scratch
```

### R-tree

```
Bounding-box hierarchy — internal nodes store the minimum bounding
rectangle covering their children. Used by PostGIS/PostgreSQL's
GiST index.

Pros: excellent for polygon/region queries (e.g., "is this point
      inside this delivery zone"), not just point-radius queries
Cons: more expensive inserts than a flat grid; less natural to
      shard across nodes; best suited to a single powerful DB node
      or read replicas, not a horizontally-scaled write-heavy index
```

### Comparison Table

| Structure | Query complexity | Update cost | Boundary handling | Shards naturally? | Best fit |
|---|---|---|---|---|---|
| Geohash | O(1) cell lookup + neighbor scan | O(1) | Needs 8-neighbor check | Yes (prefix range) | Simple systems, static or dynamic |
| H3 | O(1) + ring expansion | O(1) | Cleaner (6 equidistant neighbors) | Yes (cell-ID range) | Dynamic, high-frequency updates (Uber) |
| Quadtree | O(log n + k) | O(log n) | Clean within tree | No — needs custom sharding | Highly non-uniform density, in-memory |
| S2 | O(log n) | O(log n) | Excellent (no pole distortion) | Yes (Hilbert-ordered) | Global-scale, precision-sensitive systems |
| R-tree | O(log n + k) | O(log n), costlier | Excellent for polygons | No — typically single-node | Region/polygon queries (PostGIS) |

**Staff-level answer:** name H3 or geohash for the write-heavy dynamic case (cheap point updates, natural sharding), and R-tree/PostGIS for the static case if polygon queries (delivery zones, service areas) are in scope — and explain *why* the write pattern drove the choice, rather than picking one structure and using it everywhere.

---

## 7. Component 2: The Nearby Query Algorithm

```
Given: query point (lat, lon), radius R (or target K results)

1. Determine target precision level:
     choose geohash length / H3 resolution whose cell size ≈ R
     (too coarse → too many candidates to filter; too fine → miss
      results near cell edges)

2. Compute center cell + ring of neighbors:
     Geohash: center cell + 8 adjacent cells
     H3:      kRing(center, 1) → center + 6 neighbors

3. Fetch candidate entities from all cells in the ring

4. Compute exact distance (Haversine formula) from query point to
   each candidate; discard any outside radius R

5. Sort by distance; return top K

6. If insufficient results (sparse area):
     expand the ring (kRing radius 2, 3, ...) OR
     zoom out to a coarser precision level and retry
     — cap the expansion (e.g., max 3 rings) to bound worst-case
       latency in very sparse regions (rural areas, 3am off-peak)
```

**Staff-level gotcha — the expansion cap:** without a hard cap, a query in a sparse region (e.g., searching for drivers in a rural area at 3am) can trigger unbounded ring expansion, turning a 5ms query into a multi-second scan. Always bound the expansion and degrade gracefully — return "no drivers within 15 minutes" rather than scanning half a state.

---

## 8. Component 3: Storage Layer

| Layer | System | Used by | Rationale |
|---|---|---|---|
| POI store | PostgreSQL + PostGIS, or Elasticsearch `geo_point` | Static flavor | Combines geo filtering with text/category search; PostGIS GiST index handles polygons too |
| In-memory geo grid | Redis (`GEOADD`/`GEOSEARCH`, sorted-set-backed geohash) or custom H3-keyed hash grid | Dynamic flavor | Sub-millisecond point updates and radius queries; data is inherently ephemeral |
| Durable location history | Cassandra / time-series DB (partition by driver_id + time bucket) | Dynamic flavor (async) | Powers ETA models, trip replay, analytics — not on the hot query path |
| Event bus | Kafka, partitioned by region | Both | Decouples ingestion from downstream consumers (ETA, surge pricing, matching, analytics) |
| Matching lock state | Redis with `SET NX` / CAS, or dedicated matching service | Dynamic flavor | Needs atomicity — this is the one CP island inside an otherwise AP system |

### Why Redis GEO for the dynamic flavor specifically

`GEOADD`/`GEOSEARCH` internally store a geohash-derived score in a sorted set — O(log n) inserts, sub-millisecond radius queries, and the whole structure fits in memory for a metro-sized fleet (even 1M drivers × ~100 bytes ≈ 100MB, trivially cacheable). The tradeoff: it's a single logical structure per Redis node/cluster, so you shard it by region (Section 10), and you lose durability on node failure unless you pair it with AOF/replication or rebuild from the Kafka log.

---

## 9. Component 4: Location Ingestion (Write Path)

```
1. Driver app sends location update via persistent WebSocket or
   gRPC bidirectional stream (not a fresh HTTP request per ping —
   connection setup overhead at 250K writes/sec would be brutal)

2. Ingestion Service receives {driver_id, lat, lon, heading, ts}

3. Movement-threshold filter ("dead reckoning" style dedup):
     if distance from last recorded position < threshold (e.g. 10m)
     AND time since last update < max_staleness:
       → skip the write (driver essentially stationary — traffic light,
         waiting for a fare)
     This alone can cut write volume 30–50% in real fleets.

4. Write to in-memory geo grid (upsert — remove old cell entry,
   insert into new cell if the driver crossed a cell boundary)

5. Async: publish to Kafka topic `location-updates`,
   partitioned by region so downstream consumers see
   geographically-local, roughly-ordered streams

6. Set/refresh a TTL on the driver's grid entry (e.g. 30s).
   No update within TTL → treat driver as offline, evict from index.
```

**Why WebSocket/streaming instead of polling:** at 250K writes/sec, per-request connection setup (TCP + TLS handshake) for plain HTTP would dominate the cost. A long-lived connection amortizes that overhead across thousands of pings.

---

## 10. Component 5: Sharding the Index

### Static flavor

```
Shard by geohash prefix range (range-based sharding).
Pros: geographically co-located data, efficient range scans.
Cons: dense cities (NYC, Tokyo) become hot shards — the same
      hot-spot problem seen in every range-sharded system.

Fix: sub-shard hot cells past a threshold — split a geohash-6
     cell into its 32 geohash-7 children once it exceeds
     N businesses, same pattern as splitting a hot domain queue
     in a web crawler frontier.
```

### Dynamic flavor

```
Shard by REGION, not by a global consistent hash ring.

Rationale: rides don't cross city boundaries. A rider in Austin
will never be matched with a driver in Chicago. This means the
natural shard key (metro region) is also the natural query
boundary — a rare case where sharding costs you nothing in
cross-shard query complexity, because cross-shard queries for
this data simply never happen in the product.

Within a region: shard by H3 cell prefix via consistent hashing
across the in-memory grid nodes, so no single node holds the
entire city's driver fleet.
```

**Staff-level insight worth stating explicitly:** most systems in this guide series (web crawler, sharding strategies, Kafka) treat cross-shard queries as a problem to be solved (scatter-gather, denormalization, secondary indexes). Proximity search sidesteps that problem almost entirely, because the query's inherent locality *is* the shard key. Calling this out explicitly — rather than reflexively reaching for scatter-gather machinery you don't need — is a signal of judgment, not just recall.

---

## 11. Component 6: Matching / Assignment Service

This is the component most candidates miss entirely, and it's the one place this system needs **strong consistency** inside an otherwise availability-favoring design.

### The race condition

```
Rider A and Rider B both request a ride within milliseconds of
each other. Both proximity queries return Driver X as the
nearest match. Without coordination, both requests could assign
Driver X, resulting in one rider being picked up and the other
stranded with a "driver cancelled" experience.
```

### The fix

```
Matching is a two-phase process:
  1. Query phase (AP): fetch nearby candidates from the geo grid —
     fast, approximate, can tolerate slight staleness
  2. Assignment phase (CP): atomically claim a specific driver

Assignment uses a compare-and-swap on driver state:
  SET driver:{id}:status "assigned" NX EX 15
  (Redis SETNX — succeeds only if the driver is currently
   "available"; fails atomically if another request beat it there)

  On CAS failure: fall back to the next-nearest candidate from
  the original query results — no need to re-run the full
  geospatial query, just advance down the candidate list.
```

**This is the PACELC-style nuance to volunteer:** the *search* is PA/EL (available, low-latency, tolerant of slightly stale driver positions). The *assignment* is a narrow, deliberately-scoped CP operation layered on top — you are not making the whole system CP, you're isolating the one operation where a wrong answer (double-booking) is worse than a slow answer.

---

## 12. Component 7: Live Tracking (Push Updates)

Once a ride is matched, the rider's app needs to see the driver's dot move in real time.

```
Fan-out pattern: per-trip pub/sub channel, not a global broadcast.

  Driver location update → published to Kafka `location-updates`
       → Live Tracking Service filters for active trip_ids
       → pushes to the 1 (sometimes 2, if a dispatcher is watching
         too) subscribed WebSocket connection(s) for that trip

Contrast with a social feed fan-out problem (e.g., a celebrity
with 50M followers): here, fan-out width is O(1) — one rider, one
connection, per trip. This is a fan-out-on-write pattern, but the
"write" (a single driver's location) always has a tiny, bounded
subscriber set. No celebrity/hot-key fan-out problem exists here
by construction of the product.
```

Update frequency to the client is typically throttled independently of ingestion frequency — you might ingest driver pings every 2–4s but only push UI updates every 1s with client-side interpolation smoothing the motion between pushes, to avoid jittery map rendering.

---

## 13. Component 8: Caching Layer

| What's cached | TTL | Why |
|---|---|---|
| Static POI search results (e.g., "coffee shops near geohash 9q8yy") | 5–15 min | POIs rarely change; huge read amplification reduction |
| Reverse geocoding results (lat/lon → address) | Hours–days | Deterministic, essentially immutable |
| Dynamic nearby-driver results | **Not cached**, or ≤1–2s at most | Staleness directly degrades the product (a cached "nearby driver" that already drove off is a failed match) |
| Driver → cell mapping | N/A — lives directly in the in-memory grid | The grid *is* the cache; there's no separate DB to cache in front of |

**Staff-level nuance:** the instinct to cache aggressively (a good default in most systems) is actively harmful for the dynamic flavor's core query. Recognizing *when not to cache* — because the whole value proposition is freshness — is as important as knowing when to.

---

## 14. CAP Theorem Positioning

| Component | CAP Choice | Reasoning |
|---|---|---|
| Static POI index (Elasticsearch/PostGIS) | **AP** | A business's hours being 10 minutes stale is a non-event. Availability of search matters far more than perfect freshness. |
| Dynamic in-memory geo grid (Redis GEO) | **AP** (PA/EL) | Slightly stale driver position is acceptable; an unavailable search is not. Async replication, no cross-region sync on the hot path. |
| Location ingestion pipeline (Kafka) | **AP** | At-least-once delivery; a dropped or duplicate ping is far cheaper than blocking the write path. |
| **Matching / assignment lock** | **CP** | The one deliberate exception — double-assigning a driver is a correctness bug, not a staleness inconvenience. Scoped narrowly to a single CAS operation, not the whole system. |
| Shard/region routing metadata | **CP** | Routing table must be consistent — misrouting a whole region's traffic is worse than a brief unavailability during a routing update. |
| Trip/ride history (durable store) | **AP**, tunable | Analytics and ETA models tolerate eventual consistency; writes favor throughput. |

The Staff-level move here is the same one emphasized in the CAP/PACELC guide: **classify per component, and explicitly call out that this system is AP almost everywhere except one narrowly-scoped CP island (assignment).** Don't say "the system is AP" and stop — and don't say "matching needs consistency so the whole system is CP" either; that overcorrects and would make the 250K/sec location writes unnecessarily slow.

---

## 15. Failure Modes & Mitigations

| Failure | Symptom | Detection | Mitigation |
|---|---|---|---|
| Driver GPS drops mid-trip | Stale location shown to rider | No ping within TTL window | Client-side dead reckoning (extrapolate from last heading/speed); TTL-based eviction from the grid after N seconds |
| Boundary miss | Nearby driver just across a cell edge isn't returned | Low result counts near cell boundaries in metrics | Always query the neighbor ring (8 for geohash, 6 for H3), never just the matching cell |
| Hot cell (dense downtown at rush hour) | One shard/cell overloaded with reads+writes | Per-cell QPS/queue-depth monitoring | Sub-shard the cell past a threshold; same pattern as hot-domain mitigation in crawler frontier design |
| Double driver assignment | Two riders matched to one driver | CAS failure rate spike on assignment lock | Atomic `SETNX`-style claim; fall back to next-nearest candidate on CAS failure |
| In-memory grid node crash | All drivers in that shard vanish from search | Node health check / heartbeat failure | Replicate grid state (Redis replica/cluster); rebuild from replaying recent Kafka `location-updates` for that region |
| Regional network partition | Region isolated from global control plane | Cross-region heartbeat loss | Region operates independently — proximity is local by nature, so a region can keep matching riders/drivers within itself even while cut off globally |
| Unbounded ring expansion in sparse areas | Query latency spikes in rural/off-peak areas | p99 latency alarms scoped by region | Hard cap on ring expansion; degrade to "no results within X minutes" rather than scanning indefinitely |
| Write amplification from GPS jitter | Drivers reporting near-identical positions repeatedly, wasting write capacity | Write volume vs. distinct-cell-change ratio | Movement-threshold filter (dead-reckoning dedup) before writing to the grid |
| Kafka partition lag (region-partitioned) | Downstream ETA/surge/matching consumers fall behind for one region | Consumer lag monitoring, per-partition | Scale consumer group for the affected region; region-level partitioning means lag in one city doesn't touch others |

---

## 16. Freshness & Staleness Strategy

| Use case | Acceptable staleness | Mechanism |
|---|---|---|
| "Restaurants near me" (static) | Hours | Batch-rebuilt index; no TTL needed |
| "Drivers near me" (browsing, pre-request) | ~10–15s | TTL-based grid eviction; ring-expansion fallback |
| Active ride matching | ~5s | Tighter TTL; movement-threshold write filter tuned lower |
| Live trip tracking (post-match) | ~1–2s (client-perceived) | Client-side interpolation between server pushes; push channel throttled independently of ingestion rate |
| ETA computation | Minutes (uses recent history, not just current position) | Consumes from durable Kafka-backed history, not the hot grid |

The general principle, consistent with the adaptive-freshness approach in the Web Crawler guide: **don't use one global staleness policy.** Each consumer of location data has a different cost-of-staleness, and the TTL/refresh strategy should be tuned per consumer, not globally uniform.

---

## 17. Scalability & Sharding

```
Regional independence is the core scaling lever:
  • Each metro/region runs its own in-memory grid cluster
  • Regions scale independently — NYC's driver volume growing
    doesn't require touching the Phoenix cluster
  • A regional outage degrades one city, not the whole product

Within a region:
  • Consistent hashing across grid nodes, keyed by H3/geohash
    cell prefix, so no single node owns the entire metro's fleet
  • Read replicas for the static POI index (Elasticsearch data
    nodes) scale horizontally and trivially, since POI data is
    read-dominant and rarely written

Global control plane (thin):
  • Region → cluster routing table (CP, low write volume,
    changes only on region provisioning — not a bottleneck)
  • Cross-region analytics aggregation (async, batch — not on
    the query hot path)
```

---

## 18. Senior vs Staff Answer Differentiators

### Senior-level expectations

- Mention geohash or a geo-indexed database (PostGIS, MongoDB geo index)
- Describe a basic radius query (find nearby, filter by distance)
- Know that driver locations update frequently
- Mention caching as a general optimization

### Staff-level expectations (additional)

| Topic | What Staff-level adds |
|---|---|
| Problem framing | Proactively split static (POI) vs. dynamic (moving entity) flavors before diving into design — they need different architectures |
| Indexing | Compare geohash / H3 / quadtree / S2 / R-tree with explicit tradeoffs, not just name one |
| Boundary handling | Explicitly call out the cell-boundary miss problem and the neighbor-ring fix, unprompted |
| Sharding | Recognize proximity queries are naturally shard-local — no scatter-gather needed — and say so explicitly |
| CAP | Identify the *one* CP island (matching/assignment) inside an otherwise AP system, rather than labeling the whole system one way |
| Freshness | Per-consumer staleness budgets (browsing vs. active matching vs. live tracking), not one global TTL |
| Caching | Recognize where caching *hurts* (dynamic nearby-driver queries) as clearly as where it helps |
| Write path | Address GPS jitter / write amplification with a movement-threshold filter before it's asked about |
| Failure handling | Proactively enumerate hot cells, TTL-based entity expiry, unbounded ring expansion, and the double-assignment race |
| Regional design | Frame region-based partitioning as a free structural advantage this system gets that most distributed systems don't |

### The single most important Staff differentiator

**Naming the static-vs-dynamic fork before the interviewer forces it on you.** Almost every follow-up question in this interview — indexing choice, write throughput, caching strategy, CAP positioning — has a different correct answer depending on which flavor you're solving for. A Staff engineer resolves that ambiguity up front instead of designing something that quietly assumes one flavor and collapsing when the interviewer pivots to the other.

---

## 19. Interview Time Allocation

For a 45-minute system design session:

| Phase | Time | Focus |
|---|---|---|
| Requirements & scope | 5 min | Confirm functional + non-functional; **explicitly name the static vs. dynamic fork** |
| Capacity estimation | 5 min | Read:write ratio for the chosen flavor(s); this number should drive your architecture choices |
| High-level architecture | 5 min | Draw both paths if in scope; name the region-based partitioning up front |
| Geospatial indexing (deep dive) | 10 min | Compare 2–3 structures, justify your pick, explain the boundary problem and neighbor-ring fix |
| Write path / ingestion (dynamic) | 7 min | Streaming ingestion, movement-threshold dedup, TTL expiry |
| Matching/assignment | 5 min | The CP island — CAS-based driver claiming, race condition, fallback to next candidate |
| Failure modes | 5 min | Hot cells, GPS jitter, node failure, unbounded ring expansion |
| CAP & freshness | 3 min | Per-component CAP table; per-consumer staleness budgets |

**What to cut if short on time:** live tracking fan-out detail, full comparison of all five indexing structures (pick your top 2 and move on). **Never cut:** the static/dynamic fork, the boundary problem, and the matching race condition — these three are what separate this answer from a generic "use a database with geo indexing" response.

---

## 20. Quick-Reference Cheatsheet

```
KEY DECISION
────────────
STATIC (Yelp):  read-heavy, write-rarely, batch-rebuildable index,
                AP everywhere, cache aggressively
DYNAMIC (Uber): write-heavy, continuous updates, in-memory index,
                AP for search + CP island for assignment,
                cache almost nothing on the hot query path

INDEXING STRUCTURES AT A GLANCE
─────────────────────────────────
Geohash:    simple, shards by prefix range, 8-neighbor boundary check
H3:         hexagonal, 6 equidistant neighbors, used by Uber in production
Quadtree:   adapts to density, hard to shard, in-memory-friendly
S2:         Hilbert-curve locality, no pole distortion, used by MongoDB
R-tree:     best for polygons/regions, powers PostGIS, single-node-ish

QUERY ALGORITHM
────────────────
1. Map query point → cell at target precision
2. Fetch center cell + neighbor ring (never just center — boundary miss)
3. Haversine-filter candidates to exact radius
4. Sort, return top K
5. Expand ring if sparse — but CAP the expansion

WRITE PATH (dynamic)
─────────────────────
Streaming connection (WebSocket/gRPC) → movement-threshold dedup
→ upsert in-memory grid → async publish to region-partitioned Kafka
→ TTL on grid entry (evict stale/offline entities)

MATCHING RACE CONDITION
──────────────────────────
Query phase:      AP — fast, approximate, tolerant of staleness
Assignment phase: CP — atomic CAS claim (SETNX), fallback to
                   next-nearest candidate on failure

CAP DECISIONS
─────────────
AP:  POI index, in-memory geo grid, ingestion pipeline, trip history
CP:  driver assignment lock (narrowly scoped), region routing metadata

SHARDING
────────
Static:  geohash prefix range, sub-shard hot cells (dense cities)
Dynamic: shard by REGION first (natural product boundary — rides
         don't cross cities), then consistent-hash within region
         by H3/geohash cell — cross-shard queries essentially don't exist

STAFF-LEVEL SIGNALS TO HIT
───────────────────────────
✓ Name the static vs. dynamic fork before being asked
✓ Justify indexing structure choice against the write pattern
✓ Call out the cell-boundary problem and neighbor-ring fix unprompted
✓ Isolate the CP island (matching) inside the AP-by-default system
✓ Recognize proximity queries are naturally shard-local — no scatter-gather
✓ Per-consumer staleness budgets, not one global TTL
✓ Know when caching helps (static) vs. actively hurts (dynamic nearby query)
✓ Movement-threshold write filter to control write amplification from GPS jitter
✓ Cap ring expansion to bound worst-case latency in sparse regions
```

---

*Guide compiled for Senior & Staff-level system design interview preparation.*
*Topics: Proximity Service, Geospatial Indexing, Geohash, H3, Quadtree, CAP Theorem, Sharding.*

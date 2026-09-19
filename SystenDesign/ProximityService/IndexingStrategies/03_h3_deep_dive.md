# H3 — Deep Dive
### Uber's Hexagonal Hierarchical Spatial Index & Its Role in Proximity Services

---

## Table of Contents

1. [What Is H3?](#1-what-is-h3)
2. [Why Hexagons? (Not Squares, Not Triangles)](#2-why-hexagons-not-squares-not-triangles)
3. [How It Works: The Grid System](#3-how-it-works-the-grid-system)
4. [How It Works: Indexing, Neighbors, and Rings](#4-how-it-works-indexing-neighbors-and-rings)
5. [Resolution Table](#5-resolution-table)
6. [Worked Example: Nearby-Driver Query](#6-worked-example-nearby-driver-query)
7. [Why Proximity Services Use H3](#7-why-proximity-services-use-h3)
8. [Pros and Cons](#8-pros-and-cons)
9. [H3 vs. Quadtree vs. Geohash](#9-h3-vs-quadtree-vs-geohash)
10. [When to Reach for H3 (and When Not To)](#10-when-to-reach-for-h3-and-when-not-to)
11. [Quick-Reference Cheatsheet](#11-quick-reference-cheatsheet)

---

## 1. What Is H3?

**H3** is an open-source **hexagonal hierarchical spatial indexing system**, created by Uber and released in 2018 to power ride pricing, dispatch, and geospatial analytics at global scale. Unlike geohash (which divides the earth into rectangular cells) or a quadtree (which recursively subdivides a plane into square quadrants), H3 tiles the earth with **hexagons**, arranged in a **hierarchy of 16 resolutions (0–15)**, where each cell at one resolution is made of roughly 7 smaller cells at the next.

At its core, H3 answers the same question every geospatial index answers — *"what cell does this point fall into, and what cells are nearby?"* — but does it with a grid shape and projection specifically chosen to avoid the distortion and neighbor-asymmetry problems that plague simpler lat/lon-based grids.

```
A single H3 cell, resolution 9 (~0.1 km², roughly a city block):

              ╱‾‾‾╲
             ╱     ╲
            │   •   │   ← 6 neighbors, all equidistant,
             ╲     ╱      all sharing a full edge
              ╲___╱

Every H3 cell is represented as a single 64-bit integer —
cheap to store, index, compare, and shard by.
```

---

## 2. Why Hexagons? (Not Squares, Not Triangles)

This is the design decision H3 is built around, and it's worth understanding on its own before anything else.

### The problem with square grids

A square cell has **two different kinds of neighbors**:

```
┌───┬───┬───┐
│ NW│ N │ NE│    N/S/E/W neighbors share a full edge,
├───┼───┼───┤    distance d from center
│ W │ • │ E │
├───┼───┼───┤    NW/NE/SW/SE neighbors share only a
│ SW│ S │ SE│    corner, distance d√2 from center
└───┴───┴───┘
```

Edge-adjacent neighbors (N/S/E/W) are a different distance from the center than corner-adjacent neighbors (NW/NE/SW/SE). Any calculation involving movement, spread, or a search radius is subtly biased by this asymmetry — a "ring of neighbors" isn't actually a ring; it's an octagon-ish shape with two different radii mixed together.

### Why hexagons fix this

```
      ╱‾╲ ╱‾╲
     │NW │ N │        Every one of the 6 neighbors:
      ╲_╱‾╲_╱           • shares a full edge (not just a corner)
      ╱‾╲ ╱‾╲            • sits at the SAME distance from center
     │ W │ • │           • no asymmetry to correct for
      ╲_╱‾╲_╱
      ╱‾╲ ╱‾╲
     │SW │ S │
      ╲_╱ ╲_╱
```

All six neighbors are uniform. This is a genuine geometric property (hexagons and triangles are the only regular polygons that tile a plane with this uniformity, and hexagons pack more efficiently than triangles), not a stylistic choice — it's *why* Uber picked this shape for a system whose core operation is "find things within some radius of a moving point."

### The sphere problem, and why H3 uses an icosahedron

The earth is a sphere, not a flat plane, and you cannot tile a sphere with hexagons alone — topology forces exactly **12 unavoidable pentagons** into any hexagonal tiling of a sphere (one at each vertex of the underlying shape). H3 handles this by projecting the globe onto a **gnomonic projection centered on the faces of an icosahedron** (a 20-sided platonic solid) rather than a single flat map projection. This keeps distortion low and roughly uniform everywhere — including near the poles, where naive lat/lon grids get severely stretched. The 12 pentagons that result are deliberately positioned mostly over ocean, so most real-world workloads never actually touch one — but production code should never *assume* every cell has exactly 6 neighbors, since a pentagon has 5.

---

## 3. How It Works: The Grid System

```
Resolution 0:  122 base cells cover the entire planet
               (110 hexagons + 12 pentagons)

Each step to a finer resolution subdivides every hexagon
into 7 children (a pentagon has 6: 5 hexagon children + 1
pentagon child), so cell count grows roughly ×7 per level:

  c(r) = 2 + 120 · 7^r     (total cells at resolution r)

Resolution 0 → 122 cells        (continent-scale)
Resolution 5 → ~2.0 million     (city-scale)
Resolution 9 → ~4.8 billion     (neighborhood-block-scale)
Resolution 15 → ~570 trillion   (sub-meter — rarely used in practice)
```

Because each parent cell decomposes cleanly into (about) 7 children, you can move up or down the hierarchy cheaply — compress a cluster of fine cells into their shared parent for a coarser view, or expand a coarse cell into its children when you need more precision in one region. This hierarchical property is what makes H3 useful for both *fine-grained dispatch* (resolution 8–9, block-level) and *coarse-grained analytics* (resolution 4–5, city-level) using the same underlying index.

---

## 4. How It Works: Indexing, Neighbors, and Rings

### Point → cell

```
h3_index = latLngToCell(lat, lon, resolution)
  → returns a single 64-bit integer identifying the hexagon
    that contains this point, at the requested resolution
```

This 64-bit integer is the unit you actually store, index, and shard by — you never work with hexagon geometry directly on the hot path.

### Neighbor lookup ("k-ring")

```
kRing(h3_index, k)
  → returns every cell within k "rings" of hexagons from the center

k=0 → just the cell itself
k=1 → center + 6 immediate neighbors (7 cells total)
k=2 → center + 2 rings out (19 cells total)
```

This is the H3 equivalent of "query the center cell plus its 8 neighbors" in geohash — except with hexagons, the ring is a genuinely uniform ring, not an approximation, because every neighbor is equidistant by construction.

### The nearby-search algorithm

```
1. Compute h3_index for the query point at a resolution whose
   cell size roughly matches the desired search radius
2. kRing(h3_index, k=1) — or expand k if results are sparse
3. Fetch all entities indexed under any cell in that ring
4. Haversine-filter candidates to the exact radius
5. Sort by distance, return top K
```

Structurally identical to the geohash query pattern from the Proximity Service guide — coarse index lookup via ring expansion, then an exact-distance filter — but with cleaner, distortion-free neighbor semantics.

---

## 5. Resolution Table

*(Selected resolutions; average values — hexagon size varies slightly by position, see H3's official docs for exact min/max.)*

| Resolution | Avg. edge length | Avg. cell area | Roughly the size of |
|---|---|---|---|
| 0 | 1,281 km | 4,357,449 km² | A large country |
| 4 | 26.1 km | 1,770 km² | A metro region |
| 5 | 9.85 km | 253 km² | A city |
| 7 | 1.41 km | 5.16 km² | A large neighborhood |
| 8 | 0.53 km | 0.74 km² | A few city blocks |
| 9 | 0.20 km | 0.105 km² | A single city block |
| 10 | 76 m | 0.015 km² | A building footprint |
| 13 | 4.1 m | 0.000044 km² | A parking space |
| 15 | 0.58 m | < 1 m² | Sub-meter (rarely used) |

**For ride-hail/delivery dispatch, resolution 8–9 is the typical sweet spot** — fine enough to distinguish nearby city blocks, coarse enough that a metro area's active driver fleet fits comfortably in a manageable number of cells.

---

## 6. Worked Example: Nearby-Driver Query

```
Rider requests a ride at resolution-9 cell H = 89283082837ffff

1. kRing(H, 1) → H plus its 6 neighbors = 7 cells, ~0.7 km² covered
2. Look up all drivers currently indexed under any of those 7 cells
   (a simple hash-map / Redis lookup per cell — O(1) each)
3. Say this returns 4 candidate drivers
4. Haversine-filter: drop any actually outside the 500m target radius
5. Sort remaining by distance → return nearest driver

If fewer than the desired candidate count come back:
   kRing(H, 2) → 19 cells, ~1.9 km² covered — retry
   (cap the expansion, same principle as capping geohash ring
    expansion in sparse regions, to bound worst-case query latency)
```

Every step here is O(1) dictionary lookups per cell — no tree traversal, no bounding-box intersection math. That simplicity is a direct consequence of H3's flat, hash-friendly 64-bit keyspace.

---

## 7. Why Proximity Services Use H3

Tying back to the [Proximity Service guide](#) and the [Quadtree deep dive](#): H3 is the structure that wins when the **distributed-systems half of the problem** — sharding a write-heavy index across many machines — matters more than density-adaptive elegance.

### The write-heavy reality of dynamic proximity data

At Uber's scale, driver location pings arrive continuously (recall from the Proximity Service guide: on the order of hundreds of thousands of writes/sec across a large fleet). Every one of those writes needs to:

1. Compute a cell ID for the new position — O(1) with H3, just a coordinate transform
2. Update the in-memory index — a simple upsert into a hash map keyed by the 64-bit cell ID
3. Land on the *correct shard* — because the cell ID is just an integer, `hash(cell_id) % num_shards` (or consistent hashing over the same key) works immediately, no custom partitioning logic needed

Compare this to a quadtree, where distributing the structure across nodes means manually partitioning the top-level quadrants — there's no single flat key you can hand to a standard sharding scheme. **H3's biggest practical win over a quadtree, for this exact use case, is that it shards for free.**

### Where the hexagon geometry specifically helps

- **Ring queries are genuinely correct**, not approximated — a "search radius" maps naturally onto "k rings of hexagons" without the corner/edge distance asymmetry a square grid or geohash cell has
- **No pole/antimeridian distortion** — matters for any global service, though most ride-hail/delivery operates well away from poles in practice
- **Hierarchical resolution** lets the same cell ID scheme serve both fine-grained dispatch (resolution 9, block-level matching) and coarse-grained analytics/surge-pricing zones (resolution 5, city-level aggregation) without maintaining two separate indexing systems

### The honest tradeoff to name

H3 does **not** solve the density problem the way a quadtree does. A resolution-9 grid over Manhattan and a resolution-9 grid over rural Nebraska have the *same* cell size — dense areas just end up with many drivers per cell, sparse areas with very few or zero. H3 accepts this in exchange for a flat, uniformly-shardable keyspace; a quadtree accepts a harder sharding story in exchange for automatic density adaptation. This is precisely the tension flagged in the Quadtree deep dive, from the other direction.

---

## 8. Pros and Cons

| | Detail |
|---|---|
| ✅ **Shards trivially** | A flat 64-bit cell ID is a natural key for consistent hashing or `hash(id) % N` — no custom partitioning logic needed, unlike a quadtree |
| ✅ **O(1) point updates** | Computing a cell ID and upserting into a hash map is cheap — critical at hundreds of thousands of writes/sec |
| ✅ **Uniform, distortion-free neighbors** | All 6 neighbors equidistant and edge-sharing (except at the 12 unavoidable pentagons) — ring expansion is geometrically correct, not approximated |
| ✅ **Hierarchical** | The same coordinate system serves fine-grained dispatch and coarse-grained analytics by just changing the resolution parameter |
| ✅ **Battle-tested at scale** | Open-sourced by Uber, used in production for ride pricing and dispatch across a global fleet |
| ❌ **Fixed cell size per resolution** | Doesn't adapt to local density the way a quadtree does — dense downtown cells and sparse rural cells at the same resolution hold wildly different entity counts |
| ❌ **Pentagon edge cases** | 12 cells per resolution have only 5 neighbors, not 6 — code that blindly assumes 6 will break on those cells (rare in practice since they land mostly over ocean, but not something to assume away entirely) |
| ❌ **Resolution choice is a manual tuning knob** | Picking the "right" resolution for a given product (block-level dispatch vs. city-level surge pricing) is a design decision you have to get right up front, similar to choosing a geohash precision length |
| ❌ **Still needs a hot-cell mitigation strategy** | Because cell size is fixed, a sudden density spike (stadium letting out) can overload one cell's worth of index/query load — H3 doesn't solve this natively the way a quadtree's re-splitting does; you still need the sub-cell / secondary-index patch described in the Proximity Service guide's failure modes |

---

## 9. H3 vs. Quadtree vs. Geohash

| | H3 | Quadtree | Geohash |
|---|---|---|---|
| Cell shape | Hexagon | Square (variable size) | Rectangle (fixed size per precision) |
| Adapts to density | No — fixed size per resolution | Yes — native property | No — fixed size per precision |
| Shards naturally | Yes — flat 64-bit key | No — needs manual partitioning | Yes — flat string key, prefix range |
| Neighbor uniformity | Yes — all 6 equidistant | N/A (tree structure, not grid rings) | No — corner neighbors farther than edge neighbors |
| Update cost | O(1) | O(log n), can trigger splits | O(1) |
| Best for | Distributed, write-heavy, real-time (Uber's actual choice) | Single-node/small-cluster, highly uneven density | Distributed, simple range-shardable use cases; ubiquitous library support |

**The practical framing for an interview:** H3 and geohash solve the same *sharding* problem (flat, hashable keyspace) in similar ways, but H3's hexagonal geometry gives cleaner, distortion-free neighbor queries — which is why Uber built and open-sourced it rather than just using geohash. A quadtree solves a *different* problem (density adaptivity) at the cost of the sharding story. Knowing which problem you're optimizing for is the actual decision, not just which structure is "better" in the abstract.

---

## 10. When to Reach for H3 (and When Not To)

**Good fit:**
- Write-heavy, continuously-updating location data that needs to shard across many nodes (driver/courier tracking, real-time asset tracking)
- Any system where ring/radius queries are the dominant access pattern and neighbor-distance accuracy matters
- Systems that need both fine-grained (dispatch) and coarse-grained (analytics, zone definitions) views over the same coordinate space

**Poor fit:**
- Highly non-uniform density where a single fixed resolution wastes index capacity in sparse regions or overloads cells in dense ones, and you can't tolerate the hot-cell patching this requires — a quadtree's native adaptivity may be simpler here
- Polygon/region containment queries (delivery zone boundaries, service-area definitions with irregular shapes) — an R-tree/PostGIS is usually a more direct fit
- Small-scale, single-node systems where sharding was never going to be a concern — the extra conceptual overhead of icosahedron projection and hierarchical cell IDs may not be worth it over a simpler geohash

---

## 11. Quick-Reference Cheatsheet

```
WHAT IT IS
──────────
Hexagonal hierarchical spatial index, open-sourced by Uber (2018)
16 resolutions (0–15), each cell ≈ 7 children at the next resolution
122 base cells at resolution 0 (110 hexagons + 12 unavoidable pentagons)
Every cell = a single 64-bit integer

WHY HEXAGONS
────────────
All 6 neighbors equidistant + edge-sharing (squares have 2 distance
classes: edge-neighbors at d, corner-neighbors at d√2)
→ ring expansion for radius search is geometrically uniform, not
  approximated

WHY AN ICOSAHEDRON PROJECTION
───────────────────────────────
Spheres can't be tiled with hexagons alone — 12 pentagons are
topologically unavoidable. Icosahedron-based gnomonic projection
keeps distortion low and uniform globally, including near poles.
Pentagons deliberately land mostly over ocean.

KEY OPERATIONS
───────────────
latLngToCell(lat, lon, res)  → 64-bit cell ID
kRing(cell, k)               → cell + all neighbors within k rings
  k=1 → 7 cells (center + 6 neighbors)
  k=2 → 19 cells

RESOLUTION QUICK PICKS
────────────────────────
Res 5  ≈ 253 km²    → city-level (surge pricing zones)
Res 8  ≈ 0.74 km²   → neighborhood-level
Res 9  ≈ 0.105 km²  → city-block-level (typical dispatch resolution)
Res 10 ≈ 0.015 km²  → building-level

WHY PROXIMITY SERVICES CARE
──────────────────────────────
Flat 64-bit key → shards for free (hash(cell_id) % N)
O(1) point updates → survives hundreds of thousands of writes/sec
Uniform neighbors → correct, unbiased radius search
Hierarchical → one system serves both fine dispatch and coarse analytics

PROS                              CONS
────                              ────
Shards trivially                  Fixed cell size — no density adaptivity
O(1) updates                      Pentagon cells have only 5 neighbors
Distortion-free neighbors         Resolution is a manual tuning choice
Hierarchical (multi-resolution)   Still needs hot-cell mitigation

H3 vs QUADTREE — THE CORE TENSION
────────────────────────────────────
H3:        flat shardable keyspace, fixed cell size
Quadtree:  density-adaptive cell size, awkward to shard
Uber's actual production choice: H3 — because sharding a write-heavy,
globally-distributed index outweighs density-adaptive elegance.
```

---

*Companion reference to the Proximity Service system design guide and the Quadtree deep dive.*
*Topics: H3, Hexagonal Grid, Geospatial Indexing, Uber, Sharding, Ring Queries.*

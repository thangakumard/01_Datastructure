# Geohashing — Detailed Guide

## Table of Contents

1. [What is Geohashing?](#1-what-is-geohashing)
2. [How Geohashing Works](#2-how-geohashing-works)
3. [Why Geohashing Is Useful](#3-why-geohashing-is-useful)
4. [Latitude and Longitude](#4-latitude-and-longitude)
5. [The Core Idea](#5-the-core-idea)
6. [How Geohashing Is Calculated](#6-how-geohashing-is-calculated)
7. [Step-by-Step Example](#7-step-by-step-example)
8. [How the Binary Bits Are Generated](#8-how-the-binary-bits-are-generated)
9. [How Latitude and Longitude Bits Are Interleaved](#9-how-latitude-and-longitude-bits-are-interleaved)
10. [Converting the Bits to Base32](#10-converting-the-bits-to-base32)
11. [Geohash Precision](#11-geohash-precision)
12. [Geohash as a Spatial Index](#12-geohash-as-a-spatial-index)
13. [Finding Nearby Drivers](#13-finding-nearby-drivers)
14. [Geohash Neighbors](#14-geohash-neighbors)
15. [Important Limitation: Geohash Is Not Distance](#15-important-limitation-geohash-is-not-distance)
16. [Pros](#16-pros)
17. [Cons](#17-cons)
18. [Geohash vs Other Spatial Indexes](#18-geohash-vs-other-spatial-indexes)
19. [Interview Explanation](#19-interview-explanation)
20. [Key Takeaways](#20-key-takeaways)

---

# 1. What is Geohashing?

**Geohashing** is a technique for converting a geographic coordinate:

```text
(latitude, longitude)
```

into a short string such as:

```text
9q8yy
```

The important idea is that the string represents a **rectangular geographic cell**.

For example:

```text
9q8yy
```

might represent an area of a city.

A longer geohash represents a smaller area:

```text
9
9q
9q8
9q8y
9q8yy
9q8yyk
9q8yyk8
```

So, in general:

```text
Short geohash  -> large geographic area
Long geohash   -> small geographic area
```

This makes geohashing useful for **location-based queries**.

For example:

> Find all drivers near this rider.

Instead of calculating the exact distance from the rider to every driver in the world, we can first find the geographic cells surrounding the rider.

---

# 2. How Geohashing Works

![Geohashing visualization](https://geohash.softeng.co/)

*Interactive visualization: [https://geohash.softeng.co/](https://geohash.softeng.co/)*

Geohashing transforms two-dimensional latitude and longitude coordinates into a one-dimensional string. This is done by recursively dividing the Earth's surface into smaller and smaller grid cells.

1. The world is initially divided into 4 rows and 8 columns, forming 32 cells.
2. Each cell is assigned a character from a Base32 encoding set (`0123456789bcdefghjkmnpqrstuvwxyz` i.e. all digits + all lowercase characters except `a`, `l`, `i`, and `o`).
3. Each of these 32 cells is further subdivided into another 32 smaller cells, continuing this pattern recursively.
4. The resulting sequence of characters uniquely identifies a location, with longer geohashes representing finer precision.

This hierarchical structure enables geohashes to be used for efficient spatial indexing—shorter geohashes cover larger areas, while longer geohashes provide pinpoint accuracy.

---

# 3. Why Geohashing Is Useful

Suppose Uber-like ride sharing has:

```text
1,000,000 active drivers
```

A rider requests a ride from:

```text
latitude  = 47.6101
longitude = -122.2015
```

A naive implementation could calculate the distance between the rider and all 1 million drivers.

That is expensive:

```text
1,000,000 distance calculations
```

Instead, divide the world into geographic cells:

```text
+---------+---------+---------+
|         |         |         |
|  cell   |  cell   |  cell   |
|         |         |         |
+---------+---------+---------+
|         | DRIVER  |         |
|         |  USER   |         |
|         |         |         |
+---------+---------+---------+
|         |         |         |
|         |         |         |
|         |         |         |
+---------+---------+---------+
```

The rider belongs to one cell.

We query:

```text
rider's cell
+ neighboring cells
```

Now perhaps only 100 drivers need to be considered.

Then we calculate the **actual distance** only for those 100 candidates.

The overall strategy becomes:

```text
Geohash
   ↓
Find candidate cells
   ↓
Find candidate drivers
   ↓
Calculate exact distance
   ↓
Return nearest drivers
```

This is the main reason geohashing is valuable in proximity-service system design.

---

# 4. Latitude and Longitude

Before understanding geohashing, understand coordinates.

The Earth can be represented using:

```text
Latitude
Longitude
```

## Latitude

Latitude tells us how far north or south we are.

Range:

```text
-90°  to +90°
```

Examples:

```text
+90° = North Pole
  0° = Equator
-90° = South Pole
```

## Longitude

Longitude tells us how far east or west we are.

Range:

```text
-180° to +180°
```

Examples:

```text
0°     = Prime Meridian
+180°   = approximately opposite side of Earth
-180°   = approximately opposite side of Earth
```

A location can therefore be represented as:

```text
(latitude, longitude)
```

Example:

```text
Seattle
latitude  = 47.6062
longitude = -122.3321
```

---

# 5. The Core Idea

Geohashing repeatedly divides the Earth's coordinate ranges into halves.

For longitude:

```text
[-180, +180]
```

Divide it into two:

```text
[-180, 0]       [0, +180]
```

If the longitude is in the left half, record:

```text
0
```

If it is in the right half, record:

```text
1
```

Then divide that half again.

This produces a sequence of binary decisions.

The same process happens for latitude:

```text
[-90, +90]
```

The result is essentially a binary representation of a geographic region.

---

# 6. How Geohashing Is Calculated

There are four major steps.

```text
Latitude / Longitude
        ↓
Repeated interval subdivision
        ↓
Binary representation
        ↓
Interleave latitude + longitude bits
        ↓
Convert groups of 5 bits to Base32
        ↓
Geohash string
```

More precisely:

```text
(lat, lon)
   |
   +----> longitude bits
   |
   +----> latitude bits
              |
              v
      interleave bits
              |
              v
      5-bit groups
              |
              v
       Base32 encoding
              |
              v
         geohash
```

---

# 7. Step-by-Step Example

Let's use a simplified example.

Suppose:

```text
latitude  = 47.6
longitude = -122.3
```

We want to determine the longitude bits.

Start with:

```text
Longitude range:

[-180, +180]
```

Midpoint:

```text
0
```

Our longitude is:

```text
-122.3
```

Since:

```text
-122.3 < 0
```

it belongs to the left half.

Record:

```text
0
```

New range:

```text
[-180, 0]
```

Now calculate midpoint:

```text
(-180 + 0) / 2 = -90
```

Again:

```text
-122.3 < -90
```

Record:

```text
0
```

New range:

```text
[-180, -90]
```

Midpoint:

```text
-135
```

Now:

```text
-122.3 >= -135
```

Record:

```text
1
```

New range:

```text
[-135, -90]
```

Midpoint:

```text
-112.5
```

Now:

```text
-122.3 < -112.5
```

Record:

```text
0
```

And so on.

We eventually obtain something like:

```text
longitude bits:

0010...
```

The exact number of bits depends on the desired precision.

The exact same process is performed for latitude.

---

# 8. How the Binary Bits Are Generated

Let's examine the interval subdivision more formally.

## Longitude

Initial interval:

```text
[-180, 180]
```

For every bit:

```text
mid = (min + max) / 2
```

If:

```text
longitude < mid
```

then:

```text
bit = 0
max = mid
```

Otherwise:

```text
bit = 1
min = mid
```

Pseudo-code:

```text
min = -180
max = 180

repeat N times:

    mid = (min + max) / 2

    if longitude < mid:
        append 0
        max = mid
    else:
        append 1
        min = mid
```

Latitude works the same way:

```text
min = -90
max = 90
```

So we get two binary sequences:

```text
latitude:

101101...

longitude:

001011...
```

---

# 9. How Latitude and Longitude Bits Are Interleaved

This is one of the most important parts of geohashing.

We have:

```text
longitude bits:

L0 L1 L2 L3 L4 ...


latitude bits:

A0 A1 A2 A3 A4 ...
```

Geohash interleaves them.

Typically the first bit represents longitude, followed by latitude:

```text
longitude bit 0
latitude bit 0
longitude bit 1
latitude bit 1
longitude bit 2
latitude bit 2
...
```

For example:

```text
Longitude:

0 0 1 0 1

Latitude:

1 0 1 1 0
```

Interleave:

```text
0 1 0 0 1 1 0 1 1 0
```

Therefore:

```text
Longitude:  0 0 1 0 1
             ↓ ↓ ↓ ↓ ↓

Latitude:   1 0 1 1 0
             ↓ ↓ ↓ ↓ ↓

Combined:    0 1 0 0 1 1 0 1 1 0
```

The resulting binary sequence represents a geographic cell.

---

# 10. Converting the Bits to Base32

The binary sequence is converted into groups of five bits.

Example:

```text
0100110110...
```

Break it into groups:

```text
01001
10110
...
```

Each group contains 5 bits.

Five bits can represent:

```text
2^5 = 32
```

values.

Therefore geohash uses a **Base32 alphabet**.

The standard geohash alphabet is:

```text
0123456789bcdefghjkmnpqrstuvwxyz
```

Notice that some visually confusing characters are intentionally excluded, such as:

```text
a
i
l
o
```

This makes geohashes easier for humans to read and communicate.

Conceptually:

```text
5 binary bits
      ↓
0 - 31
      ↓
Base32 character
```

For example:

```text
00000 -> 0
00001 -> 1
00010 -> 2
...
```

The complete binary stream becomes something like:

```text
9q8yy
```

That is the geohash.

---

# 11. Geohash Precision

The length of a geohash determines its geographic precision.

Generally:

```text
More characters
      ↓
More bits
      ↓
Smaller cell
      ↓
Higher geographic precision
```

Typical approximate cell dimensions are:

| Geohash length | Approx. cell size |
|---:|---|
| 1 | ~5,000 km × 5,000 km |
| 2 | ~1,250 km × 625 km |
| 3 | ~156 km × 156 km |
| 4 | ~39 km × 20 km |
| 5 | ~5 km × 5 km |
| 6 | ~1.2 km × 0.6 km |
| 7 | ~150 m × 150 m |
| 8 | ~38 m × 19 m |
| 9 | ~5 m × 5 m |
| 10 | ~1 m × 0.6 m |

These are approximate and vary with latitude.

For a ride-sharing application, a geohash length of:

```text
5
6
7
```

might be useful depending on the city's density and the search radius.

There is no universally correct precision.

---

# 12. Geohash as a Spatial Index

The biggest practical benefit is that a 2D geographic coordinate can be transformed into a **string that can be indexed**.

Suppose drivers have:

```text
driver_id
latitude
longitude
geohash
availability
```

Example:

```text
driver_101 -> 9q8yy
driver_102 -> 9q8yy
driver_103 -> 9q8yz
driver_104 -> 9q8yx
```

Now the database can organize drivers by geohash.

Conceptually:

```text
geohash
   |
   +-- 9q8yy
   |     +-- driver_101
   |     +-- driver_102
   |
   +-- 9q8yz
   |     +-- driver_103
   |
   +-- 9q8yx
         +-- driver_104
```

A query for a particular geographic area can therefore first identify the relevant geohashes.

---

# 13. Finding Nearby Drivers

This is a classic system-design use case.

Suppose the rider is at:

```text
lat = 47.6062
lon = -122.3321
```

Calculate:

```text
geohash = 9q8yy...
```

Now suppose the requested radius is:

```text
5 km
```

We don't query every driver.

Instead:

```text
                +-------+-------+-------+
                |       |       |       |
                |  cell |  cell |  cell |
                |       |       |       |
                +-------+-------+-------+
                |       | RIDER |       |
                |       |   X   |       |
                |       |       |       |
                +-------+-------+-------+
                |       |       |       |
                |  cell |  cell |  cell |
                |       |       |       |
                +-------+-------+-------+
```

Search:

```text
center cell
+
neighboring cells
```

Then:

```text
candidate drivers
       ↓
exact Haversine distance
       ↓
drivers within 5 km
       ↓
sort by distance
       ↓
return nearest drivers
```

## Important

Geohash does **not** tell you the exact distance.

It is primarily a way to reduce the search space.

The final distance calculation should use the actual coordinates.

---

# 14. Geohash Neighbors

A common mistake is to query only the rider's geohash.

Suppose the rider is near the boundary:

```text
+-----------+-----------+
|           |           |
|           |           |
|           |     X     |
|           |           |
+-----------+-----------+
```

A driver may be only:

```text
100 meters away
```

but belong to the adjacent cell.

Therefore:

```text
Search center cell
+
north
south
east
west
+
diagonal neighbors
```

A common 3×3 search is:

```text
+---------+---------+---------+
| NW      | N       | NE      |
+---------+---------+---------+
| W       | CENTER  | E       |
+---------+---------+---------+
| SW      | S       | SE      |
+---------+---------+---------+
```

For larger search radii, more cells may be required.

The application can dynamically expand the search.

For example:

```text
Search level 1
    ↓
No enough drivers
    ↓
Search level 2
    ↓
No enough drivers
    ↓
Search level 3
```

This is often better than always querying a huge geographic area.

---

# 15. Important Limitation: Geohash Is Not Distance

This distinction is critical.

Consider:

```text
Location A -> geohash 9q8yy
Location B -> geohash 9q8yz
```

It does NOT mean:

```text
A is close to B
```

in an exact mathematical sense.

Similarly, two locations with the same geohash are not necessarily at the same distance.

They are simply guaranteed to be within the same geohash cell.

The geohash is therefore:

```text
Spatial bucket / spatial index
```

not:

```text
Distance measurement
```

Use geohash for:

```text
Candidate selection
```

Use geographic distance for:

```text
Final filtering/ranking
```

---

# 16. Pros

## 15.1 Simple

The concept is relatively easy to understand:

```text
lat/lon
  ↓
geohash
```

It converts geographic coordinates into an indexable representation.

---

## 15.2 Excellent for spatial bucketing

Locations can be grouped into cells:

```text
geohash -> objects in that geographic area
```

This is very useful for:

- nearby drivers
- delivery agents
- stores
- restaurants
- users
- IoT devices
- vehicles

---

## 15.3 Works well with key-value stores

A geohash can become part of a key:

```text
drivers:{geohash}
```

For example:

```text
drivers:9q8yy
```

This works naturally with systems such as Redis and distributed key-value stores.

---

## 15.4 Prefix property

Nearby locations often share a geohash prefix.

For example:

```text
9q8yy
9q8yz
9q8yx
```

may share:

```text
9q8y
```

The common prefix represents a larger geographic region.

This makes hierarchical geographic partitioning possible.

---

## 15.5 Easy horizontal partitioning

You can partition data using the geohash prefix.

For example:

```text
Partition A:
9q8...

Partition B:
9q9...

Partition C:
9qb...
```

This can help distribute geographic data across machines.

---

## 15.6 Compact representation

Instead of storing only a large amount of spatial metadata for indexing, a short string can identify a geographic bucket.

Example:

```text
9q8yyk
```

is compact and convenient to use in keys, caches, and indexes.

---

## 15.7 Fast candidate filtering

Instead of:

```text
1,000,000 drivers
       ↓
distance calculation
```

you can do:

```text
geohash lookup
       ↓
500 candidate drivers
       ↓
exact distance calculation
```

This dramatically reduces computational work.

---

# 17. Cons

## 16.1 Boundary problem

This is the most important weakness.

Two very close locations can fall into different cells:

```text
+-----------+-----------+
|           |           |
|           |     A     |
|           |           |
|           |           |
+-----------+-----------+
|     B     |           |
|           |           |
|           |           |
+-----------+-----------+
```

A and B may be physically close but have different geohashes.

Therefore neighboring cells must be considered.

---

## 16.2 Cell size is fixed for a given precision

Suppose we select a geohash that is approximately:

```text
5 km × 5 km
```

That may be too large in a dense downtown area.

You could have:

```text
100,000 drivers
```

in a single cell.

But in a rural area, the same cell might contain:

```text
2 drivers
```

Therefore fixed geohash precision is not equally optimal everywhere.

---

## 16.3 Geographic density can be uneven

Cities are not uniformly populated.

For example:

```text
Downtown Seattle
      ↓
Very high density

Suburban area
      ↓
Medium density

Rural area
      ↓
Very low density
```

A single geohash precision may result in:

```text
hot cells
```

and:

```text
sparse cells
```

---

## 16.4 Hotspot problem

If a huge number of users/drivers are concentrated in one cell, that cell can become a hotspot.

For example:

```text
Airport
```

might have thousands of drivers waiting in a relatively small area.

A single key such as:

```text
drivers:9q8yy
```

could receive a disproportionate amount of traffic.

Solutions include:

```text
finer geohash precision
+
sharding
+
multiple buckets
+
time/availability partitioning
```

---

## 16.5 Prefix does not always mean uniform distance

Two locations sharing a prefix does not mean they are equally close.

Likewise:

```text
same geohash
```

does not imply:

```text
small exact distance
```

The geohash is an approximation used for indexing.

---

## 16.6 Longitude behaves differently near the poles

Longitude represents different physical distances depending on latitude.

Near the equator:

```text
1 degree longitude
```

represents a relatively large distance.

Near the poles:

```text
1 degree longitude
```

represents a much smaller physical distance.

Therefore geohash cells are not perfectly uniform in physical dimensions globally.

---

## 16.7 Dateline edge cases

Longitude wraps around:

```text
+180° ↔ -180°
```

A system must correctly handle locations near the International Date Line.

For example:

```text
+179.9°
```

and:

```text
-179.9°
```

are geographically very close despite their numeric longitude values looking far apart.

---

# 18. Geohash vs Other Spatial Indexes

Geohash is not the only solution.

| Technique | Main idea | Good for |
|---|---|---|
| Geohash | Encode location into hierarchical cells | Simple proximity indexing |
| QuadTree | Recursively divide 2D space | Dynamic spatial distribution |
| R-tree | Bounding-box spatial index | GIS / database spatial queries |
| S2 | Google's spherical cell hierarchy | Global-scale geographic indexing |
| H3 | Hexagonal hierarchical grid | Large-scale spatial analytics |
| PostGIS | Database-native spatial operations | Rich SQL spatial queries |

For system design interviews, a good answer is:

> "I would use geohash or H3/S2 to partition the geographic space, then calculate exact distance for the candidate objects."

---

# 19. Interview Explanation

If the interviewer asks:

> "How does geohashing work?"

A strong answer is:

> "Geohashing converts a latitude/longitude coordinate into a hierarchical string representing a geographic cell. It repeatedly bisects the latitude and longitude ranges, recording binary decisions. The latitude and longitude bits are interleaved and then encoded using Base32 to produce the geohash string. Longer strings represent smaller geographic cells."

Then explain the practical use:

> "For a proximity service, I don't calculate the distance from a rider to every driver. I first convert the rider's location into a geohash, query that cell and neighboring cells to get candidate drivers, and then use the exact latitude/longitude to calculate distance and return the nearest drivers."

This is usually the most important part of the explanation.

---

# 20. Key Takeaways

Remember these six points:

### 1. Geohash converts coordinates into a geographic cell

```text
(lat, lon)
    ↓
geohash
```

### 2. It is created through repeated binary subdivision

```text
[-180,180]
     ↓
divide
     ↓
divide
     ↓
divide
     ↓
binary bits
```

### 3. Latitude and longitude bits are interleaved

```text
Lon: 0 0 1 0...
Lat: 1 0 1 1...

     ↓

     0 1 0 0 1 1...
```

### 4. Binary groups are converted to Base32

```text
5 bits
  ↓
Base32 character
  ↓
geohash
```

### 5. Longer geohashes mean smaller geographic cells

```text
9q
 ↓
large area

9q8yy
 ↓
smaller area

9q8yyk8
 ↓
much smaller area
```

### 6. Geohash is an index, not a distance calculation

The typical proximity architecture is:

```text
                    Rider Location
                          |
                          v
                    Calculate Geohash
                          |
                          v
              +-------------------------+
              | Center + Neighbor Cells |
              +-------------------------+
                          |
                          v
                  Candidate Drivers
                          |
                          v
                Exact Distance Check
                    (Haversine)
                          |
                          v
                 Sort / Rank Drivers
                          |
                          v
                  Nearest Drivers
```

## One-sentence mental model

> **Geohashing turns a 2D geographic location into a hierarchical string representing a grid cell, allowing us to use ordinary indexes/key-value lookups to quickly find nearby objects before performing exact distance calculations.**

# Java Interview Tips

## Sorting `int[][]` (2D arrays)

```java
int[][] intervals = {{3, 4}, {1, 2}, {5, 0}};

// Ascending by first column
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// Descending by first column
Arrays.sort(intervals, (a, b) -> b[0] - a[0]);
```

### Safer comparator (avoid overflow)

`a[0] - b[0]` breaks when values are near `Integer.MIN_VALUE`/`MAX_VALUE` — a classic interview gotcha. Interviewers sometimes plant this on purpose. Use `Integer.compare`:

```java
Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0])); // ascending
Arrays.sort(intervals, (a, b) -> Integer.compare(b[0], a[0])); // descending
```

## Declaring MinHeap and MaxHeap (`PriorityQueue`)

`PriorityQueue` is a **MinHeap by default** — the smallest element is always at the head.

```java
// MinHeap (default) — smallest element at head
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// MaxHeap — pass a reversed comparator
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
// or (overflow-safe)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
```

### With custom objects (e.g. `int[]` intervals, pairs)

```java
// MinHeap by first element
PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

// MaxHeap by first element
PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b[0], a[0]));
```

### Initial capacity + comparator

```java
// Useful when you know the size upfront (e.g. top-K problems)
PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, Collections.reverseOrder());
```

**Tip:** For "top K" / "kth largest" problems, use a **MinHeap of size K** (evict the smallest when size exceeds K) — this is more efficient (`O(n log k)`) than sorting the entire array (`O(n log n)`).


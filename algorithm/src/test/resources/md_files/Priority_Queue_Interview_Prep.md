# Priority Queue / Heap — Interview Prep

## 1. What is a Priority Queue?

A Java `PriorityQueue` is implemented using a **binary heap**.

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

By default, Java's `PriorityQueue` is a **Min Heap**, so the smallest element has the highest priority.

Example:

```text
             1
           /   \
          3     2
         / \   / \
        7   5 4   8
```

The key property is:

> Every parent is smaller than or equal to its children.

Important: A heap is **not a fully sorted structure**.

---

# 2. Most Important Concept: Heap Height = O(log N)

A binary heap is a **complete binary tree**.

Each level approximately doubles the number of nodes:

```text
Level 0 → 1 node
Level 1 → 2 nodes
Level 2 → 4 nodes
Level 3 → 8 nodes
...
```

Therefore:

```text
2^h ≈ N

h ≈ log₂(N)
```

So the height of a heap containing `N` elements is:

```text
O(log N)
```

This explains why insertion and removal from a Priority Queue take `O(log N)`.

---

# 3. Priority Queue Complexity Cheat Sheet

| Operation | Java Method | Time Complexity | Why? |
|---|---|---:|---|
| View highest priority | `peek()` | **O(1)** | Root contains highest-priority element |
| Insert | `offer()` | **O(log N)** | Element may bubble up |
| Insert | `add()` | **O(log N)** | Same heap insertion |
| Remove highest priority | `poll()` | **O(log N)** | Root removal + bubble down |
| Remove root | `remove()` | **O(log N)** | Equivalent to removing highest priority |
| Remove arbitrary value | `remove(value)` | **O(N)** | Must search for the value |
| Search | `contains()` | **O(N)** | Heap is not sorted |
| Get size | `size()` | **O(1)** | Size is maintained |
| Check empty | `isEmpty()` | **O(1)** | Size check |
| Clear | `clear()` | **O(N)** | Elements are removed/released |

### Memorize These Three

```text
peek()   → O(1)
offer()  → O(log N)
poll()   → O(log N)
```

---

# 4. `peek()` — O(1)

```java
pq.peek();
```

The highest-priority element is always at the root.

For a Min Heap:

```text
             1  ← peek()
           /   \
          3     2
```

No searching is required.

```text
peek() → O(1)
```

---

# 5. `offer()` / `add()` — O(log N)

Example:

```java
pq.offer(1);
```

A new element is initially placed at the next available position.

```text
Before:

       2
      / \
     5   4
    /
   8
```

Insert `1`:

```text
       2
      / \
     5   4
    / \
   8   1
```

The heap property is violated because:

```text
2 > 1
```

So `1` **bubbles up**:

```text
       1
      / \
     2   4
    / \
   8   5
```

The element can move from the bottom to the root.

Maximum movement = heap height = `O(log N)`.

Therefore:

```text
offer() → O(log N)
add()   → O(log N)
```

---

# 6. `poll()` — O(log N)

```java
pq.poll();
```

For a Min Heap, `poll()` removes the minimum element at the root.

Example:

```text
       1
      / \
     3   2
    / \
   7   5
```

Remove `1`.

The last element replaces the root:

```text
       5
      / \
     3   2
    /
   7
```

The heap property is now violated.

`5` **bubbles down**:

```text
       2
      / \
     3   5
    /
   7
```

Maximum movement = heap height = `O(log N)`.

Therefore:

```text
poll() → O(log N)
```

---

# 7. `remove()` — Important Interview Detail

There are two different cases.

## `remove()` with no argument

```java
pq.remove();
```

This removes the highest-priority element.

```text
remove() → O(log N)
```

## `remove(value)`

```java
pq.remove(10);
```

The heap does not provide an efficient way to find an arbitrary value.

The implementation may need to scan the heap:

```text
Search → O(N)
```

After finding it, restoring the heap can take:

```text
O(log N)
```

Overall:

```text
remove(value) → O(N)
```

The `O(N)` search dominates.

---

# 8. `contains()` — O(N)

```java
pq.contains(10);
```

A heap is not sorted.

Example:

```text
             1
           /   \
          3     2
         / \   / \
        7   5 4   8
```

If you are searching for `5`, you cannot perform binary search because the heap only guarantees the parent-child ordering.

Therefore:

```text
contains() → O(N)
```

---

# 9. `size()` — O(1)

```java
pq.size();
```

The Priority Queue maintains its current size.

It does not need to count all elements.

```text
size() → O(1)
```

---

# 10. `isEmpty()` — O(1)

```java
pq.isEmpty();
```

This is essentially a size check.

```text
isEmpty() → O(1)
```

---

# 11. `clear()` — O(N)

```java
pq.clear();
```

The elements need to be removed/released.

```text
clear() → O(N)
```

---

# 12. K Largest — Use a Min Heap

This is one of the most important Priority Queue patterns.

Suppose:

```text
nums = [10, 3, 7, 20, 15, 2, 8]
K = 3
```

We want the **3 largest elements**.

Use a **Min Heap of size K**.

```text
             10
            /  \
           15   20
```

The smallest element among our current top K is at the root:

```text
10 ← easiest to remove
```

When a new number is larger than the root:

```java
if (num > minHeap.peek()) {
    minHeap.poll();
    minHeap.offer(num);
}
```

Both operations take:

```text
poll()  → O(log K)
offer() → O(log K)
```

For `N` input elements:

```text
Overall → O(N log K)
```

### Why Min Heap?

We want to keep the largest elements.

The **smallest of those K elements is the worst candidate**, so put it at the root where it can be removed efficiently.

```text
K Largest
    ↓
Keep LARGE elements
    ↓
Worst = smallest
    ↓
Min Heap
```

---

# 13. K Smallest — Use a Max Heap

Now suppose we want the **K smallest elements**.

Use a **Max Heap of size K**.

Example:

```text
nums = [10, 3, 7, 20, 15, 2, 8]
K = 3
```

Current candidates might be:

```text
          10
         /  \
        3    7
```

The largest among the current K smallest is at the root:

```text
10 ← easiest to remove
```

So:

```text
K Smallest
    ↓
Keep SMALL elements
    ↓
Worst = largest
    ↓
Max Heap
```

Overall complexity:

```text
O(N log K)
```

---

# 14. The Top-K Memory Trick

The easiest way to remember the heap direction:

> Keep the K elements you want, and put the **worst** one at the root so you can remove it easily.

```text
K Largest
    ↓
Worst = smallest
    ↓
Min Heap

K Smallest
    ↓
Worst = largest
    ↓
Max Heap
```

### Quick Rule

```text
K Largest  → Min Heap
K Smallest → Max Heap
```

---

# 15. Building a Heap

There are two important scenarios.

## N individual insertions

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();

for (int x : nums) {
    pq.offer(x);
}
```

Each insertion is:

```text
O(log N)
```

Therefore:

```text
N insertions → O(N log N)
```

## Heapify an existing array

Building a heap using an efficient heapify operation is:

```text
O(N)
```

Important interview distinction:

```text
N individual insertions → O(N log N)

Heapify / Build Heap    → O(N)
```

---

# 16. Why Is `poll()` O(log N)?

A common interview explanation:

1. The root is removed.
2. The last element moves to the root.
3. The element may violate the heap property.
4. It bubbles down.
5. It can move at most one level at a time.
6. A heap has `O(log N)` levels.

Therefore:

```text
poll() → O(log N)
```

---

# 17. Why Is `offer()` O(log N)?

Similarly:

1. New element is placed at the bottom.
2. It may violate the heap property with its parent.
3. It bubbles up.
4. It can move at most to the root.
5. Heap height is `O(log N)`.

Therefore:

```text
offer() → O(log N)
```

---

# 18. Priority Queue vs Sorted Array

This is useful when deciding which data structure to use.

| Operation | Priority Queue | Sorted Array |
|---|---:|---:|
| Peek minimum | O(1) | O(1) |
| Insert | O(log N) | O(N) |
| Remove minimum | O(log N) | O(1) |
| Search arbitrary | O(N) | O(log N) |
| Maintain priority order | Excellent | Expensive insertion |

A Priority Queue is useful when you repeatedly need:

> "Give me the current highest-priority element."

---

# 19. Common Interview Patterns

## Pattern 1 — Top K Largest

```text
Min Heap of size K
Time: O(N log K)
Space: O(K)
```

## Pattern 2 — Top K Smallest

```text
Max Heap of size K
Time: O(N log K)
Space: O(K)
```

## Pattern 3 — Kth Largest

```text
Min Heap of size K
Time: O(N log K)
Space: O(K)
```

## Pattern 4 — Kth Smallest

```text
Max Heap of size K
Time: O(N log K)
Space: O(K)
```

## Pattern 5 — Merge K Sorted Lists

Typically:

```text
Min Heap
Time: O(N log K)
Space: O(K)
```

where `N` is the total number of elements.

## Pattern 6 — Find Median from Data Stream

Typically use:

```text
Max Heap → smaller half
Min Heap → larger half
```

Operations are generally:

```text
Insertion → O(log N)
Find median → O(1)
```

---

# 20. Interview Cheat Sheet

```text
                    PRIORITY QUEUE
                         |
             ┌───────────┴───────────┐
             |                       |
          Min Heap                Max Heap
             |                       |
       smallest first           largest first
```

### Core Operations

```text
peek()       → O(1)

offer()      → O(log N)
add()        → O(log N)

poll()       → O(log N)
remove()     → O(log N)

contains()   → O(N)
remove(x)    → O(N)

size()       → O(1)
isEmpty()    → O(1)
```

### Top K

```text
K Largest    → Min Heap  → O(N log K)
K Smallest   → Max Heap  → O(N log K)
```

### Heap Construction

```text
N insertions → O(N log N)
Heapify      → O(N)
```

---

# 21. One-Minute Interview Explanation

If an interviewer asks:

**"What is the time complexity of Priority Queue operations?"**

A strong answer is:

> "Java's PriorityQueue is implemented as a binary heap. Since the heap has a height of O(log N), insertion using offer or add takes O(log N) because the element may bubble up, and poll takes O(log N) because the replacement element may bubble down. Peek is O(1) because the highest-priority element is always at the root. Searching for an arbitrary value using contains or remove(value) is O(N), because a heap is not fully sorted. For Top-K problems, maintaining a heap of size K gives O(N log K) time and O(K) space."

---

# 22. Final Memory Map

```text
                PRIORITY QUEUE
                      |
                  Binary Heap
                      |
              Height = O(log N)
                      |
       ┌──────────────┼──────────────┐
       ↓              ↓              ↓
     peek           offer           poll
      O(1)          O(log N)       O(log N)


             TOP K PROBLEMS
                    |
          ┌─────────┴─────────┐
          ↓                   ↓
     K Largest            K Smallest
          ↓                   ↓
      Min Heap             Max Heap
          ↓                   ↓
     O(N log K)            O(N log K)
```

## Must-Memorize Rules

```text
1. Heap height = O(log N)

2. peek() = O(1)

3. offer() / add() = O(log N)

4. poll() = O(log N)

5. contains() = O(N)

6. remove(value) = O(N)

7. K Largest = Min Heap

8. K Smallest = Max Heap

9. Top K = O(N log K)

10. Heapify = O(N)
```

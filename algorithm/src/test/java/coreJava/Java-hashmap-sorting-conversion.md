# Java Reference: HashMap Iteration, Sorting `List<int[]>`, and `int[]` → `List` Conversion

Running example — `frequencySort`: count frequencies, sort by freq desc then value asc, return `List<List<Integer>>`.

```java
public List<List<Integer>> frequencySort(int[] nums) {
    Map<Integer, Integer> freqMap = new HashMap<>();
    for (int num : nums) {
        freqMap.merge(num, 1, Integer::sum);
    }
    List<int[]> pairs = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
        pairs.add(new int[]{entry.getKey(), entry.getValue()});
    }
    pairs.sort((a, b) -> {
        if (a[1] != b[1]) return b[1] - a[1];
        return a[0] - b[0];
    });
    List<List<Integer>> result = new ArrayList<>();
    for (int[] pair : pairs) {
        result.add(Arrays.asList(pair[0], pair[1]));
    }
    return result;
}
```

Each section below maps to one step of this method.

---

## 1. Looping Through a `HashMap`

There are five common styles. `entrySet()` is the one used in the sample and is almost always the right default when you need both key and value.

### a) `entrySet()` — need key AND value (most common, used above)

```java
for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
    int key = entry.getKey();
    int val = entry.getValue();
}
```
One pass over the backing table. No extra lookups. **This is what `pairs` is built from in Step 2 of the sample.**

### b) `keySet()` — need only keys, or need keys + occasional lookups

```java
for (int key : freqMap.keySet()) {
    int val = freqMap.get(key); // extra O(1) hash lookup per key
}
```
Works, but every `get(key)` is a second hash computation. Fine for O(1) amortized cost, but it's still 2n operations instead of n — avoid this when you already have the value sitting in an `entrySet()`.

### c) `values()` — need only values, don't care about keys

```java
int total = 0;
for (int val : freqMap.values()) {
    total += val;
}
```

### d) `forEach` with a lambda (Java 8+) — concise, good for simple per-entry side effects

```java
freqMap.forEach((key, val) -> System.out.println(key + " -> " + val));
```
Equivalent to `entrySet()` iteration under the hood. Slightly less flexible than a for-loop (can't easily `break`/`continue`, and mutating the map inside the lambda is riskier).

### e) Explicit `Iterator` — needed when removing entries *during* iteration

```java
Iterator<Map.Entry<Integer, Integer>> it = freqMap.entrySet().iterator();
while (it.hasNext()) {
    Map.Entry<Integer, Integer> entry = it.next();
    if (entry.getValue() == 0) {
        it.remove(); // safe removal — a for-each loop would throw ConcurrentModificationException
    }
}
```

### Comparison

| Style | Access | Extra cost | Use when |
|---|---|---|---|
| `entrySet()` | key + value | none | default choice, need both |
| `keySet()` | key (+ `get`) | +1 hash lookup/entry | only need keys, or occasional value access |
| `values()` | value only | none | only need values |
| `forEach` | key + value | none | short, self-contained per-entry logic |
| `Iterator` | key + value | none | removing entries while iterating |

All are **O(n)** for a full traversal (`keySet()`+`get()` has a larger constant factor, still O(n)).

---

## 2. Sorting `List<int[]>` or `int[][]`

`int[]` doesn't implement `Comparable`, so you always supply a `Comparator`. Two container shapes, two APIs:

| Container | Sort call |
|---|---|
| `List<int[]>` | `list.sort(Comparator<int[]>)` |
| `int[][]` | `Arrays.sort(arr, Comparator<int[]>)` |

### Single-key sort

```java
pairs.sort((a, b) -> a[1] - b[1]);              // ascending by index 1
Arrays.sort(pairsArr, (a, b) -> b[1] - a[1]);   // descending, works the same way on int[][]
```

**Gotcha — subtraction overflow.** `a[1] - b[1]` is a common shortcut, but if the values can be large (near `Integer.MIN_VALUE`/`MAX_VALUE`) the subtraction can overflow and silently give the wrong sign. Safer, overflow-proof version:

```java
pairs.sort((a, b) -> Integer.compare(a[1], b[1]));
```
For interview-sized inputs (frequencies, small counts) the subtraction trick is fine and reads faster — just know the safe alternative if asked.

### Multi-key sort (primary key, tie-broken by secondary key)

This is exactly what the sample does — **frequency descending, value ascending on ties**:

```java
pairs.sort((a, b) -> {
    if (a[1] != b[1]) return b[1] - a[1]; // freq desc: b - a flips the order
    return a[0] - b[0];                    // value asc on tie: a - b keeps natural order
});
```

The same logic expressed with `Comparator` combinators (more readable for 3+ keys):

```java
pairs.sort(
    Comparator.<int[]>comparingInt(p -> p[1]).reversed()   // freq desc
              .thenComparingInt(p -> p[0])                  // value asc
);
```

### Return-value convention (the part people forget)

A comparator's lambda must return:
- **negative** → `a` sorts before `b`
- **positive** → `a` sorts after `b`
- **zero** → tie, order unspecified between them (unless you add another tie-breaker)

To sort **descending**, just flip the operands: `b[1] - a[1]` instead of `a[1] - b[1]`.

### Complexity

`List.sort()` and `Arrays.sort(Object[]...)` both use a stable, adaptive merge sort (TimSort) — **O(n log n)** time, **O(n)** auxiliary space. (Note: `Arrays.sort` on a *primitive* array like `int[]` uses dual-pivot quicksort instead — but that overload takes no comparator, since primitives sort by natural order. As soon as you're sorting `int[][]` or `List<int[]>` with a comparator, you're back to the Object-sort/TimSort path.)

---

## 3. Converting `int[]` to a `List<Integer>` / `ArrayList<Integer>`

This is the step with the sharpest hidden pitfall in the whole problem.

### The trap: `Arrays.asList(int[])` does **not** do what it looks like it does

```java
int[] arr = {1, 2, 3};
List<int[]> broken = Arrays.asList(arr);
System.out.println(broken.size()); // prints 1, not 3!
```

`Arrays.asList` is declared as `Arrays.asList(T... a)` — varargs of a *reference* type. Since `int` is primitive, it can't be a type argument for `T`, so Java infers `T = int[]` and treats your whole array as **one single element**. You silently get a `List<int[]>` of size 1 instead of a `List<Integer>` of size n. No compiler error, no exception — just a wrong-sized list, which is a nasty bug to chase down.

This trap **only** applies to primitive arrays. `Arrays.asList(Integer[] arr)` works correctly, because `Integer` is already a reference type and matches `T` directly.

### Why the sample code's `Arrays.asList(pair[0], pair[1])` is actually fine

```java
result.add(Arrays.asList(pair[0], pair[1]));
```

This is **not** passing an array — it's passing two individual `int` values as two separate varargs arguments. Each one gets autoboxed to `Integer` individually, so this resolves to `Arrays.asList(Integer, Integer)` → `List<Integer>` of size 2. Different call shape, same method name — that's why it's easy to confuse with the broken case above.

### Correct ways to convert a whole `int[]` to `List<Integer>`

**a) Manual loop — most explicit, zero surprises**
```java
int[] arr = {1, 2, 3};
List<Integer> list = new ArrayList<>();
for (int x : arr) {
    list.add(x); // autoboxes int -> Integer one at a time
}
```

**b) Streams (Java 8+) — idiomatic one-liner**
```java
List<Integer> list = Arrays.stream(arr).boxed().collect(Collectors.toList());
// or, equivalently:
List<Integer> list2 = IntStream.of(arr).boxed().collect(Collectors.toList());
```
`Arrays.stream(int[])` gives you an `IntStream`; `.boxed()` converts each `int` to `Integer`, producing a `Stream<Integer>` you can collect.

**c) If you already have an `Integer[]` (boxed array), `Arrays.asList` works directly**
```java
Integer[] boxedArr = {1, 2, 3};
List<Integer> list = Arrays.asList(boxedArr); // fine — size 3, backed by the array
```
Note this returns a **fixed-size** list (backed by the array — no `add`/`remove`); wrap in `new ArrayList<>(...)` if you need a mutable, independent copy.

### Complexity

Boxing n primitives into `Integer` objects is **O(n) time and O(n) extra space** regardless of which method you use — you're allocating n new `Integer` objects (small ints −128..127 are cached by the JVM's `Integer` pool, so those don't allocate, but that's an implementation detail, not something to rely on).

---

## Quick Reference Table

| Task | Snippet | Complexity |
|---|---|---|
| Iterate map, need key+value | `for (var e : map.entrySet())` | O(n) |
| Iterate map, keys only | `for (var k : map.keySet())` | O(n) |
| Sort `List<int[]>`, single key | `list.sort((a,b) -> a[1]-b[1])` | O(n log n) |
| Sort `List<int[]>`, multi-key | `list.sort((a,b) -> a[1]!=b[1] ? b[1]-a[1] : a[0]-b[0])` | O(n log n) |
| Sort `int[][]` | `Arrays.sort(arr, comparator)` | O(n log n) |
| `int[]` → `List<Integer>` (loop) | `for (int x : arr) list.add(x);` | O(n) |
| `int[]` → `List<Integer>` (stream) | `Arrays.stream(arr).boxed().collect(Collectors.toList())` | O(n) |
| Two ints → `List<Integer>` | `Arrays.asList(a, b)` | O(1) |
| **Trap** | `Arrays.asList(int[] arr)` → `List<int[]>` of size **1**, not `List<Integer>` | — |

---

## Full Walkthrough of `frequencySort`

| Step | Code | Concept |
|---|---|---|
| Count | `freqMap.merge(num, 1, Integer::sum)` | HashMap put-or-increment in one call |
| Dump entries | `for (Map.Entry<...> e : freqMap.entrySet())` | §1a — HashMap iteration |
| Sort | `pairs.sort((a,b) -> ...)` | §2 — multi-key `List<int[]>` sort |
| Convert | `Arrays.asList(pair[0], pair[1])` | §3 — two-int varargs, not array-in |

**Overall complexity:** O(n) to build the map + O(k log k) to sort the k distinct values (k ≤ n) + O(k) to build the result → **O(n + k log k)**, which simplifies to **O(n log n)** in the worst case where every element is distinct.

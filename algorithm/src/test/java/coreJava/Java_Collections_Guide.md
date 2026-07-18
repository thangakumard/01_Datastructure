# Java Collections — Complete Reference Guide

A practical guide to understanding Java Collection types, declarations, and behavior.

---

## 1. `.length` vs `.length()` vs `.size()`

### The Golden Rule: "How fancy is the thing?"

| Thing | Syntax | Why |
|---|---|---|
| **Array** (`int[]`, `String[]`) | `.length` | Primitive/basic — just a plain field, no method call |
| **String** | `.length()` | Object but special/built-in — method, but no generics |
| **Collections** (`ArrayList`, `HashMap`, `HashSet`) | `.size()` | Full Collections framework objects |

### The Ladder of Complexity

```
Primitive-ish  →  Built-in Class  →  Collections Framework
   Array              String            ArrayList / HashMap
  .length            .length()              .size()
  (field)            (method)              (method)
```

Just remember it goes: **no parentheses → parentheses → different word entirely.**

### Memory Hooks

- **Array → `.length`** — Arrays are "dumb" (no methods of their own), so it's a plain field. No `()`.
- **String → `.length()`** — Strings *look* like arrays of chars, so they kept the word `length`, but they're a real class so it needs `()`.
- **Collections → `.size()`** — Collections are "smart" objects. They use `size()` to sound more intentional.

### One-Liner
> `[]` → `.length`  |  `""` → `.length()`  |  `<>` → `.size()`

---

## 2. Collection Type Interfaces and Corresponding Type Mapping

### Interface Hierarchy

```
«interface»
Iterable
    └── Collection
            ├── List              ← interface
            │     └── (ArrayList, LinkedList, Vector)
            │
            ├── Set               ← interface
            │     ├── SortedSet   ← interface
            │     │     └── NavigableSet  ← interface
            │     │               └── (TreeSet)
            │     ├── (HashSet)
            │     └── (LinkedHashSet)
            │
            └── Queue             ← interface
                  ├── Deque       ← interface
                  │     └── (ArrayDeque, LinkedList)
                  └── (LinkedList, PriorityQueue)

«interface»
Map                               ← interface (separate hierarchy)
    ├── SortedMap                 ← interface
    │     └── NavigableMap        ← interface
    │               └── (TreeMap)
    ├── (HashMap)
    └── (LinkedHashMap)
```

### Interface → Concrete Class Mapping

| Left Side (Interface) | Right Side (Concrete Class) |
|---|---|
| `List<T>` | `ArrayList`, `LinkedList` |
| `Set<T>` | `HashSet`, `LinkedHashSet`, `TreeSet` |
| `SortedSet<T>` | `TreeSet` |
| `NavigableSet<T>` | `TreeSet` |
| `Queue<T>` | `LinkedList`, `PriorityQueue` |
| `Deque<T>` | `ArrayDeque`, `LinkedList` |
| `Map<K,V>` | `HashMap`, `LinkedHashMap`, `TreeMap` |
| `SortedMap<K,V>` | `TreeMap` |
| `NavigableMap<K,V>` | `TreeMap` |

### Method Exposure by Interface (TreeSet example)

| Method | Lives in Interface | Use this on left |
|---|---|---|
| `add()`, `remove()`, `contains()` | `Set` | `Set<T>` |
| `first()`, `last()`, `headSet()`, `tailSet()` | `SortedSet` | `SortedSet<T>` |
| `descendingSet()`, `floor()`, `ceiling()`, `higher()`, `lower()` | `NavigableSet` | `NavigableSet<T>` |
| All of the above + more | `TreeSet` class | `TreeSet<T>` |

### Method Exposure by Interface (LinkedList example)

| Method | Lives in Interface | Use this on left |
|---|---|---|
| `add()`, `get()`, `remove()` | `List` | `List<T>` |
| `addFirst()`, `addLast()`, `removeFirst()` | `Deque` | `Deque<T>` |
| `offer()`, `poll()`, `peek()` | `Queue` | `Queue<T>` |
| All of the above | `LinkedList` class | `LinkedList<T>` |

---

## 3. Collection Type Declaration — The Golden Rule

### Left Always, Right Optional (Modern Java)

```java
TypeName<T> varName = new TypeName<>();   // ✅ Modern Java 7+ (Diamond Operator)
TypeName<T> varName = new TypeName<T>();  // ✅ Also valid (older style)
```

- **Left side `<T>`** → Always required (compiler needs to know the type)
- **Right side `<>`** → Can be empty (compiler *infers* it from the left)

### The Diamond Operator Mental Model

```
Left side tells the truth        Right side already knows
        |                                  |
HashSet<Integer>    =    new HashSet<>();
                                           ^
                              "I got it from the left!"
```

### All Common Collections — Declaration Cheat Sheet

```java
// Single-type collections
List<Integer>          list  = new ArrayList<>();
ArrayList<Integer>     list  = new ArrayList<>();
LinkedList<String>     list  = new LinkedList<>();
Stack<Integer>         stack = new Stack<>();
Queue<Integer>         queue = new LinkedList<>();
Deque<Integer>         deque = new ArrayDeque<>();
HashSet<Integer>       set   = new HashSet<>();
LinkedHashSet<Integer> set   = new LinkedHashSet<>();
TreeSet<String>        set   = new TreeSet<>();
PriorityQueue<Integer> pq    = new PriorityQueue<>();

// Key-Value (two types in <>)
Map<String, Integer>   map   = new HashMap<>();
HashMap<String, Int>   map   = new HashMap<>();
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
TreeMap<String, Integer>       map = new TreeMap<>();
```

> **Notice:** Right side `<>` is always empty — consistent across ALL types!

### Why Does the Left Side Have Two Type Names?

```java
List<Integer> list = new ArrayList<>();
↑                        ↑
Interface            Concrete class
(what it "is")       (what it actually creates)
```

- **Left** = the *contract/interface* — what behaviors it promises
- **Right** = the *actual object* — the real implementation in memory

**Memory hook:** *"Say what you want (left), get what you need (right)"*

---

## 4. Advantages of Declaring Interface on the Left Side

### Flexibility — Easy to Swap Implementations

```java
// ✅ Interface on left — change implementation in ONE place
List<Integer> list = new ArrayList<>();
// Later, if you need better insert performance:
List<Integer> list = new LinkedList<>();  // only this line changes!

// ❌ Concrete class on left — tightly coupled
ArrayList<Integer> list = new ArrayList<>();
// Now you need to change EVERY method signature that uses this
```

### Principle of Least Privilege — Expose Only What's Needed

```java
// ✅ Method accepts any List implementation
public void printAll(List<Integer> list) {
    // caller can pass ArrayList, LinkedList, or any future List
}

// ❌ Unnecessarily rigid — locks callers to one type
public void printAll(ArrayList<Integer> list) {
    // caller MUST pass ArrayList — LinkedList won't work!
}
```

### Coding to Interface — Design Best Practice

```java
// The rest of your code doesn't care HOW it's stored
// It only cares WHAT it can do
List<Integer>  list  = new LinkedList<>();  // "it's a list, I don't care how"
Queue<Integer> queue = new LinkedList<>();  // "it's a queue, treat it like one"
```

### Summary Table

| Benefit | Interface on Left | Concrete Class on Left |
|---|---|---|
| Swap implementation easily | ✅ Yes | ❌ No |
| Flexible method parameters | ✅ Yes | ❌ No |
| Follows SOLID principles | ✅ Yes | ❌ No |
| Access all specific methods | ❌ No | ✅ Yes |

---

## 5. Declaring Concrete Class on the Left Side

### All Methods Unlocked

```java
LinkedList<Integer> list1 = new LinkedList<>();

// ✅ List methods
list1.add(1);
list1.get(0);
list1.set(0, 5);

// ✅ Deque methods
list1.addFirst(1);
list1.addLast(2);
list1.removeFirst();
list1.peekFirst();

// ✅ Queue methods
list1.offer(1);
list1.poll();
list1.peek();
```

### Visual — What Each Left-Side Declaration Exposes

```
LinkedList actual object in memory:
┌────────────────────────────────────────────────────────┐
│  List methods  +  Deque methods  +  Queue methods      │
└────────────────────────────────────────────────────────┘
             ↓ depends on left-side declaration

List<Integer>         → List methods ONLY
Queue<Integer>        → Queue methods ONLY
Deque<Integer>        → Deque methods ONLY
LinkedList<Integer>   → ALL methods ✅
```

### Why NOT Always Use `LinkedList` on the Left?

**Reason 1 — Locks you to one implementation:**
```java
// If performance profiling shows ArrayList is faster here,
// you must update every variable, parameter, and return type
LinkedList<Integer> list = new LinkedList<>();  // ❌ locked in

// vs — only change this one line
List<Integer> list = new LinkedList<>();         // ✅ easy to swap
```

**Reason 2 — Breaks method compatibility:**
```java
void process(LinkedList<Integer> list) { }  // ❌ only LinkedList works

void process(List<Integer> list) { }         // ✅ ArrayList, LinkedList, any List works
```

**Reason 3 — Violates Interface Segregation Principle:**
Exposing all methods when only a subset is needed makes code harder to reason about and test.

### Rule of Thumb

| Situation | Use |
|---|---|
| General coding / method params | Interface on left (`List`, `Queue`, `Deque`) |
| Need ALL specific methods (e.g. LeetCode) | Concrete class on left (`LinkedList`, `TreeSet`) |
| Production / clean code | Always interface on left |

---

## 6. Insertion Order in Java Collection Objects

### Overview — Which Collections Maintain Order?

| Collection | Order Maintained? | Type of Order |
|---|---|---|
| `ArrayList` | ✅ Yes | Insertion order |
| `LinkedList` | ✅ Yes | Insertion order |
| `Stack` | ✅ Yes | Insertion order (LIFO access) |
| `ArrayDeque` | ✅ Yes | Insertion order |
| `LinkedHashSet` | ✅ Yes | Insertion order |
| `LinkedHashMap` | ✅ Yes | Insertion order |
| `TreeSet` | ✅ Yes | Natural sorted order (not insertion) |
| `TreeMap` | ✅ Yes | Natural sorted order (not insertion) |
| `HashSet` | ❌ No | Random / unpredictable |
| `HashMap` | ❌ No | Random / unpredictable |
| `PriorityQueue` | ❌ No | Priority order (not insertion) |

### How Each Collection Manages Order Internally

#### ArrayList — Index-Based Insertion Order
```java
List<Integer> list = new ArrayList<>();
list.add(5); list.add(1); list.add(3);
System.out.println(list);  // [5, 1, 3] — insertion order preserved ✅
// Backed by a resizable array — index position = insertion position
```

#### LinkedList — Node-Based Insertion Order
```java
List<Integer> list = new LinkedList<>();
list.add(5); list.add(1); list.add(3);
System.out.println(list);  // [5, 1, 3] — insertion order preserved ✅
// Each node holds a reference to next and previous — order is baked into the chain
```

#### HashSet — No Order (Hash-Based)
```java
Set<Integer> set = new HashSet<>();
set.add(5); set.add(1); set.add(3);
System.out.println(set);  // [1, 3, 5] or [3, 1, 5] — unpredictable ❌
// Uses hashCode() to determine bucket position — no concept of order
```

#### LinkedHashSet — Insertion Order via Linked Nodes
```java
Set<Integer> set = new LinkedHashSet<>();
set.add(5); set.add(1); set.add(3);
System.out.println(set);  // [5, 1, 3] — insertion order preserved ✅
// HashSet internally + doubly-linked list tracking insertion sequence
```

#### TreeSet — Natural Sorted Order (not insertion order)
```java
Set<Integer> set = new TreeSet<>();
set.add(5); set.add(1); set.add(3);
System.out.println(set);  // [1, 3, 5] — sorted order, NOT insertion order
// Backed by a Red-Black Tree — always sorted by natural ordering or Comparator
```

#### HashMap — No Order
```java
Map<String, Integer> map = new HashMap<>();
map.put("banana", 2); map.put("apple", 1); map.put("cherry", 3);
System.out.println(map);  // {apple=1, cherry=3, banana=2} — unpredictable ❌
// Keys stored by hashCode() bucket — no insertion order
```

#### LinkedHashMap — Insertion Order
```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("banana", 2); map.put("apple", 1); map.put("cherry", 3);
System.out.println(map);  // {banana=2, apple=1, cherry=3} ✅
// HashMap + doubly-linked list across entries in insertion sequence
```

#### TreeMap — Sorted Key Order
```java
Map<String, Integer> map = new TreeMap<>();
map.put("banana", 2); map.put("apple", 1); map.put("cherry", 3);
System.out.println(map);  // {apple=1, banana=2, cherry=3} — sorted by key ✅
// Red-Black Tree sorted by key's natural ordering or Comparator
```

### Choosing the Right Collection by Order Need

```
Do you need ordering?
        │
        ├── No  → HashSet / HashMap        (fastest, O(1) operations)
        │
        └── Yes
              │
              ├── Insertion Order → LinkedHashSet / LinkedHashMap
              │                     ArrayList / LinkedList
              │
              └── Sorted Order   → TreeSet / TreeMap
                                   (natural order or custom Comparator)
```

### Important: Left Side Does NOT Affect Runtime Behavior

```java
// All three maintain sorted order — TreeSet behavior is always active
Set<Integer>          s1 = new TreeSet<>();  // sorted ✅
SortedSet<Integer>    s2 = new TreeSet<>();  // sorted ✅
NavigableSet<Integer> s3 = new TreeSet<>();  // sorted ✅
TreeSet<Integer>      s4 = new TreeSet<>();  // sorted ✅

// Left side only controls which METHODS you can call
// Right side controls how the object BEHAVES at runtime
```

---

## Quick Decision Guide

```
What do I need?
│
├── Store elements in sequence?
│         └── ArrayList (fast reads) or LinkedList (fast inserts/deletes)
│
├── Unique elements only?
│         ├── Don't care about order → HashSet
│         ├── Insertion order        → LinkedHashSet
│         └── Sorted order           → TreeSet
│
├── Key-Value pairs?
│         ├── Don't care about order → HashMap
│         ├── Insertion order        → LinkedHashMap
│         └── Sorted by key          → TreeMap
│
├── FIFO Queue?
│         └── LinkedList or ArrayDeque
│
├── Priority-based access?
│         └── PriorityQueue
│
└── LIFO Stack?
          └── ArrayDeque (preferred) or Stack
```

---

*Java Collections Quick Reference — covers .length/.size(), interface hierarchy, declaration patterns, and ordering behavior.*

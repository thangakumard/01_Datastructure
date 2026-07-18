# Java Type Conversions: Complete Reference Guide

**Table of Contents**
1. [Primitive to Primitive Conversions](#1-primitive-to-primitive-conversions)
2. [Widening vs Narrowing](#2-widening-vs-narrowing-order)
3. [Special Case: char](#3-special-case-char)
4. [Boxing and Unboxing](#4-primitive-to-object-boxingunboxing)
5. [String Conversions](#5-string-conversions)
6. [Casting Rules](#6-casting-rules-critical-for-interviews)
7. [Memory Tricks](#memory-tricks)
8. [Common Interview Scenarios](#common-interview-scenarios)
9. [Array to List Conversions](#array-to-list)
10. [List to Array Conversions](#list-to-array)
11. [Array to Set](#array-to-set)
12. [Set to Array](#set-to-array)
13. [Primitive Arrays to List](#primitive-arrays-to-list-critical)
14. [List of Primitives to Primitive Array](#list-of-primitives-to-primitive-array)
15. [Map Conversions](#map-conversions)
16. [Quick Reference Tables](#quick-reference-table)
17. [Common Pitfalls](#common-interview-pitfalls)

---

## 1. Primitive to Primitive Conversions

### Widening (Automatic) — No Data Loss

```java
byte → short → int → long → float → double

byte b = 10;
int i = b;           // automatic
long l = i;          // automatic
float f = l;         // automatic
double d = f;        // automatic
```

**Memory**: Each step moves to a type with ≥ storage capacity.

### Narrowing (Explicit) — Requires Cast, Risk of Data Loss

```java
double → float → long → int → short → byte

double d = 99.99;
int i = (int) d;     // 99 (decimal truncated, not rounded)
byte b = (byte) 300; // 44 (overflow wraps: 300 % 256)
```

---

## 2. Widening vs Narrowing Order

**Widening chain (memorize left-to-right):**
```
byte → short → int → long → float → double
       ↑
    char also widens to int
```

**Narrowing is the reverse (right-to-left):**
```
double → float → long → int → short → byte
```

---

## 3. Special Case: char

```java
char c = 'A';
int i = c;           // 65 (widening - ASCII value)

int num = 65;
char c = (char) num; // 'A' (narrowing - cast required)
```

---

## 4. Primitive to Object (Boxing/Unboxing)

### Boxing (Automatic in Java 5+)

```java
int i = 42;
Integer obj = i;              // Integer.valueOf(i) called automatically
```

### Unboxing (Automatic)

```java
Integer obj = 42;
int i = obj;                  // obj.intValue() called automatically
```

### Common Wrapper Classes

```
Integer, Long, Double, Float, Boolean, Character, Byte, Short
```

---

## 5. String Conversions

### To String

```java
int i = 42;
String s1 = String.valueOf(i);        // "42" - preferred
String s2 = Integer.toString(i);      // "42" - also good
String s3 = "" + i;                   // "42" - works but less efficient
String s4 = i + "";                   // same as s3

double d = 3.14;
String s = String.valueOf(d);         // "3.14"

boolean b = true;
String s = String.valueOf(b);         // "true"
```

### String to Primitive

```java
String s = "42";
int i = Integer.parseInt(s);          // 42
long l = Long.parseLong(s);           // 42L
double d = Double.parseDouble("3.14"); // 3.14
boolean b = Boolean.parseBoolean("true"); // true
```

### String to Primitive with Radix (Base)

```java
int hex = Integer.parseInt("FF", 16); // 255 (hexadecimal)
int bin = Integer.parseInt("1010", 2); // 10 (binary)
int oct = Integer.parseInt("755", 8); // 493 (octal)
```

### Exception Handling

```java
try {
    int i = Integer.parseInt("abc");
} catch (NumberFormatException e) {
    System.out.println("Invalid number format");
}
```

---

## 6. Casting Rules (Critical for Interviews)

### Rule 1: Widening Allowed Implicitly

```java
int i = 10;
long l = i;          // ✓ OK (automatic)
```

### Rule 2: Narrowing Requires Explicit Cast

```java
long l = 10L;
int i = (int) l;     // ✓ Must cast
```

### Rule 3: Objects Require instanceof Before Casting

```java
Object obj = "hello";
if (obj instanceof String) {
    String s = (String) obj;  // ✓ Safe
}
```

### Rule 4: Incompatible Types → Compile Error

```java
int i = (int) "42";  // ✗ Compile error (must use Integer.parseInt)
```

---

## Memory Tricks

### Trick 1: "BISCILD" Acronym

**B**yte → **S**hort → **C**har/**I**nt → **L**ong → (F)loat → **D**ouble

- Forget "F" in the acronym — float comes right before double
- Use "**BISCILD**" if you want to include char explicitly

### Trick 2: "Size + Capacity"

```
Widening = larger container (always safe)
Narrowing = smaller container (explicit cast needed)

byte (1 byte) → int (4 bytes) ✓ widening (automatic)
int (4 bytes) → byte (1 byte) ✗ narrowing (cast needed)
```

### Trick 3: For String Conversion

```
String → Primitive: Use Type.parseType()
  - Integer.parseInt()
  - Double.parseDouble()
  - Boolean.parseBoolean()

Primitive → String: Use String.valueOf() or Type.toString()
```

### Trick 4: Truncation vs Rounding

```java
double d = 99.99;
int i = (int) d;  // 99 (truncates, not rounds!)

// To round:
int rounded = Math.round(d);  // 100
```

### Trick 5: Overflow Wrapping

```java
byte max = 127;
byte overflow = (byte) (max + 1); // -128 (wraps around)
// Reason: byte is -128 to 127, so 128 % 256 - 256 = -128
```

---

## Common Interview Scenarios

| Scenario | Code | Key Point |
|----------|------|-----------|
| Parse array of digit strings | `int[] nums = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();` | Know `parseInt()` |
| Convert int to char digit | `char c = (char)('0' + i);` | Leverage ASCII offsets |
| Frequency counting (char → index) | `freq[c - 'a']++` | Narrowing with offset |
| Object type checking | `if (obj instanceof Integer) { int i = (Integer) obj; }` | Always instanceof before cast |
| Array of strings to ints | `int[] nums = Stream.of(arr).mapToInt(Integer::parseInt).toArray();` | Stream + parseInt |

---

## Array to List

### Method 1: Arrays.asList() — Fixed Size Wrapper

```java
String[] arr = {"a", "b", "c"};
List<String> list = Arrays.asList(arr);

// ⚠️ TRAP: Fixed-size view, backed by original array
list.add("d");        // ✗ UnsupportedOperationException
list.remove(0);       // ✗ UnsupportedOperationException
arr[0] = "x";
System.out.println(list); // [x, b, c] — list reflects array changes!
```

### Method 2: new ArrayList<>() — Mutable Copy

```java
String[] arr = {"a", "b", "c"};
List<String> list = new ArrayList<>(Arrays.asList(arr));

// ✓ Independent list
list.add("d");        // OK
list.remove(0);       // OK
arr[0] = "x";
System.out.println(list); // [b, c, d] — unaffected
```

### Method 3: ArrayList Constructor (One-liner)

```java
String[] arr = {"a", "b", "c"};
List<String> list = new ArrayList<>(Arrays.asList(arr));
// Same as Method 2
```

### Method 4: Java 9+ Streams

```java
String[] arr = {"a", "b", "c"};
List<String> list = Arrays.stream(arr).collect(Collectors.toList());

// For primitive arrays:
int[] nums = {1, 2, 3};
List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
```

| Method | Mutable? | Backed by Array? | Performance |
|--------|----------|-----------------|-------------|
| `Arrays.asList()` | ✗ | ✓ | O(1) |
| `new ArrayList<>(Arrays.asList())` | ✓ | ✗ | O(n) copy |
| Streams | ✓ | ✗ | O(n) |

---

## List to Array

### Method 1: toArray() — Generic Object Array

```java
List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
Object[] arr = list.toArray();  // Object[]
System.out.println(arr[0]); // "a" but type is Object
```

### Method 2: toArray(T[]) — Typed Array (Preferred)

```java
List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
String[] arr = list.toArray(new String[0]);
// Creates String[] of sufficient size

// Old style (still works):
String[] arr = list.toArray(new String[list.size()]);
```

### Method 3: Streams

```java
List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));
String[] arr = list.stream().toArray(String[]::new);

// Primitives:
List<Integer> list = Arrays.asList(1, 2, 3);
int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
```

| Method | Type | Interview Use |
|--------|------|---------------|
| `toArray()` | Object[] | Rarely; type-unsafe |
| `toArray(new T[0])` | T[] | **BEST** — explicit type |
| Streams | T[] | Modern; flexible |

**Interview Tip**: `list.toArray(new String[0])` is preferred. JVM optimizes `new T[0]` better than `new T[size]` in modern Java.

---

## Array to Set

### Method 1: new HashSet<>(Arrays.asList())

```java
String[] arr = {"a", "b", "a", "c"};
Set<String> set = new HashSet<>(Arrays.asList(arr));
System.out.println(set); // {a, b, c} — duplicates removed
```

### Method 2: Streams

```java
String[] arr = {"a", "b", "a", "c"};
Set<String> set = Arrays.stream(arr).collect(Collectors.toSet());

// LinkedHashSet (maintains insertion order):
Set<String> set = Arrays.stream(arr)
    .collect(Collectors.toCollection(LinkedHashSet::new));
```

### Method 3: Manual Loop (Interview-Safe)

```java
String[] arr = {"a", "b", "a", "c"};
Set<String> set = new HashSet<>();
for (String s : arr) {
    set.add(s);
}
```

---

## Set to Array

### Method 1: toArray(T[])

```java
Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));
String[] arr = set.toArray(new String[0]);
```

### Method 2: Streams

```java
Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));
String[] arr = set.stream().toArray(String[]::new);
```

---

## Primitive Arrays to List (Critical)

### Problem: Integer[] vs int[]

```java
int[] arr = {1, 2, 3};
List<Integer> list = Arrays.asList(arr);
// ✗ WRONG! Creates List<int[]> with single element
System.out.println(list.size()); // 1 (the array itself)
```

### Solution 1: Use Streams (Best)

```java
int[] arr = {1, 2, 3};
List<Integer> list = Arrays.stream(arr)
    .boxed()  // int → Integer
    .collect(Collectors.toList());
System.out.println(list); // [1, 2, 3]
```

### Solution 2: Manual Boxing

```java
int[] arr = {1, 2, 3};
List<Integer> list = new ArrayList<>();
for (int num : arr) {
    list.add(num);  // auto-boxing
}
```

### Solution 3: Google Guava (Alternative)

```java
// Guava
List<Integer> list = Ints.asList(1, 2, 3);

// But streams are preferred in modern Java
```

---

## List of Primitives to Primitive Array

### Problem: Can't Directly Cast

```java
List<Integer> list = Arrays.asList(1, 2, 3);
int[] arr = (int[]) list.toArray(); // ✗ ClassCastException
```

### Solution: Streams

```java
List<Integer> list = Arrays.asList(1, 2, 3);
int[] arr = list.stream()
    .mapToInt(Integer::intValue)  // unbox Integer → int
    .toArray();
System.out.println(Arrays.toString(arr)); // [1, 2, 3]
```

### Alternative: Manual Loop

```java
List<Integer> list = Arrays.asList(1, 2, 3);
int[] arr = new int[list.size()];
for (int i = 0; i < list.size(); i++) {
    arr[i] = list.get(i);  // auto-unboxing
}
```

---

## Map Conversions

### Map Keys/Values/Entries to Array

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);

// Keys to array
String[] keys = map.keySet().toArray(new String[0]);

// Values to array
Integer[] values = map.values().toArray(new Integer[0]);

// Entries to array
Map.Entry<String, Integer>[] entries = 
    map.entrySet().toArray(new Map.Entry[0]);
```

### 2D Array from Map (for LeetCode)

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 1);
map.put("b", 2);

// Convert to 2D array (manual)
int[][] arr = new int[map.size()][2];
int idx = 0;
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    arr[idx][0] = entry.getKey().charAt(0);
    arr[idx][1] = entry.getValue();
    idx++;
}

// Or with streams:
int[][] arr = map.entrySet().stream()
    .map(e -> new int[]{e.getKey().charAt(0), e.getValue()})
    .toArray(int[][]::new);
```

---

## Quick Reference Table

| From | To | Code | Notes |
|------|----|----|--------|
| `int[]` | `List<Integer>` | `Arrays.stream(arr).boxed().collect(Collectors.toList())` | ⭐ Best |
| `List<Integer>` | `int[]` | `list.stream().mapToInt(Integer::intValue).toArray()` | ⭐ Best |
| `String[]` | `List<String>` | `new ArrayList<>(Arrays.asList(arr))` | Mutable copy |
| `List<String>` | `String[]` | `list.toArray(new String[0])` | ⭐ Interview ready |
| `String[]` | `Set<String>` | `new HashSet<>(Arrays.asList(arr))` | Removes duplicates |
| `Set<String>` | `String[]` | `set.toArray(new String[0])` | ⭐ Interview ready |
| `String` | `int` | `Integer.parseInt(s)` | ⭐ Most common |
| `int` | `String` | `String.valueOf(i)` | ⭐ Preferred |
| `char[]` | `String` | `new String(arr)` | Direct conversion |
| `String` | `char[]` | `s.toCharArray()` | Direct conversion |
| `Object[]` | `String[]` | `Arrays.copyOf(obj, obj.length, String[].class)` | Safe cast |

---

## Common Interview Pitfalls

### ✗ WRONG: Mutable Operations on Arrays.asList()

```java
List<String> list = Arrays.asList(arr);
list.add("d");  // UnsupportedOperationException
```

### ✓ CORRECT: Use ArrayList Constructor

```java
List<String> list = new ArrayList<>(Arrays.asList(arr));
list.add("d");  // OK
```

### ✗ WRONG: Primitive Array Trap

```java
int[] nums = {1, 2, 3};
List<Integer> list = Arrays.asList(nums);  // List<int[]>!
System.out.println(list.size()); // 1, not 3
```

### ✓ CORRECT: Use Streams with boxed()

```java
int[] nums = {1, 2, 3};
List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
System.out.println(list.size()); // 3
```

### ✗ WRONG: Type Mismatch

```java
List<Integer> list = Arrays.asList(1, 2, 3);
int[] arr = (int[]) list.toArray();  // ClassCastException
```

### ✓ CORRECT: Unbox with Stream

```java
List<Integer> list = Arrays.asList(1, 2, 3);
int[] arr = list.stream().mapToInt(Integer::intValue).toArray();
```

### ✗ WRONG: String Parsing

```java
int i = (int) "42";  // Compile error
```

### ✓ CORRECT: Use Parse Methods

```java
int i = Integer.parseInt("42");
```

### ✗ WRONG: Missing Cast

```java
Object obj = "hello";
String s = obj;  // Compile error
```

### ✓ CORRECT: Use instanceof and Cast

```java
Object obj = "hello";
if (obj instanceof String) {
    String s = (String) obj;  // Safe
}
```

---

## Additional Tips

### Null Handling

```java
// Check for null before conversion
String s = null;
if (s != null) {
    int i = Integer.parseInt(s);
}

// Try-catch for robustness
try {
    int i = Integer.parseInt(userInput);
} catch (NumberFormatException e) {
    System.out.println("Invalid input");
}
```

### Performance Considerations

```java
// Avoid repeated conversions in loops
// ✗ WRONG
for (String s : stringArray) {
    int num = Integer.parseInt(s);  // Parsing in loop
}

// ✓ BETTER
int[] nums = new int[stringArray.length];
for (int i = 0; i < stringArray.length; i++) {
    nums[i] = Integer.parseInt(stringArray[i]);
}

// ✓ BEST (Streams)
int[] nums = Arrays.stream(stringArray)
    .mapToInt(Integer::parseInt)
    .toArray();
```

### Immutable Collections

```java
// Java 9+
List<String> list = List.of("a", "b", "c");  // Immutable
Set<String> set = Set.of("a", "b", "c");     // Immutable
Map<String, Integer> map = Map.of("a", 1);   // Immutable

// Convert to mutable
List<String> mutable = new ArrayList<>(List.of("a", "b", "c"));
```

---

## Interview Preparation Checklist

- [ ] Know the widening chain: byte → short → int → long → float → double
- [ ] Understand boxing/unboxing and when it happens automatically
- [ ] Master `Arrays.asList()` vs `new ArrayList<>(Arrays.asList())`
- [ ] Always use `toArray(new T[0])` not `toArray()`
- [ ] For primitive arrays: use `Arrays.stream().boxed()`
- [ ] For List to primitive array: use `.mapToInt().toArray()`
- [ ] Know common exceptions: `UnsupportedOperationException`, `ClassCastException`, `NumberFormatException`
- [ ] Use `String.valueOf()` for primitive → string
- [ ] Use `Integer.parseInt()` for string → int
- [ ] Always check for null before conversion in production code

---

**Last Updated**: 2026-07-15
**Difficulty Level**: Interview Ready
**Languages**: Java (Primary), Python (Secondary)

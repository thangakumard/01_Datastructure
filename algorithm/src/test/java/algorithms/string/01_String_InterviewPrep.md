# Coding Interview Notes: Integer Overflow, Anagrams, Isomorphic Strings & Substrings vs. Subsequences

Quick reference notes for four classic interview topics: detecting integer overflow before it happens, recognizing/checking anagrams, checking whether two strings are isomorphic, and telling substrings apart from subsequences.

## Table of Contents

1. [Integer Overflow Checks](#integer-overflow-checks)
    - [Why Check Before Multiplying](#why-check-before-multiplying)
    - [Bounding by Integer.MAX_VALUE](#bounding-by-integermax_value)
    - [Bounding by Integer.MIN_VALUE](#bounding-by-integermin_value)
    - [Putting It Together: Reverse Integer](#putting-it-together-reverse-integer)
2. [What Is an Anagram?](#what-is-an-anagram)
3. [What Is an Isomorphic String?](#what-is-an-isomorphic-string)
    - [Two Hash Maps Approach](#two-hash-maps-approach)
    - [Alternative: First-Occurrence Encoding](#alternative-first-occurrence-encoding)
4. [Substring vs. Subsequence](#substring-vs-subsequence)
    - [Key Difference](#key-difference)
    - [Why It Matters for Algorithm Choice](#why-it-matters-for-algorithm-choice)

---

## Integer Overflow Checks

### Why Check Before Multiplying

In Java, `int` overflow doesn't throw an exception — it silently wraps around (two's complement arithmetic). That means an expression like `reversed = reversed * 10 + digit` can produce a wrong, overflowed value with no error at all. The fix is to check **before** the multiply/add whether the result would exceed the valid `int` range, and bail out early if so (by convention, LeetCode's "Reverse Integer" problem returns `0` on overflow).

### Bounding by Integer.MAX_VALUE

```java
if (reversed > Integer.MAX_VALUE / 10 ||
   (reversed == Integer.MAX_VALUE / 10 && digit > 7)) {
    return 0; // would overflow past MAX_VALUE
}
```

`Integer.MAX_VALUE` is `2147483647`. Splitting off its last digit:

```
2147483647
└───┬────┘└┬┘
 214748364  7
```

Integer division truncates, so `Integer.MAX_VALUE / 10 == 214748364`, with `7` left over as the final digit. That's why the check compares `reversed` against `214748364`, and — in the tie case where `reversed` already equals that boundary — checks whether the incoming `digit` would exceed `7`.

### Bounding by Integer.MIN_VALUE

```java
if (reversed < Integer.MIN_VALUE / 10 ||
   (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
    return 0; // would overflow past MIN_VALUE
}
```

`Integer.MIN_VALUE` is `-2147483648`. Split the same way:

```
-2147483648
└───┬─────┘└┬┘
-214748364   -8
```

`Integer.MIN_VALUE / 10 == -214748364`, with `-8` left over. Two details are easy to get backwards here:

- The comparison direction flips to **`<`**, not `>` — you're checking whether `reversed` has already dropped *below* the safe threshold, not above it.
- The digit comparison is **`digit < -8`**, not `digit > 8`. In Java, `%` keeps the sign of its dividend, so when reversing a negative number, `digit` comes out negative too (e.g. `-1234 % 10 == -4`). You're comparing signed values here, not magnitudes.

*(Both of these were flipped in the original draft of these notes — corrected above.)*

### Putting It Together: Reverse Integer

```java
public int reverse(int x) {
    int reversed = 0;
    while (x != 0) {
        int digit = x % 10;
        x /= 10;

        if (reversed > Integer.MAX_VALUE / 10 ||
           (reversed == Integer.MAX_VALUE / 10 && digit > 7)) {
            return 0;
        }
        if (reversed < Integer.MIN_VALUE / 10 ||
           (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
            return 0;
        }

        reversed = reversed * 10 + digit;
    }
    return reversed;
}
```

Runs in O(log₁₀ x) time (one loop iteration per digit) and O(1) space.

---

## What Is an Anagram?

An **anagram** is a word or phrase formed by rearranging the letters of another word or phrase, using all the original letters exactly once.

Examples:
- `"listen"` → `"silent"`
- `"elbow"` → `"below"`
- `"the eyes"` → `"they see"`

In programming — this comes up constantly in interviews — two strings are typically checked for being anagrams by comparing their sorted characters or their character-frequency counts.

```java
public boolean isAnagram(String a, String b) {
    if (a.length() != b.length()) return false;

    int[] counts = new int[26];
    for (char c : a.toCharArray()) counts[c - 'a']++;
    for (char c : b.toCharArray()) counts[c - 'a']--;

    for (int count : counts) {
        if (count != 0) return false;
    }
    return true;
}
```

This runs in **O(n)** time using a fixed-size frequency array (26 lowercase letters), which is generally preferred in interviews over sorting both strings first (**O(n log n)**).

---

## What Is an Isomorphic String?

Two strings `s` and `t` are **isomorphic** if the characters in `s` can be consistently replaced to get `t`. This is LeetCode #205, and it comes up as a common warm-up before problems move into harder pattern-matching territory. The rules:

1. Each character in `s` maps to exactly one character in `t` — no character maps to two different characters.
2. Two different characters in `s` cannot map to the same character in `t` (the mapping must be a **one-to-one bijection**, not just one-directional).
3. Character order is preserved — a character always maps to the same character every time it appears.

Examples:
- `"egg"` and `"add"` → **true** (`e→a`, `g→d`)
- `"foo"` and `"bar"` → **false** (`o` would need to map to both `a` and `r`)
- `"paper"` and `"title"` → **true** (`p→t`, `a→i`, `e→l`, `r→e`)
- `"badc"` and `"baba"` → **false** (`d→a` and `c→a` — two different source characters mapping to the same target character violates rule 2)

### Two Hash Maps Approach

The trap most people fall into is checking the mapping in only one direction (`s → t`). That misses cases like `"badc"`/`"baba"`, where the `s → t` mapping alone looks fine but two different characters collapse onto the same target. Tracking both `s → t` and `t → s` catches it:

```java
public boolean isIsomorphic(String s, String t) {
    if (s.length() != t.length()) return false;

    Map<Character, Character> mapST = new HashMap<>();
    Map<Character, Character> mapTS = new HashMap<>();

    for (int i = 0; i < s.length(); i++) {
        char c1 = s.charAt(i);
        char c2 = t.charAt(i);

        if (mapST.containsKey(c1)) {
            if (mapST.get(c1) != c2) return false;
        } else {
            mapST.put(c1, c2);
        }

        if (mapTS.containsKey(c2)) {
            if (mapTS.get(c2) != c1) return false;
        } else {
            mapTS.put(c2, c1);
        }
    }
    return true;
}
```

Runs in **O(n)** time, **O(k)** space, where `k` is the size of the character set involved.

### Alternative: First-Occurrence Encoding

A neater single-pass trick: encode each string by the *index of first occurrence* of each character, then compare the two encoded sequences. This implicitly captures the same bijection constraint without maintaining two explicit maps:

```java
public boolean isIsomorphic(String s, String t) {
    if (s.length() != t.length()) return false;

    int[] firstSeenS = new int[256];
    int[] firstSeenT = new int[256];
    Arrays.fill(firstSeenS, -1);
    Arrays.fill(firstSeenT, -1);

    for (int i = 0; i < s.length(); i++) {
        char c1 = s.charAt(i);
        char c2 = t.charAt(i);

        if (firstSeenS[c1] != firstSeenT[c2]) return false;

        firstSeenS[c1] = i;
        firstSeenT[c2] = i;
    }
    return true;
}
```

Same **O(n)** time, and this version uses fixed-size arrays instead of hash maps, so it avoids hashing overhead. The "two hash maps for bidirectional constraint" technique from the first approach also generalizes well to related problems like Word Pattern (LC #290) and Word Pattern II.

---

## Substring vs. Subsequence

These two terms get mixed up constantly, but they impose very different constraints — and mixing them up leads to solving the wrong problem entirely.

### Key Difference

- A **substring** is a **contiguous** block of characters taken from the original string. Nothing can be skipped — you're just picking a start index and an end index.
- A **subsequence** preserves the **relative order** of characters, but they don't need to be contiguous — characters can be skipped along the way.

Given `"abcde"`:

| Type | Valid examples | Why |
|---|---|---|
| Substring | `"abc"`, `"bcd"`, `"cde"` | Each is an unbroken run of consecutive characters |
| Substring | `"ace"` | **Invalid** — skips `b` and `d`, breaking contiguity |
| Subsequence | `"ace"` | Valid — `a`, `c`, `e` appear in order, skips allowed |
| Subsequence | `"abc"` | Also valid — a substring is always a valid subsequence too, since contiguous counts as "in order" |
| Subsequence | `"eca"` | **Invalid** — order is reversed, so relative order isn't preserved |

Every substring is a subsequence, but not every subsequence is a substring. Subsequence is the more permissive category; substring is a strict subset of it.

A string of length `n` has:
- **O(n²)** substrings (choose a start and end index)
- **O(2ⁿ)** subsequences (each character is either included or excluded, independently)

That exponential blowup for subsequences is exactly why subsequence problems (e.g., Longest Common Subsequence, Longest Increasing Subsequence) almost always need dynamic programming rather than brute-force enumeration — brute force is only tractable for tiny inputs.

### Why It Matters for Algorithm Choice

Misreading the problem statement here is a classic way to pick the wrong technique:

- **Substring problems** (e.g., Longest Substring Without Repeating Characters, Minimum Window Substring) are usually solved with a **sliding window**, since contiguity means you can grow/shrink a window over the string in O(n) or O(n·k) time.
- **Subsequence problems** (e.g., Longest Common Subsequence, Longest Palindromic Subsequence, Is Subsequence) are usually solved with **dynamic programming** or, for simpler checks, **two pointers**, since you need to reason about "include this character or skip it" decisions rather than a contiguous window.

Quick sanity check while reading a problem: if the wording allows characters to be "deleted" or "skipped" while keeping the rest in order, it's a subsequence problem. If it talks about a "contiguous" or "window" of the string, it's a substring problem.

## Palindrome
### Palindromic Partition
   - Backtracking
   - Time Complexity O(n 2^n)
   - Space Complexity O(n)
### Palindromic Permutation
   - All the Palindromic Permutation uses freq counter 
   - Make sure to assert count of ODD chat count
   - Getting Palindromic Permutation - Uses freq counter + Backtracking (uses char freq array to build string) 
### Palindromic Subsequence
   - All the Palindromic Subsequence problems use dynamic programming approach
### Palindromic Substring
   - All the Palindromic Subsequence problems use expand from middle apprach
```Java
   for(int i=0; i< input.length(); i++){
      expandFromMiddle(charInput , i, i);// for odd length
      expandFromMiddle(charInput, i, i+1);// for even length
    }
   ```



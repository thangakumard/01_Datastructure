# Two Pointer vs. Sliding Window

A reference guide comparing two of the most common array/string interview patterns — when each applies, how they differ, and the problem signals that point to one or the other.

---

## 1. Two Pointer Technique

### What it is
Two Pointer is a technique where you maintain **two index variables** (pointers) that traverse a data structure (usually a sorted array, string, or linked list) according to some rule, instead of using nested loops. The pointers can move:

- **Toward each other** (opposite ends closing in) — most common on sorted arrays
- **In the same direction** (fast/slow) — common for linked lists and in-place array modification
- **Independently across two different arrays** — common for merge-style problems

The key idea: by exploiting some structure in the data (usually **sorted order**), you can eliminate the need to check every pair `(i, j)`, collapsing an O(n²) brute force into O(n).

### Example: Two Sum II — Input Array Is Sorted

**Problem:** Given a 1-indexed sorted array, find two numbers that add up to a target. Return their indices.

**Brute force:** check every pair → O(n²).

**Two pointer idea:** Start `left = 0`, `right = n - 1`.
- If `nums[left] + nums[right] == target` → found it.
- If the sum is **too small**, the only way to increase it is to move `left` right (since the array is sorted, a bigger left value helps).
- If the sum is **too large**, move `right` left.

```java
public int[] twoSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum == target) {
            return new int[]{left + 1, right + 1}; // 1-indexed
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    return new int[]{-1, -1};
}
```

```python
def two_sum(nums: list[int], target: int) -> list[int]:
    left, right = 0, len(nums) - 1
    while left < right:
        total = nums[left] + nums[right]
        if total == target:
            return [left + 1, right + 1]  # 1-indexed
        elif total < target:
            left += 1
        else:
            right -= 1
    return [-1, -1]
```

**Why it's correct:** Each step provably eliminates one candidate pair from consideration without ever needing to revisit it. That's what gets you from O(n²) to O(n).

**Complexity:** Time O(n), Space O(1) — no extra data structures needed.

### Other classic Two Pointer problems
| Problem | Pointer style |
|---|---|
| Valid Palindrome | Opposite ends, skip non-alphanumeric |
| Container With Most Water | Opposite ends, move the shorter wall |
| 3Sum | Fix one element, two-pointer on the rest |
| Remove Duplicates from Sorted Array | Slow/fast, same direction |
| Linked List Cycle Detection (Floyd's) | Slow/fast, same direction, different speeds |
| Merge Two Sorted Arrays/Lists | Independent pointers on two structures |

---

## 2. Sliding Window Algorithm

### What it is
Sliding Window is a technique for problems involving a **contiguous** range (subarray or substring) where you maintain a "window" defined by two pointers, `left` and `right`, and **slide** it across the data — expanding by moving `right`, and shrinking by moving `left` — instead of recomputing the result for every possible window from scratch.

There are two flavors:
- **Fixed-size window:** the window size `k` is given up front.
- **Variable-size window:** the window grows and shrinks based on a condition (e.g., "longest substring satisfying X").

The core efficiency gain: instead of recomputing a sum/count/state for each window independently (O(n·k) or worse), you **incrementally update** the state as the window slides — add what enters on the right, remove what exits on the left.

### Example A: Fixed-Size Window — Maximum Sum Subarray of Size K

**Problem:** Given an array and integer `k`, find the max sum of any contiguous subarray of size `k`.

**Brute force:** for each starting index, sum `k` elements → O(n·k).

**Sliding window idea:** Compute the sum of the first `k` elements. Then slide one step at a time: subtract the element leaving the window, add the element entering it.

```java
public int maxSumSubarray(int[] nums, int k) {
    int windowSum = 0;
    for (int i = 0; i < k; i++) windowSum += nums[i];

    int maxSum = windowSum;
    for (int right = k; right < nums.length; right++) {
        windowSum += nums[right] - nums[right - k]; // slide: add new, remove old
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

```python
def max_sum_subarray(nums: list[int], k: int) -> int:
    window_sum = sum(nums[:k])
    max_sum = window_sum
    for right in range(k, len(nums)):
        window_sum += nums[right] - nums[right - k]  # slide
        max_sum = max(max_sum, window_sum)
    return max_sum
```

**Complexity:** Time O(n), Space O(1) — down from O(n·k).

### Example B: Variable-Size Window — Longest Substring Without Repeating Characters

**Problem:** Given a string, find the length of the longest substring with no repeated characters.

**Sliding window idea:** Expand `right` one character at a time, adding it to a set/map. If a duplicate is found, shrink from `left` until the duplicate is gone. Track the max window size seen.

```java
public int lengthOfLongestSubstring(String s) {
    Set<Character> window = new HashSet<>();
    int left = 0, maxLen = 0;

    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        while (window.contains(c)) {
            window.remove(s.charAt(left));
            left++;
        }
        window.add(c);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

```python
def length_of_longest_substring(s: str) -> int:
    window = set()
    left = 0
    max_len = 0

    for right, c in enumerate(s):
        while c in window:
            window.remove(s[left])
            left += 1
        window.add(c)
        max_len = max(max_len, right - left + 1)
    return max_len
```

**Complexity:** Time O(n) — each character is added to the window and removed from it at most once, so `left` and `right` each traverse the string only once (amortized O(1) per step, not O(n) per step despite the nested `while`). Space O(min(n, charset size)).

---

## 3. When to Use Sliding Window vs. Two Pointer — and How They Differ

They're closely related — sliding window is really a **specialized form of two pointer** — but the mental model and the problems they solve differ:

| | Two Pointer | Sliding Window |
|---|---|---|
| **Core structure** | Two indices moving independently or toward each other | Two indices (`left`, `right`) that always define a **contiguous** range |
| **What's tracked** | Usually just the two positions/values | Running **aggregate state** over the range (sum, count, frequency map, distinct-char set) |
| **Typical data shape** | Often needs sorted input (or a linked list) | Works on **any** array/string; contiguity matters more than order |
| **Movement pattern** | Both pointers often move toward each other and each moves **at most once total** (converging) | `right` almost always expands forward; `left` only moves forward to shrink — window never "jumps" |
| **Goal shape** | "Find a pair/triplet satisfying X" | "Find the longest/shortest/max/min/count of **contiguous** ranges satisfying X" |
| **State on move** | Recompute a simple comparison (sum vs target) | Incrementally update a running aggregate as elements enter/exit |

**Decision rule of thumb:**
- If the problem is about **pairs of elements** (possibly from a sorted array) → **Two Pointer**.
- If the problem is about a **contiguous subarray or substring** and asks for a max/min/count/length/existence over that contiguous range → **Sliding Window**.
- If you find yourself needing a running sum/count/frequency-map that updates as a range grows or shrinks, that's the signature of sliding window, not plain two pointer.

Think of it this way: **every sliding window problem technically uses two pointers**, but not every two-pointer problem involves a "window" — because not every two-pointer problem cares about everything *between* the pointers. Two Pointer just cares about the two positions. Sliding Window cares about the whole span between them as a unit.

---

## 4. Common Problem Patterns That Signal Sliding Window

Look for this language in the problem statement:

- **"Contiguous subarray / substring"** — almost always sliding window, since the window itself represents a contiguous range.
- **"Longest / shortest substring or subarray that satisfies condition X"** — variable-size window (expand to find validity, shrink to minimize/maximize).
- **"Maximum / minimum sum (or average) of a subarray of size k"** — fixed-size window.
- **"At most K distinct characters/elements"** or **"exactly K distinct"** — variable window with a frequency map, classic "at most K" minus "at most K-1" trick for the "exactly K" variant.
- **"Find all anagrams / permutations of a pattern in a string"** — fixed-size window matching a frequency map (e.g., Find All Anagrams in a String, Permutation in String).
- **"Minimum window containing all characters of..."** — variable window that expands to become valid, then shrinks to minimize (Minimum Window Substring).
- **"No repeating characters"** — variable window with a set/map tracking duplicates.
- **Counting subarrays that satisfy a condition, where the condition is monotonic as the window grows** (e.g., "number of subarrays with sum less than K") — variable window, count `right - left + 1` at each valid state.
- **Anything involving a running sum/count over a moving contiguous range** — a strong tell, because the whole point of sliding window is avoiding recomputation of that aggregate from scratch.

### Quick-reference problem list
| Category | Example Problems |
|---|---|
| Fixed window | Max Sum Subarray of Size K, Find All Anagrams in a String |
| Variable window (shrink to minimize) | Minimum Window Substring, Minimum Size Subarray Sum |
| Variable window (expand to maximize) | Longest Substring Without Repeating Characters, Longest Repeating Character Replacement, Fruit Into Baskets |
| Counting windows | Subarrays with K Different Integers, Count Subarrays with Sum Less Than K |
| Two Pointer (not window) | Two Sum II, 3Sum, Container With Most Water, Trapping Rain Water, Valid Palindrome |

---

## Summary

- **Two Pointer**: two indices, usually converging, used to avoid re-checking pairs — best for sorted-array pair/triplet problems.
- **Sliding Window**: a specialized two-pointer pattern where the span *between* the pointers is itself meaningful, and you maintain a running aggregate as it expands/contracts — best for contiguous subarray/substring optimization problems.
- Both bring brute-force O(n²) (or worse) down to O(n) by ensuring each pointer only moves forward, never backtracking — that's the amortized-linear-time guarantee in both patterns.

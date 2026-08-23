# Greedy Algorithms

## What Is a Greedy Algorithm?

A **greedy algorithm** builds a solution piece by piece, always choosing the option that looks best *right now* — the **locally optimal choice** — without reconsidering earlier decisions. There's no backtracking and no exploring alternative branches.

A greedy algorithm is *correct* only when the problem has two properties:

| Property | Meaning |
|---|---|
| **Greedy-choice property** | A globally optimal solution can be reached by making a sequence of locally optimal choices. Picking the best option now never rules out an optimal final answer. |
| **Optimal substructure** | An optimal solution to the problem contains optimal solutions to its subproblems. |

If a problem doesn't have the greedy-choice property, a greedy approach will produce a solution that *looks* reasonable but is wrong — usually you need DP or backtracking instead.

---

## Worked Example: Activity Selection Problem

**Problem:** You have `n` activities, each with a `start` and `end` time. A person can only work on one activity at a time. Select the **maximum number of non-overlapping activities**.

```
Activities (start, end):
A: (1, 4)   B: (3, 5)   C: (0, 6)
D: (5, 7)   E: (3, 9)   F: (5, 9)
G: (6, 10)  H: (8, 11)  I: (8, 12)
J: (2, 14)  K: (12, 16)
```

### Greedy strategy
1. **Sort by end time** (the greedy choice: always finish as early as possible to leave the most room for future activities).
2. Pick the first activity.
3. For each next activity, pick it **only if its start time ≥ the end time of the last picked activity**.
4. Repeat.

### Trace
Sorted by end time: `A(1,4) B(3,5) C(0,6) D(5,7) E(3,9) F(5,9) G(6,10) H(8,11) I(8,12) K(12,16) J(2,14)`

| Step | Candidate | Start ≥ last end? | Decision | Last end |
|---|---|---|---|---|
| 1 | A (1,4) | — (first) | ✅ pick | 4 |
| 2 | B (3,5) | 3 ≥ 4? No | ❌ skip | 4 |
| 3 | C (0,6) | 0 ≥ 4? No | ❌ skip | 4 |
| 4 | D (5,7) | 5 ≥ 4? Yes | ✅ pick | 7 |
| 5 | E (3,9) | 3 ≥ 7? No | ❌ skip | 7 |
| 6 | F (5,9) | 5 ≥ 7? No | ❌ skip | 7 |
| 7 | G (6,10) | 6 ≥ 7? No | ❌ skip | 7 |
| 8 | H (8,11) | 8 ≥ 7? Yes | ✅ pick | 11 |
| 9 | I (8,12) | 8 ≥ 11? No | ❌ skip | 11 |
| 10 | K (12,16) | 12 ≥ 11? Yes | ✅ pick | 16 |
| 11 | J (2,14) | 2 ≥ 16? No | ❌ skip | 16 |

**Result: `{A, D, H, K}` — 4 activities.** No other selection can beat this count; sorting by end time provably never eliminates an optimal solution (standard exchange-argument proof: if an optimal solution doesn't include the earliest-finishing activity, you can always swap it in without losing anything).

### Java

```java
import java.util.*;

public class ActivitySelection {
    record Activity(int start, int end) {}

    public static List<Activity> selectActivities(List<Activity> activities) {
        activities.sort(Comparator.comparingInt(Activity::end)); // greedy: sort by end time

        List<Activity> result = new ArrayList<>();
        int lastEnd = Integer.MIN_VALUE;

        for (Activity a : activities) {
            if (a.start() >= lastEnd) {
                result.add(a);
                lastEnd = a.end();
            }
        }
        return result;
    }
}
```

**Complexity:** `O(n log n)` for the sort, `O(n)` for the single pass → **`O(n log n)` overall**, `O(n)` space (ignoring sort's internal space).

### Python

```python
def select_activities(activities: list[tuple[int, int]]) -> list[tuple[int, int]]:
    activities.sort(key=lambda a: a[1])  # greedy: sort by end time

    result = []
    last_end = float('-inf')

    for start, end in activities:
        if start >= last_end:
            result.append((start, end))
            last_end = end

    return result
```

---

## When to Use Greedy

Reach for greedy when **all** of the following hold:

1. **A locally optimal, irrevocable choice at each step leads to a global optimum** — you never need to undo a decision.
2. **The problem has optimal substructure** — solving the rest of the problem after your greedy choice is independent of how you got there.
3. **You can (informally or formally) justify the greedy choice**, typically with one of:
   - **Exchange argument** — show any optimal solution can be transformed into the greedy solution without making it worse.
   - **"Greedy stays ahead"** — show the greedy solution is at least as good as any other solution at every step.
   - **Matroid / cut property** (for graph problems like MST) — the structure guarantees local optimality implies global optimality.
4. **Sorting or a priority queue naturally exposes the "best next choice"** — most greedy algorithms are `sort + single pass` or `heap + repeated extraction`.

**When greedy fails:** if a locally good choice can lock you out of the true optimum, you need **DP** (overlapping subproblems, need to consider multiple choices) or **backtracking**. Classic counterexample: **coin change with arbitrary denominations** — e.g., coins `{1, 3, 4}`, target `6`. Greedy picks `4 + 1 + 1 = 3 coins`, but the optimum is `3 + 3 = 2 coins`. This is why LeetCode's "Coin Change" is a DP problem, not greedy — always check whether a proposed greedy rule has a counterexample before trusting it in an interview.

---

## Common Problem Patterns That Signal Greedy

| Pattern | Signal in the problem statement | Example problems |
|---|---|---|
| **Interval scheduling / merging** | "maximum non-overlapping," "minimum rooms/resources," "merge overlapping" | Activity Selection, Merge Intervals, Non-overlapping Intervals, Meeting Rooms II |
| **Sort + single greedy pass** | Answer only depends on relative order, not exact values | Assign Cookies, Boats to Save People, Gas Station |
| **Exchange-argument-friendly optimization** | "maximum/minimum total," and swapping two adjacent choices never hurts | Task Scheduler, Jump Game II, Candy |
| **Reachability / "can you get there"** | "furthest index reachable," "minimum jumps," "can you reach the end" | Jump Game, Jump Game II, Gas Station |
| **Priority-queue-driven selection** | "always take the largest/smallest available next," repeated extraction | Huffman Coding, Task Scheduler, Minimum Cost to Connect Sticks |
| **Fractional / divisible resource allocation** | Items can be split or partially taken | Fractional Knapsack |
| **Graph MST construction** | "minimum cost to connect all nodes," "minimum spanning tree" | Kruskal's, Prim's, Min Cost to Connect All Points |
| **String/array construction to satisfy constraints in one pass** | "lexicographically smallest," "remove k digits" | Remove K Digits, Create Maximum Number |
| **Two-pointer with a greedy discard rule** | Discarding one end is always safe | Container With Most Water, Boats to Save People |

### Quick self-check before committing to greedy in an interview
- Can I state the greedy rule in one sentence ("always pick the X with the smallest/largest Y")?
- Can I find a counterexample by trying 2–3 small hand-crafted inputs?
- Does sorting by some key make the "right" choice obvious at each step?

If you can't easily disprove it with a small example and the "stays ahead" or exchange argument feels defensible, greedy is very likely the intended approach — and it'll almost always beat DP on time complexity when it applies.

package algorithms.array.medium.backtracking.combination;

import java.util.*;

import org.testng.annotations.Test;

/**
 * https://leetcode.com/problems/combination-sum/
 * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
 * 
 * Given an array of distinct integers candidates and a target integer target,
 * return a list of all unique combinations of candidates where the chosen
 * numbers sum to target. You may return the combinations in any order.
 * 
 * The same number may be chosen from candidates an unlimited number of times.
 * Two combinations are unique if the frequency of at least one of the chosen
 * numbers is different.
 * 
 * It is guaranteed that the number of unique combinations that sum up to target
 * is less than 150 combinations for the given input.
 * 
 * Example 1:
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3],[7]]
 * Explanation:
 * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple
 * times. 7 is a candidate, and 7 = 7. These are the only two combinations.
 * 
 * Example 2:
 * Input: candidates = [2,3,5], target = 8 
 * Output: [[2,2,2,2],[2,3,3],[3,5]]

 * Example 3:
 * Input: candidates = [2], target = 1 Output: [] Example 4:
 * Input: candidates = [1], target = 1 Output: [[1]] Example 5:
 * Input: candidates = [1], target = 2 Output: [[1,1]]
 *
 * Constraints:
 * 1 <= candidates.length <= 30 1 <= candidates[i] <= 200 All elements of
 * candidates are distinct. 1 <= target <= 500
 */

/***
 * Time Complexity: O(N^(T/M))
 *================
 * N = number of candidates
 * T = target value
 * M = minimal value among candidates
 * In the worst case, we explore all possible combinations where each element can be picked repeatedly up to T/M times
 * The branching factor is N at each level, and tree depth is T/M
 * Each leaf node requires O(combination length) to copy the result, but this is amortized
 *
 * Space Complexity: O(T/M)
 *==================
 * Recursion depth: At most T/M (when we pick the smallest candidate repeatedly)
 * Current list: Stores at most T/M elements
 * Result storage: Not counted in space complexity (it's the output)
 */
public class Backtrack_Array06_combination_sum {

	@Test
	private void test() {
		int[] input = {1, 1, 2, 3, 4};
		//System.out.println(combinationSum(input, 7));
		List<List<Integer>> result = combinationSum(input, 5);
		for (List<Integer> sets : result) {
			System.out.println("");
			for (Integer x : sets) {
				System.out.print(x);
			}
		}
	}

	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> result = new ArrayList<>();
		if (candidates == null || candidates.length == 0 || target < 0) return result;

		Arrays.sort(candidates);
		backtrack(candidates, target, 0, new ArrayList<>(), result);
		return result;
	}

	private void backtrack(int[] candidates, int remain, int start,
						   List<Integer> path, List<List<Integer>> result) {
		if (remain == 0) {
			result.add(new ArrayList<>(path));
			return;
		}

		for (int i = start; i < candidates.length; i++) {
			int val = candidates[i];

			// Prune: since sorted, no need to continue if val > remain
			if (val > remain) break;

			path.add(val);
			backtrack(candidates, remain - val, i, path, result); // i (reuse allowed)
			path.remove(path.size() - 1);
		}
	}
}

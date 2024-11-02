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
public class Backtrack_Array06_combination_sum {

	@Test
	private void test() {
		int[] input = { 1,1,2,3,4};
		//System.out.println(combinationSum(input, 7));
		List<List<Integer>> result = combinationSum(input,5);
		for(List<Integer> sets : result){
			System.out.println("");
			for(Integer x: sets){
				System.out.print(x);
			}
		}
	}
	
	public List<List<Integer>> combinationSum(int[] nums, int target) {
		List<List<Integer>> list = new ArrayList<>();
		//Arrays.sort(nums); // NOT REQUIRED
		backtrack(list, new ArrayList<>(), nums, target, 0);
		return list;
	}

	/**
	 Time: O(2 ^N)
	 Space: O(N)
	 */
	private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start) {
		if (remain < 0)
			return;
		else if (remain == 0)
			list.add(new ArrayList<>(tempList));
		else {
			for (int i = start; i < nums.length; i++) {
				tempList.add(nums[i]);
				backtrack(list, tempList, nums, remain - nums[i], i); // not i + 1 because we can reuse same elements
				tempList.remove(tempList.size() - 1);
			}
		}
	}
}

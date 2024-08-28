package algorithms.array.medium.backtracking.subset;

import java.util.*;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/subsets/
 * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
 * 
 * Given an integer array nums, return all possible subsets (the power set).
 * 
 * The solution set must not contain duplicate subsets.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,2,3] Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 * Example 2:
 * 
 * Input: nums = [0] Output: [[],[0]]
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 10 -10 <= nums[i] <= 10
 */
public class Backtrack_Array01_SubsetsWithoutDuplicate {

	@Test
	private void test() {
		int[] input = { 1, 2, 3 };
		System.out.println(subsets(input));
		String a = "";
	}

	/*
	 * Time complexity )(n * 2 ^ n)
	 */
	public List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		backtrack(list, new ArrayList<>(), nums, 0);
		return list;
	} 

	private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
		list.add(new ArrayList<>(tempList));
		for (int i = start; i < nums.length; i++) {
			tempList.add(nums[i]);
			backtrack(list, tempList, nums, i + 1);//IMPORTANT to increment i not start
			tempList.remove(tempList.size() - 1);
		}
	}
}

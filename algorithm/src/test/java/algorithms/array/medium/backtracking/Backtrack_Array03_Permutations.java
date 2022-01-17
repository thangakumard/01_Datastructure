package algorithms.array.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/permutations/
 * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
 * 
 * Given an array nums of distinct integers, return all the possible
 * permutations. You can return the answer in any order.
 * 
 * Example 1:
 * 
 * Input: nums = [1,2,3] Output:
 * [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]] Example 2:
 * 
 * Input: nums = [0,1] Output: [[0,1],[1,0]] Example 3:
 * 
 * Input: nums = [1] Output: [[1]]
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 6 -10 <= nums[i] <= 10 All the integers of nums are
 * unique.
 * 
 */

public class  Backtrack_Array03_Permutations {

	@Test
	private void test() {
		int[] input = { 1, 2, 3 };
		System.out.println(permute(input));
		String a = "";
	}
	
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		// Arrays.sort(nums); // not necessary
		backtrack(list, new ArrayList<>(), nums);
		return list;
	}

	private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums) {
		if (tempList.size() == nums.length) {
			list.add(new ArrayList<>(tempList));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (tempList.contains(nums[i]))
					continue; // element already exists, skip
				tempList.add(nums[i]);
				backtrack(list, tempList, nums);
				tempList.remove(tempList.size() - 1);
			}
		}
	}
}

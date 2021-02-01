package algorithms.array.medium.backtracking;

import java.util.*;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/permutations-ii/
 * https://leetcode.com/problems/combination-sum/discuss/16502/A-general-approach-to-backtracking-questions-in-Java-(Subsets-Permutations-Combination-Sum-Palindrome-Partitioning)
 * 
 *  Given a collection of numbers,
 * nums, that might contain duplicates, return all possible unique permutations
 * in any order.
 * 
 * Example 1:
 * 
 * Input: nums = [1,1,2] Output: [[1,1,2], [1,2,1], [2,1,1]] Example 2:
 * 
 * Input: nums = [1,2,3] Output:
 * [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 8 -10 <= nums[i] <= 10
 * 
 */
public class Backtrack_Array04_Permutations_II {

	@Test
	private void test() {
		int[] input = { 1, 2, 2 };
		System.out.println(permuteUnique(input));
		String a = "";
	}
	
	public List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> list = new ArrayList<>();
		Arrays.sort(nums);
		backtrack(list, new ArrayList<>(), nums, new boolean[nums.length]);
		return list;
	}

	private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, boolean[] used) {
		if (tempList.size() == nums.length) {
			list.add(new ArrayList<>(tempList));
		} else {
			for (int i = 0; i < nums.length; i++) {
				if (used[i] || i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
					continue;
				used[i] = true;
				tempList.add(nums[i]);
				backtrack(list, tempList, nums, used);
				used[i] = false;
				tempList.remove(tempList.size() - 1);
			}
		}
	}
}

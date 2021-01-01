package algorithms.array.medium;

import java.util.*;
/*
 * https://leetcode.com/problems/find-all-duplicates-in-an-array/
 * Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

	Find all the elements that appear twice in this array.
	
	Could you do it without extra space and in O(n) runtime?
	
	Example:
	Input:
	[4,3,2,7,8,2,3,1]
	
	Output:
	[2,3]
 */

import org.testng.annotations.Test;

public class Array06_FindAllDuplicates_without_extra_space {
	
	@Test
	private void test() {
		int[] input = {2,1,3,1,2,4,5,6};
		System.out.println(findDuplicates(input));
	}

	public List<Integer> findDuplicates(int[] nums) {
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < nums.length; i++) {
			if (nums[Math.abs(nums[i]) - 1] < 0) {
				result.add(Math.abs(nums[i]));
			} else {
				nums[Math.abs(nums[i]) - 1] = -nums[Math.abs(nums[i]) - 1];
			}
		}
		return result;
	}
}

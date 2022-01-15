package algorithms.array.medium;

import java.util.*;

import org.testng.annotations.Test;

/*
 * Given an array nums of n integers, are there elements a, b, c in nums
 *  such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Notice that the solution set must not contain duplicate triplets.

 

Example 1:

Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]

Example 2:

Input: nums = []
Output: []
Example 3:

Input: nums = [0]
Output: []
 

Constraints:

0 <= nums.length <= 3000
-105 <= nums[i] <= 105
 */

public class Array12_3Sum {

	@Test
	public void test() {
		int[] input = { -1, 0, 1, 2, -1, -4 };
		System.out.println(threeSum(input));
	}

	public List<List<Integer>> threeSum(int[] nums) {
		if (nums.length < 3)
			return new ArrayList<>();

		Set<List<Integer>> result = new HashSet<>();
		Arrays.sort(nums);
		int sum = 0;
		for (int i = 0; i < nums.length - 2; i++) {
			int j = i + 1, k = nums.length - 1;
			while (j < k) {
				sum = nums[i] + nums[j] + nums[k];
				if (sum == 0)
					result.add(Arrays.asList(nums[i], nums[j++], nums[k--]));
				else if (sum > 0)
					k--;
				else if (sum < 0)
					j++;

			}
		}
		return new ArrayList<>(result);
	}
}

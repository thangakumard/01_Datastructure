package algorithms.array.medium.twoPointer;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/*
 * https://leetcode.com/problems/3sum-closest/

Given an array nums of n integers and an integer target,
* find three integers in nums such that the sum is closest to target.
* Return the sum of the three integers.
* You may assume that each input would have exactly one solution.


Example 1:
Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
 

Constraints:

3 <= nums.length <= 10^3
-10^3 <= nums[i] <= 10^3
-10^4 <= target <= 10^4
 */
public class twoPointer03_3SumClosest {



	@Test
	public void test(){

		/*
	 Example 1:
	 Input: nums = [-1,2,5,-4], target = -2
	 Output: 2
	 */
		int[] nums = new int[] {-1,2,5,-4};
		Assert.assertEquals(threeSumClosest(nums, -2), -3);
	}

	public int threeSumClosest(int[] nums, int target) {
		int min_difference = Integer.MAX_VALUE;
		int sum = 0, result = 0;
		Arrays.sort(nums);
		for (int i = 0; i < nums.length - 2; i++) {
			int j = i + 1, k = nums.length - 1;

			while (j < k) {
				sum = nums[i] + nums[j] + nums[k];

				if (sum == target)
					return sum;

				int diff = Math.abs(target - sum);
				if (diff < min_difference) { // IT IS IMPORTANT TO CAPTURE THE SUM FOR RESULT. WE CANNOT USE Math.min HERE
					min_difference = diff;
					result = sum;
				}
				if (sum > target) {
					k--;
				} else if (sum < target) {
					j++;
				}
			}
		}
		return result;
	}
}

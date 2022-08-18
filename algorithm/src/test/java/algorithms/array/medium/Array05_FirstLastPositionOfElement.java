package algorithms.array.medium;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1].

Follow up: Could you write an algorithm with O(log n) runtime complexity?

 

Example 1:

Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
Example 2:

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
Example 3:

Input: nums = [], target = 0
Output: [-1,-1]
 

Constraints:

0 <= nums.length <= 105
-109 <= nums[i] <= 109
nums is a non-decreasing array.
-109 <= target <= 109
 */

public class Array05_FirstLastPositionOfElement {

	@Test
	public void test() {

		int[] input = { 5, 7, 7, 8, 8, 10 };
		int[] result = searchRange(input, 8);
		System.out.println("First Inde :" + result[0]);
		System.out.println("Last Inde :" + result[1]);
	}

	/*
	 * Time complexity O(logn) Space complexity O(1)
	 */

	public int[] searchRange(int[] nums, int target) {
		int[] result = { -1, -1 };

		if (nums.length < 1)
			return result;

		result[0] = leftMostPositionOfElement(nums, target);
		result[1] = rightMostPositionOfElement(nums, target);

		return result;
	}

	private int leftMostPositionOfElement(int[] nums, int target) {
		int targetMatchingIndex = -1;
		int start = 0, end = nums.length - 1, mid = 0;

		while (start <= end) {
			mid = start + (end - start) / 2;

			if (nums[mid] >= target) { //Even if the value matches, keep moving to the left
				end = mid - 1;
			} else {
				start = mid + 1;
			}

			//**IMPORTANT - Keep track of the target matching index. But do not return here.
			if (nums[mid] == target)
				targetMatchingIndex = mid;
		}

		return targetMatchingIndex;
	}

	private int rightMostPositionOfElement(int[] nums, int target) {
		int targetMatchingIndex = -1;
		int start = 0, end = nums.length - 1, mid = 0;

		while (start <= end) {
			mid = start + (end - start) / 2;

			if (nums[mid] <= target) {//Even if the value matches, keep moving to the right
				start = mid + 1;
			} else {
				end = mid - 1;
			}

			if (nums[mid] == target)
				targetMatchingIndex = mid;
		}

		return targetMatchingIndex;
	}

}

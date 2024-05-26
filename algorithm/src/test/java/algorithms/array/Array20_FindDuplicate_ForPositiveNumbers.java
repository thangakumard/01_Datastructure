package algorithms.array;

import java.util.HashSet;

import org.testng.annotations.Test;

//Find duplicates in O(n) time and O(1) extra space | Set 1
//http://www.geeksforgeeks.org/find-duplicates-in-on-time-and-constant-extra-space/

/****
 * https://leetcode.com/problems/find-the-duplicate-number/description/
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 *
 * There is only one repeated number in nums, return this repeated number.
 *
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,3,4,2,2]
 * Output: 2
 * Example 2:
 *
 * Input: nums = [3,1,3,4,2]
 * Output: 3
 * Example 3:
 *
 * Input: nums = [3,3,3,3,3]
 * Output: 3
 *
 *
 * Constraints:
 *
 * 1 <= n <= 105
 * nums.length == n + 1
 * 1 <= nums[i] <= n
 * All the integers in nums appear only once except for precisely one integer which appears two or more times.
 *
 *
 * Follow up:
 *
 * How can we prove that at least one duplicate number must exist in nums?
 * Can you solve the problem in linear runtime complexity?
 */

public class Array20_FindDuplicate_ForPositiveNumbers {

	/**
	 * Mark visited element negative
	 * Time Complexity: O(n)
	 * Space Complexity: O(1)
	 * @param nums
	 * @return
	 */
	@Test
	public int findDuplicate_01(int[] nums) {

		for(int i=0; i < nums.length; i++){
			int value = Math.abs(nums[i]);
			if(nums[value] < 0){
				return value;
			}
			nums[value] = -nums[value];
		}
		return -1;
	}
	/**
	 * Counter approach
	 * Time Complexity: O(n)
	 * Space Complexity: O(n)
	 * @param nums
	 * @return
	 */
	@Test
	public int findDuplicate(int[] nums) {
		int[] counter = new int[nums.length+1];

		for(int i=0; i < nums.length; i++){
			if(counter[nums[i]] == 1)
				return nums[i];
			else{
				counter[nums[i]]++;
			}
		}

		return -1;
	}
}

 package algorithms.array.medium;

import org.testng.annotations.Test;

/****
 * 
 * https://leetcode.com/problems/product-of-array-except-self/
 *
 * Given an array nums of n integers where n > 1, return an array output such
 * that output[i] is equal to the product of all the elements of nums except
 * nums[i].
 * 
 * Example:
 * 
 * Input: [1,2,3,4] Output: [24,12,8,6] Constraint: It's guaranteed that the
 * product of the elements of any prefix or suffix of the array (including the
 * whole array) fits in a 32 bit integer.
 * 
 * Note: Please solve it without division and in O(n).
 * 
 * Follow up: Could you solve it with constant space complexity? (The output
 * array does not count as extra space for the purpose of space complexity
 * analysis.)
 */

public class Array01_ProductOfArrayExceptSelf {

	@Test
	public void test() {
		int[] input_1 = { 1, 2, 3, 4 };
		int[] result = WithSpacecomplex_1(input_1);
		for (int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}

	/*
	 * Time Complexity O(n) Space Complexity O(n)
	 */
	public int[] productExceptSelf(int[] nums) {
		int[] result = new int[nums.length];
		int[] L = new int[nums.length];
		int[] R = new int[nums.length];

		L[0] = 1;
		for (int i = 1; i < nums.length; i++) {
			L[i] = nums[i - 1] * L[i - 1];
		}

		R[nums.length - 1] = 1;
		for (int i = nums.length - 2; i >= 0; i--) {
			R[i] = nums[i + 1] * R[i + 1];
		}

		for (int i = 0; i < nums.length; i++) {
			result[i] = L[i] * R[i];
		}

		return result;
	}

	/****
	 * 
	 * Time Complexity O(n) Space Complexity O(1) if we do not consider result
	 * array. The problem statement mentions that using the answeranswer array
	 * doesn't add to the space complexity
	 */
	public int[] WithSpacecomplex_1(int[] nums) {
		//[1,2,3,4]
		int[] result = new int[nums.length];

		result[0] = 1; //1
		for (int i = 1; i < nums.length; i++) {
			result[i] = nums[i - 1] * result[i - 1];
		}
		//result = [1,1,2,6]

		int R = 1;
		for (int i = nums.length - 1; i >= 0; i--) { //***IMPORTANT TO START FROM nums.length - 1 NOT nums.length - 2
			result[i] = result[i] * R;
			R *= nums[i];
		}
		//result = [24,12,8,6]

		return result;
	}
}

package algorithms.array.medium.slidingWindow;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/subarray-product-less-than-k/
 * 
 * Your are given an array of positive integers nums.

Count and print the number of (contiguous) subarrays where the product of all the elements in the subarray is less than k.

Example 1:
Input: nums = [10, 5, 2, 6], k = 100
Output: 8
Explanation: The 8 subarrays that have product less than 100 are: [10], [5], [2], [6], [10, 5], [5, 2], [2, 6], [5, 2, 6].
Note that [10, 5, 2] is not included as the product of 100 is not strictly less than k.
Note:

0 < nums.length <= 50000.
0 < nums[i] < 1000.
0 <= k < 10^6.


 */
public class SlidingWindow04_SubarrayProductLessThanK {

	@Test
	public void test() {
		Assertions.assertThat(numSubarrayProductLessThanK((new int[] {1,1,1}), 1)).isEqualTo(18);
		//Assertions.assertThat(numSubarrayProductLessThanK((new int[] {10,9,10,4,3,8,3,3,6,2,10,10,9,3}), 19)).isEqualTo(18);
//		System.out.println(this.numSubarrayProductLessThanK(new int[] { 10, 5, 2, 6 }, 100));
//		System.out.println(this.numSubarrayProductLessThanK(new int[] { 2, 5, 3, 10 }, 30));
//		System.out.println(this.numSubarrayProductLessThanK(new int[] { 8, 2, 6, 5 }, 50));
	}

	public int numSubarrayProductLessThanK(int[] nums, int k) {

		if (k <= 1)
			return 0;

		int product = 1;
		int result = 0;

		int left = 0, right = 0;

		while (right < nums.length) {
			product *= nums[right];
			while (product >= k) { // note : While loop not IF
				product /= nums[left];
				left++; // Move left pointer only when product >= k
			}
			result += right - left + 1;
			right++; //for each setup increment the right pointer
		}
		return result;
	}
}

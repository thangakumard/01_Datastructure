package algorithms.array.medium.subArray;

import org.testng.Assert;
import org.testng.annotations.Test;
/****
 * https://leetcode.com/problems/maximum-product-subarray/
 * Given an integer array nums, find a contiguous non-empty subarray within the array that has the largest product, 
 * and return the product.

It is guaranteed that the answer will fit in a 32-bit integer.

A subarray is a contiguous subsequence of the array.

Example 1:
Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.

Example 2:
Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 

Constraints:

1 <= nums.length <= 2 * 104
-10 <= nums[i] <= 10

 *
 */

public class Subarray02_MaximumProductSubarray {

	@Test
	private void test() {
		int[] input1 = {2,3,-2,4};
		Assert.assertEquals(maxProduct(input1), 6);
		
		int[] input_1 = {2,3,-2,400};
		Assert.assertEquals(maxProduct(input_1), 400);
		
		int[] input2 = {0,2};
		Assert.assertEquals(maxProduct(input2), 2);
	}

    /**
     * Dynamic programming approach
     * @param nums
     * @return
     */
	public int maxProduct(int[] nums) {
        if(nums == null || nums.length == 0) return 0;

        int max_so_far = nums[0];
        int min_so_far = nums[0]; //IMPORTANT to initialize with nums[0];
        int maxProduct = max_so_far;

        int currentMax;
        int currentMin;

        for(int i=1; i < nums.length; i++){
            currentMax = Math.max(max_so_far * nums[i], min_so_far * nums[i]);
            currentMin = Math.min(max_so_far * nums[i], min_so_far * nums[i]);

            max_so_far =  Math.max(nums[i], currentMax);
            min_so_far =  Math.min(nums[i], currentMin);

            maxProduct = Math.max(maxProduct, max_so_far);
        }

        return maxProduct;
    }
}

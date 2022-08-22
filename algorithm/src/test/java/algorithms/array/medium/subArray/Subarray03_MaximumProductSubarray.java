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

public class Subarray03_MaximumProductSubarray {

	@Test
	private void test() {
		int[] input1 = {2,3,-2,4};
		Assert.assertEquals(maxProduct(input1), 6);
		
		int[] input_1 = {2,3,-2,400};
		Assert.assertEquals(maxProduct(input_1), 400);
		
		int[] input2 = {0,2};
		Assert.assertEquals(maxProduct(input2), 2);
	}
	
	public int maxProduct(int[] nums) {
        if(nums.length == 0) return 0;
        
        int max_so_far = nums[0];
        int min_so_far = nums[0];
        int result = max_so_far; //********* Important to initialize result with nums[0] not with 0
        
        for(int i=1; i < nums.length; i++){
            int curr = nums[i];
            
            int temp = Math.max(curr,Math.max(max_so_far * curr , min_so_far * curr));
            min_so_far = Math.min(curr,Math.min(max_so_far * curr , min_so_far * curr));
            
            max_so_far = temp;
            result = Math.max(result, max_so_far);
        }
        
        return result;
    }
}

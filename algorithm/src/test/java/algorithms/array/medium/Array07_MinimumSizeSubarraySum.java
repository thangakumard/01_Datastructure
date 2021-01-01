package algorithms.array.medium;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/minimum-size-subarray-sum/
 * 
 * Given an array of n positive integers and a positive integer s, 
 * find the minimal length of a contiguous subarray of which the sum ≥ s. 
 * If there isn't one, return 0 instead.

Example: 
	
	Input: s = 7, nums = [2,3,1,2,4,3]
	Output: 2
	Explanation: the subarray [4,3] has the minimal length under the problem constraint.
	Follow up:
	If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n)

 */
public class Array07_MinimumSizeSubarraySum {
	
	@Test
	private void test() {
		int[] input = {2,3,1,2,4,3};
		int s = 5;
		Assert.assertEquals(minSubArrayLen(s,input), 2); 
		
	}
	
	/*
	 * Time complexity O(n)
	 * Space complexity O(1)
	 */
	public int minSubArrayLen(int s, int[] nums) {
        if(nums.length < 1) return 0;
        
        int min_subarray = Integer.MAX_VALUE, i=0, j=0, current_sum=0;
        System.out.println();
        while(j < nums.length) {
        	current_sum += nums[j];
        	j++;
        	
        	while(current_sum >= s) {
        		min_subarray = Math.min(min_subarray, j-i);
        		current_sum -= nums[i++];
        	}
        }
       return min_subarray == Integer.MAX_VALUE ? 0 : min_subarray;
    }
	
	

}

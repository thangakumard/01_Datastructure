package algorithms.array.easy;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/maximum-subarray/
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.

 

Example 1:

Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Example 2:

Input: nums = [1]
Output: 1
Example 3:

Input: nums = [0]
Output: 0
Example 4:

Input: nums = [-1]
Output: -1
Example 5:

Input: nums = [-2147483647]
Output: -2147483647
 

Constraints:

1 <= nums.length <= 2 * 104
-231 <= nums[i] <= 231 - 1

 */
public class Array10_Kadanes_MaxSumSubArray {

	@Test
	public void Test(){
				
		//int[] input ={1,3,-2,4,-2,1};
		int[] input ={-2,1,-3,4,-1,2,1,-5,4};
		System.out.println(maxSubArray_01(input));
		System.out.println(maxSubArray_02(input));
	}
	
	public int maxSubArray_01(int[] nums) {
		int currentSum = nums[0]; // IMPORTANT TO initialize with 1st element in the array for the scenario [-1]
		int maxSum = nums[0];

		for (int i = 1; i < nums.length; i++){
		    currentSum = Math.max(nums[i], currentSum + nums[i]);
		    maxSum = Math.max(maxSum, currentSum);
		}
		return maxSum;
	}
	
	public int maxSubArray_02(int[] nums) {
        int size =nums.length;
        int maxValue = Integer.MIN_VALUE;
        int addition = 0;
        for(int i=0; i< size; i++){
            addition = addition + nums[i];
            
            if(maxValue < addition){
                maxValue = addition;
            }
            if(addition < 0)
            {
                addition = 0;
            }
        }
        return maxValue;  
    }
}

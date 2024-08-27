package algorithms.array.medium.subArray;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * https://leetcode.com/problems/maximum-subarray/description
 * Given an integer array nums, find the subarray with the largest sum, and return its sum.
 *
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.

 * Example 2:
 * Input: nums = [1]
 * Output: 1
 * Explanation: The subarray [1] has the largest sum 1.

 * Example 3:
 * Input: nums = [5,4,-1,7,8]
 * Output: 23
 * Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 */

public class Subarray01_Kadanes_MaxSumSubArray {
    @Test
    public void Test(){
        Assertions.assertThat(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4})).isEqualTo(6);
    }
    public int maxSubArray(int[] nums) {
        // Initialize our variables using the first element.
        int currentSum = nums[0];
        int maxSubarraySum = nums[0];

        // Start with the 2nd element since we already used the first one.
        for (int i = 1; i < nums.length; i++) {
            // If current_subarray is negative, throw it away. Otherwise, keep adding to it.
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSubarraySum = Math.max(maxSubarraySum, currentSum);
        }

        return maxSubarraySum;
    }
}



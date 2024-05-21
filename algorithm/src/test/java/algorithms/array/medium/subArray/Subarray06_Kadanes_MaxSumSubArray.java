package algorithms.array.medium.subArray;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class Subarray06_Kadanes_MaxSumSubArray {

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



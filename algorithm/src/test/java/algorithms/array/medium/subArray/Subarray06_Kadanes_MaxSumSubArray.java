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
        int currentSubarray = nums[0];
        int maxSubarray = nums[0];

        // Start with the 2nd element since we already used the first one.
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            // If current_subarray is negative, throw it away. Otherwise, keep adding to it.
            currentSubarray = Math.max(num, currentSubarray + num);
            maxSubarray = Math.max(maxSubarray, currentSubarray);
        }

        return maxSubarray;
    }
}



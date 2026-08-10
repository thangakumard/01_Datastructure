package algorithms.dynamicProgramming;

import java.util.*;

public class Dynamic23_maximumJumps {
    public int maximumJumps(int[] nums, int target) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp,-1);
        dp[0] = 0;

        for(int j=1; j < n; j++){
            for(int i=0; i < j; i++){
                if(dp[i] == -1) continue;
                if(Math.abs(nums[j] - nums[i]) <= target){
                    dp[j] = Math.max(dp[j], dp[i]+1);
                }
            }
        }
        return dp[n-1];
    }
}

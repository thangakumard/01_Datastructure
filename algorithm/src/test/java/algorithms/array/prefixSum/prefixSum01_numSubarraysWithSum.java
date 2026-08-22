package algorithms.array.medium.prefixSum;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/binary-subarrays-with-sum/description/
 * Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
 * A subarray is a contiguous part of the array.
 *
 * Example 1:
 * Input: nums = [1,0,1,0,1], goal = 2
 * Output: 4
 * Explanation: The 4 subarrays are bolded and underlined below:
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 * [1,0,1,0,1]
 * [1,0,1,0,1]

 * Example 2:
 * Input: nums = [0,0,0,0,0], goal = 0
 * Output: 15
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 104
 * nums[i] is either 0 or 1.
 * 0 <= goal <= nums.length
 */
public class prefixSum01_numSubarraysWithSum {

    public int numSubarraysWithSum(int[] nums, int goal) {
        HashMap<Integer, Integer> prefixSum = new HashMap<>();
        int result = 0, currentSum = 0;

        for(int i=0; i < nums.length; i++){
            currentSum += nums[i];
            if(currentSum == goal){
                result++;
            }
            if(prefixSum.containsKey(currentSum - goal)){
                result += prefixSum.get(currentSum - goal);
            }
            prefixSum.put(currentSum, prefixSum.getOrDefault(currentSum, 0)+1);
        }

        return result;
    }
}

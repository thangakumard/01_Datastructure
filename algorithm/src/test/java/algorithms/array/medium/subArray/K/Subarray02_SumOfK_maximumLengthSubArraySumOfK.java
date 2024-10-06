package algorithms.array.medium.subArray.K;

import java.util.HashMap;

/***
 * https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/
 * Given an integer array nums and an integer k, return the maximum length of a subarray
 *  that sums to k. If there is not one, return 0 instead.
 *
 * Example 1:
 * Input: nums = [1,-1,5,-2,3], k = 3
 * Output: 4
 *
 * Explanation: The subarray [1, -1, 5, -2] sums to 3 and is the longest.
 * Example 2:
 * Input: nums = [-2,-1,2,1], k = 1
 * Output: 2
 * Explanation: The subarray [-1, 2] sums to 1 and is the longest.
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 105
 * -104 <= nums[i] <= 104
 * -109 <= k <= 109
 */
public class Subarray02_SumOfK_maximumLengthSubArraySumOfK {
    public int maxSubArrayLen(int[] nums, int k) {
        int prefixSum = 0;
        int longestSubarray = 0;
        HashMap<Integer, Integer> indices = new HashMap<>();//num is key and index is value
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];

            // Check if all of the numbers seen so far sum to k.
            if (prefixSum == k) {
                longestSubarray = i + 1;
            }

            // If any subarray seen so far sums to k, then update the length of the longest_subarray.
            if (indices.containsKey(prefixSum - k)) {
                longestSubarray = Math.max(longestSubarray, i - indices.get(prefixSum - k));
            }

            // Only add the current prefix_sum index pair to the
            // map if the prefix_sum is not already in the map.
            if (!indices.containsKey(prefixSum)) {
                indices.put(prefixSum, i);
            }
        }

        return longestSubarray;
    }
}

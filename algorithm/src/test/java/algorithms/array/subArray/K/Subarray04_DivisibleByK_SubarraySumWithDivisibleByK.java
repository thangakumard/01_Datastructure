package algorithms.array.medium.subArray.K;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/subarray-sums-divisible-by-k/
 * Given an integer array nums and an integer k, return the number of non-empty subarrays that have a sum divisible by k.
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 *
 * Input: nums = [4,5,0,-2,-3,1], k = 5
 * Output: 7
 * Explanation: There are 7 subarrays with a sum divisible by k = 5:
 * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
 * Example 2:
 *
 * Input: nums = [5], k = 9
 * Output: 0
 */
public class Subarray04_DivisibleByK_SubarraySumWithDivisibleByK {
    public int subarraysDivByK(int[] nums, int k) {
        int prefixMod = 0;
        HashMap<Integer, Integer> modSeen = new HashMap<>();// prefixMod is key. Counter is value
        modSeen.put(0, 1);
        int counter =0;

        for (int i = 0; i < nums.length; i++) {
            prefixMod = (prefixMod + nums[i]) % k;
            if(prefixMod < 0) prefixMod += k; //To handle negative values
            counter += modSeen.getOrDefault(prefixMod, 0);
            modSeen.put(prefixMod, modSeen.getOrDefault(prefixMod, 0)+1);
        }
        return counter;
    }
}

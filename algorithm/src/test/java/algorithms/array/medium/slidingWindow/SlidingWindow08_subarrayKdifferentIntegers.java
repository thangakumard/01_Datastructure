package algorithms.array.medium.slidingWindow;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/subarrays-with-k-different-integers/description/
 * Given an integer array nums and an integer k, return the number of good subarrays of nums.
 *
 * A good array is an array where the number of different integers in that array is exactly k.
 *
 * For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 * Input: nums = [1,2,1,2,3], k = 2
 * Output: 7
 *
 * Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2]
 * Example 2:
 * Input: nums = [1,2,1,3,4], k = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
 *
 * https://leetcode.com/problems/subarrays-with-k-different-integers/solutions/523136/JavaC++Python-Sliding-Window/
 *
 * Intuition:
 * First you may have feeling of using sliding window.
 * Then this idea get stuck in the middle.
 *
 * This problem will be a very typical sliding window,
 * if it asks the number of subarrays with at most K distinct elements.
 *
 * Just need one more step to reach the folloing equation:
 * exactly(K) = atMost(K) - atMost(K-1)
 */

public class SlidingWindow08_subarrayKdifferentIntegers {
    public int subarraysWithKDistinct(int[] nums, int k) {
        return atMostKInteger(nums,k) - atMostKInteger(nums, k-1);
    }

    private int atMostKInteger(int[] nums, int k){
        int i=0, result = 0;
        HashMap<Integer, Integer> mapCounter = new HashMap<>();
        for(int j=0; j < nums.length; j++){
            if(mapCounter.getOrDefault(nums[j], 0) == 0) k--;
            mapCounter.put(nums[j], mapCounter.getOrDefault(nums[j], 0)+1);

            while(k < 0){
                mapCounter.put(nums[i], mapCounter.get(nums[i])-1);
                if(mapCounter.get(nums[i]) == 0) k++;
                i++;
            }
            result += j-i+1;
        }
        return result;
    }
}

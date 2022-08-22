package algorithms.array.medium.subArray;

import java.util.HashMap;

/***
 * https://leetcode.com/problems/subarray-sum-equals-k/
 *
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 *
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 */
public class Subarray04_SubarraySum {
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap<Integer, Integer> mapSum = new HashMap<Integer, Integer>();
        mapSum.put(0,1);
        for(int i: nums){
            sum += i;
            if(mapSum.containsKey(sum-k)){
                count += mapSum.get(sum-k);
            }
            mapSum.put(sum, mapSum.getOrDefault(sum, 0)+1);
        }

        return count;
    }
}

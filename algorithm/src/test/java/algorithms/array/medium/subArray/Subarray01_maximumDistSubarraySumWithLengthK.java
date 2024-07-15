package algorithms.array.medium.subArray;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/maximum-sum-of-distinct-subarrays-with-length-k/
 *
 * You are given an integer array nums and an integer k. Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:
 * The length of the subarray is k, and
 * All the elements of the subarray are distinct.
 * Return the maximum subarray sum of all the subarrays that meet the conditions. If no subarray meets the conditions, return 0.
 *
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Example 1:
 * Input: nums = [1,5,4,2,9,9,9], k = 3
 * Output: 15
 * Explanation: The subarrays of nums with length 3 are:
 * - [1,5,4] which meets the requirements and has a sum of 10.
 * - [5,4,2] which meets the requirements and has a sum of 11.
 * - [4,2,9] which meets the requirements and has a sum of 15.
 * - [2,9,9] which does not meet the requirements because the element 9 is repeated.
 * - [9,9,9] which does not meet the requirements because the element 9 is repeated.
 * We return 15 because it is the maximum subarray sum of all the subarrays that meet the conditions
 *
 *  Example 2:
 * Input: nums = [4,4,4], k = 3
 * Output: 0
 * Explanation: The subarrays of nums with length 3 are:
 * - [4,4,4] which does not meet the requirements because the element 4 is repeated.
 * We return 0 because no subarrays meet the conditions.
 *
 * Constraints:
 * 1 <= k <= nums.length <= 105
 * 1 <= nums[i] <= 105
 */
public class Subarray01_maximumDistSubarraySumWithLengthK {
    public long maximumSubarraySum(int[] A, int k) {
        HashMap<Integer, Integer> mapCounter = new HashMap<>();
        long maxSum = 0, sum = 0;
        for (int i = 0; i < A.length; i++){
            sum +=A[i];
            mapCounter.put(A[i], mapCounter.getOrDefault(A[i],0) + 1);

            if (i >= k - 1){
                if (mapCounter.size() == k) maxSum = Math.max(maxSum, sum);
                sum -= A[i - k + 1];
                mapCounter.put(A[i - k + 1], mapCounter.get(A[i - k + 1]) - 1);
                if (mapCounter.get(A[i - k + 1]) == 0) mapCounter.remove(A[i - k + 1]);
            }
        }
        return maxSum;
    }

    /***
     * Meta Interview Quest : Give the maximum sum of a contiguous subarray of size k in an array of size n.
     */
    public long maximumSubarraySum_(int[] A, int k) {
        long maxSum = 0, sum = 0;
        for (int i = 0; i < A.length; i++){
            sum +=A[i];
            if (i >= k - 1){
                maxSum = Math.max(maxSum, sum);
                sum -= A[i - k + 1];
            }
        }
        return maxSum;
    }
}

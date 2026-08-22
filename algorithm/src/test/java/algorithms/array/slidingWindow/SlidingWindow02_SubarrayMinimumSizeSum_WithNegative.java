package algorithms.array.medium.slidingWindow;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/description/
 * Given an integer array nums and an integer k, return the length of the shortest non-empty subarray of nums with a sum of at least k. If there is no such subarray, return -1.
 * A subarray is a contiguous part of an array.
 *
 * Example 1:
 * Input: nums = [1], k = 1
 * Output: 1
 *
 * Example 2:
 * Input: nums = [1,2], k = 4
 * Output: -1
 *
 * Example 3:
 * Input: nums = [2,-1,2], k = 3
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -105 <= nums[i] <= 105
 * 1 <= k <= 109
 */
public class SlidingWindow02_SubarrayMinimumSizeSum_WithNegative {
    public int shortestSubarray(int[] input, int K) {
        int N = input.length, minLength = N + 1;
        long[] currentSum = new long[N + 1];
        for (int i = 0; i < N; i++) currentSum[i + 1] = currentSum[i] + input[i];
        LinkedList<Integer> lList = new LinkedList<>();
        for (int i = 0; i < N + 1; i++) {
            while (lList.size() > 0 && currentSum[i] - currentSum[lList.getFirst()] >=  K)
                minLength = Math.min(minLength, i - lList.pollFirst());
            while (lList.size() > 0 && currentSum[i] <= currentSum[lList.getLast()])
                lList.pollLast();
            lList.addLast(i);
        }
        return minLength <= N ? minLength : -1;
    }
}

package algorithms.array.medium.twoPointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/maximum-frequency-of-an-element-after-performing-operations-i/

 * Explanation
 * Case 1: Focusing on Existing Elements
 * This case tries to maximize the frequency of a number
 * that's already present in the array.
 * Range: [a - k, a + k]
 *
 * Case 2: Creating a New Target Value
 * This case explores the possibility of making several numbers equal to a value
 * that might not initially exist in the array.
 * Range: [a, a + k + k]
 */
public class twoPointer05_MaxFrequency {
    public int maxFrequency(int[] A, int k, int numOperations) {
        Arrays.sort(A);

        // Case 1
        Map<Integer, Integer> count = new HashMap<>();
        int res = 0, left = 0, right = 0, n = A.length;
        for (int a : A) {
            while (right < n && A[right] <= a + k) {
                count.put(A[right], count.getOrDefault(A[right], 0) + 1);
                right++;
            }
            while (left < n && A[left] < a - k) {
                count.put(A[left], count.get(A[left]) - 1);
                left++;
            }
            int cur = Math.min(right - left, count.getOrDefault(a, 0) + numOperations);
            res = Math.max(res, cur);
        }

        // Case 2
        for (left = 0, right = 0; right < n; right++) {
            while (A[left] + k + k < A[right]) {
                left++;
            }
            res = Math.max(res, Math.min(right - left + 1, numOperations));
        }
        return res;
    }
}

package algorithms.array.medium;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/***
 * https://leetcode.com/problems/split-array-into-consecutive-subsequences/
 *
 * You are given an integer array nums that is sorted in non-decreasing order.
 *
 * Determine if it is possible to split nums into one or more subsequences such that both of the following conditions are true:
 *
 * Each subsequence is a consecutive increasing sequence (i.e. each integer is exactly one more than the previous integer).
 * All subsequences have a length of 3 or more.
 * Return true if you can split nums according to the above conditions, or false otherwise.
 *
 * A subsequence of an array is a new array that is formed from the original array
 * by deleting some (can be none) of the elements without disturbing the relative positions of the remaining elements.
 * (i.e., [1,3,5] is a subsequence of [1,2,3,4,5] while [1,3,2] is not).
 *
 * Example 1:
 * Input: nums = [1,2,3,3,4,5]
 * Output: true
 * Explanation: nums can be split into the following subsequences:
 * [1,2,3,3,4,5] --> 1, 2, 3
 * [1,2,3,3,4,5] --> 3, 4, 5
 *
 * Example 2:
 * Input: nums = [1,2,3,3,4,4,5,5]
 * Output: true
 * Explanation: nums can be split into the following subsequences:
 * [1,2,3,3,4,4,5,5] --> 1, 2, 3, 4, 5
 * [1,2,3,3,4,4,5,5] --> 3, 4, 5
 *
 * Example 3:
 * Input: nums = [1,2,3,4,4,5]
 * Output: false
 * Explanation: It is impossible to split nums into consecutive increasing subsequences of length 3 or more.
 *
 */

public class Array36_SplitArraySubSequence {

    @Test
    public void test(){
        int[] input = {1,2,3,3,4,5,7};
        System.out.println(isPossible(input));
    }

    public boolean isPossible(int[] nums) {
        Map<Integer, Integer> subsequences = new HashMap<>();
        Map<Integer, Integer> frequency = new HashMap<>();
        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }
        for (int num : nums) {
            //num already part of a valid subsequence.
            if (frequency.get(num) == 0) {
                continue;
            }
            // If a valid subsequence exists with the last element = num - 1.
            if (subsequences.getOrDefault(num - 1, 0) > 0) {
                subsequences.put(num - 1, subsequences.get(num - 1) - 1);
                subsequences.put(num, subsequences.getOrDefault(num, 0) + 1);
            } else if (frequency.getOrDefault(num + 1, 0) > 0 &&
                    frequency.getOrDefault(num + 2, 0) > 0) {
                // If we want to start a new subsequence, check if num + 1 and num + 2 exist.
                // Update the list of subsequences with the newly created subsequence
                subsequences.put(num + 2, subsequences.getOrDefault(num + 2, 0) + 1);
                frequency.put(num + 1, frequency.getOrDefault(num + 1, 0) - 1);
                frequency.put(num + 2, frequency.getOrDefault(num + 2, 0) - 1);
            } else {
                //No valid subsequence is possible with num
                return false;
            }
            frequency.put(num, frequency.get(num) - 1);
        }
        return true;
    }
}

package algorithms.array.medium;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/***
 * https://leetcode.com/problems/split-array-into-consecutive-subsequences/
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

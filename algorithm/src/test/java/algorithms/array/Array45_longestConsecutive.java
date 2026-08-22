package algorithms.array.medium;

import java.util.HashSet;
import java.util.Set;

/***
 *
 * https://leetcode.com/problems/longest-consecutive-sequence
 *
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 * You must write an algorithm that runs in O(n) time.
 *
 * Example 1:
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 *
 * Example 2:
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 * Constraints:
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */

public class Array45_longestConsecutive {
    public int longestConsecutive(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        int maxLength = 0;

        for(int n: nums){
            numSet.add(n);
        }

        for(int n: numSet){
            if(!numSet.contains(n-1)){
                int currentVal = n;
                int currentLength = 1;

                while(numSet.contains(currentVal+1)){
                    currentVal += 1;
                    currentLength += 1;
                }
                maxLength = Math.max(currentLength, maxLength);
            }
        }

        return maxLength;
    }
}

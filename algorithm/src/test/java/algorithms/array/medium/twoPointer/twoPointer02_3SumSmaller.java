package algorithms.array.medium.twoPointer;

import java.util.Arrays;

/***
 * https://leetcode.com/problems/3sum-smaller/
 * Given an array of n integers nums and an integer target, find the number of index triplets i, j, k with 0 <= i < j < k < n that satisfy the condition nums[i] + nums[j] + nums[k] < target.
 *
 * Example 1:
 * Input: nums = [-2,0,1,3], target = 2
 * Output: 2
 * Explanation: Because there are two triplets which sums are less than 2:
 * [-2,0,1]
 * [-2,0,3]
 *
 * Example 2:
 * Input: nums = [], target = 0
 * Output: 0

 * Example 3:
 * Input: nums = [0], target = 0
 * Output: 0
 *
 * Constraints:
 *
 * n == nums.length
 * 0 <= n <= 3500
 * -100 <= nums[i] <= 100
 * -100 <= target <= 100
 */

public class twoPointer02_3SumSmaller {
        int count;
        public int threeSumSmaller(int[] nums, int target) {
            count = 0;
            Arrays.sort(nums);
            int len = nums.length;

            for(int i=0; i<len-2; i++) {
                int left = i+1, right = len-1;
                while(left < right) {
                    if(nums[i] + nums[left] + nums[right] < target) {
                        count += right-left;
                        left++;
                    } else {
                        right--;
                    }
                }
            }
            return count;
        }
}

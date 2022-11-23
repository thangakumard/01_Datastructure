package algorithms.array.easy;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 *
 * https://leetcode.com/problems/largest-positive-integer-that-exists-with-its-negative/
 *
 * Given an integer array nums that does not contain any zeros, find the largest positive integer k such that -k also exists in the array.
 *
 * Return the positive integer k. If there is no such integer, return -1.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [-1,2,-3,3]
 * Output: 3
 * Explanation: 3 is the only valid k we can find in the array.
 * Example 2:
 *
 * Input: nums = [-1,10,6,7,-7,1]
 * Output: 7
 * Explanation: Both 1 and 7 have their corresponding negative values in the array. 7 has a larger value.
 * Example 3:
 *
 * Input: nums = [-10,8,6,7,-2,-3]
 * Output: -1
 * Explanation: There is no a single valid k, we return -1.
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * -1000 <= nums[i] <= 1000
 * nums[i] != 0
 */
public class Array44_LargestPositiveInteger {

    @Test
    public void sampleTest(){
        int[] nums = new int[]{-30,34,1,32,26,-9,-30,22,-49,29,48,47,38,4,43,12,-1,-8,11,-37,32,40,9,15,-34,-34,-16,-5,26,-44,-36,-13,-16,10,39,-17,-22,17,-16};
        Assertions.assertThat(findMaxK(nums)).isEqualTo(34);

        nums = new int[] {167,199,-659,-19,64,245,609,-641,846,-553,-49,-1000,-375,-936,-151,-410,314,-763,744,-255,-118,-463,288,-596,424,-885,272,-925,813,-597,-963,-791,-77,-979,383,193,420,-277,-568,381,365};
        Assertions.assertThat(findMaxK(nums)).isEqualTo(-1);
    }

    public int findMaxK(int[] nums) {
        int[] counter = new int[1001];
        Arrays.fill(counter, -9999);

        for(int i=0; i < nums.length; i++){
            if(counter[Math.abs(nums[i])] == 0) continue;
            if(counter[Math.abs(nums[i])] == nums[i]) continue;

            if(counter[Math.abs(nums[i])] == -9999)
                counter[Math.abs(nums[i])] = nums[i];
            else
                counter[Math.abs(nums[i])] += nums[i];
        }

        for(int i=1000; i >= 0; i--){
            if(counter[i] == 0)
                return i;
        }

        return -1;
    }
}

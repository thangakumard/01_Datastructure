package algorithms.array.medium.slidingWindow;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;

/***
 * https://leetcode.com/problems/subarray-sum-equals-k/description/
 *
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 * A subarray is a contiguous non-empty sequence of elements within an array.
 *
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 104
 * -1000 <= nums[i] <= 1000
 * -107 <= k <= 107
 */

public class SlidingWindow01_SubarraySumOfK {

    @Test
    public void subarraySumTest(){
        int[] input = {1,2,3,7,5};
        ArrayList<Integer> result = new ArrayList<>();
        result.add(2);result.add(4);
        Assertions.assertThat(subarraySum(input,12)).isEqualTo(result);
    }

    static ArrayList<Integer> subarraySum(int[] arr, int s)
    {
        ArrayList<Integer> result = new ArrayList<>();
        int sumSoFar = 0,  left = 0, right = 0;

        while(right < arr.length && left < arr.length){
            sumSoFar += arr[right];
            if(sumSoFar > s){
                sumSoFar = sumSoFar - arr[left];
                left ++;
            }
            if(sumSoFar == s) {
                result.add(left + 1);
                result.add(right + 1);
                return result;
            }
            right++;
        }
        return result;
    }
}

package algorithms.array.medium.slidingWindow;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

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

    public int subarraySum(int[] nums, int k) {
        int sum_so_far = 0;
        int counter = 0;
        HashMap<Integer, Integer> mapSumCounter = new HashMap<>();
        mapSumCounter.put(0, 1);
        for(int num: nums){
            sum_so_far += num;
            if(mapSumCounter.containsKey(sum_so_far - k)){
                counter += mapSumCounter.get(sum_so_far - k);
            }
            mapSumCounter.put(sum_so_far,mapSumCounter.getOrDefault(sum_so_far, 0)+1);
        }
        return counter;
    }
}

package algorithms.array.medium.subArray;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;

/***
 * https://leetcode.com/problems/subarray-sum-equals-k/
 *
 * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
 *
 * A subarray is a contiguous nzzzon-empty sequence of elements within an array.
 *
 * Example 1:
 * Input: nums = [1,1,1], k = 2
 * Output: 2
 *
 * Example 2:
 * Input: nums = [1,2,3], k = 3
 * Output: 2
 */
public class Subarray04_SubarrayCountSumOfK {

    @Test
    public void Test(){
        Assertions.assertThat(subarraySum(new int[]{1,2,3}, 3)).isEqualTo(2);
    }

    public int subarraySum(int[] nums, int k) {
        int count = 0, sum_so_far = 0;
        HashMap<Integer, Integer> mapSum = new HashMap<>();
        mapSum.put(0,1);
        for(int i: nums){
            sum_so_far += i;
            if(mapSum.containsKey(sum_so_far-k)){
                count += mapSum.get(sum_so_far-k);
            }
            mapSum.put(sum_so_far, mapSum.getOrDefault(sum_so_far, 0)+1);
        }

        return count;
    }
}

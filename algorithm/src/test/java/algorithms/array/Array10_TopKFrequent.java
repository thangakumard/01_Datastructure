package algorithms.array.medium;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/***
 * https://leetcode.com/problems/top-k-frequent-elements/description/
 *Given an integer array nums and an integer k, return the k most frequent elements. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 * Input: nums = [1,1,1,2,2,3], k = 2
 * Output: [1,2]
 *
 *  Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * k is in the range [1, the number of unique elements in the array].
 * It is guaranteed that the answer is unique.
 *
 *
 * Follow up: Your algorithm's time complexity must be better than O(n log n), where n is the array's size.
 */
public class Array10_TopKFrequent {
    @Test
    public void topKFrequentTest(){
        int[] nums = {1,1,1,2,2,3}; int k = 2;
        int[] result = {1,2};
        Assertions.assertThat(topKFrequent(nums,k)).isEqualTo(result);
    }
    public int[] topKFrequent(int[] nums, int k) {
        int[] result = new int[k];
        HashMap<Integer, Integer> mapCounter = new HashMap<>();

        for(int i: nums){
            mapCounter.put(i , mapCounter.getOrDefault(i, 0)+1);
        }

        List<Integer> lstKeys = new ArrayList<>(mapCounter.keySet());
        Collections.sort(lstKeys, (a, b) -> mapCounter.get(b) - mapCounter.get(a));

        for(int i=0; i < k; i++){
            result[i] = lstKeys.get(i);
        }

        return result;

    }
}

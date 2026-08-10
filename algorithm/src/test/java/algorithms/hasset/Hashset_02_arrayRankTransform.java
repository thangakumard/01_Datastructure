package algorithms.hasset;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/***
 * https://leetcode.com/problems/rank-transform-of-an-array/
 *
 * Given an array of integers arr, replace each element with its rank.
 * The rank represents how large the element is. The rank has the following rules:
 *
 * Rank is an integer starting from 1.
 * The larger the element, the larger the rank. If two elements are equal, their rank must be the same.
 * Rank should be as small as possible.
 *
 * Example 1:
 * Input: arr = [40,10,20,30]
 * Output: [4,1,2,3]
 * Explanation: 40 is the largest element. 10 is the smallest. 20 is the second smallest. 30 is the third smallest.
 *
 * Example 2:
 * Input: arr = [100,100,100]
 * Output: [1,1,1]
 * Explanation: Same elements share the same rank.
 *
 * Example 3:
 * Input: arr = [37,12,28,9,100,56,80,5,12]
 * Output: [5,3,4,2,8,6,7,1,3]
 *
 *
 * Constraints:
 * 0 <= arr.length <= 105
 * -109 <= arr[i] <= 109
 */
public class Hashset_02_arrayRankTransform {
    public int[] arrayRankTransform(int[] arr) {
        int n = arr.length;
        if(n == 0) return arr;

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer,Integer> mapSorted = new HashMap<>();
        int rank = 1;
        for(int i: sorted){
            if(!mapSorted.containsKey(i)){
                mapSorted.put(i, rank++);
            }
        }
        int[] result = new int[n];
        for(int i=0; i < n; i++){
            result[i] = mapSorted.get(arr[i]);
        }
        return result;
    }

    @Test
    public void testBasicRanking() {
        int[] result = arrayRankTransform(new int[]{40, 10, 20, 30});
        Assertions.assertThat(result).isEqualTo(new int[]{4, 1, 2, 3});
    }

    @Test
    public void testAllDuplicates() {
        int[] result = arrayRankTransform(new int[]{100, 100, 100});
        Assertions.assertThat(result).isEqualTo(new int[]{1, 1, 1});
    }

    @Test
    public void testMixedWithDuplicates() {
        int[] result = arrayRankTransform(new int[]{37, 12, 28, 9, 100, 56, 80, 5, 12});
        Assertions.assertThat(result).isEqualTo(new int[]{5, 3, 4, 2, 8, 6, 7, 1, 3});
    }

    @Test
    public void testEmptyArray() {
        int[] result = arrayRankTransform(new int[]{});
        Assertions.assertThat(result).isEqualTo(new int[]{});
    }

    @Test
    public void testSingleElement() {
        int[] result = arrayRankTransform(new int[]{5});
        Assertions.assertThat(result).isEqualTo(new int[]{1});
    }

    @Test
    public void testNegativeNumbers() {
        int[] result = arrayRankTransform(new int[]{-5, -1, -3});
        Assertions.assertThat(result).isEqualTo(new int[]{1, 3, 2});
    }
}

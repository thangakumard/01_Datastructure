package algorithms.string.math;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/***
 * https://leetcode.com/problems/add-to-array-form-of-integer/
 *
 * The array-form of an integer num is an array representing its digits in left to right order.
 *
 * For example, for num = 1321, the array form is [1,3,2,1].
 * Given num, the array-form of an integer, and an integer k, return the array-form of the integer num + k.
 *
 * Example 1:
 * Input: num = [1,2,0,0], k = 34
 * Output: [1,2,3,4]
 * Explanation: 1200 + 34 = 1234
 *
 *  Example 2:
 * Input: num = [2,7,4], k = 181
 * Output: [4,5,5]
 * Explanation: 274 + 181 = 455

 * Example 3:
 * Input: num = [2,1,5], k = 806
 * Output: [1,0,2,1]
 * Explanation: 215 + 806 = 1021
 *
 * Constraints:
 * 1 <= num.length <= 104
 * 0 <= num[i] <= 9
 * num does not contain any leading zeros except for the zero itself.
 * 1 <= k <= 104
 */
public class Add02_AddToArrayForm {

    @Test
    public void addToArrayFormTest(){
        int[] num = {1,2,0,0};
        int k = 34;
        List<Integer> result = new ArrayList<>();
        result.add(1);result.add(2);result.add(3);result.add(4);
        Assertions.assertThat(addToArrayForm(num,k)).isEqualTo(result);

        int[] num1 = {9,9,9,9};
        k = 99;
        result = new ArrayList<>();
        result.add(1);result.add(0);result.add(0);result.add(9);result.add(8);
        Assertions.assertThat(addToArrayForm(num1,k)).isEqualTo(result);
    }
    public List<Integer> addToArrayForm(int[] num, int k) {
        List<Integer> result = new ArrayList<>();
        int l = num.length-1, carry = 0;

        while(l >=0 || k >0){
            int a = l < 0 ? 0 : num[l];
            k = k +  a ;
            result.add(k%10);
            k = k / 10;
            l--;
        }
        Collections.reverse(result);
        return result;
    }
}

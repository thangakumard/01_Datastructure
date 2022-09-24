package algorithms.array.medium;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/***
 * https://www.geeksforgeeks.org/rearrange-given-array-place/
 * Given an array arr[] of size n where every element is in the range from 0 to n-1.
 * Rearrange the given array so that arr[i] becomes arr[arr[i]]. This should be done with O(1) extra space
 *
 * Input: arr[]  = {3, 2, 0, 1}
 * Output: arr[] = {1, 0, 3, 2}
 *
 * Input: arr[] = {4, 0, 2, 1, 3}
 * Output: arr[] = {3, 4, 2, 0, 1}
 */
public class Array43_RearrangeArray {

    @Test
    public void arrangeTest(){
        long input[] = {1,0};
        arrange(input);
        Assertions.assertThat(input).isEqualTo(new long[]{0,1});
    }
    public void arrange(long arr[])
    {
        int n = arr.length;
        // First step: Increase all values by
        // (arr[arr[i]]%n)*n
        for (int i = 0; i < n; i++)
            arr[i] += (arr[(int)arr[i]] % n) * n;

        // Second Step: Divide all values by n
        for (int i = 0; i < n; i++)
            arr[i] /= n;
    }
}

package algorithms.array.medium.comparatorInterface;

import java.util.Arrays;
import java.util.Comparator;

/***
 * https://leetcode.com/problems/largest-number/description/
 * Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
 * Since the result may be very large, so you need to return a string instead of an integer.
 *
 * Example 1:
 * Input: nums = [10,2]
 * Output: "210"
 *
 * Example 2:
 * Input: nums = [3,30,34,5,9]
 * Output: "9534330"
 *
 * Constraints:
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 109
 */
public class Comparator01_BuildLargeNumber01 {

    /** in C# ***
     * private class LargerNumberComparator: IComparer<string>
     *     {
     *         public int Compare(string a, string b){
     *             string order1 = a+b;
     *             string order2 = b+a;
     *             return order2.CompareTo(order1);
     *         }
     *     }
     */

    /*** in Java ****/
    private class LargerNumberComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            String order1 = a + b;
            String order2 = b + a;
            return order2.compareTo(order1);
        }
    }

    public String largestNumber(int[] nums) {

        // Get input integers as strings.
        String[] strNums = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strNums[i] = String.valueOf(nums[i]);
        }

        // Sort strings according to custom comparator.
        Arrays.sort(strNums, new LargerNumberComparator());

        // If, after being sorted, the largest number is `0`, the entire number
        // is zero.
        if (strNums[0].equals("0")) {
            return "0";
        }

        // Build largest number from sorted array.
        String largestNumberStr = new String();
        for (String numAsStr : strNums) {
            largestNumberStr += numAsStr;
        }

        return largestNumberStr;
    }
}

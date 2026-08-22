package algorithms.string.permutation;

/***
 * https://leetcode.com/problems/next-greater-element-iii/
 * Given a positive integer n, find the smallest integer which has exactly the same digits existing in the integer n and is greater in value than n. If no such positive integer exists, return -1.
 * Note that the returned integer should fit in 32-bit integer, if there is a valid answer but it does not fit in 32-bit integer, return -1.
 *
 * Example 1:
 * Input: n = 12
 * Output: 21
 *
 * Example 2:
 * Input: n = 21
 * Output: -1
 *
 * Constraints:
 *
 * 1 <= n <= 231 - 1
 */

/***
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
public class NextGreaterElement_03 {
    public int nextGreaterElement(int n) {
        char[] digits = Integer.toString(n).toCharArray();

        //1. Find the right most digit that can be increased
        int i = digits.length-2;
        while( i >=0 && digits[i] >= digits[i+1]){
            i--;
        }

        if(i < 0) return -1;
        //2. Find the smallest digit greater than digits[i]
        int j = digits.length-1;
        while( j >= 0 && digits[j] <= digits[i]){
            j--;
        }

        //3.swap
        char temp = digits[j];
        digits[j] = digits[i];
        digits[i] = temp;

        //4. Reverse the suffix
        int left = i+1;
        int right = digits.length-1;

        while(left < right){
            temp = digits[left];
            digits[left] = digits[right];
            digits[right] = temp;
            left++;
            right--;
        }

        //5. Check Interger overflow
        long result = Long.parseLong(new String(digits));
        return result > Integer.MAX_VALUE ? -1 : (int) result;
    }
}

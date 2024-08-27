package algorithms.string.encodeDecode;

import java.util.Stack;

/***
 * https://leetcode.com/problems/decode-string/description/
 * Given an encoded string, return its decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 * You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].
 * The test cases are generated so that the length of the output will never exceed 105.
 *
 * Example 1:
 * Input: s = "3[a]2[bc]"
 * Output: "aaabcbc"
 *
 * Example 2:
 * Input: s = "3[a2[c]]"
 * Output: "accaccacc"
 *
 * Example 3:
 * Input: s = "2[abc]3[cd]ef"
 * Output: "abcabccdcdcdef"
 *
 * Constraints:
 * 1 <= s.length <= 30
 * s consists of lowercase English letters, digits, and square brackets '[]'.
 * s is guaranteed to be a valid input.
 * All the integers in s are in the range [1, 300].
 */
public class ed01_decodeString {
    public String decodeString(String s) {
        Stack<Integer> stackCounter = new Stack<>();
        Stack<StringBuilder> stackString = new Stack<>();

        StringBuilder sbResult = new StringBuilder();
        int n = 0;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                n = n * 10 + (c - '0');
            } else if (c == '[') {
                stackCounter.push(n);
                n = 0;
                stackString.push(sbResult);
                sbResult = new StringBuilder();
            } else if (c == ']') {
                int counter = stackCounter.pop();
                StringBuilder temp = sbResult;
                sbResult = stackString.pop();
                while (counter > 0) {
                    sbResult.append(temp);
                    counter--;
                }
            } else {
                sbResult.append(c);
            }
        }

        return sbResult.toString();
    }
}

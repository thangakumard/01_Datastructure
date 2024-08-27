package algorithms.string.parenthesis;

import java.util.Stack;

/***
 * Given a string s of '(' , ')' and lowercase English characters.
 * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.
 * Formally, a parentheses string is valid if and only if:
 *
 * It is the empty string, contains only lowercase characters, or
 * It can be written as AB (A concatenated with B), where A and B are valid strings, or
 * It can be written as (A), where A is a valid string.
 *
 *
 * Example 1:
 * Input: s = "lee(t(c)o)de)"
 * Output: "lee(t(c)o)de"
 * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.

 * Example 2:
 * Input: s = "a)b(c)d"
 * Output: "ab(c)d"
 *
 * Example 3:
 * Input: s = "))(("
 * Output: ""
 * Explanation: An empty string is also valid.
 * Constraints:
 *
 * 1 <= s.length <= 105
 * s[i] is either '(' , ')', or lowercase English letter.
 */
public class Parenthesis05_minimumRemove {
    public String minRemoveToMakeValid(String s) {
        int left = 0, right = s.length()-1;

        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> open = new Stack<>();
        Stack<Integer> close = new Stack<>();

        while(left < s.length()){
            if(s.charAt(left) == '('){
                open.add(left);
            }
            if(s.charAt(left) == ')'){
                if(!open.isEmpty()){
                    open.pop();
                }
                else{
                    close.add(left);
                }
            }
            left++;
        }

        while (!open.isEmpty()) {
            sb.deleteCharAt(open.pop());
        }
        while (!close.isEmpty()) {
            sb.deleteCharAt(close.pop());
        }

        return sb.toString();
    }
}

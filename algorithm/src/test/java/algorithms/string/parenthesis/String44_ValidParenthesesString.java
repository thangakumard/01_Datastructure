package algorithms.string.parenthesis;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * https://leetcode.com/problems/valid-parenthesis-string/
 *
 * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
 *
 * The following rules define a valid string:
 *
 * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "(*)"
 * Output: true
 *
 * Example 3:
 * Input: s = "(*))"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 100
 * s[i] is '(', ')' or '*'.
 *
 */

public class String44_ValidParenthesesString {

    @Test
    public void checkValidStringTest_01(){
        String input = "(*)";
        Assertions.assertThat(checkValidString(input)).isTrue();
    }
    @Test
    public void checkValidStringTest_02(){
        String input = "(*))";
        Assertions.assertThat(checkValidString(input)).isTrue();
    }

    /****************
     * Brute Force approach starts
     * ****************/
    boolean ans = false;

    public boolean checkValidString_BruteForce(String s) {
        solve(new StringBuilder(s), 0);
        return ans;
    }

    public void solve(StringBuilder sb, int i) {
        if (i == sb.length()) {
            ans |= valid(sb);
        } else if (sb.charAt(i) == '*') {
            for (char c: "() ".toCharArray()) {
                sb.setCharAt(i, c);
                solve(sb, i+1);
                if (ans) return;
            }
            sb.setCharAt(i, '*');
        } else
            solve(sb, i + 1);
    }

    public boolean valid(StringBuilder sb) {
        int bal = 0;
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '(') bal++;
            if (c == ')') bal--;
            if (bal < 0) break;
        }
        return bal == 0;
    }
    /****************
     * Brute Force approach ends
     * ****************/
    public boolean checkValidString(String s) {
        int n =s.length();
        int minLeft=0, maxLeft=0;
        for(int i=0;i<n;i++)
        {
            if(s.charAt(i) == '*')
            {
                minLeft--;
                maxLeft++;

            }
            else
            {
                if(s.charAt(i)=='(')
                {
                    minLeft++;
                    maxLeft++;
                }
                else if(s.charAt(i)==')')
                {
                    maxLeft--;
                    minLeft--;
                }
            }
            if(maxLeft < 0) break;// Excess closing
            minLeft = Math.max(minLeft,0);
        }
        return minLeft == 0; //Checks the opening
    }
}

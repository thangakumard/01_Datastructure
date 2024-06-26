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

public class Parenthesis01_ValidParenthesesString {

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

    public boolean checkValidString(String s) {
        int minCount =0 , maxCount = 0;
        for(char c: s.toCharArray()){
            if(c == '('){
                minCount++;
                maxCount++;
            }else if(c == ')'){
                minCount--;
                maxCount--;
            }
            else if(c == '*'){
                minCount--;
                maxCount++;
            }
            if(maxCount < 0) return false;

            minCount = Math.max(minCount,0);
        }
        return minCount == 0;//Checks the opening
    }
}

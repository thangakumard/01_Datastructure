package algorithms.string.parenthesis;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Stack;

/***
 * https://leetcode.com/problems/valid-parenthesis-string/
 * Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.
 * The following rules define a valid string:
 * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".
 *
 * Example 1:
 * Input: s = "()"
 * Output: true

 * Example 2:
 * Input: s = "(*)"
 * Output: true

 * Example 3:
 * Input: s = "(*))"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 100
 * s[i] is '(', ')' or '*'.
 */
public class String30_checkValidString {
	
	@Test
	private void test() {
//		Assert.assertEquals(checkValidString("(())((())()()(*)(*()(())())())()()((()())((()))(*"), false);
        Assertions.assertThat(checkValidString_new("(*(*((**(((*)*((*)*)((*(((((*()*(**)")).isFalse();
    }
	
	public boolean checkValidString_new(String s) {
        int cmin = 0, cmax = 0; // open parentheses count in range [cmin, cmax]
        for (char c : s.toCharArray()) {
            if (c == '(') {
                cmax++;
                cmin++;
            } else if (c == ')') {
                cmax--;
                cmin--;
            } else if (c == '*') {
                cmax++; // if `*` become `(` then openCount++
                cmin--; // if `*` become `)` then openCount--
                // if `*` become `` then nothing happens
                // So openCount will be in new range [cmin-1, cmax+1]
            }
            if (cmax < 0) return false; // Currently, don't have enough open parentheses to match close parentheses-> Invalid
                                        // For example: ())(
            cmin = Math.max(cmin, 0);   // It's invalid if open parentheses count < 0 that's why cmin can't be negative
        }
        return cmin == 0; // Return true if you find `openCount == 0` in range [cmin, cmax]
    }

	public boolean checkValidString(String s) {
        Stack<Character> charStack = new Stack<Character>();
        int star_count = 0;
        for(char c: s.toCharArray()){
            if(c == '*'){
                star_count++;
            }
            else if(c == '('){
                charStack.push(c);
            }
            else if(c == ')'){
                if(!charStack.isEmpty()){
                    charStack.pop();
                }
                else if(star_count > 0){
                    star_count--;
                }
                else if(charStack.isEmpty() && star_count == 0) return false;
            }
        }
        if(!charStack.isEmpty())
            return charStack.size() <= star_count;
        else
            return true;

    }
}

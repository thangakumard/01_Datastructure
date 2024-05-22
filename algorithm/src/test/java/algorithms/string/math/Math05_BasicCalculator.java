package algorithms.string.math;

import java.util.Stack;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**********
 * https://leetcode.com/problems/basic-calculator/ 
 * Given a string s representing
 * an expression, implement a basic calculator to evaluate it.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "1 + 1" Output: 2 Example 2:
 * 
 * Input: s = " 2-1 + 2 " Output: 3 Example 3:
 * 
 * Input: s = "(1+(4+5+2)-3)+(6+8)" Output: 23
 * 
 * 
 * Constraints:
 * 
 * 1 <= s.length <= 3 * 105 s consists of digits, '+', '-', '(', ')', and ' '. s
 * represents a valid expression.
 */

public class Math05_BasicCalculator {

	@Test
	public void test() {
		calculate("11+(4+5+2)-3");
		Assertions.assertThat(calculate("(1+(4+5+2)-3)+(6+8)")).isEqualTo(23);
	}

	public int calculate(String s) {
		Stack<Integer> stack = new Stack<>();
		int result = 0;
		int tempResult = 0;
		int sign = 1;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				tempResult = 10 * tempResult + (c - '0');
			} else if (c == '+') {
				result += sign * tempResult;
				tempResult = 0;
				sign = 1;
			} else if (c == '-') {
				result += sign * tempResult;
				tempResult = 0;
				sign = -1;
			} else if (c == '(') {
				// we push the result first, then sign;
				stack.push(result);
				stack.push(sign);
				// reset the sign and result for the value in the parenthesis
				sign = 1;
				result = 0;
			} else if (c == ')') {
				result += sign * tempResult;
				tempResult = 0;
				result *= stack.pop(); // stack.pop() is the sign before the parenthesis
				result += stack.pop(); // stack.pop() now is the result calculated before the parenthesis

			}
		}
		if (tempResult != 0)
			result += sign * tempResult;
		return result;
	}
}

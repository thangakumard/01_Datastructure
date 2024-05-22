package algorithms.string.math;

import java.util.Stack;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

/****
 * 
 * 
 * https://leetcode.com/problems/basic-calculator-ii/
 * 
 * Given a string s which represents an expression, evaluate this expression and
 * return its value.
 * 
 * The integer division should truncate toward zero.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: s = "3+2*2" Output: 7 Example 2:
 * 
 * Input: s = " 3/2 " Output: 1 Example 3:
 * 
 * Input: s = " 3+5 / 2 " Output: 5
 * 
 * 
 * Constraints:
 * 
 * 1 <= s.length <= 3 * 105 s consists of integers and operators ('+', '-', '*',
 * '/') separated by some number of spaces. s represents a valid expression. All
 * the integers in the expression are non-negative integers in the range [0, 231
 * - 1]. The answer is guaranteed to fit in a 32-bit integer.
 *
 */

public class Math06_BasicCalculator_II {

	@Test
	private void test() {
		Assert.assertEquals(7, calculate("3+2*2"));
//		Assert.assertEquals(1, calculate("3/2"));
//		Assert.assertEquals(5, calculate("  3+5 / 2"));
	}
	/*****
	 For + and - sign push the value into the stack
	 For * and / sign push pop the top value from the stack and perform * or / with currentNumber
	 
	 Operation always will have previous operation sign, 
	 ex: 3+2*2 => when reaches * operation will be the previous sign +. 
	 Once it is performed operation will assign as *
	 */

	@Test
	public void calculateTest(){
		//Assertions.assertThat(calculate("5*5")).isEqualTo(25);
		Assertions.assertThat(calculate("3+2*2")).isEqualTo(7);
	}

	public int calculate(String s) {
		if (s == null || s.isEmpty())
			return 0;
		int len = s.length();
		Stack<Integer> stack = new Stack<>();
		int tempResult = 0;
		char operation = '+';
		for (int i = 0; i < len; i++) {
			char currentChar = s.charAt(i);
			if (Character.isDigit(currentChar)) {
				tempResult = (tempResult * 10) + (currentChar - '0');
			}
			if (!Character.isDigit(currentChar) && !Character.isWhitespace(currentChar) || i == len - 1) {
				if (operation == '-') {
					stack.push(-tempResult);
				} else if (operation == '+') {
					stack.push(tempResult);
				} else if (operation == '*') {
					stack.push(stack.pop() * tempResult);
				} else if (operation == '/') {
					stack.push(stack.pop() / tempResult);
				}
				operation = currentChar;
				tempResult = 0;
			}
		}
		int result = 0;
		while (!stack.isEmpty()) {
			result += stack.pop();
		}
		return result;
	}

}

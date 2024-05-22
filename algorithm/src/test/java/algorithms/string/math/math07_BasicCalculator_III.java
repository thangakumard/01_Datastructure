package algorithms.string.math;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Stack;

/***
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string contains only non-negative integers, '+', '-', '*', '/' operators, and open '(' and closing parentheses ')'. The integer division should truncate toward zero.
 * You may assume that the given expression is always valid. All intermediate results will be in the range of [-231, 231 - 1].
 * Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().
 *
 * Example 1:
 * Input: s = "1+1"
 * Output: 2
 *
 * Example 2:
 * Input: s = "6-4/2"
 * Output: 4
 *
 * Example 3:
 * Input: s = "2*(5+5*2)/3+(6/2+8)"
 * Output: 21
 * Constraints:
 * 1 <= s <= 104
 * s consists of digits, '+', '-', '*', '/', '(', and ')'.
 * s is a valid expression.
 */
public class math07_BasicCalculator_III {

    @Test
    public void test(){
        Assertions.assertThat(calculate("(5-(10/2)*5)")).isEqualTo(-20);
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
                }else if (s.charAt(i) == '(') {
                    int j = i + 1; int braces = 1;
                    for (; j < len; j++) {
                        if (s.charAt(j) == '(') ++braces;
                        if (s.charAt(j) == ')') --braces;
                        if (braces == 0) break;
                    }
                    tempResult = calculate(s.substring(i + 1, j));
                    i = j;
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

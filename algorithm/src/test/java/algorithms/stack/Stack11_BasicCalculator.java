package algorithms.stack;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.LinkedList;

/***
 * https://leetcode.com/problems/basic-calculator
 Given a string s representing a valid expression, implement a basic calculator to evaluate it, and return the result of the evaluation.

 Note: You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as eval().



 Example 1:

 Input: s = "1 + 1"
 Output: 2
 Example 2:

 Input: s = " 2-1 + 2 "
 Output: 3
 Example 3:

 Input: s = "(1+(4+5+2)-3)+(6+8)"
 Output: 23


 Constraints:

 1 <= s.length <= 3 * 105
 s consists of digits, '+', '-', '(', ')', and ' '.
 s represents a valid expression.
 '+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
 '-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
 There will be no two consecutive operators in the input.
 Every number and running calculation will fit in a signed 32-bit integer.

/**
 Time: O(n) — single pass
 Space: O(d) where d = nesting depth (stack only stores parenthesis context)
 */
public class Stack11_BasicCalculator {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for(String token: tokens){
            if(token.equals("+") || token.equals("-") ||
                    token.equals("*") || token.equals("/")){

                int b = stack.pop();
                int a = stack.pop();
                int result = 0;
                switch (token) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    case "/":
                        result = a / b;
                        break;
                    default:
                        break;
                }
                stack.push(result);

            }else{
                stack.push(Integer.parseInt(token));
            }
        }

        return stack.pop();
    }
}
package algorithms.stack;

import java.util.ArrayDeque;

import org.testng.Assert;
import org.testng.annotations.Test;



/****
 * https://leetcode.com/problems/valid-parentheses/description/
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

	An input string is valid if:
	
	Open brackets must be closed by the same type of brackets.
	Open brackets must be closed in the correct order.
	Note that an empty string is also considered valid.
	
	Example 1:
	
	Input: "()"
	Output: true
	Example 2:
	
	Input: "()[]{}"
	Output: true
	Example 3:
	
	Input: "(]"
	Output: false
	Example 4:
	
	Input: "([)]"
	Output: false
	Example 5:
	
	Input: "{[]}"
	Output: true
 *
 */

public class Stack01_ValidateParentheses {

	@Test
	public void TestParentheses(){
		Assert.assertTrue(hasValidParentheses("{{()}}"));
	}
	
	private boolean hasValidParentheses(String s){
		
		ArrayDeque<Character> stack = new ArrayDeque<Character>();
		
		for(char c : s.toCharArray()){
			if(c == '('){
				stack.addFirst(')');
			}
			else if(c == '{'){
				stack.addFirst('}');
			}
			else if(c == '['){
				stack.addFirst(']');
			}
			else if(stack.isEmpty() || stack.removeFirst() != c){
				return false;
			}
		}		
		return stack.isEmpty();
	}
}

package algorithms.string;

import java.util.HashSet;
import java.util.Stack;

import org.testng.Assert;
import org.testng.annotations.Test;

/*****
 * 
https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
Given a string s of '(' , ')' and lowercase English characters. 

Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.

Formally, a parentheses string is valid if and only if:

It is the empty string, contains only lowercase characters, or
It can be written as AB (A concatenated with B), where A and B are valid strings, or
It can be written as (A), where A is a valid string.
 

Example 1:

Input: s = "lee(t(c)o)de)"
Output: "lee(t(c)o)de"
Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
Example 2:

Input: s = "a)b(c)d"
Output: "ab(c)d"
Example 3:


Input: s = "))(("
Output: ""
Explanation: An empty string is also valid.
Example 4:

Input: s = "(a(b(c)d)"
Output: "a(b(c)d)"
 

Constraints:

1 <= s.length <= 10^5
s[i] is one of  '(' , ')' and lowercase English letters.
 *
 */

public class String27_minRemoveToMakeValid {
	
	@Test
	private void test() {
		Assert.assertEquals("lee(t(c)o)de", minRemoveToMakeValid("lee(t(c)o)de)")); 
		Assert.assertEquals("ab(c)d", minRemoveToMakeValid("a)b(c)d")); 
		Assert.assertEquals("", minRemoveToMakeValid("))(("));
		Assert.assertEquals("a(b(c)d)", minRemoveToMakeValid("(a(b(c)d)"));
	}
	
	
	public String minRemoveToMakeValid(String s) {
        Stack<Integer> stackChar = new Stack<Integer>();
        HashSet<Integer> indexToRemove = new HashSet<Integer>();
        
        for(int i=0; i < s.length(); i++){
            if(s.charAt(i) == '(') stackChar.push(i);
            else if(s.charAt(i) == ')') {
                if(stackChar.isEmpty()){
                    indexToRemove.add(i);
                }else{
                    stackChar.pop();
                }
            }
        }
        
        while(!stackChar.isEmpty()){
            indexToRemove.add(stackChar.pop());
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < s.length(); i++){
            if(!indexToRemove.contains(i)){
                sb.append(s.charAt(i));
            }
        }
        
        return sb.toString();
    }
}

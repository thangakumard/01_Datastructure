package algorithms.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/***
 * https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
 */
public class Stack14_ReverseParentheses {
    public String reverseParentheses(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        Deque<Character> tempStack = new ArrayDeque<>();
        for(int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            if(c == ')'){
                //reverses the chat by placing in temp stack
                while(!stack.isEmpty() && stack.peek() != '('){
                    tempStack.push(stack.pop());
                }
                //pop '(' from the main stack
                if(!stack.isEmpty()){
                    stack.pop();
                }
                //put back the reversed string to the main stack
                while(!tempStack.isEmpty()){
                    stack.push(tempStack.pollLast());
                }
            }else{
                stack.push(c);
            }
        }
        StringBuilder result = new StringBuilder();
        while(!stack.isEmpty()){
            result.append(stack.pollLast());
        }
        return result.toString();
    }
}

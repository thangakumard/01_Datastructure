package algorithms.string;

import java.util.*;
/****
 * 
Given a string s, remove duplicate letters so that every letter appears once and only once. 
You must make sure your result is the smallest in lexicographical order among all possible results.

Note: This question is the same as 1081: https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/

 

Example 1:

Input: s = "bcabc"
Output: "abc"
Example 2:

Input: s = "cbacdcbc"
Output: "acdb"
 

Constraints:

1 <= s.length <= 104
s consists of lowercase English letters.
 *
 */

public class String38_RemoveDuplicateLetters {

	public String removeDuplicateLetters(String s) {
        Stack<Character> stack = new Stack<Character>();
        HashSet<Character> seen = new HashSet<Character>();
        
        HashMap<Character, Integer> last_occurrence = new HashMap<Character, Integer>();
        
        for(int i=0; i < s.length(); i++){
            last_occurrence.put(s.charAt(i), i);
        }
        
        for(int i=0; i < s.length(); i++){
            if(!seen.contains(s.charAt(i))){
              while(!stack.isEmpty() && stack.peek() > s.charAt(i) && last_occurrence.get(stack.peek()) > i){
                  seen.remove(stack.pop());
              }
                seen.add(s.charAt(i));
                stack.push(s.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder(stack.size());
        for(Character c: stack){
            sb.append(c.charValue());
        }
        
        return sb.toString();
    }
}

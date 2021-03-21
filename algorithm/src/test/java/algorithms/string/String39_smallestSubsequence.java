package algorithms.string;

import java.util.*;
/***
Return the lexicographically smallest subsequence of s that contains all the distinct characters of s exactly once.

Note: This question is the same as 316: https://leetcode.com/problems/remove-duplicate-letters/

 

Example 1:

Input: s = "bcabc"
Output: "abc"
Example 2:

Input: s = "cbacdcbc"
Output: "acdb"
 

Constraints:

1 <= s.length <= 1000
s consists of lowercase English letters.
 */
public class String39_smallestSubsequence {

	public String smallestSubsequence(String s) {
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

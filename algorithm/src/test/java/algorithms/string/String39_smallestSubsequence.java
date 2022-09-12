package algorithms.string;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
/***
Return lexicographically the smallest subsequence of s that contains all the distinct characters of s exactly once.
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
    @Test
    public void smallestSubsequenceTest(){
        String input = "cbacdcbc";
        Assertions.assertThat(smallestSubsequence(input)).isEqualTo("acdb");
    }

	public String smallestSubsequence(String s) {
        Stack<Character> stack = new Stack<>();
        HashSet<Character> seen = new HashSet<>();
        HashMap<Character, Integer> last_occurrence = new HashMap<>();
        
        for(int i=0; i < s.length(); i++){
            last_occurrence.put(s.charAt(i), i);
        }
        
        for(int i=0; i < s.length(); i++){
            if(!seen.contains(s.charAt(i))){
                /*IMPORTANT to use While loop*/
              while(!stack.isEmpty() && stack.peek() > s.charAt(i) && last_occurrence.get(stack.peek()) > i){
                  seen.remove(stack.pop()); /* IMPORTANT TO REMOVE the char in Stack from seen. Not the s.CharAt(i)*/
              }
                seen.add(s.charAt(i));
                stack.push(s.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder(stack.size());
        while(!stack.isEmpty()){
            sb.insert(0,stack.pop());
        }
        
        return sb.toString();
    }
}

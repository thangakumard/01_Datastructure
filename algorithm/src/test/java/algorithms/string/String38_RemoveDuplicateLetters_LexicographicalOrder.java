package algorithms.string;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
/****
 *
 * https://leetcode.com/problems/remove-duplicate-letters/
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

public class String38_RemoveDuplicateLetters_LexicographicalOrder {
    @Test
    public void removeDuplicateLettersTest(){
        Assertions.assertThat(removeDuplicateLetters("fedcbabcdef")).isEqualTo("abcdef");//**in the  smallest in lexicographical order
        Assertions.assertThat(removeDuplicateLetters("baabc")).isEqualTo("abc");
        Assertions.assertThat(removeDuplicateLetters("welovejava")).isEqualTo("welojav");
    }
	public String removeDuplicateLetters(String s) {
        Stack<Character> stack = new Stack<>();
        HashSet<Character> seen = new HashSet<>();
        HashMap<Character, Integer> last_occurrence = new HashMap<>();
        
        for(int i=0; i < s.length(); i++){
            last_occurrence.put(s.charAt(i), i);
        }
        
        for(int i=0; i < s.length(); i++){
            if(!seen.contains(s.charAt(i))){
                //If not seen already check we can stack this char on top of already
                //existing char in the stock. If we need to remove existing char in stack
                //remove those
              while(!stack.isEmpty() && stack.peek() > s.charAt(i)
                      && last_occurrence.get(stack.peek()) > i){
                  // If stack.peek() lexicographically larger than s.charAt(i)
                  //AND stack.peek() is not the last occurrence, remove from the stack and the seen list
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

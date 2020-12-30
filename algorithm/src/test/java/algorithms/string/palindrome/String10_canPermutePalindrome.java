package algorithms.string.palindrome;

import java.util.*;

public class String10_canPermutePalindrome {

	/*https://leetcode.com/problems/palindrome-permutation/
	 * Given a string, determine if a permutation of the string could form a palindrome.

	Example 1:
	
	Input: "code"
	Output: false
	Example 2:
	
	Input: "aab"
	Output: true
	Example 3:
	
	Input: "carerac"
	Output: true


	 * Time complexity O(n)
	 * Space complexity O(1)
	 */
	 public boolean canPermutePalindrome(String s) {
	        Set<Character> set_Input = new HashSet<Character>();
	        for(int i=0; i<s.length(); i++){
	            if(set_Input.contains(s.charAt(i))){
	                set_Input.remove(s.charAt(i));
	            }else{
	                set_Input.add(s.charAt(i));
	            }
	        }
	        if(set_Input.size() < 2)
	            return true;
	        return false;
	    }
}

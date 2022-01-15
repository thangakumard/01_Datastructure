package algorithms.string;

import java.util.*;

/*
 * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
 * 
 * Given a string s , find the length of the longest substring t  that contains at most 2 distinct characters.

	Example 1:
	
	Input: "eceba"
	Output: 3
	Explanation: t is "ece" which its length is 3.
	Example 2:
	
	Input: "ccaabbb"
	Output: 5
	Explanation: t is "aabbb" which its length is 5.
 * 
 */
public class String03_LengthOfLongestSubstringTwoDistinctChar {

	/*
	 * Test Cases
	 * a
	 * b
	 * aa
	 * ab
	 * aba
	 * abc
	 * abcdefab
	 * 
	 */
/******
 * 
 * @param s
 * @return
 */
public int lengthOfLongestSubstringTwoDistinct(String s) {
        
        if(s.length() < 3) return s.length();
        
        HashMap<Character, Integer> map = new HashMap<Character,Integer>();
        int left=0, right = 0, max = 0, index_delete = -1;
        
       while(right < s.length()){
           map.put(s.charAt(right), right);
           right++;
           
           if(map.size() == 3){
               index_delete = Collections.min(map.values());
               map.remove(s.charAt(index_delete));
               left = index_delete + 1;
           }
           
           max = Math.max( max, right - left);
       }
        
        return max;
        
    }
}

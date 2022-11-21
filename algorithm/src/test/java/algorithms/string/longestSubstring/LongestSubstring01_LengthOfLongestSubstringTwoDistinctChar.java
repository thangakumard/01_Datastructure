package algorithms.string.longestSubstring;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

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
public class LongestSubstring01_LengthOfLongestSubstringTwoDistinctChar {

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
 * @param
 * @return
 */

@Test
public void lengthOfLongestSubstringTwoDistinct_test1(){
    String input = "a";
    Assertions.assertThat(lengthOfLongestSubstringTwoDistinct(input)).isEqualTo(1);
}

@Test
public void lengthOfLongestSubstringTwoDistinct_test2(){
    String input = "ab";
    Assertions.assertThat(lengthOfLongestSubstringTwoDistinct(input)).isEqualTo(2);
}

@Test
public void lengthOfLongestSubstringTwoDistinct_test3(){
    String input = "ababbac";
    Assertions.assertThat(lengthOfLongestSubstringTwoDistinct(input)).isEqualTo(6);
}

@Test
public void lengthOfLongestSubstringTwoDistinct_test4(){
    String input = "abcdef";
    Assertions.assertThat(lengthOfLongestSubstringTwoDistinct(input)).isEqualTo(2);
}

public int lengthOfLongestSubstringTwoDistinct(String s) {
        
        if(s.length() < 3) return s.length();
        //Keep the character as Key and it's index
        HashMap<Character, Integer> rightmostPositionCharMap = new HashMap<>();
        int left=0, right = 0, max = 0, index_delete = -1;
        
       while(right < s.length()){
           rightmostPositionCharMap.put(s.charAt(right), right);
           right++;

           if(rightmostPositionCharMap.size() == 3){
               index_delete = Collections.min(rightmostPositionCharMap.values());
               rightmostPositionCharMap.remove(s.charAt(index_delete));
               left = index_delete + 1;
           }
           
           max = Math.max( max, right - left);
       }
        
        return max;
        
    }
}

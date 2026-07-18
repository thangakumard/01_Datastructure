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
    Assertions.assertThat(lengthOfLongestSubstringKDistinct_slidingwindow1(input)).isEqualTo(1);
}

@Test
public void lengthOfLongestSubstringTwoDistinct_test2(){
    String input = "ab";
    Assertions.assertThat(lengthOfLongestSubstringKDistinct_slidingwindow1(input)).isEqualTo(2);
}

@Test
public void lengthOfLongestSubstringTwoDistinct_test3(){
    String input = "ababbac";
    Assertions.assertThat(lengthOfLongestSubstringKDistinct_slidingwindow1(input)).isEqualTo(6);
}

@Test
public void lengthOfLongestSubstringTwoDistinct_test4(){
    String input = "abcdef";
    Assertions.assertThat(lengthOfLongestSubstringKDistinct_slidingwindow1(input)).isEqualTo(2);
}

    /***
     * Time : O(N)
     * Space: O(1) (Hashmap will hold at the max 3 distinct char O(3))
     *
     *  int[26] for Letters 'a' - 'z' or 'A' - 'Z'
     * 	int[128] for ASCII
     *  int[256] for Extended ASCII  ****** [IMPORTANT] *****
     */
    public int lengthOfLongestSubstringKDistinct_slidingwindow1(String s) {
        int[] counter = new int[256];

        int left =0, right =0, maxLength = 0, distinctChar = 0;

        while(right < s.length()){
            if(counter[s.charAt(right)] == 0){
                distinctChar++;
            }
            counter[s.charAt(right)]++;
            right++;

            while(distinctChar > 2){
                if(counter[s.charAt(left)] == 1)
                {
                    distinctChar--;
                }
                counter[s.charAt(left)]--;
                left++;
            }
            maxLength = Math.max(maxLength, right - left);
        }

        return maxLength;
    }
    public int lengthOfLongestSubstringTwoDistinct_slidingwindow2(String s) {

        if (s == null || s.length() == 0) return 0;

        Map<Character, Integer> charCount = new HashMap<>();
        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);

            // Shrink window while we have more than 2 distinct characters
            while (charCount.size() > 2) {
                char leftChar = s.charAt(left);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
        
    }
}

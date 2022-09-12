package algorithms.string.longestSubstring;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class LongestSubstring04_LongestSubstringWithSameLettersReplacement {

	/*
	 * https://www.youtube.com/watch?v=00FmUN1pkGE
	 * https://leetcode.com/problems/longest-repeating-character-replacement/
	 * 
	 * Given a string s that consists of only uppercase English letters, you can perform at most k operations on that string.

	In one operation, you can choose any character of the string and change it to any other uppercase English character.
	
	Find the length of the longest sub-string containing all repeating letters you can get after performing the above operations.
	
	Note:
	Both the string's length and k will not exceed 104.
	
	Example 1:
	
	Input:
	s = "ABAB", k = 2
	
	Output:
	4
	
	Explanation:
	Replace the two 'A's with two 'B's or vice versa.
	 
	
	Example 2:
	
	Input:
	s = "AABABBA", k = 1
	
	Output:
	4
	
	Explanation:
	Replace the one 'A' in the middle with 'B' and form "AABBBBA".
	The substring "BBBB" has the longest repeating letters, which is 4.

	 */

	@Test
	public  void test() {
		Assertions.assertThat(this.findLength("AABCCBB", 2)).isEqualTo(5);
		Assertions.assertThat(this.findLength("ABBCB", 1)).isEqualTo(4);
		Assertions.assertThat(this.findLength("ABCCDE", 1)).isEqualTo(3);
	}

	/*
		Using sliding window approach to solve the problem
	 */
	 public int findLength(String str, int k) {
		 int[] char_input = new int[128];
	    int left = 0;
	    int maxLengthSubstring = 0;
	    int maxCharFrequency = 0;

	    for(int right =0; right < str.length(); right++){
	      char_input[str.charAt(right) - 'A']++;
	      int char_count = char_input[str.charAt(right)-'A'];
	      maxCharFrequency = Math.max(maxCharFrequency, char_count);

	      //When this condition breaks, remove a char from the sliding window start and
		  //increment the window start index
	      while(right - left - maxCharFrequency + 1 > k){
	        char_input[str.charAt(left)-'A']--;
	        left++;
	      }
			maxLengthSubstring = Math.max(maxLengthSubstring, right-left + 1);
	    }

	    return maxLengthSubstring;
	  }


}

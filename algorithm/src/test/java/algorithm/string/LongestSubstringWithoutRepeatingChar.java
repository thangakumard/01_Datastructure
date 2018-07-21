package algorithm.string;

import org.junit.Assert;
import org.testng.annotations.Test;

/*****
 * 
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/description/
 	
 	Given a string, find the length of the longest substring without repeating characters.

	Examples:
	
	Given "abcabcbb", the answer is "abc", which the length is 3.
	
	Given "bbbbb", the answer is "b", with the length of 1.
	
	Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

 */

public class LongestSubstringWithoutRepeatingChar {

	@Test
	public void getSubstringLength(){
		Assert.assertEquals(4, lengthOfLongestSubstring("abcafbb"));
	}
	
	public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
       int[] index = new int[128]; // current index of character
       // try to extend the range [i, j]
       for (int j = 0, i = 0; j < n; j++) {
           i = Math.max(index[s.charAt(j)], i);
           ans = Math.max(ans, j - i + 1);
           index[s.charAt(j)] = j + 1;
       }
       return ans;
   }

}



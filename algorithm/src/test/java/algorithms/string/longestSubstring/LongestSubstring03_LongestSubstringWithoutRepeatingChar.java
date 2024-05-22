package algorithms.string.longestSubstring;

import java.util.HashSet;

import org.assertj.core.api.Assertions;
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

public class LongestSubstring03_LongestSubstringWithoutRepeatingChar {

	@Test
	public void getSubstringLength(){
		//Assert.assertEquals(4, lengthOfLongestSubstring_01("abccabd"));
		//Assert.assertEquals(4, lengthOfLongestSubstring_01("abcabcdbbef"));
		Assertions.assertThat(lengthOfLongestSubstring_slidingWindow1("abccbafd")).isEqualTo(5);
		Assertions.assertThat(lengthOfLongestSubstring_slidingWindow2("abccbafd")).isEqualTo(5);
	}

	/**
	 * 		int[26] for Letters 'a' - 'z' or 'A' - 'Z'
	 * 		int[128] for ASCII
	 * 		int[256] for Extended ASCII  ****** [IMPORTANT] *****
	 * @param s
	 * @return
	 */
	public int lengthOfLongestSubstring_slidingWindow1(String s) {
		int[] counter = new int[256];
		int left =0, right =0, maxLength =0;

		while(right < s.length()){
			if(counter[s.charAt(right)] == 0){
				counter[s.charAt(right)]++;
				right++;
			}else{
				counter[s.charAt(left)]--;
				left++;
			}
			maxLength = Math.max(maxLength, right -left);
		}

		return maxLength;
	}
	
	//from https://www.youtube.com/watch?v=3IETreEybaA&t=69s
	public int lengthOfLongestSubstring_slidingWindow2(String s) {
		
		int left = 0, right = 0, max = 0;
		HashSet<Character> uniqueCharSubstringSet = new HashSet<>();
		while(right < s.length()){
		
			if(!uniqueCharSubstringSet.contains(s.charAt(right))) {
				uniqueCharSubstringSet.add(s.charAt(right));
				right++;
				max = Math.max(uniqueCharSubstringSet.size(), max);
			}else {
				uniqueCharSubstringSet.remove(s.charAt(left));
				left++;
			}	
		}
		
		return max;
	}
	
	public int lengthOfLongestSubstring_02(String s) {
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



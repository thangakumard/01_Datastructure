package algorithms.string;

import java.util.HashSet;

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
		Assert.assertEquals(4, lengthOfLongestSubstring_01("abcafbb"));
		Assert.assertEquals(4, lengthOfLongestSubstring_01("abcabcdbbef"));
		//Assert.assertEquals(4, lengthOfLongestSubstring_02("abcafbb"));
	}
	
	//from https://www.youtube.com/watch?v=3IETreEybaA&t=69s
	public int lengthOfLongestSubstring_01(String s) {
		
		int a_pointer = 0, b_pointer = 0, max = 0;
		HashSet<Character> cset = new HashSet<Character>();
		while(b_pointer < s.length()){
		
			if(!cset.contains(s.charAt(b_pointer))) {
				cset.add(s.charAt(b_pointer));
				b_pointer++;
				max = Math.max(cset.size(), max);
			}else {
				cset.remove(s.charAt(a_pointer));
				a_pointer++;
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



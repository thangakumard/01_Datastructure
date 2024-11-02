package algorithms.string.longestSubstring;

import java.util.Collections;
import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*****
 * 
 * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
 * Given a string s and an integer k, 
 * return the length of the longest substring of s that contains at most k distinct characters.

	Example 1:
	
	Input: s = "eceba", k = 2
	Output: 3
	Explanation: The substring is "ece" with length 3.
	Example 2:
	
	Input: s = "aa", k = 1
	Output: 2
	Explanation: The substring is "aa" with length 2.
	 
	
	Constraints:
	
	1 <= s.length <= 5 * 104
	0 <= k <= 50
 *
 */

public class LongestSubstring02_LengthOfLongestSubstringKDistinct {
	
	@Test
	public void test() {
	    System.out.println("Length of the longest substring: " + this.lengthOfLongestSubstringKDistinct_slidingwindow2("araaci", 2));
	    System.out.println("Length of the longest substring: " + this.lengthOfLongestSubstringKDistinct_slidingwindow2("araaci", 1));
	    System.out.println("Length of the longest substring: " + this.lengthOfLongestSubstringKDistinct_slidingwindow2("cbbebi", 3));

		Assertions.assertThat(lengthOfLongestSubstringKDistinct_slidingwindow1("abaccc",2)).isEqualTo(4);

	  }

	/**
	 * Time: O(N)
	 * Space: O(k)
	 * We need to record the occurrence of each distinct character in the valid window.
	 * During the iteration,there might be at most O(k+1) unique characters in the window,
	 * which takes O(k) space.
	 *
	 *  int[26] for Letters 'a' - 'z' or 'A' - 'Z'
	 * 	int[128] for ASCII
	 *  int[256] for Extended ASCII  ****** [IMPORTANT] *****
	 */
	public int lengthOfLongestSubstringKDistinct_slidingwindow1(String s, int k) {
		int[] counter = new int[256];

		int left =0, right =0, maxLength = 0, distinctChar = 0;

		while(right < s.length()){
			if(counter[s.charAt(right)] == 0){
				distinctChar++;
			}
			counter[s.charAt(right)]++;
			right++;

			while(distinctChar > k){
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
	public int lengthOfLongestSubstringKDistinct_slidingwindow2(String s, int k) {
        char[] input = s.toCharArray();
        
        HashMap<Character, Integer> rightmostPositionCharMap = new HashMap<>();
        int max_length = 0, left =0, right = 0;
        
        while(right < input.length){
			rightmostPositionCharMap.put(input[right], right);
        	right++;

            if(rightmostPositionCharMap.size() > k){
                int index_to_delete = Collections.min(rightmostPositionCharMap.values());  /******** Collections.min *******/
				rightmostPositionCharMap.remove(input[index_to_delete]);
                left = index_to_delete + 1;  /******** IMPORTANT *******/
            }
            max_length = Math.max(max_length, right-left);
        }
        return max_length;
    }



}

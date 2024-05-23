package algorithms.string.palindrome.permutation;

import java.util.*;

public class permutation01_canPermutePalindrome {

	/*
	 * https://leetcode.com/problems/palindrome-permutation/ Given a string,
	 * determine if a permutation of the string could form a palindrome.
	 * 
	 * Example 1:
	 * Input: "code" Output: false 
	 * 
	 * Example 2:
	 * Input: "aab" Output: true 
	 * 
	 * Example 3:
	 * Input: "carerac" Output: true
	 * 
	 * 
	 * Time complexity O(n) Space complexity O(1)
	 */

	public boolean canPermutePalindrome_counter(String s) {

		int[] counter = new int[26];
		int oddChar = 0;

		for(int i=0; i < s.length(); i++){
			counter[s.charAt(i) - 'a']++;
		}
		for(int i=0; i < 26; i++)
		{
			if(counter[i] % 2 != 0)
			{
				oddChar++;
				if(oddChar > 1) return false;
			}
		}
		return true;
	}

	public boolean canPermutePalindrome_bitManipulation(String s) {
		int bitMask = 0;
		for (int i = 0; i < s.length(); i++) {
			int step = s.charAt(i) - 'a';
			bitMask ^= (1 << step);
		}
		return ((bitMask) & (bitMask - 1)) == 0;
	}
	public boolean canPermutePalindrome(String s) {
		Set<Character> set_Input = new HashSet<Character>();
		for (int i = 0; i < s.length(); i++) {
			if (set_Input.contains(s.charAt(i))) {
				set_Input.remove(s.charAt(i));
			} else {
				set_Input.add(s.charAt(i));
			}
		}
		if (set_Input.size() < 2)
			return true;
		return false;
	}


}

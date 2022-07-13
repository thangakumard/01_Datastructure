package algorithms.string.palindrome;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/longest-palindromic-substring/
 * 
 Given a string s, return the longest palindromic substring in s.

Example 1:
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:
Input: s = "cbbd"
Output: "bb"
*
***/
public class String05_Palindrome_Longest_Substring {

	@Test
	public void Test() {
		// System.out.println(longestPalindrome("abacdfgdcaba"));
		System.out.println(longestPalindrome("racecarerrrj"));
		// System.out.println(longestPalindrome("racecar"));
		// System.out.println(longestPalindrome("bab2cbabcz"));
	}

	public String longestPalindrome(String s) {
		
		if (s.isEmpty() && s.length() < 1) {
			return "";
		}

		String longest = s.substring(0, 1);
		for (int i = 0; i < s.length(); i++) {
			String tmp = expandFromMiddle(s, i, i); //Covers odd length of Palindrome
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}

			tmp = expandFromMiddle(s, i, i + 1); //Covers even length of Palindrome
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
		}
		return longest;
	}

	// Given a center, either one letter or two letter,
	// Find longest palindrome
	public String expandFromMiddle(String s, int begin, int end) {
		while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
			begin--;
			end++;
		}
		return s.substring(begin + 1, end);
	}
}

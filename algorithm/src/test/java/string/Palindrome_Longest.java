package string;
import org.testng.annotations.Test;
/****
 * 
 * https://articles.leetcode.com/longest-palindromic-substring-part-i/
 *
 */
public class Palindrome_Longest {
	
	@Test
	public void Test(){
		//System.out.println(longestPalindrome("abacdfgdcaba"));
		//System.out.println(longestPalindrome("babcbabcbaccba"));
		System.out.println(longestPalindrome("aaaaa"));
		//System.out.println(longestPalindrome("bab2cbabcz"));
	}

	public String longestPalindrome(String s) {
		if (s.isEmpty()) {
			return null;
		}
	 
		if (s.length() == 1) {
			return s;
		}
	 
		String longest = s.substring(0, 1);
		for (int i = 0; i < s.length(); i++) {
			// get longest palindrome with center of i
			String tmp = helper(s, i, i);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
	 
			// get longest palindrome with center of i, i+1
			tmp = helper(s, i, i + 1);
			if (tmp.length() > longest.length()) {
				longest = tmp;
			}
		}
	 
		return longest;
	}
	 
	// Given a center, either one letter or two letter, 
	// Find longest palindrome
	public String helper(String s, int begin, int end) {
		while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
			begin--;
			end++;
		}
		System.out.println(s.substring(begin+1, end));
		return s.substring(begin + 1, end);
	}
}

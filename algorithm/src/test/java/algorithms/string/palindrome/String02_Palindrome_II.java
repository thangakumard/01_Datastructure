package algorithms.string.palindrome;

import org.testng.annotations.Test;

/********
 * 
 * https://leetcode.com/problems/valid-palindrome-ii/description/
 * 
 * https://www.youtube.com/watch?v=L_74qbyPHXE&t=138s
 * 
 * Given a non-empty string s, you may delete at most one character. Judge
 * whether you can make it a palindrome.
 * 
 * Example 1: Input: "aba" Output: True Example 2: Input: "abca" Output: True
 * Explanation: You could delete the character 'c'. Note: The string will only
 * contain lowercase characters a-z. The maximum length of the string is 50000.
 */
public class String02_Palindrome_II {

	@Test
	public void Test() {
		String s = "abcabca";
		System.out.println(isPalindrome(s));
	}
	
	private boolean isPalindrome(String s) {
		int left =0, right = s.length()-1;
		while(left < right) {
			if(s.charAt(left) != s.charAt(right)){
				return validPalindrome(s, left+1, right) || validPalindrome(s,left, right-1);
			}
			left++;
			right--;
		}
		return true;
	}

	
	private boolean validPalindrome(String s, int start, int end) {
		while (start < end) {
			if (s.charAt(start) != s.charAt(end)) {
				return false;
			}
			start++;
			end--;
		}
		return true;
	}
}

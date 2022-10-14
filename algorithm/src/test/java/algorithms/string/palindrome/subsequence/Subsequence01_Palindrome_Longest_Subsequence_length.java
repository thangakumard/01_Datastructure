package algorithms.string.palindrome.subsequence;

import org.testng.annotations.Test;
/*
 * https://leetcode.com/problems/longest-palindromic-subsequence/
 * 
 * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length of s is 1000.

	Example 1:
	Input:
	
	"bbbab"
	Output:
	4
	One possible longest palindromic subsequence is "bbbb".
	 
	
	Example 2:
	Input:
	
	"cbbd"
	Output:
	2
	One possible longest palindromic subsequence is "bb".
	 
	
	Constraints:
	
	1 <= s.length <= 1000
	s consists only of lowercase English letters.
 */

public class Subsequence01_Palindrome_Longest_Subsequence_length {

	@Test
	private void test() {
		//for the input bbbab => expected output is 4
		System.out.println(longestPalindromeSubseq("bab"));
	}
		/***
	 *       b  b
	 *       0  1
	 * b  0  1  2 ====> dp[0][s.length()-1] result is 2
	 * a  1  0  1
	 *
	 *       b  a  b
	 *       0  1  2
	 * b  0  1  1  3  ====> dp[0][s.length()-1] result is 3
	 * a  1  0  1  1
	 * b  2  0  0  1
	 */
	
	public int longestPalindromeSubseq(String s) {
		
		int[][] dp = new int[s.length()][s.length()];
		
		for(int i=s.length()-1; i >=0; i--) {
			dp[i][i] = 1; // Assign all the diagonal position to 1
			for(int j=i+1; j<s.length();j++ ) {
				if(s.charAt(i) == s.charAt(j)) {
					dp[i][j] += dp[i+1][j-1] + 2;
				}else {
					dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		
		return dp[0][s.length()-1];
	}
}

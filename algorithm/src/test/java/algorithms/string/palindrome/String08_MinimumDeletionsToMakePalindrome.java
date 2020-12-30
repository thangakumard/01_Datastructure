package algorithms.string.palindrome;

import org.testng.annotations.Test;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/JEk75NvQ01y
 * https://leetcode.com/discuss/interview-question/371677/Google-or-Onsite-or-Min-Deletions-to-Make-Palindrome
 * 
 */
public class String08_MinimumDeletionsToMakePalindrome {

	@Test
	private void test() {
		//for the input bbbab => expected output is 1
		System.out.println(min_deletePalindromeSubseq("bbbab"));
	}
	
	
	public int min_deletePalindromeSubseq(String s) {
		
		int[][] dp = new int[s.length()][s.length()];
		
		for(int i=s.length()-1; i >=0; i--) {
			dp[i][i] = 1;
			for(int j=i+1; j<s.length();j++ ) {
				if(s.charAt(i) == s.charAt(j)) {
					dp[i][j] += dp[i+1][j-1] + 2;
				}else {
					dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		
		return s.length()- dp[0][s.length()-1];
	}
}

package algorithms.string;

import org.testng.annotations.Test;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/qARrkkw48Ak
 * 
 */
public class String02_LongestCommonSubsequence {
	
	@Test
	public void test() {
	    System.out.println(findLCSLength_recursive("abdca", "cbda"));
	    System.out.println(findLCSLength_recursive("passport", "ppsspt"));
	    
	    System.out.println(findLCSLength_dynamic("abdca", "cbda"));
	    System.out.println(findLCSLength_dynamic("passport", "ppsspt"));
	  }
	
	
	/*
	 * Time complexity O(m * n)
	 * Space complexity O(m * n)
	 */
	private int findLCSLength_dynamic(String s1, String s2) {
		
		int[][] dp = new int[s1.length()+1][s2.length()+1];
		int maxLength = 0;
		for(int i=1; i<= s1.length(); i++) {
			for(int j=1; j <= s2.length(); j++) {
				if(s1.charAt(i-1) == s2.charAt(j-1)) {
					dp[i][j] = 1 + dp[i-1][j-1];
				}else {
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
				maxLength = Math.max(maxLength, dp[i][j]);
			}
		}
		return maxLength;
	}
	
	
	
	/*
	 * Time complexity O(2 ^ (m+n))
	 * Space complexity O(m+n)
	 */
	private int findLCSLength_recursive(String s1, String s2) {
		
		return use_recursive(s1,s2,0,0);
	}
	
	private int use_recursive(String s1, String s2, int i,int j) {
		if(i == s1.length() || j == s2.length())
			return 0;
		
		if(s1.charAt(i) == s2.charAt(j)) {
			return 1+ use_recursive(s1, s2, i+1, j+1);
		}
		
		int c1 = use_recursive(s1, s2, i+1, j);
		int c2 = use_recursive(s1, s2, i, j+1);
		
		return Math.max(c1, c2);
	}

}

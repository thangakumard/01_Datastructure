package algorithms.dynamicProgramming;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * https://www.youtube.com/watch?v=BysNXJHzCEs
 *https://leetcode.com/problems/maximum-length-of-repeated-subarray/
 */
public class Dynamic17_LongestCommonSubstring {

	@Test
	 public void test() {
		Assertions.assertThat(findLCSLength("abdca", "cbda")).isEqualTo(2);
		Assertions.assertThat(findLCSLength("passport", "ppsspt")).isEqualTo(3);
	 }
	 
	/*
	 * Time complexity O(m * n)
	 * Space complexity O(m * n)
	 */
	 	public int findLCSLength(String s1, String s2) {
		    if(s1 == null || s2 == null) return 0;
		    int[][] dp = new int[s1.length()+1][s2.length()+1];
		    int maxLength = 0;
		    for(int i=1; i<=s1.length(); i++){
		      for(int j=1; j<=s2.length();j++){
		        if(s1.charAt(i-1) == s2.charAt(j-1)){
		             dp[i][j] = 1 + dp[i-1][j-1];
		             maxLength = Math.max(dp[i][j], maxLength);
		        }else{
		          dp[i][j] = 0;
		        }
		      }
		    }
		    return maxLength;
		  }
	 	
	 	/*Same time complexity BUT Space complexity with O(n)
	 	 * 
		 * Time complexity O(m * n)
		 * Space complexity O(n)
		 */
	 	static int findLCSLength_02(String s1, String s2) {
	 	    int[][] dp = new int[2][s2.length()+1];
	 	    int maxLength = 0;
	 	    for(int i=1; i <= s1.length(); i++) {
	 	      for(int j=1; j <= s2.length(); j++) {
	 	        dp[i%2][j] = 0;
	 	        if(s1.charAt(i-1) == s2.charAt(j-1)) {
	 	          dp[i%2][j] = 1 + dp[(i-1)%2][j-1];
	 	          maxLength = Math.max(maxLength, dp[i%2][j]);
	 	        }
	 	      }
	 	    }
	 	    return maxLength;
	 	  }
	 	
	 	/*
	 	 * Because of the three recursive calls, the time complexity of the above algorithm is 
	 	 * exponential O(3^{m+n}), where ‘m’ and ‘n’ are the lengths of the two input strings. 
			The space complexity is O(m+n), this space will be used to store the recursion stack.
	 	 */
	 	private int recursive(String s1, String s2) {
	 		
	 		return use_recursive(s1,s2,0,0,0);
	 	}

		
	 	private int use_recursive(String s1, String s2, int i, int j, int count) {
	 		if(i == s1.length() || j == s2.length())
	 			return count;
	 		
	 		if(s1.charAt(i) == s2.charAt(j)) {
	 			count = use_recursive(s1, s2, i+1, j+1, count+1);
	 		}
	 		
	 		int c1 = use_recursive(s1, s2, i, j+1, 0);
	 		int c2 = use_recursive(s1, s2, i+1, j, 0);
	 		
	 		return Math.max(count, Math.max(c1,c2));
	 	}
	
}

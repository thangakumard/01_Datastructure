package algorithms.dynamicProgramming;

import org.testng.annotations.*;
import org.testng.Assert;

/***
 * 
 * https://www.geeksforgeeks.org/longest-common-subsequence/
 * 
 * https://leetcode.com/problems/longest-common-subsequence/
 * 
 * https://www.youtube.com/watch?v=NnD96abizww
 * 
 * https://github.com/mission-peace/interview/blob/master/src/com/interview/dynamic/LongestCommonSubsequence.java
 * 
 */
public class Dynamic02_LongestCommonSubsequence {
	
	@Test
	public void Test(){
		String a = "GXTXAYB";
		String b = "AGGTAB";
		System.out.println(longestCommonSubsequence(a, b)); //GTAB
	}

	
	public int longestCommonSubsequence(String text1, String text2) {
        int x = text1.length();
        int y = text2.length();
        
        int[][] dp = new int[x+1][y+1];
        for(int i=1; i<dp.length; i++){
            for(int j=1; j< dp[0].length; j++){
                if(text1.charAt(i-1) == text2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else {
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        
        return dp[x][y] ;
    }
}

package algorithms.dynamicProgramming;

import org.testng.annotations.Test;

public class Dynamic11_DeleteOperationforTwoStrings {
	
	@Test
	private void test() {
		System.out.println(minDistance("123450", "67890"));
	}

    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        
        for(int i=1; i<= word1.length(); i++){
            for(int j=1; j <= word2.length(); j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        
        return (word1.length() - dp[word1.length()][word2.length()]) + 
            (word2.length() - dp[word1.length()][word2.length()]);
    }
}

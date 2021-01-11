package algorithms.dynamicProgramming;

import org.testng.annotations.*;
import org.testng.Assert;

/***
 * 
 * @author THANGAKUMAR
 * LCS for input Sequences �ABCDGH� and �AEDFHR� is �ADH� of length 3.
 * LCS for input Sequences �AGGTAB� and �GXTXAYB� is �GTAB� of length 4.
 * https://www.geeksforgeeks.org/longest-common-subsequence/
 */
public class Dynamic02_LongestCommonSubsequence {
	
	@Test
	public void Test(){
		String a = "GXTXAYB";
		String b = "AGGTAB";
		System.out.println(getLongestCommonSubsequence(a, b)); //GTAB
	}

	
	private int getLongestCommonSubsequence(String a, String b){
		
		int[][] temp = new int[a.length()+1][b.length()+1];
		int max_x = -1, max_y = -1;
		StringBuilder s = new StringBuilder();
		for(int i=0; i<= a.length(); i++){
			for(int j=0; j <= b.length(); j++){
				if(i ==0 || j== 0){
					temp[i][j] = 0;
					continue;
				}
				if(a.charAt(i-1) != b.charAt(j-1)){
					temp[i][j] = Math.max(temp[i-1][j], temp[i][j-1]);
				}
				else{
					if(max_x < i-1 && max_y < j-1){
						max_x = i-1;
						max_y = j-1;
						s.append(a.charAt(i-1));
					}
					temp[i][j] = temp[i-1][j-1] + 1;					
				}
				
			}
		}
		
		System.out.println(s.toString());
		return temp[a.length()][b.length()];
	}
}

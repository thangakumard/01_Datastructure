package dynamicProgramming;

import org.testng.annotations.*;

public class Dynamic04_SubsetSum {

	@Test
	public void test(){
		int[] input = {2,3,7,8,10};
		int total = 11;
		isSubsetSum(input, total);
	}
	
	
	private boolean isSubsetSum(int[] input, int total){
		boolean[][] temp = new boolean[input.length+1][total+1];
		
		for(int i=0; i<= input.length;i++){
			for(int j=0; j<= total; j++){
				if(i == 0 || j== 0){ //Set 0th column and 0th row as TRUE
					temp[i][j] = true;
					continue;
				}
				if(j - input[i-1]>= 0){
					//Previous Rows value || previous row's j-input value
					temp[i][j] = temp[i-1][j] || temp[i-1][j-input[i-1]];
				}
				else
				{
					//Previous Row value
					temp[i][j] = temp[i-1][j];
				}
			
			}
		}
		
		
		return temp[input.length][total];
	}
	
}

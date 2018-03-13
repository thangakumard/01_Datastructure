package dynamicProgramming;

public class Dynamic04_SubsetSum {

	private boolean isSubsetSum(int[] input, int total){
		boolean[][] temp = new boolean[input.length+1][total+1];
		
		for(int i=0; i<= input.length;i++){
			for(int j=0; j<= total; j++){
				if(i == 0 || j== 0){
					temp[i][j] = true;
					continue;
				}
				if(j - input[i-1]>= 0){
					temp[i][j] = temp[i-1][j] || temp[i-1][j-input[i-1]];
				}
				else
				{
					temp[i][j] = temp[i-1][j];
				}
			
			}
		}
		
		
		return temp[input.length][total];
	}
	
}

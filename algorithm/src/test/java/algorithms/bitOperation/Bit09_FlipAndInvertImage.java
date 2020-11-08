package algorithms.bitOperation;

import org.testng.annotations.Test;

public class Bit09_FlipAndInvertImage {
	
	@Test
	public void Test(){
		 int[][] input = new int[][]{{1,1,0},{1,0,1},{0,0,0}};
  		 int[][] result = flipAndInvertImage(input);
  		 for(int i=0; i < result.length; i++){
  			 for(int j=0; j < result[i].length; j++){
  				 System.out.print(result[i][j]);
  			 }
  			 System.out.println("****");
  		 }
	}


public int[][] flipAndInvertImage(int[][] A) {
        
        int[][] result = new int[A.length][A[0].length];
        
        for(int i=0; i < A.length; i++){
            int k=0;
            for(int j=A[0].length-1; j>=0;j--){                
                result[i][k] = 1 ^ A[i][j];               
                k++;
            }
        }
        return result;
    }
}

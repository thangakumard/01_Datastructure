package algorithms.array.medium;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Array04_smallestCommonElementInAllRows {

	 @Test
	 private void test() {
		 int[][] mat = {{1,2,3,4,5},{2,4,5,8,10},{3,5,7,9,11},{1,3,5,7,9}};
		 Assert.assertEquals(5, smallestCommonElement(mat));
	 }
	
	 public int smallestCommonElement(int[][] mat) {
	        int[] count = new int[10001];
	        int n= mat.length, m = mat[0].length;
	        
	        for(int j=0; j < m; j++){
	            for(int i =0; i< n; i++){
	                if(++count[mat[i][j]] == n){
	                    return mat[i][j];
	                }
	            }
	        }
	        return -1;
	    }
}

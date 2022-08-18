package algorithms.array.medium;

import org.testng.Assert;
import org.testng.annotations.Test;

/****
 * https://leetcode.com/problems/find-smallest-common-element-in-all-rows/
 * Given a matrix mat where every row is sorted in strictly increasing order, return the smallest common element in all rows.

	If there is no common element, return -1.
	
	 
	
	Example 1:
	
	Input: mat = [[1,2,3,4,5],[2,4,5,8,10],[3,5,7,9,11],[1,3,5,7,9]]
	Output: 5
	 
	
	Constraints:
	
	1 <= mat.length, mat[i].length <= 500
	1 <= mat[i][j] <= 10^4
	mat[i] is sorted in strictly increasing order.
 */

public class Array04_smallestCommonElementInAllRows {

	 @Test
	 private void test() {
		 int[][] mat = {{1,2,3,4,5},{2,4,5,8,10},{3,5,7,9,11},{1,3,5,7,9}};
		 Assert.assertEquals(5, smallestCommonElement(mat));
	 }


	 public int smallestCommonElement(int[][] mat) {
	        if(mat == null || mat.length == 0)
	            return 0;
	        
	        int[] counter = new int[10001];
	        
	        int row = mat.length;
	        int col = mat[0].length;
	        
	        for(int c=0; c < col; c++){ //** IMPORTANT *** It is good idea to traverse via each column first
	            for(int r=0; r < row; r++){
	                counter[mat[r][c]]++;
	                if(counter[mat[r][c]] == mat.length) //** IMPORTANT *** This condition should be made inside the for loop
	                    return mat[r][c];
	            }
	        }
	        
	        return -1;
	    }
}

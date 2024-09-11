package algorithms.matrix;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/rotate-image/
 * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [[7,4,1],[8,5,2],[9,6,3]]

Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

Input: matrix = [[1,2],[3,4]]
Output: [[3,1],[4,2]]

Constraints:

matrix.length == n
matrix[i].length == n
1 <= n <= 20
-1000 <= matrix[i][j] <= 1000
 */
public class TwoD02_RotateImage {
	
	@Test
	private void test() {
		int[][] input = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
		rotate(input);
	}

	public void rotate(int[][] matrix) {
	      int N = matrix.length;
	        
	      /*
	       * Rotates whole array to 90 degree anti-clock wise
	       */
	        for(int i=0; i< N; i++){
	            for(int j=i; j<N; j++){
	                int temp = matrix[i][j];
	                matrix[i][j]= matrix[j][i];
	                matrix[j][i] = temp;
	            }
	        }
	        
	        /*
	         * Flip the matrix column wise
	         */
	         for(int i=0; i< N; i++){
	            for(int j=0; j<(N/2); j++){
	                int temp = matrix[i][j];
	                matrix[i][j]= matrix[i][N-1-j];
	                matrix[i][N-1-j] = temp;
	            }
	        }
	    }
}

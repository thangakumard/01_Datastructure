package algorithms.matrix;

/*
 * https://leetcode.com/problems/transpose-matrix/
 * Example 1:
 * 
 * Given a matrix A, return the transpose of A.

The transpose of a matrix is the matrix flipped over it's main diagonal, 
switching the row and column indices of the matrix.

Input: [[1,2,3],[4,5,6],[7,8,9]]
Output: [[1,4,7],[2,5,8],[3,6,9]]
Example 2:

Input: [[1,2,3],[4,5,6]]
Output: [[1,4],[2,5],[3,6]]
 

Note:

1 <= A.length <= 1000
1 <= A[0].length <= 1000
 */
public class TwoD01_TransposeMatrix {

    public int[][] transpose(int[][] A) {
    	int row = A.length;
    	int col = A[0].length;
    	int[][] newMatrix = new int[col][row];
    	
    	for(int i=0; i< row; i++) {
    		for(int j=0; j< col; j++) {
    			newMatrix[j][i] = A[i][j];
    		}
    	}
        return newMatrix;
    }
}

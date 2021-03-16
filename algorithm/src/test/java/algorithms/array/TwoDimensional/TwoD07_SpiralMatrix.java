package algorithms.array.TwoDimensional;

import java.util.ArrayList;
import java.util.List;

/*****
 * 
 * https://leetcode.com/problems/spiral-matrix/
 * 
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 * 
 * Example 1:
 * 
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]] Output: [1,2,3,6,9,8,7,4,5] Example
 * 2:
 * 
 * 
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]] Output:
 * [1,2,3,4,8,12,11,10,9,5,6,7]
 * 
 * Constraints:
 * 
 * m == matrix.length n == matrix[i].length 1 <= m, n <= 10 -100 <= matrix[i][j]
 * <= 100
 *
 */
public class TwoD07_SpiralMatrix {

	public List<Integer> spiralOrder(int[][] inputMatrix) {
		int row_start = 0, col_start = 0;
		int row_end = inputMatrix.length - 1;
		int col_end = inputMatrix[0].length - 1;
		List<Integer> result = new ArrayList<>();

		while (row_start <= row_end && col_start <= col_end) {
			// Right
			for (int i = col_start; i <= col_end; i++) {
				result.add(inputMatrix[row_start][i]);
			}
			row_start++;

			// Down
			for (int i = row_start; i <= row_end; i++) {
				result.add(inputMatrix[i][col_end]);
			}
			col_end--;

			// Left
			if (row_start <= row_end) {
				for (int i = col_end; i >= col_start; i--) {
					result.add(inputMatrix[row_end][i]);
				}
			}
			row_end--;

			// up
			if (col_start <= col_end) {
				for (int i = row_end; i >= row_start; i--) {
					result.add(inputMatrix[i][col_start]);
				}
			}
			col_start++;
		}
		return result;
	}
}

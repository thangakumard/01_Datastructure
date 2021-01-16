package algorithms.dynamicProgramming;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/maximal-square/ Given an m x n binary matrix
 * filled with 0's and 1's, find the largest square containing only 1's and
 * return its area.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: matrix =
 * [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 4 Example 2:
 * 
 * 
 * Input: matrix = [["0","1"],["1","0"]] Output: 1 Example 3:
 * 
 * Input: matrix = [["0"]] Output: 0
 * 
 * 
 * Constraints:
 * 
 * m == matrix.length n == matrix[i].length 1 <= m, n <= 300 matrix[i][j] is '0'
 * or '1'.
 * 
 * 
 */

public class Dynamic16_MaximalSquare {

	@Test
	private void test() {

		char[][] input = new char[2][2];
		input[0][0] = '1';
		input[0][1] = '1';
		input[1][0] = '1';
		input[1][1] = '1';

		System.out.println(maximalSquare(input));
	}

	public int maximalSquare(char[][] matrix) {
		if (matrix == null || matrix.length == 0)
			return 0;

		int row = matrix.length, col = matrix[0].length;
		int[][] dp = new int[row + 1][col + 1];

		int max_square = Integer.MIN_VALUE;

		for (int i = 1; i <= row; i++) {
			for (int j = 1; j <= col; j++) {
				if (matrix[i - 1][j - 1] == '1') {
					dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
					max_square = Math.max(max_square, dp[i][j]);

				}
			}
		}

		return max_square * max_square;
	}
}

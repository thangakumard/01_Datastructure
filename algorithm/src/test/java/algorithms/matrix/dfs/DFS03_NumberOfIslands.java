package algorithms.matrix.dfs;

import org.testng.annotations.Test;

/*************
 * 
 * https://leetcode.com/problems/number-of-islands/description/ Given a 2d grid
 * map of '1's (land) and '0's (water), count the number of islands. An island
 * is surrounded by water and is formed by connecting adjacent lands
 * horizontally or vertically. You may assume all four edges of the grid are all
 * surrounded by water.
 * 
 * Example 1:
 * 
 * Input: 11110 11010 11000 00000
 * 
 * Output: 1 Example 2:
 * 
 * Input: 11000 11000 00100 00011
 * 
 * Output: 3
 *
 */

public class DFS03_NumberOfIslands {

	@Test
	public void GetNumberOfIslands() {

		char[][] grid = new char[4][5];
//				grid[0] = new char[]{'1','1','1','1','0'};
//				grid[1] = new char[]{'1','1','0','1','0'};
//				grid[2] = new char[]{'1','1','0','0','0'};
//				grid[3] = new char[]{'0','0','0','0','0'};

//		grid[0] = new char[] { '1', '1', '0', '0', '0' };
//		grid[1] = new char[] { '1', '1', '0', '0', '0' };
//		grid[2] = new char[] { '0', '0', '1', '0', '0' };
//		grid[3] = new char[] { '0', '0', '0', '1', '1' };
		
		grid[0] = new char[] { '1', '1', '1' };
		grid[1] = new char[] { '0', '1', '0' };
		grid[2] = new char[] { '1', '1', '1' };


		System.out.println(numIslands(grid));
	}

	/**
	 * Time: O(M×N)
	 * Space: O(min(M,N))
	 */
	public int numIslands(char[][] grid) {

		int row = grid.length;
		int col = grid[0].length;

		int counter = 0;

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (grid[i][j] == '1') {
					grid = markVisited(i, j, grid, row, col);
					counter++;
				}
			}
		}

		return counter;

	}

	private char[][] markVisited(int i, int j, char[][] grid, int row, int col) {
		if (i < row && i > -1 && j < col && j > -1) {
			if (grid[i][j] == '1') {
				grid[i][j] = 'x';
				markVisited(i + 1, j, grid, row, col);
				markVisited(i - 1, j, grid, row, col);
				markVisited(i, j + 1, grid, row, col);
				markVisited(i, j - 1, grid, row, col);
			}
		}
		return grid;
	}

}

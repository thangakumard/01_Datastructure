package algorithms.matrix.slidingWindow;

/***
 * https://leetcode.com/problems/largest-local-values-in-a-matrix/
 * You are given an n x n integer matrix grid.
 *
 * Generate an integer matrix maxLocal of size (n - 2) x (n - 2) such that:
 * maxLocal[i][j] is equal to the largest value of the 3 x 3 matrix in grid centered around row i + 1 and column j + 1.
 * In other words, we want to find the largest value in every contiguous 3 x 3 matrix in grid.
 *
 * Return the generated matrix.
 *
 * Example 1:
 * Input: grid = [[9,9,8,1],[5,6,2,6],[8,2,6,4],[6,2,2,2]]
 * Output: [[9,9],[8,6]]
 * Explanation: The diagram above shows the original matrix and the generated matrix.
 * Notice that each value in the generated matrix corresponds to the largest value of a contiguous 3 x 3 matrix in grid.
 *
 * Example 2:
 * Input: grid = [[1,1,1,1,1],[1,1,1,1,1],[1,1,2,1,1],[1,1,1,1,1],[1,1,1,1,1]]
 * Output: [[2,2,2],[2,2,2],[2,2,2]]
 * Explanation: Notice that the 2 is contained within every contiguous 3 x 3 matrix in grid.
 *
 *
 * Constraints:
 * n == grid.length == grid[i].length
 * 3 <= n <= 100
 * 1 <= grid[i][j] <= 100
 */

/**
 * Time: O(m × n)
 * Every cell is visited exactly once; the two additions per cell are O(1), so total work scales linearly with the number of elements.
 *
 * Space: O(m + n) (excluding output)
 * rowSum uses O(m), colSum uses O(n). If you count the returned result as required output (not auxiliary), the space is effectively O(1) auxiliary beyond what must be returned — worth stating explicitly, since interviewers often ask you to distinguish "space used" from "output space."
 */
public class LargestLocal {
    public int[][] largestLocal(int[][] grid) {
        int n = grid.length;
        int[][] result = new int[n-2][n-2];

        for(int r=0; r < n-2; r++){
            for(int c=0; c < n-2; c++){
                result[r][c] = findMax(grid, r ,c);
            }
        }
        return result;
    }

    private int findMax(int[][] grid, int x, int y){
        int maxValue = 0;
        for(int r = x; r < x+3; r++){
            for(int c = y; c < y+3; c++){
                maxValue = Math.max(maxValue, grid[r][c]);
            }
        }
        return maxValue;
    }
}

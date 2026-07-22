package algorithms.graph.medium.dfs;

/***
 * https://leetcode.com/problems/number-of-islands/
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
 *
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 *
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 * Example 2:
 *
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 */
public class graph_dfs05_numIslands {
    /***
     * DFS
     * ===
     * Time complexity : O(m × n)
     * Space complexity : O(m × n) (worst-case recursion stack)
     *
     * m = number of rows
     * n = number of columns
     */
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0)
            return 0;
        int totalIslands = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for(int r=0; r < rows; r++){
            for(int c=0; c < cols; c++){
                if(grid[r][c] == '1'){
                    totalIslands++;
                    dfs(rows,cols, r,c,grid);
                }
            }
        }
        return totalIslands;
    }

    private void dfs(int rows, int cols, int r, int c, char[][] grid){
        if(r < 0 || r >= rows ||
                c < 0 || c >= cols ||
                grid[r][c] == '0') return;

        grid[r][c] = '0';

        int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        for(int[] dir: directions){
            dfs(rows, cols, r + dir[0], c + dir[1], grid);
        }
    }
}

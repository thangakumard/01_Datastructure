package algorithms.matrix.dfs;

import org.testng.annotations.Test;

/**
 * https://leetcode.com/problems/max-area-of-island/ Given a non-empty 2D array
 * grid of 0's and 1's, an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.) You may assume all four
 * edges of the grid are surrounded by water.
 * 
 * Find the maximum area of an island in the given 2D array. (If there is no
 * island, the maximum area is 0.)
 * 
 * 
 *
 [
 [0,0,1,0,0,0,0,1,0,0,0,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,1,1,0,1,0,0,0,0,0,0,0,0],
 [0,1,0,0,1,1,0,0,1,0,1,0,0],
 [0,1,0,0,1,1,0,0,1,1,1,0,0],
 [0,0,0,0,0,0,0,0,0,0,1,0,0],
 [0,0,0,0,0,0,0,1,1,1,0,0,0],
 [0,0,0,0,0,0,0,1,1,0,0,0,0]
 ]
 
 * 
 * Given the above
 * grid, return 6. Note the answer is not 11, because the island must be
 * connected 4-directionally. Example 2:
 * 
 * [[0,0,0,0,0,0,0,0]] Given the above grid, return 0. Note: The length of each
 * dimension in the given grid does not exceed 50.
 *
 */
public class DFS03_MaxAreaIsland {

	@Test
	private void test() {
		
		int[][] grid = new int[4][5];
		grid[0] = new int[]{1,1,0,0,0};
		grid[1] = new int[]{1,1,0,0,0};
		grid[2] = new int[]{0,0,1,0,0};
		grid[3] = new int[]{0,0,0,1,1};
		System.out.println(maxAreaOfIsland(grid));
		
	}
	
	 public int maxAreaOfIsland(int[][] grid) {
	        if(grid == null || grid.length == 0){
	            return 0;
	        }
	        int maxArea = 0;
	        for(int i=0; i < grid.length; i++){
	            for(int j=0; j < grid[0].length; j++){
	                if(grid[i][j] == 1){
	                    maxArea = Math.max(maxArea, dfs(grid, i, j));
	                }
	            }
	        }
	        
	        return maxArea;
	    }
	    
	    private int dfs(int[][] grid, int i, int j){
	        int m = grid.length;
	        int n = grid[0].length;
	        
	        if(i < 0 || i >= m || j < 0 || j >= n || grid[i][j] != 1){
	            return 0;
	        }
	        
	        grid[i][j] = 'X';
	        int area = 1;
	        
	        area += dfs(grid, i+1, j);
	        area += dfs(grid, i-1, j);
	        area += dfs(grid, i, j+1);
	        area += dfs(grid, i, j-1);
	        
	        return area;
	    }
}


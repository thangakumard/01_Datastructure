package algorithms.dfs;
import org.testng.annotations.Test;
/*************
 * 
 * https://leetcode.com/problems/number-of-islands/description/
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

	Example 1:
	
	Input:
	11110
	11010
	11000
	00000
	
	Output: 1
	Example 2:
	
	Input:
	11000
	11000
	00100
	00011
	
	Output: 3
 *
 */

public class DFS02_NumberOfIslands {

	@Test
	public void GetNumberOfIslands(){
	
		char[][] grid = new char[4][5];
//				grid[0] = new char[]{'1','1','1','1','0'};
//				grid[1] = new char[]{'1','1','0','1','0'};
//				grid[2] = new char[]{'1','1','0','0','0'};
//				grid[3] = new char[]{'0','0','0','0','0'};
		
		grid[0] = new char[]{'1','1','0','0','0'};
		grid[1] = new char[]{'1','1','0','0','0'};
		grid[2] = new char[]{'0','0','1','0','0'};
		grid[3] = new char[]{'0','0','0','1','1'};
		
				System.out.println(numIslands(grid));
	}
	
	public int numIslands(char[][] grid) {
	    if(grid==null || grid.length==0||grid[0].length==0)
	        return 0;
	 
	    int m = grid.length;
	    int n = grid[0].length;
	 
	    int count=0;
	    for(int i=0; i<m; i++){
	        for(int j=0; j<n; j++){
	            if(grid[i][j]=='1'){
	                count++;
	                dfs(grid, i, j);
	            }
	        }
	    }
	 
	    return count;
	}
	 
	public void dfs(char[][] grid, int i, int j){
	    int m=grid.length;
	    int n=grid[0].length;
	 
	    if(i<0||i>=m||j<0||j>=n||grid[i][j]!='1')
	        return;
	 
	    grid[i][j]='X';
	 
	    dfs(grid, i-1, j);
	    dfs(grid, i+1, j);
	    dfs(grid, i, j-1);
	    dfs(grid, i, j+1);
	}
	
}
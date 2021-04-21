package algorithms.array.TwoDimensional;
import org.testng.annotations.*;
/******
 * https://leetcode.com/problems/number-of-closed-islands/
 * Given a 2D grid consists of 0s (land) and 1s (water).  An island is a maximal 4-directionally connected group of 0s and a closed island is an island totally (all left, top, right, bottom) surrounded by 1s.

	Return the number of closed islands.

	Example 1:
	Input: grid = [[1,1,1,1,1,1,1,0],[1,0,0,0,0,1,1,0],[1,0,1,0,1,1,1,0],[1,0,0,0,0,1,0,1],[1,1,1,1,1,1,1,0]]
	Output: 2
	Explanation: 
	Islands in gray are closed because they are completely surrounded by water (group of 1s).
	
	Example 2:
	Input: grid = [[0,0,1,0,0],[0,1,0,1,0],[0,1,1,1,0]]
	Output: 1
	Example 3:
	
	Input: grid = [[1,1,1,1,1,1,1],
	               [1,0,0,0,0,0,1],
	               [1,0,1,1,1,0,1],
	               [1,0,1,0,1,0,1],
	               [1,0,1,1,1,0,1],
	               [1,0,0,0,0,0,1],
	               [1,1,1,1,1,1,1]]
	Output: 2
	 
	Constraints:
	
	1 <= grid.length, grid[0].length <= 100
	0 <= grid[i][j] <=1
 *
 */
public class TwoD13_ClosedIsland {
	
	@Test
	private void Test() {
		int[][] grid = new int[5][8];
		grid[0] = new int[] {1,1,1,1,1,1,1,0};
		grid[1] = new int[] {1,0,0,0,0,1,1,0};
		grid[2] = new int[] {1,0,1,0,1,1,1,0};
		grid[3] = new int[] {1,0,0,0,0,1,0,1};
		grid[4] = new int[] {1,1,1,1,1,1,1,0};
		System.out.println(closedIsland(grid));
	}

	 public int closedIsland(int[][] grid) {
	        int count = 0;
	        for(int i=0; i < grid.length; i++){
	            for(int j=0; j < grid[0].length; j++)
	            {
	                if(grid[i][j] == 0){
	                    if(isClosedIsland(grid,i,j))
	                         count++;
	                }        
	            }
	        }
	        return count;
	    }
	    
	    private boolean isClosedIsland(int[][] grid,int i, int j){
	        int row = grid.length-1;
	        int col = grid[0].length-1;
	        
	        if(i < 0 || i > row || j < 0 || j > col){
	            return false;
	        }
	        if(grid[i][j] == 1) return true;
	        
	        grid[i][j] = 1;
	        return 
	            isClosedIsland(grid, i+1, j) & 
	            isClosedIsland(grid, i-1, j) &
	            isClosedIsland(grid, i, j+1) &
	            isClosedIsland(grid, i, j-1);
	        //bitwise & operator checks both left and right value. but && evaluates the left side of the operation, if it's true, only then it continues and evaluates the right side
	    }
}

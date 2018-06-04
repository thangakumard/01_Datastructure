package algorithms;
import org.testng.annotations.Test;

public class NumberOfIslands {

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
	                merge(grid, i, j);
	            }
	        }
	    }
	 
	    return count;
	}
	 
	public void merge(char[][] grid, int i, int j){
	    int m=grid.length;
	    int n=grid[0].length;
	 
	    if(i<0||i>=m||j<0||j>=n||grid[i][j]!='1')
	        return;
	 
	    grid[i][j]='X';
	 
	    merge(grid, i-1, j);
	    merge(grid, i+1, j);
	    merge(grid, i, j-1);
	    merge(grid, i, j+1);
	}
	
}

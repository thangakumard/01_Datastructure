package matrix;
import org.testng.annotations.Test;

/**
 * 
 * https://leetcode.com/problems/max-area-of-island/solution/
 *
 */
public class MaxAreaofIsland {
	
	@Test
	public void Test(){
		
		int[][] input = {
				{0,0,1,0,0,0,0,1,0,0,0,0,0},
				 {0,0,0,0,0,0,0,1,1,1,0,0,0},
				 {0,1,1,0,1,0,0,0,0,0,0,0,0},
				 {0,1,0,0,1,1,0,0,1,0,1,0,0},
				 {0,1,0,0,1,1,0,0,1,1,1,0,0},
				 {0,0,0,0,0,0,0,0,0,0,1,0,0},
				 {0,0,0,0,0,0,0,1,1,1,0,0,0},
				 {0,0,0,0,0,0,0,1,1,0,0,0,0}
		};
	  System.out.println("Max area of Island :");
	  System.out.println(maxAreaOfIsland(input));	
	}
	
	boolean[][] seen ;
	public int maxAreaOfIsland(int[][] input){
		int area = 0;
		if(input == null) return 0;
		seen = new boolean[input.length][input[0].length];
		
		for(int i=0; i< input.length; i++){
			for(int j=0; j< input[0].length; j++){
				area = Math.max(area, getArea(input, i, j));
			}
		}
	
		return area;
	}
	
	public int getArea(int[][] input,int x, int y){
		
		if(x >= 0 && x < input.length && 
		  y >=0 && y < input[0].length && 
		  input[x][y] == 1 && !seen[x][y]){
			seen[x][y] = true;
			System.out.println("x: " + x + "y :" + y);
			return (1 + getArea(input, x+1, y) + getArea(input, x-1, y)
					+ getArea(input, x, y+1) + getArea(input, x, y-1));
		}
		return 0;
	}
	
}

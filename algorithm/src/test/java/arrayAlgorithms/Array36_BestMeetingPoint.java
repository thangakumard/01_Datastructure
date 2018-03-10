package arrayAlgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Array36_BestMeetingPoint {

	@Test
	public void Test(){
		int[][] grid ={{1,0,0,0,1},
				{0,0,0,0,0},
				{0,0,1,0,0,0}};
		
		int result= minDistance(grid);
		Assert.assertEquals(result , 6);
	}
	
	public int minDistance(int[][] grid){
		if(grid == null)
			return 0;
		if(grid.length == 0 || grid[0].length == 0){
			return 0;
		}
		
		List<Integer> vertical = new ArrayList<>();
		List<Integer> horizontal = new ArrayList<>();
		
		for(int i=0; i< grid.length; i++){
			for(int j=0; j<grid[0].length;j++){
				if(grid[i][j] == 1){
					vertical.add(i);
					horizontal.add(j);
				}
			}
		}
		
		Collections.sort(vertical);
		Collections.sort(horizontal);
		
		int middle = vertical.size()/2;
		int x = vertical.get(middle);
		int y = horizontal.get(middle);
		int distance = 0;
		
		for(int i=0; i < grid.length; i++){
			for(int j=0; j < grid[0].length; j++){
				if(grid[i][j] == 1){
					distance += Math.abs(x -i) + Math.abs(y -j);
				}
			}
		}
		
		return distance;		
	}
	
}

package algorithms.array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
/******
 * 
  https://www.programcreek.com/2014/07/leetcode-best-meeting-point-java/
  
	A group of two or more people wants to meet and minimize the total travel distance. You are given a 2D grid of values 0 or 1, where each 1 marks the home of someone in the group. The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
	
	For example, given three people living at (0,0), (0,4), and (2,2):
	1 - 0 - 0 - 0 - 1
	|   |   |   |   |
	0 - 0 - 0 - 0 - 0
	|   |   |   |   |
	0 - 0 - 1 - 0 - 0
	The point (0,2) is an ideal meeting point, as the total travel distance of 2+2+2=6 is minimal. So return 6.
 *
 */

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

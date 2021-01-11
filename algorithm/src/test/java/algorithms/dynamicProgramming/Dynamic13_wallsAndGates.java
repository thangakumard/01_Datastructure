package algorithms.dynamicProgramming;

import org.testng.annotations.Test;

/****
 * 
https://leetcode.com/problems/walls-and-gates/
 *
 *You are given an m x n grid rooms initialized with these three possible values.

-1 A wall or an obstacle.
0 A gate.
INF Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.

 

Example 1:


Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
Example 2:

Input: rooms = [[-1]]
Output: [[-1]]
Example 3:

Input: rooms = [[2147483647]]
Output: [[2147483647]]
Example 4:

Input: rooms = [[0]]
Output: [[0]]
 

Constraints:

m == rooms.length
n == rooms[i].length
1 <= m, n <= 250
rooms[i][j] is -1, 0, or 231 - 1.

 */
public class Dynamic13_wallsAndGates {

	@Test
	public void GetDistanceToGate(){
		int[][] rooms = new int[4][4];
		rooms[0] = new int[] {2147483647,-1,0,2147483647};
		rooms[1] = new int[] {2147483647,2147483647,2147483647,-1};
		rooms[2] = new int[] {2147483647,-1,2147483647,-1};
		rooms[3] = new int[] {0,-1,2147483647,2147483647};
		
		wallsAndGates(rooms);
		
		for(int i=0; i< rooms.length;i++){
			for(int j=0; j< rooms[0].length;j++){
				System.out.print(rooms[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public void wallsAndGates(int[][] rooms) {
        if(rooms == null)
            return;
        
        for(int i=0; i < rooms.length; i++){
            for(int j =0; j< rooms[0].length; j++){
                if(rooms[i][j] == 0){
                    dfs(rooms, i, j, 0);
                }
            }
        }
    }
    
    private void dfs(int[][] rooms, int i, int j, int count){
        if(i < 0 || i >= rooms.length || j < 0 || j >= rooms[0].length || rooms[i][j] < count){
            return;
        }
        
        rooms[i][j] = count;
        dfs(rooms, i + 1, j, count + 1);
        dfs(rooms, i -1, j, count + 1);
        dfs(rooms, i, j+1, count + 1);
        dfs(rooms, i , j-1, count + 1);
    }
}

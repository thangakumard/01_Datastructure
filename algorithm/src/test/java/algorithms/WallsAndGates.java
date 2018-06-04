package algorithms;
import java.util.List;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;

import org.testng.annotations.Test;

public class WallsAndGates {
	
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

	private static final int EMPTY = Integer.MAX_VALUE;
	private static final int GATE = 0;
	private static final List<int[]> DIRECTIONS = Arrays.asList(
	        new int[] { 1,  0},
	        new int[] {-1,  0},
	        new int[] { 0,  1},
	        new int[] { 0, -1}
	);

	public void wallsAndGates(int[][] rooms) {
	    int m = rooms.length;
	    if (m == 0) return;
	    int n = rooms[0].length;
	    Queue<int[]> q = new LinkedList<>();
	    for (int row = 0; row < m; row++) {
	        for (int col = 0; col < n; col++) {
	            if (rooms[row][col] == GATE) {
	                q.add(new int[] { row, col });
	            }
	        }
	    }
	    while (!q.isEmpty()) {
	        int[] point = q.poll();
	        int row = point[0];
	        int col = point[1];
	        for (int[] direction : DIRECTIONS) {
	            int r = row + direction[0];
	            int c = col + direction[1];
	            if (r < 0 || c < 0 || r >= m || c >= n || rooms[r][c] != EMPTY) {
	                continue;
	            }
	            rooms[r][c] = rooms[row][col] + 1;
	            q.add(new int[] { r, c });
	        }
	    }
	}
}

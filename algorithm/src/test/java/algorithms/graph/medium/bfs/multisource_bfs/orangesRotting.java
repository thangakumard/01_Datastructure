package algorithms.graph.medium.bfs.multisource_bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/rotting-oranges/description/
 * You are given an m x n grid where each cell can have one of three values:
 *
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
 *
 * Example 1:
 * Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 * Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.

 * Example 3:
 * Input: grid = [[0,2]]
 * Output: 0
 * Explanation: Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 10
 * grid[i][j] is 0, 1, or 2.
 */

/***
 * Time Complexity: O(rows × cols)
 *================
 * Initial grid traversal: O(rows × cols) to count fresh oranges and populate queue with rotten ones
 * BFS traversal: Each cell is visited at most once (when a fresh orange becomes rotten).
 * For each visited cell, we check 4 neighbors in O(1) time
 * Total: O(rows × cols) + O(rows × cols × 4) = O(rows × cols)
 * Space Complexity: O(rows × cols)
 *==================
 * Queue size: In the worst case (e.g., all cells are rotten or most become rotten simultaneously),
 * the queue can hold O(rows × cols) cells
 * No additional data structures beyond the queue (grid is modified in-place)
 * Total: O(rows × cols)
 */
public class orangesRotting {
    class Solution {
        public int orangesRotting(int[][] grid) {
            int r = grid.length;
            int c = grid[0].length;

            int freshOranges = 0;
            Queue<int[]> rottenQueue = new LinkedList<>();

            for(int i=0; i < r; i++){
                for(int j=0; j < c; j++){
                    if(grid[i][j] == 2){
                        rottenQueue.offer(new int[]{i,j,0}); //row,col,time
                    }else if(grid[i][j] == 1){
                        freshOranges++;
                    }
                }
            }

            if(freshOranges == 0) return 0;

            int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
            int maxTime = 0;

            while(!rottenQueue.isEmpty()){
                int[] current = rottenQueue.poll();
                int row = current[0];
                int col = current[1];
                int time = current[2];

                maxTime = time;

                for(int[] dir: directions){
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];

                    if(newRow >=0 && newRow < r && newCol >=0 && newCol < c &&
                            grid[newRow][newCol] == 1){
                        grid[newRow][newCol] = 2;
                        freshOranges--;
                        rottenQueue.offer(new int[]{newRow, newCol, time+1});
                    }
                }
            }

            return freshOranges == 0 ? maxTime : -1;
        }
    }
}

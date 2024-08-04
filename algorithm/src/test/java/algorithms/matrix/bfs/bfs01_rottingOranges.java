package algorithms.matrix.bfs;

import java.util.HashSet;

/**
 * https://leetcode.com/problems/rotting-oranges/description/
 *
 * You are given an m x n grid where each cell can have one of three values:
 *
 * 0 representing an empty cell,
 * 1 representing a fresh orange, or
 * 2 representing a rotten orange.
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
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
 *
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
public class bfs01_rottingOranges {
    public int orangesRotting(int[][] grid) {
        HashSet<String> fresh = new HashSet<>();
        HashSet<String> rotten = new HashSet<>();

        for(int row =0; row < grid.length; row++){
            for(int col =0; col < grid[0].length; col++){
                if(grid[row][col] == 1){
                    fresh.add("" + row + col);
                }else if(grid[row][col] == 2){
                    rotten.add("" + row + col);
                }
            }
        }

        int minutes = 0;
        int[][] DIRECTIONS = {{0,1},{1,0},{-1,0},{0,-1}};
        while (fresh.size()>0) {
            HashSet<String> infected = new HashSet<>();
            for(String s: rotten){
                int i = s.charAt(0) -'0';
                int j = s.charAt(1) - '0';

                for(int[] direction: DIRECTIONS){
                    int nextRow = i + direction[0];
                    int nextCol = j + direction[1];

                    if(fresh.contains("" + nextRow+ nextCol)){
                        fresh.remove("" + nextRow+ nextCol);
                        infected.add("" + nextRow+ nextCol);
                    }
                }
            }
            if(infected.size() == 0){
                return -1;
            }
            rotten = infected;
            minutes++;
        }
        return minutes;
    }
}

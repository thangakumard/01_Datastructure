package algorithms.graph.medium.bfs;

import algorithms.graph.NodeWithNeighbors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/***
 * https://leetcode.com/problems/snakes-and-ladders
 * You are given an n x n integer matrix board where the cells are labeled from 1 to n2 in a Boustrophedon style starting from the bottom left of the board (i.e. board[n - 1][0]) and alternating direction each row.
 *
 * You start on square 1 of the board. In each move, starting from square curr, do the following:
 *
 * Choose a destination square next with a label in the range [curr + 1, min(curr + 6, n2)].
 * This choice simulates the result of a standard 6-sided die roll: i.e., there are always at most 6 destinations, regardless of the size of the board.
 * If next has a snake or ladder, you must move to the destination of that snake or ladder. Otherwise, you move to next.
 * The game ends when you reach the square n2.
 * A board square on row r and column c has a snake or ladder if board[r][c] != -1. The destination of that snake or ladder is board[r][c]. Squares 1 and n2 are not the starting points of any snake or ladder.
 *
 * Note that you only take a snake or ladder at most once per dice roll. If the destination to a snake or ladder is the start of another snake or ladder, you do not follow the subsequent snake or ladder.
 *
 * For example, suppose the board is [[-1,4],[-1,3]], and on the first move, your destination square is 2. You follow the ladder to square 3, but do not follow the subsequent ladder to 4.
 * Return the least number of dice rolls required to reach the square n2. If it is not possible to reach the square, return -1.
 *
 * Example 1:
 * Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],[-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
 * Output: 4
 * Explanation:
 * In the beginning, you start at square 1 (at row 5, column 0).
 * You decide to move to square 2 and must take the ladder to square 15.
 * You then decide to move to square 17 and must take the snake to square 13.
 * You then decide to move to square 14 and must take the ladder to square 35.
 * You then decide to move to square 36, ending the game.
 * This is the lowest possible number of moves to reach the last square, so return 4.
 *
 *
 * Example 2:
 * Input: board = [[-1,-1],[-1,3]]
 * Output: 1
 *
 *
 * Constraints:
 * n == board.length == board[i].length
 * 2 <= n <= 20
 * board[i][j] is either -1 or in the range [1, n2].
 * The squares labeled 1 and n2 are not the starting points of any snake or ladder.
 */

/***
 * Time: O(n²)
 * Space: O(n²)
 */
public class snakeAndLadder {
    class Solution {
        public int snakesAndLadders(int[][] board) {
            int n = board.length;
            int target = n * n;

            boolean[] visited = new boolean[target+1];
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(1);
            visited[1] = true;
            int moves = 0;

            while(!queue.isEmpty()){
                int queueSize = queue.size();
                for(int i=0; i < queueSize; i++){
                    int curr = queue.poll();
                    if(curr == target){
                        return moves;
                    }

                    //Try for all 6 dice outcome
                    for(int dice=1; dice <= 6; dice++){
                        int next = curr + dice;
                        if(next > target) break;

                        int[] pos = getCell(next, n);
                        int row = pos[0];
                        int col = pos[1];

                        if(board[row][col] != -1){
                            next = board[row][col];
                        }
                        if(!visited[next]){
                            visited[next] = true;
                            queue.offer(next);
                        }
                    }

                }
                moves++;
            }

            return -1;
        }

        private int[] getCell(int square, int n){
            int q = (square - 1) / n;
            int r = (square - 1) % n;

            int row = n - 1 - q;
            int col = 0;
            if(q % 2 == 0)
                col = r;
            else
                col = n - 1 - r;
            return new int[]{row, col};
        }
    }
}
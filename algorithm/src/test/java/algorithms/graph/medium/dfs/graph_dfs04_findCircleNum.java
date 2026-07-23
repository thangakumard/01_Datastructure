package algorithms.graph.medium.dfs;

/***
 * https://leetcode.com/problems/number-of-provinces/
 *
 * There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.
 * A province is a group of directly or indirectly connected cities and no other cities outside of the group.
 * You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
 * Return the total number of provinces.
 *
 * Example 1:
 * Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
 * Output: 2
 *
 * Example 2:
 * Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= n <= 200
 * n == isConnected.length
 * n == isConnected[i].length
 * isConnected[i][j] is 1 or 0.
 * isConnected[i][i] == 1
 * isConnected[i][j] == isConnected[j][i]
 *========================
 * DFS
 * ===
 * Time:O(n²)
 * Space:O(n) — recursion stack
 */
public class graph_dfs04_findCircleNum {
    class Solution {
        public int findCircleNum(int[][] isConnected) {
            int n = isConnected.length;
            boolean[] visited = new boolean[n];
            int provinces = 0;

            for (int city = 0; city < n; city++) {
                if (!visited[city]) {
                    provinces++;
                    dfs(isConnected, visited, city, n);
                }
            }
            return provinces;
        }

        private void dfs(int[][] isConnected, boolean[] visited, int city, int n) {
            visited[city] = true;
            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (isConnected[city][neighbor] == 1 && !visited[neighbor]) {
                    dfs(isConnected, visited, neighbor, n);
                }
            }
        }
    }
}

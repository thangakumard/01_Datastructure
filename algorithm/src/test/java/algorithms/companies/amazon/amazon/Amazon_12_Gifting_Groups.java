package algorithms.companies.amazon.amazon;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/***
 * Related Question : https://leetcode.com/problems/number-of-provinces/
 */

public class Amazon_12_Gifting_Groups {

    @Test
    public void findCircleNumTest(){
        int[][] input = {{1,1,0,0},{1,1,1,0},{0,1,1,0},{0,0,0,1}};
        Assertions.assertThat(findCircleNum(input)).isEqualTo(2);
        /***
         * 1 1 0 0
         * 1 1 1 0
         * 0 1 1 0
         * 0 0 0 1
         */
    }


    public int findCircleNum(int[][] M) {
        int[] visited = new int[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (visited[i] == 0) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }

    public void dfs(int[][] M, int[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dfs(M, visited, j);
            }
        }
    }
}

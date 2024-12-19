package algorithms.dynamicProgramming.subMatrix;

/**
 * https://leetcode.com/problems/count-square-submatrices-with-all-ones/description/
 * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
 *
 * Example 1:
 * Input: matrix =
 * [
 *   [0,1,1,1],
 *   [1,1,1,1],
 *   [0,1,1,1]
 * ]
 * Output: 15
 * Explanation:
 * There are 10 squares of side 1.
 * There are 4 squares of side 2.
 * There is  1 square of side 3.
 * Total number of squares = 10 + 4 + 1 = 15.
 *
 * Example 2:
 * Input: matrix =
 * [
 *   [1,0,1],
 *   [1,1,0],
 *   [1,1,0]
 * ]
 * Output: 7
 * Explanation:
 * There are 6 squares of side 1.
 * There is 1 square of side 2.
 * Total number of squares = 6 + 1 = 7.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 300
 * 1 <= arr[0].length <= 300
 * 0 <= arr[i][j] <= 1
 */
public class Dynamic01_countSquaresSubmatrices {
    public int countSquares(int[][] matrix) {
        int result = 0;
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int[][] dp = new int[rowLen][];

        for(int row=0; row < rowLen; row++){
            dp[row] = new int[colLen];
            for(int col=0; col < colLen; col++){
                if(matrix[row][col] == 1){
                    if(row == 0 || col == 0){
                        dp[row][col] = 1;
                    }else{
                        dp[row][col] = Math.min(dp[row-1][col-1],
                                Math.min(dp[row][col-1], dp[row-1][col])) + 1;
                    }
                }
                result += dp[row][col];
            }
        }
        return result;
    }
}

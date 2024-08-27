package algorithms.array.TwoDimensional.spiralMatrix;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/*****
 *
 * https://leetcode.com/problems/spiral-matrix/
 *
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 *
 * Example 1:
 *
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]] Output: [1,2,3,6,9,8,7,4,5] Example
 * 2:
 *
 *
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]] Output:
 * [1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * Constraints:
 *
 * m == matrix.length n == matrix[i].length 1 <= m, n <= 10 -100 <= matrix[i][j]
 * <= 100
 *
 */
public class Spiral01_SpiralMatrix {

    @Test
    private void test() {
        int[][] input = {{1,2,3},{4,5,6},{7,8,9}}; //[1, 2, 3, 6, 9, 8, 7, 4, 5]
        System.out.println(spiralOrder(input));
    }
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if(matrix.length == 0)
            return result;

        int rowBegin = 0, rowEnd = matrix.length-1;
        int colBegin = 0, colEnd = matrix[0].length-1;

        while (rowBegin <= rowEnd && colBegin <= colEnd) {
            for(int i= colBegin; i <= colEnd; i++){
                result.add(matrix[rowBegin][i]);
            }
            rowBegin++;

            for(int i=rowBegin; i <= rowEnd; i++){
                result.add(matrix[i][colEnd]);
            }
            colEnd--;
            if (rowBegin <= rowEnd) {
                for(int i= colEnd; i >= colBegin; i--){
                    result.add(matrix[rowEnd][i]);
                }
            }
            rowEnd--;

            if (colBegin <= colEnd) {
                for(int i= rowEnd; i >= rowBegin; i--){
                    result.add(matrix[i][colBegin]);
                }
            }
            colBegin++;
        }

        return result;
    }
}

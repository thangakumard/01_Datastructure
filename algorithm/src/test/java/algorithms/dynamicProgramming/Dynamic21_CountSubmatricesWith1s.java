package algorithms.dynamicProgramming;

/***
https://leetcode.com/problems/count-square-submatrices-with-all-ones/description/
 Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.



 Example 1:

 Input: matrix =
 [
 [0,1,1,1],
 [1,1,1,1],
 [0,1,1,1]
 ]
 Output: 15
 Explanation:
 There are 10 squares of side 1.
 There are 4 squares of side 2.
 There is  1 square of side 3.
 Total number of squares = 10 + 4 + 1 = 15.
 Example 2:

 Input: matrix =
 [
 [1,0,1],
 [1,1,0],
 [1,1,0]
 ]
 Output: 7
 Explanation:
 There are 6 squares of side 1.
 There is 1 square of side 2.
 Total number of squares = 6 + 1 = 7.


 Constraints:

 1 <= arr.length <= 300
 1 <= arr[0].length <= 300
 0 <= arr[i][j] <= 1
 */
public class Dynamic21_CountSubmatricesWith1s {

    /**
     *
        https://leetcode.com/problems/count-square-submatrices-with-all-ones/solutions/441306/java-c-python-dp-solution/
        Time: O(row * col)
        Space: O(1)
     */
    public int countSquares(int[][] matrix) {
        int result = 0;
        for(int r=0; r< matrix.length; r++){
            for(int c=0; c < matrix[0].length; c++){
                if(matrix[r][c] > 0 && r > 0 && c >0){
                    matrix[r][c] = Math.min(matrix[r-1][c-1], Math.min(matrix[r-1][c], matrix[r][c-1]))+1;
                }
                result += matrix[r][c];
            }
        }
        return result;
    }
}

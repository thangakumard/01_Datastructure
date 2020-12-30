package algorithms.array.TwoDimensional;

/*
 * https://leetcode.com/problems/diagonal-traverse/solution/
 * Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in the below image.

 

Example:

Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]

Output:  [1,2,4,7,5,3,6,8,9]
 */
public class TwoD04_DiagonalTraverse {

	 public int[] findDiagonalOrder(int[][] matrix) {
	        int n = matrix.length;
	        int m = matrix[0].length;
	        int row =0, column= 0 , k=0;
	        int[] result = new int[n * m];
	        int direction = 1;
	        
	        while(row < n && column < m){
	            result[k++] = matrix[row][column];
	            
	            int new_row = row + (direction == 1 ? -1 : 1);
	            int new_col = column + (direction == 1 ? 1: -1);
	            
	            if(new_row < 0 || new_row == n || new_col < 0 || new_col == m){
	                if(direction == 1){
	                    row += (column == m-1 ? 1 : 0);
	                    column += (column < m-1 ? 1 : 0);
	                }else{
	                    column += (row == n-1 ? 1: 0);
	                    row += (row < n-1 ? 1 :0);
	                }
	                direction = 1 - direction;
	            }else{
	                row = new_row;
	                column = new_col;
	            }
	            
	        }
	        return result;
	    }
}

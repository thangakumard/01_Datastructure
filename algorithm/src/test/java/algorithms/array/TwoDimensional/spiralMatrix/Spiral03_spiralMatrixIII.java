package algorithms.array.TwoDimensional.spiralMatrix;

/***
 * You start at the cell (rStart, cStart) of an rows x cols grid facing east. The northwest corner is at the first row and column in the grid, and the southeast corner is at the last row and column.
 *
 * You will walk in a clockwise spiral shape to visit every position in this grid. Whenever you move outside the grid's boundary, we continue our walk outside the grid (but may return to the grid boundary later.). Eventually, we reach all rows * cols spaces of the grid.
 *
 * Return an array of coordinates representing the positions of the grid in the order you visited them.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: rows = 1, cols = 4, rStart = 0, cStart = 0
 * Output: [[0,0],[0,1],[0,2],[0,3]]
 * Example 2:
 *
 *
 * Input: rows = 5, cols = 6, rStart = 1, cStart = 4
 * Output: [[1,4],[1,5],[2,5],[2,4],[2,3],[1,3],[0,3],[0,4],[0,5],[3,5],[3,4],[3,3],[3,2],[2,2],[1,2],[0,2],[4,5],[4,4],[4,3],[4,2],[4,1],[3,1],[2,1],[1,1],[0,1],[4,0],[3,0],[2,0],[1,0],[0,0]]
 *
 *
 * Constraints:
 *
 * 1 <= rows, cols <= 100
 * 0 <= rStart < rows
 * 0 <= cStart < cols
 */
public class Spiral03_spiralMatrixIII {
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int[][] result = new int[rows*cols][2];
        int xdirection = 0, ydirection = 1, n=0, temp = 0;
        for(int i=0; i < rows*cols; n++){
            for(int j=0; j < n/2 + 1; j++){ //to move 1,1,2,2,3,3,etc..
                if(0 <= rStart && rStart < rows && 0 <= cStart && cStart < cols){
                    //Add to result only with the rows and cols are in the boundary
                    result[i] = new int[] {rStart, cStart};
                    i++;
                }
                //move to next cell / move out of the boundary
                rStart += xdirection;
                cStart += ydirection;
            }
            //Change the direction
            temp = xdirection;
            xdirection = ydirection;
            ydirection = -temp;

        }
        return result;
    }
}

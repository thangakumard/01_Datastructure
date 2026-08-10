package algorithms.matrix.traversal;

/***
 * Time: O(m × n)
 * Every cell is visited exactly once; the two additions per cell are O(1), so total work scales linearly with the number of elements.
 *
 * Space: O(m + n) (excluding output)
 * rowSum uses O(m), colSum uses O(n). If you count the returned result as required output (not auxiliary), the space is effectively O(1) auxiliary beyond what must be returned — worth stating explicitly, since interviewers often ask you to distinguish "space used" from "output space."
 */
public class M02_RowSumColSum {
    public int[][] rowAndColumnSums(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        int[] rowSum = new int[m];
        int[] colSum = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSum[i] += matrix[i][j];
                colSum[j] += matrix[i][j];
            }
        }

        return new int[][] { rowSum, colSum };
    }
}

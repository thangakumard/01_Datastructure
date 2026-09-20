package algorithms.array.binarySearch;

/***
 * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/description/
 * Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the kth smallest element in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * You must find a solution with a memory complexity better than O(n2).
 *
 *
 *
 * Example 1:
 *
 * Input: matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
 * Output: 13
 * Explanation: The elements in the matrix are [1,5,9,10,11,12,13,13,15], and the 8th smallest number is 13
 * Example 2:
 *
 * Input: matrix = [[-5]], k = 1
 * Output: -5
 *
 *
 * Constraints:
 *
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 300
 * -109 <= matrix[i][j] <= 109
 * All the rows and columns of matrix are guaranteed to be sorted in non-decreasing order.
 * 1 <= k <= n2
 *
 *
 * Follow up:
 *
 * Could you solve the problem with a constant memory (i.e., O(1) memory complexity)?
 * Could you solve the problem in O(n) time complexity? The solution may be too advanced for an interview but you may find reading this paper fun.
 */
public class BinarySearch13_KthSmallestiInMatrix {
    /***
     * Time: O(N log(max-min))
     *  The range of values is Max - Min. Binary search takes O(log(max-min)) iterations.
     *  In each iteration, counting elements takes O(n) time via the bottom-left staircase traversal.
     * Space: O(1)
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];

        while (left < right) {
            int mid = left + (right - left) / 2;
            int count = countLessOrEqual(matrix, mid, n);

            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;

    }
    private int countLessOrEqual(int[][] matrix, int target, int n) {
        int count = 0;
        int row = n - 1;
        int col = 0;

        // Traverse from bottom-left to top-right
        while (row >= 0 && col < n) {
            if (matrix[row][col] <= target) {
                // All elements above this in the current column are also <= target
                count += (row + 1);
                col++; // Move to the next column
            } else {
                row--; // Current element too large, move up
            }
        }

        return count;
    }
}

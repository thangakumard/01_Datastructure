package algorithms.dynamicProgramming;

import java.util.Arrays;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/unique-paths/
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).

	The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right corner of the grid (marked 'Finish' in the diagram below).
	
	How many possible unique paths are there?
	
	 
	
	Example 1:
	
	
	Input: m = 3, n = 7
	Output: 28
	Example 2:
	
	Input: m = 3, n = 2
	Output: 3
	Explanation:
	From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
	1. Right -> Down -> Down
	2. Down -> Down -> Right
	3. Down -> Right -> Down
	Example 3:
	
	Input: m = 7, n = 3
	Output: 28
	Example 4:
	
	Input: m = 3, n = 3
	Output: 6
	 
	
	Constraints:
	
	1 <= m, n <= 100
	It's guaranteed that the answer will be less than or equal to 2 * 109.

 */
public class Dynamic08_UniquePaths {

	@Test
	private void test() {
		System.out.println(uniquePath(1, 1));
		System.out.println(uniquePath(2, 2));
		System.out.println(uniquePath(7, 3));
	}

	private int uniquePath(int m, int n) {
		int[][] dp = new int[m][n];

		for (int[] arr : dp) {
			Arrays.fill(arr, 1);
		}

		for (int col = 1; col < m; col++) {
			for (int row = 1; row < n; row++) {
				dp[col][row] = dp[col - 1][row] + dp[col][row - 1];
			}
		}
		return dp[m - 1][n - 1];
	}

}

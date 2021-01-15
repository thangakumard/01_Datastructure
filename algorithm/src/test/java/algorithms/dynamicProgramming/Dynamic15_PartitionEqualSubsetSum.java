package algorithms.dynamicProgramming;

import org.testng.annotations.Test;

/****
 * 
 * https://leetcode.com/problems/partition-equal-subset-sum/ Given a non-empty
 * array nums containing only positive integers, find if the array can be
 * partitioned into two subsets such that the sum of elements in both subsets is
 * equal.
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [1,5,11,5] Output: true Explanation: The array can be
 * partitioned as [1, 5, 5] and [11]. Example 2:
 * 
 * Input: nums = [1,2,3,5] Output: false Explanation: The array cannot be
 * partitioned into equal sum subsets.
 * 
 * 
 * Constraints:
 * 
 * 1 <= nums.length <= 200 1 <= nums[i] <= 100
 *
 * 
 */

public class Dynamic15_PartitionEqualSubsetSum {

	@Test
	private void test() {
		int[] input = {1,5,11,5};
		System.out.println(canPartition(input));
	}
	public boolean canPartition(int[] nums) {
		int n = nums.length;
		int sum = 0;
		for (int i : nums)
			sum += i;

		if (sum % 2 != 0)
			return false;
		sum = sum / 2;

		boolean subset[][] = new boolean[n + 1][sum + 1];
		for (int i = 0; i <= n; i++)
			subset[i][0] = true;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= sum; j++) {
				if (j < nums[i - 1]) {
					subset[i][j] = subset[i - 1][j];
				} else {
					subset[i][j] = subset[i - 1][j] || subset[i - 1][j - nums[i - 1]];
				}
			}
		}
		return subset[n][sum];
	}
}

package algorithms.dynamicProgramming;

import java.util.*;

import org.testng.annotations.*;

/**
 * 
 * https://leetcode.com/problems/coin-change/submissions/
 * https://www.youtube.com/watch?v=1R0_7HqNaW0&list=PLi9RQVmJD2fZgRyOunLyt94uVbJL43pZ_&index=38
 *
 *
 * You are given coins of different denominations and a total amount of money
 * amount. Write a function to compute the fewest number of coins that you need
 * to make up that amount. If that amount of money cannot be made up by any
 * combination of the coins, return -1.
 * 
 * You may assume that you have an infinite number of each kind of coin.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: coins = [1,2,5], amount = 11 Output: 3 Explanation: 11 = 5 + 5 + 1
 * Example 2:
 * 
 * Input: coins = [2], amount = 3 Output: -1 Example 3:
 * 
 * Input: coins = [1], amount = 0 Output: 0 Example 4:
 * 
 * Input: coins = [1], amount = 1 Output: 1 Example 5:
 * 
 * Input: coins = [1], amount = 2 Output: 2
 * 
 * 
 * Constraints:
 * 
 * 1 <= coins.length <= 12 1 <= coins[i] <= 231 - 1 0 <= amount <= 104
 * 
 * Time complexity - O(coins.size * total) Space complexity - O(coins.size *
 * total)
 * 
 * Formula T[i] = Minimum (T[i] , 1 + T[i - coins[j])
 *
 */
public class Dynamic06_CoinChanging {

	@Test
	public void Test() {
		int[] coins = { 7, 2, 3, 6 };
		int total = 13;
		System.out.println(coinChange(coins, total));
	}

	public int coinChange(int[] coins, int amount) {
		Arrays.sort(coins);
		int[] dp = new int[amount + 1];

		Arrays.fill(dp, amount + 1);
		dp[0] = 0;
		for (int i = 0; i <= amount; i++) {
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] <= i) {
					dp[i] = Math.min(dp[i], 1 + dp[i - coins[j]]);
				} else {
					break;
				}
			}
		}

		return dp[amount] > amount ? -1 : dp[amount];
	}

}

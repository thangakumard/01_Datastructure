package algorithms.dynamicProgramming;

import org.testng.annotations.Test;

public class Dynamic07_ClimbingStairs {
	
	/*
	 * https://leetcode.com/problems/climbing-stairs/
	 * 
	 * 
	You are climbing a staircase. It takes n steps to reach the top.
	
	Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
	
	 
	
	Example 1:
	
	Input: n = 2
	Output: 2
	Explanation: There are two ways to climb to the top.
	1. 1 step + 1 step
	2. 2 steps
	Example 2:
	
	Input: n = 3
	Output: 3
	Explanation: There are three ways to climb to the top.
	1. 1 step + 1 step + 1 step
	2. 1 step + 2 steps
	3. 2 steps + 1 step
	 
	
	Constraints:
	
	1 <= n <= 45

	 */
	@Test
	private void test() {
		System.out.println(climbStairs(1));
		System.out.println(climbStairs(2));
		System.out.println(climbStairs(3));
		System.out.println(climbStairs(45));
	}

	/*
	 * Time complexity O(n)
	 * Space complexity O(n)
	 */
	public int climbStairs(int n) {
        if(n < 2) return n;
        int[] dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 2;
        for(int i=3; i<= n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
        
    }
}

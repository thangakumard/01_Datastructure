package algorithms.array.easy;

import org.testng.Assert;
import org.testng.annotations.Test;

/******

https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

You are given an array prices where prices[i] is the price of a given stock on the ith day.

You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.

 

Example 1:

Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
Example 2:

Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.
 

Constraints:

1 <= prices.length <= 105
0 <= prices[i] <= 104
 *
 */

public class Array03_BuySellStockProfit_01 {

	@Test
	private void test() {
		int[] prices = {7,1,5,3,6,4};
		Assert.assertEquals(5, maxProfit(prices)); 
		
		int[] prices_1 = {7,6,5,4,3,2};
		Assert.assertEquals(0, maxProfit(prices_1)) ;
	}
	
	/*
	 * Time complexity O(n)
	 * Space complexity O(1)
	 */
	public int maxProfit_01(int[] prices) {
        
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        
        for(int i=0; i< prices.length; i++){
            
            if(prices[i] < minPrice){
                minPrice = prices[i];
            }
            else if(maxProfit < prices[i] - minPrice){
                    maxProfit = prices[i] - minPrice;
            }  
       
        }
        
        return maxProfit;
    }
   
public int maxProfit(int[] prices) {
        int maxProfit = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;        
        for(int price: prices){
            minValue = Math.min(minValue, price);
            maxProfit = Math.max(maxProfit, price - minValue);
        }
        return maxProfit;
    }
}

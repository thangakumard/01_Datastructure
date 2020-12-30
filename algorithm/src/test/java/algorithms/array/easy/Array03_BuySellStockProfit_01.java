package algorithms.array.easy;

import org.testng.Assert;
import org.testng.annotations.Test;

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
	 public int maxProfit(int[] prices) {
		  if(prices.length < 2) return 0;
		 
	        int min_price = prices[0], max_profit = 0;
	        
	        for(int i=1; i< prices.length; i++) {
	        	if(prices[i] <= min_price) {
	        		min_price = prices[i];
	        	}else if(prices[i] - min_price > max_profit) {
	        			max_profit = prices[i] - min_price;
	        	}
	        	
	        }
	        
	      return max_profit;
	 }
}

package algorithms.array.easy;

import org.testng.Assert;
import org.testng.annotations.Test;


public class Array20_BuySellStockProfit_01 {

	@Test
	private void test() {
		int[] prices = {7,1,5,3,6,4};
		Assert.assertEquals(5, maxProfit(prices)); 
		
		int[] prices_1 = {7,6,5,4,3,2};
		Assert.assertEquals(0, maxProfit(prices_1)) ;
	}
	
	 public int maxProfit(int[] prices) {
	        int min_price = Integer.MAX_VALUE, max_profit = 0;
	        
	        for(int i=0; i < prices.length; i++){
	            if(min_price > prices[i]){
	                min_price = prices[i];
	            }
	            if(max_profit < prices[i] - min_price)
	                max_profit = prices[i] - min_price;
	        }
	    return max_profit;
	}
}

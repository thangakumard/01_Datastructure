package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;


public class Array61_ReverseInteger {
	
	@Test
	public void test() {
		Assert.assertEquals(321,reverse(123));
		Assert.assertEquals(-321,reverse(-123));
		Assert.assertEquals(-21,reverse(-120));
		Assert.assertEquals(0,reverse(1213239999));
	}
	
	 public int reverse(int x) {
	        
	        if(x == 0) return 0;
	        
	        boolean isNegative = false;
	        if(x < 0)
	            isNegative = true;
	        x = Math.abs(x);
	        long result = 0;
	        while(x > 0){
	            result = (result * 10) + (x % 10);
	            x = x / 10;
	        }
	    
	        if(result < Integer.MIN_VALUE || result > Integer.MAX_VALUE)
	            return 0;
	        
	        if(isNegative)
	            return - (int)result;
	        else
	             return (int)result;
	        
	    }

}

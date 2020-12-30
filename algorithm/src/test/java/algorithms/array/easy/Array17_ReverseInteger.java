package algorithms.array.easy;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/reverse-integer/
 * Given a 32-bit signed integer, reverse digits of an integer.

Note:
Assume we are dealing with an environment that could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

 

Example 1:

Input: x = 123
Output: 321
Example 2:

Input: x = -123
Output: -321
Example 3:

Input: x = 120
Output: 21
Example 4:

Input: x = 0
Output: 0
 

Constraints:

-231 <= x <= 231 - 1
 */

public class Array17_ReverseInteger {

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
	            return  -1 * (int)result;
	        else
	             return (int)result;
	        
	    }
}

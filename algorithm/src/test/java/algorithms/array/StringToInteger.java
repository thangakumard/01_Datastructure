package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;

public class StringToInteger {

	@Test
	public void converToInteger() {

		/*
		 * 1. null or empty string 2. white spaces 3. +/- sign 4. calculate real
		 * value 5. handle min & max
		 */
		
		Assert.assertEquals(100, stringToInt("100"));

	}

	private int stringToInt(String input) {
		
		//check null or empty string
		if (input == null || input.isEmpty()) {
			return 0;
		}
		
		//trim white spaces
		if(input.trim().isEmpty()) {
			return 0;
		}
		
		char flag = '+';
		int i =0;
		
		if(input.charAt(0) == '-'){
			flag = '-';
			i++;
		}
		else if(input.charAt(0) == '+'){
			flag = '+';
			i++;
		}
		
		double result = 0;
		//calculate value
		while(input.length() > i && input.charAt(i) >= '0' && input.charAt(i) <= '9'){
			result = result * 10 + (input.charAt(i)-'0');
			i++;
		}
		
		if(flag == '-'){
			result = -result;
		}
		
		if(result > Integer.MAX_VALUE)
			result = Integer.MAX_VALUE;
		
		if(result < Integer.MIN_VALUE)
			result = Integer.MIN_VALUE;
		
		return (int) result;
	}

}

package algorithms.string.math;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
/***
 * 
 * 
 * https://leetcode.com/problems/add-strings/
 * 
 * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.

Note:

The length of both num1 and num2 is < 5100.
Both num1 and num2 contains only digits 0-9.
Both num1 and num2 does not contain any leading zero.
You must not use any built-in BigInteger library or convert the inputs to integer directly.
 **********************************
 *Before solving think about the test case 
 *addition of num1 and num2 goes beyond integer.MAX_VALUE
 **********************************/

public class Math01_AddString {
	
	/*
	 * 
	 * NOTE: As inputs are string after addition result may go beyond the long max
	 * 
	 */
	
	@Test
	public void test() {
		Assertions.assertThat(addStrings("0", "0")).isEqualTo("0");
		Assertions.assertThat(addStrings("0", "9")).isEqualTo("9");
		Assertions.assertThat(addStrings("5", "5")).isEqualTo("10");
		Assertions.assertThat(addStrings("5", "9")).isEqualTo("14");
		Assertions.assertThat(addStrings("99", "9")).isEqualTo("108");
		Assertions.assertThat(addStrings("9999999999999999999999", "9")).isEqualTo("10000000000000000000008");
	}
	
	/**
	 * Time complexity O(max(n1,n2))
	 * Space complexity O(n) 
	 * @param num1
	 * @param num2
	 * @return
	 */
	public String addStrings(String num1, String num2) {
        if(num1.isEmpty()) return num2;
        if(num2.isEmpty()) return num1;
        
        int l1 = num1.length()-1, l2 = num2.length()-1;
        int x = 0, y =0, carry = 0;
        StringBuilder sb = new StringBuilder();
        while(l1 > -1 || l2 >-1 || carry > 0){
            
            x = l1 < 0 ? 0 : num1.charAt(l1) - '0'; /****** IMPORTANT TO USE SUBRACTION, NOT ADDITION OF ZERO ****/
            y = l2 < 0 ? 0 : num2.charAt(l2) - '0';
            sb.insert(0,(x + y + carry) %10);
            carry = (x+y+carry)/10;
            l1--;
            l2--;
        }
        return sb.toString();
    }

}

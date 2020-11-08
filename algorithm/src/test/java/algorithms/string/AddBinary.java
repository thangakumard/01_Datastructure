package algorithms.string;

import org.testng.Assert;
import org.testng.annotations.Test;

/****
 * 
    https://leetcode.com/problems/add-binary/description/
   
 	Given two binary strings, return their sum (also a binary string).

	The input strings are both non-empty and contains only characters 1 or 0.
	
	Example 1:
	
	Input: a = "11", b = "1"
	Output: "100"
	Example 2:
	
	Input: a = "1010", b = "1011"
	Output: "10101"
 *
 */

public class AddBinary {

	@Test
	public void add(){
		String a = "10", b = "1";
		Assert.assertEquals(MyFirstApproach(a, b), "11");
		Assert.assertEquals(BestApproach(a, b), "11");
	}
	private String BestApproach(String a, String b){
		if(a == null || a.length() == 0)
			return b;
		if(b == null || b.length() == 0)
			return a;
		
		int x = a.length()-1, y = b.length()-1;
		StringBuilder sb = new StringBuilder();
		int i = 0, j =0, carry = 0;
		
		while(x > -1 || y > -1 || carry == 1){
			i = (x > -1 ? Character.getNumericValue(a.charAt(x--)) : 0);
			j = (y > -1 ? Character.getNumericValue(a.charAt(y--)) : 0);
			sb.insert(0, i ^ j ^ carry);
			carry = (i + j + carry) >= 2 ? 1 : 0;
		}
		
		
		return sb.toString();
	}
	private String MyFirstApproach(String a, String b){
		
		if(a == null || a.length() == 0)
			return b;
		if(b == null || b.length() == 0)
			return a;
		
		int x = a.length()-1, y = b.length()-1;
		StringBuilder sb = new StringBuilder();
		String carry = "0";
		while(x >=0 && y >= 0){
			if(a.charAt(x) == '1' && b.charAt(y) == '1')
			{
				sb.insert(0, "1");
				carry = "1";
			}
			else if(a.charAt(x) == '1' || b.charAt(y) == '1'){
				sb.insert(0, (carry == "1" ? "0" : "1"));
				carry = "0";
			}
			else{
				sb.insert(0, carry);
				carry = "0";
			}
			x--;
			y--;
		}	
		while(x >= 0){
			sb.insert(0, a.charAt(x));
			x--;
		}
		while(y >= 0){
			sb.insert(0, b.charAt(y));
			y--;
		}

		return sb.toString();
	}
}

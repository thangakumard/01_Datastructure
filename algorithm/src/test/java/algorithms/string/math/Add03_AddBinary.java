package algorithms.string.math;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

public class Add03_AddBinary {

	@Test
	public void add(){
		String a = "10", b = "1";
		Assert.assertEquals(BestApproach(a, b), "11");
		int[] A = {1,2,3};
		int k = 10;
		 List<Integer> result = new ArrayList<Integer>();
	        for(int i=A.length-1; i> -1; i--){
	            result.add((A[i]+k) % 10);
	            k = (A[i]+k) / 10;
	        }
	}
	private String BestApproach(String a, String b){
		if(a == null || a.length() == 0)
			return b;
		if(b == null || b.length() == 0)
			return a;
		
		int x = a.length()-1, y = b.length()-1;
		StringBuilder sb = new StringBuilder();
		int i, j, carry = 0;
		
		while(x > -1 || y > -1 || carry == 1){
			i = (x > -1 ? Character.getNumericValue(a.charAt(x)) : 0);
			j = (y > -1 ? Character.getNumericValue(a.charAt(y)) : 0);
			sb.insert(0, i ^ j ^ carry);
			carry = (i + j + carry) >= 2 ? 1 : 0;
			x--;y--;
		}
		return sb.toString();
	}
}
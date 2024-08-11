package myInterviews;

import org.testng.annotations.Test;
/************
 * 
 * 19/June/2018
 * Write a library to add two big numbers
 *https://leetcode.com/problems/add-strings/description/
 */

public class amazon06_addLargeNumbers {
	
	@Test
	public void Test(){
		System.out.println(addStrings("10","10"));
		System.out.println(addStrings("99","99"));
		System.out.println(addStrings("99999","99999"));
		System.out.println(addStrings("9999999999","9999999999"));
		System.out.println(addStrings("99999999999999999999","99999999999999999999"));
		System.out.println(addStrings("9999999999999999999999999999999999999999","9999999999999999999999999999999999999999"));
		System.out.println(addStrings("99999999999999999999999999999999999999999999999999999999999999999999999999999999","99999999999999999999999999999999999999999999999999999999999999999999999999999999"));
	}

	public String addStrings(String num1, String num2) {

		StringBuilder result = new StringBuilder();

		int i = num1.length()-1, j = num2.length()-1, carry = 0, x = 0, y = 0;

		while(i >= 0 || j >= 0 || carry > 0){
			x = i < 0 ? 0 : num1.charAt(i) - '0'; /****** IMPORTANT TO USE SUBRACTION, NOT ADDITION OF ZERO ****/
			y = j < 0 ? 0 : num2.charAt(j) - '0';
			result.insert(0,(x + y + carry) %10);
			carry = (x+y+carry)/10;
			i--;
			j--;
		}
		return new String(result);
	}


	public String add_old(String x1, String x2){
		if(x1 == null || x1==""){
			x1 = "0";
		}
		if(x2 == null || x2==""){
			x2 = "0";
		}
		
		String result = "";
		String input1 = x1, input2 = x2;
		
		StringBuilder sb = new StringBuilder(); 
		long value1, value2 = 0, value3, carry = 0;
		while(input1 != "" && input2 != ""){
			if(input1.length() > 6){
				value1 = Long.parseLong(input1.substring(input1.length()-6, input1.length()));
				input1 = input1.substring(0,input1.length()-6);
			}
			else{
				value1 = Long.parseLong(input1);
				input1 = "";
			}
			if(input2.length() > 6){
				value2 = Long.parseLong(input2.substring(input2.length()-6, input2.length()));
				input2 = input2.substring(0,input2.length()-6);
			}
			else{
				value2 = Long.parseLong(input2);
				input2 = "";
			}	
			value3 = value1 + value2 + carry;
			if(String.valueOf(value3).length() > 6){
				String z = String.valueOf(value3);
				carry = Long.parseLong(z.substring(0, z.length()-6));
				value3 = Long.parseLong(z.substring(z.length()-6,z.length()));
			}
			else{
				carry = 0;
			}
			
			sb.insert(0,value3);
		}
		
		if(input1 != ""){
			if(input1.length() > 6){
				value1 = Long.parseLong(input1.substring(input1.length()-6, input1.length()));
				input1 = input1.substring(0,input1.length()-6);
			}
			else{
				value1 = Long.parseLong(input1);
				input1 = "";
			}
			value3 = value1+carry;
			if(String.valueOf(value3).length() > 6){
				String z = String.valueOf(value3);
				carry = Long.parseLong(z.substring(0, z.length()-6));
				value3 = Long.parseLong(z.substring(z.length()-6,z.length()));
			}
			else{
				carry = 0;
			}
			sb.insert(0, value3);
		}
		if(input2 != ""){
			if(input2.length() > 6){
				value2 = Long.parseLong(input2.substring(input2.length()-6, input2.length()));
				input2 = input1.substring(0,input2.length()-6);
			}
			else{
				value2 = Long.parseLong(input2);
				input2 = "";
			}
			value3 = value2+carry;
			if(String.valueOf(value3).length() > 6){
				String z = String.valueOf(value3);
				carry = Long.parseLong(z.substring(0, z.length()-6));
				value3 = Long.parseLong(z.substring(z.length()-6,z.length()));
			}
			else{
				carry = 0;
			}
			sb.insert(0, value3);
		}
		if(carry > 0){
			sb.insert(0,carry);
		}
		return sb.toString();
	}
	
	
}

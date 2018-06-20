package myInterviews;

import org.testng.annotations.Test;
/************
 * 
 * 19/June/2018
 * Write a library to add two big numbers
 *
 */

public class amazon06_addLargeNumbers {
	
	@Test
	public void Test(){
		System.out.println(add("10","10"));
		System.out.println(add("99","99"));
		System.out.println(add("99999","99999"));
		System.out.println(add("9999999999","9999999999"));
		System.out.println(add("99999999999999999999","99999999999999999999"));
		System.out.println(add("9999999999999999999999999999999999999999","9999999999999999999999999999999999999999"));
		System.out.println(add("99999999999999999999999999999999999999999999999999999999999999999999999999999999","99999999999999999999999999999999999999999999999999999999999999999999999999999999"));
	}
	
	public String add(String x1, String x2){		
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

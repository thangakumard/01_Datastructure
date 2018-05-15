package coreJava;

import org.testng.annotations.Test;

import org.testng.Assert;

public class StringAPIs {

	@Test
	public void compareString(){
	     String str = "abc";
	     char data[] = {'a', 'b', 'c'};
	     String str1 = new String(data);
	 
	     System.out.println(str.compareTo(str1));
	     Assert.assertEquals(str, str1);
	}
	
	@Test
	public void stringAPIs(){
		String input ="I am learning Java";
		
		System.out.println("charAt(int index) :" + input.charAt(0));
		
		System.out.println("codePointAt(int index) :" + input.codePointAt(0));
		
		System.out.println("codePointBefore(int index):" + input.codePointBefore(1));
		
		System.out.println("codePointCount(int beginIndex, int endIndex):" + input.codePointCount(0,2));
		System.out.println("");
	}
	
	@Test
	public void stringCompare(){
		System.out.println("********* (INT) STRING COMPARE ***********");
		System.out.println(" 1. -Ve result => Calling string will come first lexicograghically.");
		System.out.println(" 2. +Ve result => Arugument string will come first lexicograghically.");
		System.out.println(" 3. 0 result => Both strings are lexocographically equal.");
		
		String value = "ABC";
		System.out.println("Compare: ABC with abc : " + value.compareTo("abc"));
		System.out.println("Compare: ABC with Abc : " + value.compareTo("Abc"));
		
		value = "Apple";
		System.out.println("Compare: Apple with Banana : " + value.compareTo("Banana"));
		System.out.println("Compare: Apple with apple : " + value.compareTo("apple"));
		
		System.out.println("Compare: Apple with 123Apple : " + value.compareTo("123Apple") + ".Means 123Apple comes first.");
		
		System.out.println("********* (INT)STRING COMPARE IGNORE CASE***********");
		System.out.println("Compare: Apple with apple : " + value.compareToIgnoreCase("apple"));
		System.out.println("Compare: Apple with APPLE : " + value.compareToIgnoreCase("APPLE"));
		System.out.println("");
		
		System.out.println("********* (BOOLEAN)STRING COMPARE - CHAR ***********");
		System.out.println("contentEquals(CharSequence s): Apple with apple : " + value.contentEquals("apple"));
		System.out.println("contentEquals(CharSequence s): Apple with Apple : " + value.contentEquals("Apple"));
		System.out.println("");
		
		System.out.println("********* (BOOLEAN)STRING COMPARE - StringBuffer ***********");
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("apple");
		System.out.println("contentEquals(StringBuffer sb): Apple with apple : " + value.contentEquals(strBuffer));
		System.out.println("contentEquals(StringBuffer sb): Apple with Apple : " + value.contentEquals(strBuffer));
	
		System.out.println("");		
		System.out.println("********* (BOOLEAN)STRING CONTAINS ***********");
		System.out.println("contains(CharSequence s): Apple with apple : " + value.contains("App"));
	
		System.out.println("");		
		System.out.println("********* (BOOLEAN)equals(Object anObject) ***********");
		System.out.println("equals(Object anObject): Apple with Apple : " + value.equals("Apple"));
		
		System.out.println("");		
		System.out.println("********* (BOOLEAN)equalsIgnoreCase(String anotherString) ***********");
		System.out.println("equals(Object anObject): Apple with apple : " + value.equalsIgnoreCase("apple"));
	}
	
	@Test
	public void stringFormat(){
		
	}
}

package coreJava;

import org.testng.annotations.Test;

import java.util.*;

public class CastingPractice {
	
	/****
	 * Primitive Types
	 * ***************
			byte - 8 bit signed integer
			short - 16 bit signed integer
			int - 32 bit signed integer
			long - 64 bit signed integer
			float - 32 bit floating point number that can have decimal places
			double - 64 bit floating point number that can have decimal places
			boolean - represents logical operation that can either be true or false
			char - represents a single unicode (16 bit) character.
	 */
	
	@Test
	public void CharToInt() {
		String str = "198";
		//Convert each char into an integer and print it
	}
	
	@Test
	public void IntToChar() {
		int a = 10;
		//Convert Integer to char and print it
	}

	@Test
	public void StringToInt(){
		String str = "10";
		// To covert String to int (Primitive Type)

		// To covert int to Integer
	}
	
	@Test
	public void IntegerToString(){
					
		int mark = 100;
		System.out.println("using helper method in Integer:");
		System.out.println("using helper method in String:");
	}
	
	@Test
	public void DoubleToInt(){		
		
		double doubleValue = 15.4;
	    System.out.println(" 15.4: to integer ");
		System.out.println(" 15.4: to integer using helper method in Double");

	    //Convert Double to Int with Rounding
	    double doubleValue1 = -21.3;
		double doubleValue2 = -21.7;
		
	    System.out.println("Integer round(-21.3): ");
	    System.out.println("Integer round(-21.7): ");
	      

	}
	
	
	@Test
	public void DoubleToLong(){
		//Read more: http://www.java67.com/2014/11/how-to-convert-double-to-long-in-java-example.html#ixzz5AUPOJJjY
			
		// first example - converting double to long using longValue() method 
		double d = 102.9520; 
		System.out.println("double value=" + d + ", long=" );

		// second example - rather simple just cast double to long 
		double bill = 293.05;
		//long myBill = ;
		System.out.println("double value=" + bill + ", long=" );

		// third example - rounding double value to long in Java 
		double dbl = 3421.56;
		//long rnd =
		System.out.println("double value=" + dbl + ", long=");
	}
	
	
	@Test
	public void LongToInt(){	
		//Refer : https://stackoverflow.com/questions/1590831/safely-casting-long-to-int-in-java
		long foo = 10L;
		int bar = Math.toIntExact(foo);
		System.out.println("Math.toIntExact(foo) :" + Math.toIntExact(foo));
	}
	
	@Test
	public void ArrayToArrayList() {
		
		//Method 1
		Integer[] input_primitive = new Integer[] {1,2,3,4}; 
		List<Integer> lstInput_01 = Arrays.asList(input_primitive); //only if the input array is in Primitive type;
		
		//Method 2
		List<Integer> lstInput_02 = new ArrayList<>();
		Collections.addAll(lstInput_02, input_primitive);
		Collections.addAll(lstInput_02, 5 ,6 ,7);
		
	}
	
	@Test
	public void ArrayListToArray() {
		//Method 1
		List<Integer> lstValues = new ArrayList<Integer>();
		lstValues.add(10);
		lstValues.add(20);
		lstValues.add(30);
		lstValues.add(40);
		
		// Error: incompatible types: Object[] 
        // cannot be converted to Integer[] 
		//Integer[] arrError = lstValues.toArray(); 
		
		//the correct way is using public  T[] toArray(T[] arr)
		
//		Integer[] arr_primitive = new Integer[lstValues.size()];
//		arr_primitive =
		
		//Method 2 - Using stream method
//		int[] arr_int =
//		System.out.println("ArrayListToArray");
//		for(int v: arr_int) {
//			System.out.println(v);
//		}
		
	}
	
	@Test
	public void HashSetToArrayList() {
	
		HashSet<Integer> set = new HashSet<>();
		set.add(10);
		set.add(20);
		set.add(30);
		
		//List<Integer> lstInput =
//		for(int value: lstInput){
//			System.out.println(value);
//		}
	}
	
	@Test
	public void ArrayListToSet() {
		List<Integer> lstInput = new ArrayList<>();
		lstInput.add(10);
		lstInput.add(20);
		lstInput.add(30);
		lstInput.add(10);
		
//		Set<Integer> set =
//		for(int value: set){
//			System.out.println(value);
//		}
	}
	
	@Test
	public void ListofArraytoArrayOfArray() {
		List<int[]> lstArray = new ArrayList<>();
//		int[][] output =
	}
	
}

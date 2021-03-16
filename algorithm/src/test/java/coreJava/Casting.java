package coreJava;
import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Casting {
	
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
		String str = "123";
		System.out.println(Character.getNumericValue(str.charAt(0)));
		System.out.println(Character.getNumericValue(str.charAt(1)));
		System.out.println(Character.getNumericValue(str.charAt(2)));

	}

	@Test
	public void StringToInt(){
		
		String str = "10";
		int i = Integer.parseInt(str); // To covert String to int (Primitive Type)
		@SuppressWarnings("unused")
		Integer j = Integer.valueOf(i); // To covert int to Integer
		//Double d = new Double(0); Deprecated
	}
	
	@Test
	public void IntegerToString(){
					
		int mark = 100;
		String value1 = Integer.toString(mark);
		String value2 = String.valueOf(mark);
		System.out.println("Integer.toString(mark) :" + Integer.toString(mark));
		System.out.println("String.valueOf(mark) :" + String.valueOf(mark));
	}
	
	@Test
	public void DoubleToInt(){		
		
		double doubleValue = 15.4;
	    int intValue = (int) doubleValue;
	    System.out.println("(int) 15.4: " + intValue);

	    //Convert Double to Int with Rounding
	    double doubleValue1 = -21.3;
		double doubleValue2 = -21.7;
		
		int intValue1 = (int) Math.round(doubleValue1);
		int intValue2 = (int) Math.round(doubleValue2);
	    System.out.println("(int) Math.round(-21.3): " + intValue1);
	    System.out.println("(int) Math.round(-21.7): " + intValue2);
	      
		//Double to int/Integer
		Double d = 5.25;
		Integer value = d.intValue();
		int x = d.intValue();
	    System.out.println("(5.25).intValue(): " + value);
	}
	
	
	@Test
	public void DoubleToLong(){
		//Read more: http://www.java67.com/2014/11/how-to-convert-double-to-long-in-java-example.html#ixzz5AUPOJJjY
			
		// first example - converting double to long using longValue() method 
		double d = 102.9520; 
		long l = (new Double(d)).longValue(); 
		System.out.println("double value=" + d + ", long=" + l); 

		// second example - rather simple just cast double to long 
		double bill = 293.05; long myBill = (long) bill; 
		System.out.println("double value=" + bill + ", long=" + myBill); 

		// third example - rounding double value to long in Java 
		double dbl = 3421.56; long rnd = Math.round(dbl); 
		System.out.println("double value=" + dbl + ", long=" + rnd);		
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
		List<Integer> lstInput_02 =new ArrayList<Integer>();
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
		
		Integer[] arr_primitive = new Integer[lstValues.size()];
		arr_primitive = lstValues.toArray(arr_primitive);
		
		//Method 2
		int[] arr_int = lstValues.stream().mapToInt(i -> i).toArray();
		System.out.println("ArrayListToArray");
		for(int v: arr_int) {
			System.out.println(v);
		}
		
	}
	
	@Test
	public void HashSetToArrayList() {
	
		HashSet<Integer> set = new HashSet<Integer>();
		set.add(10);
		set.add(20);
		set.add(30);
		
		List<Integer> lstInput = new ArrayList<>(set);
	}
	
	@Test
	public void ArrayListToSet() {
		List<Integer> lstInput = new ArrayList<>();
		lstInput.add(10);
		lstInput.add(20);
		lstInput.add(30);
		
		Set<Integer> set = new HashSet<>(lstInput);
		
	}
	
}

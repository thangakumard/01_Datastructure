package coreJava;
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
	public void StringToInt(){
		
		String str = "10";
		int i = Integer.parseInt(str); // To covert String to int (Primitive Type)
		Integer j = Integer.valueOf(i); // To covert int to Integer
	}
	
	@Test
	public void DoubleToInt(){		
		
		double doubleValue = 15.4;
	    int intValue = (int) doubleValue;
	    
	    //Convert Double to Int with Rounding
	    double doubleValue1 = -21.3;
		double doubleValue2 = -21.7;
		int intValue1 = (int) Math.round(doubleValue1);
		int intValue2 = (int) Math.round(doubleValue2);
	    System.out.println("The first int value is: " + intValue1);
	    System.out.println("The second int value is: " + intValue2);
	      
		//Double to int/Integer
		Double d = 5.25;
		Integer value = d.intValue();
		int x = d.intValue();
	}
	
	
	@Test
	public void DoubleToLong(){
		//Read more: http://www.java67.com/2014/11/how-to-convert-double-to-long-in-java-example.html#ixzz5AUPOJJjY
			
		// first example - converting double to long using longValue() method 
		double d = 102.9520; long l = (new Double(d)).longValue(); 
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
	}
	@Test
	public void IntegerToString(){
					
		int mark = 100;
		String value1 = Integer.toString(mark);
		String value2 = String.valueOf(mark);
		
	}
}

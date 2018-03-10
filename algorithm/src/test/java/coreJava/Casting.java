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
	public void Test(){
		
		String str = "10";
		int i = Integer.parseInt(str);
		
		int j = 100;
		String value1 = Integer.toString(j);
		String value2 = String.valueOf(j);
		
		
		
	}
}


package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PowerOfTwo {
	
	@Test
	public void checkPowerOf2()
	{
		int input = 8;
		
		if( input < 1)
			Assert.fail();
		
		while(input > 1)
		{
			if((input & 1) == 1)// Get the first bit and check it is 1
				Assert.fail();
			
			input = input >> 1; // // Move the bits for 1 index to the right 
		}
		
		
		Assert.assertTrue(input == 1);
	}

}

package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstNonRepeatingChar {
	
	@Test
	public void getFirstNonRepeating()
	{
		char[] input = "Thangakumar".toCharArray();
		
		char[] counter = new char[256];
		
		for(int i=0; i < 256; i ++)
		{
			counter[i] = 0;
		}
		
		for(int j=0; j < input.length; j++)
		{
			counter[input[j]] += 1;
		}
		
		for(int k=0; k < input.length; k++)
		{
			if(counter[input[k]] == 1)
			{
				Assert.assertEquals('T', input[k]);
				break;
			}
		}
		
	}

}

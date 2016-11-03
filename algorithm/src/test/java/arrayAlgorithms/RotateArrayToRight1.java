package arrayAlgorithms;


import org.testng.Assert;
import org.testng.annotations.Test;

public class RotateArrayToRight1 {
	
	
	@Test
	public void RotateArray()
	{
	      int[] input = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
          int[] output = { 7, 8, 9, 1, 2, 3, 4, 5, 6 };
          
          int k=3;       
          int length = input.length;
          
          if(k==0)
        	  Assert.assertTrue(true);
          
          rotate(input,0,length-1-k);
          rotate(input,length-k,length-1);
          rotate(input,0,length-1);
          
          Assert.assertEquals(input, output);
	}
	
	private void rotate(int[] input,int start, int end)
	{
		int temp = 0;
		while(start < end)
		{
			temp = input[start];
			input[start++] = input[end];
			input[end--] = temp;
		}
	}

}

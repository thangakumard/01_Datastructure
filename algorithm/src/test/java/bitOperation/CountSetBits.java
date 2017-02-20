package bitOperation;

import org.testng.annotations.Test;

public class CountSetBits {

	
	/*
	 * Input: n = 6 (110)
	 * Output : 2
	 * */
	 @Test
	public void SimpleApproach()
	{
		int n = 6;
		int counter = 0;
		
		while(n > 0){			
			counter += n & 1;
			n >>= 1;
		}
		System.out.println(counter);		
	}
	 
	 @Test
	 public void BrianKernighanAlgorithm()
	 {
		 int counter = 0;
		 int n = 6;
		 
		 while(n > 0)
		 {
			 n &= (n-1);
			 counter++;
		 }
		 System.out.println(counter);
	 }
	
}

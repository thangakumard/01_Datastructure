package algorithms.bitOperation;

import org.testng.annotations.Test;

public class SwapAllOddAndEvenBits {

	@Test
	public void SwapBits()
	{
		int n = 23;
		
		int even_bits = n & 0xAAAAAAAA;
		int odd_bits = n & 0x55555555;
		
		even_bits >>= 1;
		odd_bits >>= 1;
		
		System.out.println(even_bits | odd_bits);
	}
}

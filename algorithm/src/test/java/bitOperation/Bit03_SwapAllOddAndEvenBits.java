package bitOperation;
import org.testng.annotations.*;

public class Bit03_SwapAllOddAndEvenBits {
	
	@Test
	public void test(){
		System.out.println("********************");
		System.out.println(swapBits(23));
		System.out.println("********************");
	}
	
	
	private int swapBits(int n){
		
		//get all the even bits of n
		//The number 0xAAAAAAAA is a 32 bit number with all even bits set as 1 and all odd bits as 0.
		int evenBits = n & 0xAAAAAAAA;
		
		//get all the odd bits of n
		//The number 0x55555555 is a 32 bit number with all odd bits set as 1 and all even bits as 0.
		int oddBits = n & 0x55555555;
		
		//Right shift even bits
		evenBits >>= 1;
		
		//Left shift odd bits
		oddBits <<= 1;
		
		//Combine even and odd bits
		return (evenBits | oddBits);
	}
	
}

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
		int evenBits = n & 0xAAAAAAAA;
		
		//get all the odd bits of n
		int oddBits = n & 0x55555555;
		
		//Right shift even bits
		evenBits >>= 1;
		
		//Left shift odd bits
		oddBits <<= 1;
		
		//Combine even and odd bits
		return (evenBits | oddBits);
	}
	
}

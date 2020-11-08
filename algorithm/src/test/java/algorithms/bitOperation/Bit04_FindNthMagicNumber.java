package algorithms.bitOperation;

import org.testng.annotations.*;

import algorithms.array.PowerOfTwo;
/*********
 * 
 * A magic number is defined as a number which can be expressed as a power of 5 
 * or sum of unique powers of 5. First few magic numbers are 5, 25, 30(5 + 25), 125, 130(125 + 5), �.
 * 
 * IF Input = 2
 * Output = 25
 * 
 * IF Input = 5
 * Output = 130
 */
public class Bit04_FindNthMagicNumber {

	@Test
	public void test(){
		System.out.println("***************");
		System.out.println(nthMagic(3));
		System.out.println("*************");
	}
	
	/**
	 * 
	 * if N = 3, Binary value of 3 is 011. 1st and 2nd bits are set.
	 * 0*5^3 + 1*5^2 + 1*5^1 = 0 + 25 + 5 = 30
	 * 
	 */
	private int nthMagic(int n){
		int magicNumber = 0;
		int bit = 0;
		int power = 1;		
		while(n > 0){
			power = power * 5;
			if((n & 1) == 1){
				magicNumber = magicNumber +  power;
			}
			n = n >> 1;			
		}
		return magicNumber;
	}
}

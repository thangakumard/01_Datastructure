package bitOperation;
import org.testng.annotations.*; 

public class Bit02_CountSetBitsInInteger {

	/********
	 * Input : n = 13
       Output : 3
	   Binary representation of 11 is 1101 and has 3 set bits
	 */
	@Test
	public void test(){
		System.out.println("*************");
		System.out.println(solution1(13));
		System.out.println("*************");
		
		System.out.println("***Brian Kernighan’s Algorithm***");
		System.out.println(BrianKernighanAlgorithm(13));
		System.out.println("*************");
		
		System.out.println("*** Using Lookup Table ***");
		System.out.println(usingLookupTable(13));
		System.out.println("*************");
	}
	
	/*****
	 * 
	 * Time Complexity is O(logn)
	 * Space Complexity is O(1)
	 */
	public int solution1(int n){
		int noOfSetBits = 0;		
		while(n > 0){
			if((n & 1) == 1)
			{
				noOfSetBits++;				
			}
			n = n >> 1;
		}		
		return noOfSetBits;
	}
	
	/************
	 * As per Brian Kernighan’s Algorithm: 
	 *  Untill n > 0 do this 
	 * 		1. n = (n & n-1)
	 * 		2. Counter ++
	 * 		3. Right shift n by 1
	 * Time Complexity is O(logn)
	 * Space Complexity is O(1)
	 */
	public int BrianKernighanAlgorithm(int n){
		int count =0;
		while(n > 0){
			n = n & n-1;
			count++;
		}
		return count;
	}
	
	public int usingLookupTable(int n){
		int[] table = {0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4};
		int count =0;
		for(int i =n; i > 0; i = i >> 4){
			//Checking number of bits in each nibble
			count = count + table[i & 0xF];			
		}
		return count;
	}
}

package algorithms.bitOperation;

import org.testng.annotations.*; 
/**************
 * 
 * Input n = 12
   Output: 4
   
	12^i = 12+i hold only for i = 0, 1, 2, 3
	for i=0, 12+0 = 12^0 = 12
	for i=1, 12+1 = 12^1 = 13
	for i=2, 12+2 = 12^2 = 14
	for i=3, 12+3 = 12^3 = 15
	
	https://www.geeksforgeeks.org/equal-sum-xor/
 *
 */

public class Bit01_SumEqualXOR {

	@Test
	public void test(){
		
		System.out.println("*************");
		System.out.println(solution1(12));
		System.out.println("*************");
		
		System.out.println("*************");
		System.out.println(solution2(12));
		System.out.println("*************");
	}	
	
	public int solution1(int n){
		int count = 0;
		
		for(int i=0; i < n; i++){
			if(n+i == (n^i)){
				count++;
			}
		}		
		return count;
	}
	
	/****
	 * A+B = A XOR B + 2 * (A AND B). To make A+B = A XOR B, we have to make (A AND B) as 0
	 * In the given problem A is n and B is i. 
	 * So to make n AND i == 0 binaray value of i should have 0 
	 * where ever binaray value of n has 1. 
	 * That will be 2 * unsetbit (no of zeros) in n
	 */
	public int solution2(int n){
		int noOfZeros =0;
		while(n > 0){
			if((n & 1) == 0)
				noOfZeros++;
			n = n >> 1; // Right shift to check next bit
		}		
		return 1 << noOfZeros; // 1 * 2 ^ noOfZeros		
	}
}

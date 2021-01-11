package algorithms.bitOperation;

import org.testng.annotations.Test;

/**
 * 
 * https://leetcode.com/problems/number-of-1-bits/
 *
 * Write a function that takes an unsigned integer and returns the number of '1'
 * bits it has (also known as the Hamming weight).
 * 
 * Note:
 * 
 * Note that in some languages such as Java, there is no unsigned integer type.
 * In this case, the input will be given as a signed integer type. It should not
 * affect your implementation, as the integer's internal binary representation
 * is the same, whether it is signed or unsigned. In Java, the compiler
 * represents the signed integers using 2's complement notation. Therefore, in
 * Example 3 above, the input represents the signed integer. -3. Follow up: If
 * this function is called many times, how would you optimize it?
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: n = 00000000000000000000000000001011 Output: 3 Explanation: The input
 * binary string 00000000000000000000000000001011 has a total of three '1' bits.
 * Example 2:
 * 
 * Input: n = 00000000000000000000000010000000 Output: 1 Explanation: The input
 * binary string 00000000000000000000000010000000 has a total of one '1' bit.
 * Example 3:
 * 
 * Input: n = 11111111111111111111111111111101 Output: 31 Explanation: The input
 * binary string 11111111111111111111111111111101 has a total of thirty one '1'
 * bits.
 * 
 * 
 * Constraints:
 * 
 * The input must be a binary string of length 32
 */

public class Bit15_numberOf1Bits {

	@Test
	private void test() {
		SimpleApproach(3);
		SimpleApproach(-3);
	}
	public void SimpleApproach(int n) {
		
		int counter = 0;

		while (n != 0) {
			int last_digit = n % 2;
			counter += last_digit & 1;
			n >>>= 1; 
			/*
			 * >> operator fills LSB bit 0 for +ve numbers and 1 for -ve numbers
			 * >>> operator fills LSB bit 0 for both +ve and -ve numbers
			 */
		}
		System.out.println(counter);
	}

}

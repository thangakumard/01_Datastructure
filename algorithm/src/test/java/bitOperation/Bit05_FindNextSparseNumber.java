package bitOperation;

import org.testng.annotations.*;
/*******
 * 
 * A number is Sparse if there are no two adjacent 1s in its binary representation. 
 * For example 5 (binary representation: 101) is sparse, 
 * but 6 (binary representation: 110) is not sparse.
 * Given a number x, find the next smallest Sparse number which greater than or equal to x.
 *
 * Input: x = 6
 * Output: Next Sparse Number is 8
	
 * Input: x = 4
 * Output: Next Sparse Number is 4
	
 * Input: x = 38
 * Output: Next Sparse Number is 40
	
 * Input: x = 44
 * Output: Next Sparse Number is 64
 */
public class Bit05_FindNextSparseNumber {

	public void test(){
		
	}
	
	public boolean simpleSolution(int n){
		
		int i = 1;
		i = i & (n & 1);
		n = n >> 1;
		while(n > 0){
			i = i & (n & 1);
			if(i == 1)
				return false;
			n = n >> 1;
		}
		return true;
	}
	
}

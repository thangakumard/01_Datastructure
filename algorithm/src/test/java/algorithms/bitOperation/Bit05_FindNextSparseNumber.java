package algorithms.bitOperation;

import java.util.ArrayList;

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

	@Test
	public void test(){		
		System.out.println("**************************");
		System.out.println(simpleSolution(6));
		System.out.println("**************************");
		System.out.println(simpleSolution(4));
		System.out.println("**************************");
		System.out.println(simpleSolution(38));
		System.out.println("**************************");
		System.out.println(simpleSolution(44));
		System.out.println("**************************");
		System.out.println(efficientApproach(44));
		System.out.println("**************************");
	}
	
	public int  simpleSolution(int n){			
		while(true){
			if(isSparse(n)){
				return n;
			}else{
				n++;
			}
		}		
	}
	
	public boolean isSparse(int n){
		
		int continuousSetBit = 0;
		while(n > 0){			
			if((n & 1) == 1){
				continuousSetBit ++;
			}
			else{
				continuousSetBit = 0;
			}
			if(continuousSetBit == 2)
				return false;
			n = n >> 1;
		}		
		return true;
	}
	
	public int efficientApproach(int n){
		
		while(n > 0){
			if(isSparse(n)){
				return n;
			}else{
				n = fixNumber(n);
			}
		}	
		return 0;
	}
	
	private int fixNumber(int n){
		return 0;
//		ArrayList<Integer> binaryValue = new ArrayList<Integer>();
//		while(n > 0){
//			binaryValue.add(n&1);
//			n = n >> 1;
//		}		
//		while(binaryValue.size() < 8){
//			binaryValue.add(0);
//		}
//		int last_index = -1;
//		for(int i = binaryValue.size()-1; i > 1; i--){
//			
//			if(binaryValue.get(i) == 1 && binaryValue.get(i+1) == 1 && binaryValue.get(i-1) == 0){
//				binaryValue.set(i+1, 1);			
//				for(int j = i-1; j > last_index; j --){
//					binaryValue.set(j, 0);									
//				}
//				last_index = i-1;
//			}
//		}
//		
//		//Find decimal equivalent of modified bin[]
//		int ans = 0;
//		for(int i=0; i < binaryValue.size();i++){
//			ans += binaryValue.get(i) * (1 << i);
//		}
//		return ans;
	}
	
}

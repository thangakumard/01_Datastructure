package algorithms.array;

import org.testng.annotations.*;
/******
 * 
	https://www.geeksforgeeks.org/array-rotation/
 */

public class Array38_RotateArray_Left {

	@Test
	public void Test(){
		int[] input = {10,20,30,40,50,60};
		int k = 2; //rotate numbers
		int[] result = rotateArray(input, k);	
		
		for(int i=0; i< input.length; i++){
			System.out.println(result[i]);
		}
	}
	
	private int[] rotateArray(int[] input, int k){
		
		int[] result = new int[input.length];
		int n = input.length;
		int mod = k % n;
		
		for(int i=0; i < n; i++){
			int j = (i+mod) % n;
			result[i] = input[j];
		}
		return result;
	}
	
}

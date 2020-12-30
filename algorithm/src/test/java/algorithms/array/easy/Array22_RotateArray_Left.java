package algorithms.array.easy;

import org.testng.Assert;
import org.testng.annotations.*;
/******
 * 
	https://www.geeksforgeeks.org/array-rotation/
	https://leetcode.com/problems/rotate-array/
	
 */

public class Array22_RotateArray_Left {

	@Test
	public void rotateArrayLeft() {
		int[] input = { 10,20,30,40,50 };
		int[] expected = {30,40,50,10,20};
		
		int n = input.length;
		int rotateCount = 22;
		int[] output = rotateArray(input,rotateCount);
		for(int i=0; i < output.length ; i++){
			System.out.println(output[i]);
		}
	}
	
	private int[] rotateArray(int[] input, int k){
		int[] result = new int[input.length];
		int l = input.length;
		k = k%l;
		if(k ==0) return input;
		
		for(int i=0; i < l; i++){
			int j = (i+l-k) % l;
			result[j] = input[i];
		}
		return result;
	}
	
}

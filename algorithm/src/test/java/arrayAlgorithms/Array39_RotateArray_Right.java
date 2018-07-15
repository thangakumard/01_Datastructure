package arrayAlgorithms;
import org.testng.annotations.*;

/****
 https://leetcode.com/problems/rotate-array/description/

	Given an array, rotate the array to the right by k steps, where k is non-negative.
	
	Example 1:
	
	Input: [1,2,3,4,5,6,7] and k = 3
	Output: [5,6,7,1,2,3,4]
	Explanation:
	rotate 1 steps to the right: [7,1,2,3,4,5,6]
	rotate 2 steps to the right: [6,7,1,2,3,4,5]
	rotate 3 steps to the right: [5,6,7,1,2,3,4]
	Example 2:
	
	Input: [-1,-100,3,99] and k = 2
	Output: [3,99,-1,-100]
	Explanation: 
	rotate 1 steps to the right: [99,-1,-100,3]
	rotate 2 steps to the right: [3,99,-1,-100]
*/
public class Array39_RotateArray_Right {

	@Test
	public void Test(){
		
		int[] input = {10,20,30,40,50,60};
		rightRotate(input, 3);
		for(int i=0; i < input.length ; i++){
			System.out.println(input[i]);
		}
	}
	
	public void rightRotate(int[] input, int d){
		
		reverse(input, 0, input.length-1-d);
		reverse(input, input.length-d, input.length-1);
		reverse(input,0, input.length-1);
	}
	
	private void reverse(int[] input, int start, int end){
		
		int temp = 0;
		while(start < end){			
			temp = input[start];
			input[start] = input[end];
			input[end] = temp;
			
			start++;
			end--;
		}
		
	}
}

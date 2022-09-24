package algorithms.array.easy;
import org.testng.annotations.*;

/****
 https://leetcode.com/problems/rotate-array/

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
public class Array23_RotateArray_Right_Clockwise {

	@Test
	public void Test(){
		
		int[] input = {10,20,30,40,50,60};
		//rightRotate_01(input, 3);
		rightRotate_02(input, 30);
		for(int i=0; i < input.length ; i++){
			System.out.println(input[i]);
		}
	}
	
	private void rightRotate_01(int[] nums, int k) {
        
        int l = nums.length;
         k = k % l; 
        
        int[] result = new int[nums.length];
        
        if(k == 0) return;
        int j;
        for(int i =0; i < l; i++){
            j = (i+k) % l;
            result[j] = nums[i];
        }
        
       System.arraycopy(result,0,nums,0,l);
    }
	
	public void rightRotate_02(int[] input, int k){
		
		int l = input.length;
		k = k % l;

		/*
		Input: [1,2,3,4,5,6,7] and k = 3
		Step 1: [4,3,2,1,5,6,7]
		Step 2: [4,3,2,1,7,6,5]
		Step 3: [5,6,7,1,2,3,4]
		*/
		reverse(input, 0, l-k-1);
		reverse(input, l-k, l-1);
		reverse(input,0, l-1);
	}
	
	private void reverse(int[] input, int start, int end){
		
		int temp = 0;
		while(start < end){		//use proper looping statement. Last time mistakenly I used If condition 	
			temp = input[start];
			input[start] = input[end];
			input[end] = temp;
			
			start++;
			end--;
		}
		
	}
	
	 
}

package algorithms.array;

import org.testng.annotations.*;
/****
 *
 * 
 https://www.geeksforgeeks.org/convert-array-into-zig-zag-fashion/
 
	Convert array into Zig-Zag fashion
	Given an array of distinct elements, rearrange the elements of array in zig-zag fashion in O(n) time. 
	The converted array should be in form a < b > c < d > e < f.
	
	Example: 
	Input:  arr[] = {4, 3, 7, 8, 6, 2, 1}
	Output: arr[] = {3, 7, 4, 8, 2, 6, 1}
	
	Input:  arr[] =  {1, 4, 3, 2}
	Output: arr[] =  {1, 4, 2, 3}
 *
 */

public class Array41_ZigZagArray {
	
	@Test
	public void test(){
		int[] input = {4, 3, 7, 8, 6, 2, 1};//{10,20,30,40,50,60,70,80,90,100};
		zigZag(input);
		for(int i=0; i < input.length; i++){
			System.out.print(input[i] + ", ");
		}
		System.out.println();
	}
	
	public void zigZag(int[] input){
		
		int i = 0;
		int temp = 0;
		boolean flip = true;
		while(i < input.length-1){
			if(flip){
				if(input[i] > input[i+1]){				
					temp = input[i];
					input[i] = input[i+1];
					input[i+1] = temp;
				}
			}
			else{
				if(input[i] < input[i+1]){
					temp = input[i];
					input[i] = input[i+1];
					input[i+1] = temp;
				}
			}
			flip = !flip;
			i++;
		}
		
	}
}

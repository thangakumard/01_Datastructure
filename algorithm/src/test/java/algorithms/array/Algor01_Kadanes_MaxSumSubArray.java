package algorithms.array;

import org.testng.annotations.Test;
/***************
 * 
 * https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
 * Write an efficient C program to find the sum of contiguous subarray 
 * within a one-dimensional array of numbers which has the largest sum.
 * -2, -3, 4, -1, -2, 1, 5, -3
 * 	Maximum Array Sum is : 4, -1, -2, 1, 5 = 7	
 **************/


public class Algor01_Kadanes_MaxSumSubArray {
	@Test
	public void Test(){
				
		//int[] input ={1,3,-2,4,-2,1};
		int[] input ={-2,1,-3,4,-1,2,1,-5,4};
		System.out.println(kadanesAlgorithm(input));
	}
	
	private int kadanesAlgorithm(int[] input){
		
		int size = input.length;
		int max_so_for = Integer.MIN_VALUE;
		int sum_so_far = 0;
		
		for(int i=0; i< size; i++){
			sum_so_far = sum_so_far + input[i];
			
			Math.max(max_so_for ,sum_so_far);
			if(max_so_for < 0){
				max_so_for = 0;
			}
		}
		
		return max_so_for;
	}

}

package arrayAlgorithms;

import org.testng.annotations.Test;
/***************
 * 
 * https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
 * Write an efficient C program to find the sum of contiguous subarray 
 * within a one-dimensional array of numbers which has the largest sum.
 **************/


public class Algor01_Kadanes_MaxSumSubArray {
	@Test
	public void Test(){
				
		int[] input ={1,3,-2,4,-2,1};
		System.out.println(kadanesAlgorithm(input));
	}
	
	private int kadanesAlgorithm(int[] input){
		
		int size = input.length;
		int max_so_for = Integer.MIN_VALUE;
		int max_ending_here = 0;
		
		for(int i=0; i< size; i++){
			max_ending_here = max_ending_here + input[i];
			
			if(max_so_for < max_ending_here){
				max_so_for = max_ending_here;
			}
			if(max_ending_here < 0){
				max_ending_here = 0;
			}
		}
		
		return max_so_for;
	}

}

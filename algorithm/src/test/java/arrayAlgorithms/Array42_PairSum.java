package arrayAlgorithms;

import org.testng.annotations.*;
/****
 * 
 	Given an array of integers, and a number ‘sum’, find the number of pairs of integers in the array whose sum is equal to ‘sum’.

	Examples:
	Input  :  arr[] = {1, 5, 7, -1}, 
	          sum = 6
	Output :  2
	Pairs with sum 6 are (1, 5) and (7, -1)
	
	Input  :  arr[] = {1, 5, 7, -1, 5}, 
	          sum = 6
	Output :  3
	Pairs with sum 6 are (1, 5), (7, -1) &
	                     (1, 5)         
	
	Input  :  arr[] = {1, 1, 1, 1}, 
	          sum = 2
	Output :  6
	There are 3! pairs with sum 2.
	
	Input  :  arr[] = {10, 12, 10, 15, -1, 7, 6, 
	                   5, 4, 2, 1, 1, 1}, 
	          sum = 11
	Output :  9
 *
 */

public class Array42_PairSum {

	@Test
	public void test(){
		int[] input = {-1, 4, 17, 6, 10, -8};
		mergeSort(input);
		printPairSum(input,16);
		
		
		for(int i=0; i < input.length; i++){
			System.out.println(input[i]);
		}
	}
	
	private void printPairSum(int[] input, int sum){
		
		int l= 0;
		int r = input.length-1;
		int total = 0;
		while(l < r){		
			total = input[l] + input[r];
			if(total < sum){
				l++;
			}
			else if(total > sum){
				r--;
			}
			else{
				System.out.println("Pair :" + input[l] + "," + input[r]);
				l++;
			}
		}
		
		
	}
	
	public void mergeSort(int[] input){
		splitAndSort(input, 0 , input.length-1);
	}
	
	private void splitAndSort(int[] input,int start,int end)
	{
		if(start == end) return;
		int mid = (start + end)/2;
		splitAndSort(input, start, mid);
		splitAndSort(input, mid+1, end);
		mergeAndSort(input, start, mid, end);
	}
	
	private void mergeAndSort(int[] input, int start, int mid, int end){
		
		int[] temp = new int[input.length];
		int tempIndex = start;
		
		int array1_left = start;
		int array1_right = mid;
		int array2_left = mid+1;
		int array2_right = end;
		
		while(array1_left <= array1_right && array2_left <= array2_right){			
			if(input[array1_left] <= input[array2_left]){
				temp[tempIndex] = input[array1_left];
				array1_left++;
			}
			else{
				temp[tempIndex] = input[array2_left];
				array2_left++;
			}
			tempIndex++;			
		}	
		
		while(array1_left <= array1_right){
			temp[tempIndex] = input[array1_left];
			array1_left++;
			tempIndex++;
		}
		
		while(array2_left <= array2_right){
			temp[tempIndex] = input[array2_left];
			array2_left++;
			tempIndex++;
		}
		
		int totalRecords = end-start;
		for(int i=0; i<= totalRecords;i++){
			input[start] = temp[start];
			start++;
		}
		
	}
}

package algorithms.array;

import org.testng.annotations.Test;

public class Array17_FloorAndCeilling {
	
	//http://www.geeksforgeeks.org/search-floor-and-ceil-in-a-sorted-array/
	// Assume than the array is sorted in non-decreasing order
	@Test
	//Time complexity is O(nlogn) 
	public void approach01(){
		
		int[] input = {1,5,10,15,20,25,30,35,40,45,50};
		int x = 5;
		int size = input.length;
		if(x < input[0])
			System.out.println("No floor. Ceiling is" + input[0]);
		else if(x > input[size-1])
			System.out.println("No Ceiling. Floor is" + input[size-1]);
		else
			floor(input, 0, input.length-1, x);
		
	}
	
	public void floor(int[] input, int left, int right, int x){
		
		int mid = (left + right) /2;
		
		if(input[mid] == x){
			System.out.println("Floor and ceilling is :" + input[mid]);
		}			
		if(input[mid] < x){
			if(input[mid+1] > x){
				System.out.println("Floor is " + input[mid] + " and ceilling is :" + input[mid+1]);
			}
			if(input[mid+1] == x){
				System.out.println("Floor and ceilling is :" + input[mid+1]);	
			}
		}
		else
		{
			if(input[mid] < x)
				floor(input, mid+1, input.length,x);
			else
				floor(input, 0, mid-1 ,x);
		}
	}

}

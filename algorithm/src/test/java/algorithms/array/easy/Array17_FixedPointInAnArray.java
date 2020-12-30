package algorithms.array.easy;

import org.testng.annotations.Test;

/*
 *  https://leetcode.com/problems/fixed-point/
 http://www.geeksforgeeks.org/find-a-fixed-point-in-a-given-array/
		/*************
* Given an array of n distinct integers sorted in ascending order, 
* write a function that returns a Fixed Point in the array, if there is any Fixed Point present in array, 
* else returns -1. Fixed Point in an array is an index i such that arr[i] is equal to i. 
* Note that integers in array can be negative.
*
* Input: arr[] = {-10, -5, 0, 3, 7}
Output: 3  // arr[3] == 3 

Input: arr[] = {0, 2, 5, 8, 17}
Output: 0  // arr[0] == 0 


Input: arr[] = {-10, -5, 3, 4, 7, 9}
Output: -1  // No Fixed Point
*/

public class Array17_FixedPointInAnArray {

	@Test
	//Time complexity O(n)
	public void approach1(){
		
		System.out.println("Approach 1");
		int[] a = {1,2,2,5,6,7};
		int point = -1;
		for(int i=0; i < a.length; i++){
			if(a[i] == i){
				point = i;
				break;
			}
		}
		if(point >=0 ){
			System.out.println("Fixed point is :" + point);
		}
		else{
			System.out.println("Fixed point is not available");
		}
	}
	
	@Test
	//Time Complexity: O(Logn)
	public void approach2(){
		System.out.println("Approach 2");
		int[] a = {1,2,2,5,6,7};
		int point = devideAndSearch(a, 0, a.length-1); 
		if( point == -1){
			System.out.println("Fixed point is not available");
		}else{
			System.out.println("Fixed point is :" + point );
		}
	}
	
	public int devideAndSearch(int[] input,int left,int right){
		if(left < right){
			int mid = (left+right)/2;
			if(mid == input[mid]){
				return mid;
			}
			if(mid > input[mid]){
				return devideAndSearch(input, mid+1, right);
			}
			else {
				return devideAndSearch(input, left, mid-1);
			}
		}
		return -1;
	}
}

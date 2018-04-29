package arrayAlgorithms;

import org.testng.annotations.Test;
/***
 * 
 * 
 * https://www.geeksforgeeks.org/count-strictly-increasing-subarrays/
Given an array of integers, count number of subarrays (of size more than one) that are strictly increasing.
Expected Time Complexity : O(n)
Expected Extra Space: O(1)

Examples:

Input: arr[] = {1, 4, 3}
Output: 1
There is only one subarray {1, 4}

Input: arr[] = {1, 2, 3, 4}
Output: 6
There are 6 subarrays {1, 2}, {1, 2, 3}, {1, 2, 3, 4}
                      {2, 3}, {2, 3, 4} and {3, 4}

Input: arr[] = {1, 2, 2, 4}
Output: 2
There are 2 subarrays {1, 2} and {2, 4}
 * 
 *
 */

public class Array11_StrictlyIncreasingSubarray {

	
	public void approach1(){
		//linear search
	}
	
	@Test
	public void appraoch2(){
		int[] a={1, 2, 2, 4};
		int len = 1;
		int cnt =0;
		
		for(int i=0; i < a.length-1; i++){
			if(a[i+1] > a[i]){
				len++;
			}
			else{
				cnt += (len * (len-1))/2;
				len = 1;
			}
		}
		if(len > 1){
			cnt += (len * (len-1))/2;
		}
		System.out.println("Number of increasing sub-arrays are : " + cnt);
	}
	
}

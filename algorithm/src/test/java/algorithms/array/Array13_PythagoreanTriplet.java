package algorithms.array;

import java.util.Arrays;

/***
 * Given an array of integers, write a function that returns true 
 * if there is a triplet (a, b, c) that satisfies a2 + b2 = c2.
Example:

Input: arr[] = {3, 1, 4, 6, 5}
Output: True
There is a Pythagorean triplet (3, 4, 5).

Input: arr[] = {10, 4, 6, 12, 5}
Output: False
There is no Pythagorean triplet.
 */

import org.testng.annotations.Test;
/*********
 * 
 * https://www.geeksforgeeks.org/find-pythagorean-triplet-in-an-unsorted-array/
 *Given an array of integers, write a function that returns true if there is a triplet (a, b, c) that satisfies a2 + b2 = c2.

	Example:
	
	Input: arr[] = {3, 1, 4, 6, 5}
	Output: True
	There is a Pythagorean triplet (3, 4, 5).
	
	Input: arr[] = {10, 4, 6, 12, 5}
	Output: False
	There is no Pythagorean triplet.
 */
public class Array13_PythagoreanTriplet {

	@Test
	//Time complexity O(n^3)
	public void approach01(){
		int[] a = {3, 1, 4, 6, 5};
		int n = a.length;

		System.out.println("Approch 1");
		System.out.println("Given input has pythagorean : " + pythagorean(a, n)); 
	}

	boolean pythagorean(int[] input, int n){
		for(int i=0; i < n; i++){

			for(int j=i+1; j < n; j++){

				for(int k=j+1; k < n; k++ ){

					int x = input[i] * input[i], y = input[j] * input[j], z = input[k] * input[k];

					if(x == y + z || y == x + z || z == x + y)
						return true;
				}
			}
		}
		return false;
	}

	@Test
	//Time complexity n(log n)
	public void approach2(){
		int[] a = {3,1,4,6,5};
		int n = a.length;
		boolean pythagorean = false; 
		
		for(int i=0; i<n; i++){
			a[i] = a[i] * a[i];
		}
		Arrays.sort(a);
		
		for(int j=n-1; j >= 2; j--){
			
			int l = 0; //starting index 
			int r = j-1; //index of last index
			
			while(l < r){
				if(a[l] + a[r] == a[j])
					pythagorean = true;
				if(a[l] + a[r] < a[j]){
					l++;
				}else
				{
					r--;
				}
			}
		}
		System.out.println("Pythagoran : " + pythagorean);
	}

	int[] sort(int[] input, int left, int right){
		int[] a = input; 
		
		int pivotValue = input[left];
		int initalLeft = left;
		int initalRight = right;
		
		while(left < right){
			
			while(pivotValue < a[right] && left < right){
				right --;
			}
			if(left < right){
				a[left] = a[right];
				left++;
			}
			
			while(a[left] > pivotValue){
				left++;
			}
			if(left < right){
				a[right] = a[left];
				right--;
			}
		}
		
		a[left] = pivotValue;
		int pivotIndex = left;
		
		if(pivotIndex-1 > initalLeft){
			sort(input , initalLeft, pivotIndex - 1);
		}
		if(pivotIndex+1 < initalRight ){
			sort(input, pivotIndex + 1, initalRight);
		}
		
		return a;
	}

}

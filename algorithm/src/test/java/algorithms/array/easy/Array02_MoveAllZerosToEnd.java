package algorithms.array.easy;

import org.testng.annotations.Test;
/*********
 * https://leetcode.com/problems/move-zeroes/
 *
 * https://www.geeksforgeeks.org/move-zeroes-end-array/
 * Given an array of random numbers, Push all the zero�s of a given array to the end of the array. For example, if the given arrays is {1, 9, 8, 4, 0, 0, 2, 7, 0, 6, 0}, it should be changed to {1, 9, 8, 4, 2, 7, 6, 0, 0, 0, 0}. The order of all other elements should be same. Expected time complexity is O(n) and extra space is O(1).

	Input :  arr[] = {1, 2, 0, 4, 3, 0, 5, 0};
	Output : arr[] = {1, 2, 4, 3, 5, 0, 0};
	
	Input : arr[]  = {1, 2, 0, 0, 0, 3, 6};
	Output : arr[] = {1, 2, 3, 6, 0, 0, 0};
 *
 */
public class Array02_MoveAllZerosToEnd {

	@Test
	//Time complexity O(n/2) 
	public void approach01(){
		
		System.out.println("Approach 01");
		int[] a = {1,2,0,4,0,5,0,0,6,0,0};
		int count=0;
		
		int left = 0, right = a.length-1;
		
		while(left < right){
			
			while(a[left] != 0){
				left++;
			}
			while(a[right] == 0){
				right--;
			}
			a[left] = a[right];
			a[right] = 0;
			left++;
			right--;
			count++;
		}
		
		System.out.println("Loop counter :" + count);
		System.out.println();
		for(int i=0;i < a.length; i++){
			System.out.print(a[i] +",");			
		}
	}
	
	@Test
	//Time complexity O(n)
	public void approach02(){
		
		System.out.println();
		System.out.println("Approach 02");

		int[] b = {1,2,0,4,0,50,7,0,2,0};
		int j = 0;
		int size = b.length;
		
		for(int i=0; i < size; i++){
			if(b[i] != 0)
				b[j++] = b[i];
		}
		
		while(j < size)
			b[j++] = 0;
		
		for(int i=0;i < size; i++){
			System.out.println(b[i]);			
		}
	}
}

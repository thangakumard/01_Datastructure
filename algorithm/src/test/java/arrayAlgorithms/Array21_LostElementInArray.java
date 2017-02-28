package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/find-lost-element-from-a-duplicated-array/
public class Array21_LostElementInArray {

	@Test
	//Time complexity is O(n+n) = O(n)
	public void approach01(){
		int[] a = {1,2,3,4,5,6,7,8,9,10};
		int[] b = {1,2,3,4,5,6,7,8,9};
		
		int x=0;
		
		for(int i=0; i < a.length; i++){
			x = x ^ a[i]; 
		}
		for(int j=0; j < b.length; j++){
			x = x ^ b[j]; 
		}		

		
		System.out.println("Missing element is :" + x);
	}
	
	@Test
	//Assume the array is in sorted order
	public void approach2(){
		
		int[] a = {1,2,3,4,5,6,7,8,9,10};
		int[] b = {1,2,3,4,5,6,7,8,9};
		
		
	}
	
	
	
}

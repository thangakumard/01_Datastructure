package algorithms.bitOperation;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/find-the-number-occurring-odd-number-of-times/
/*
* Given an array of positive integers. 
* All numbers occur even number of times except one number which occurs odd number of times. 
* Find the number in O(n) time & constant space.
*/
public class Bit13_OccurringOddNumberOfTime {

	@Test
	//Time complexity O(n).
	public void approach3(){
		System.out.println("Approach 3 :");
		int[] a = {1, 2, 3, 2, 3, 1, 3};			
		int x = a[0];
		
		for(int i=1; i < a.length; i++){
			x = x ^ a[i];
		}
		
		System.out.println("Odd occourence is :" + x);			
	}
}

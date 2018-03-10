package coreJava;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleArray {

	@Test
	public void Test(){
		
		  int[] testArray1 = {1, 2, 3, 4, 5};
	      int testArray2[] = {6, 7, 8, 9, 10};
	      int[] testArray3 = new int[] {11, 12, 13, 14, 15};
	      int testArray4[] = new int[] {16, 17, 18, 19, 20};
	      System.out.println(Arrays.toString(testArray1));
	      System.out.println(Arrays.toString(testArray2));
	      System.out.println(Arrays.toString(testArray3));
	      System.out.println(Arrays.toString(testArray4));
	      
	      
	      int[] testArray = new int[10];
	      Arrays.fill(testArray, 50);
	      System.out.println(Arrays.toString(testArray));//[50, 50, 50, 50, 50, 50, 50, 50, 50, 50]
	      
		//integer array
		int arr[] = {1,2,3,4,5};
		System.out.println(arr.length);
		System.out.println(arr[0]);
		
		char input[] = {'a','b','c'};
		System.out.println(input.length);
		
		
		
		
	}
	
}

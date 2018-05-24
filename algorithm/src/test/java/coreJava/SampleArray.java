package coreJava;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleArray {

	@Test
	public void Test(){
		
		 // 1. Initialize
			int[] testArray1 = new int[5];
			int[] testArray2 = {1, 2, 3};		
		 // 2. Get Length
			System.out.println("The size of testArray1 is: " + testArray1.length);
		// 3. Access Element
			System.out.println("The first element is: " + testArray1[0]);
		// 4. Iterate all Elements
			System.out.print("[Version 1] The contents of testArray1 are:");
			for (int i = 0; i < a1.length; ++i) {
			    System.out.print(" " + testArray1[i]);
			}
			System.out.println();
			System.out.print("[Version 2] The contents of testArray2 are:");
			for (int item: testArray2) {
			    System.out.print(" " + item);
			}
		System.out.println();
		// 5. Modify Element
			a1[0] = 4;
		// 6. Sort
			Arrays.sort(a1);		   
	      	// 7. fill	      
			int[] testArray3 = new int[10];
			Arrays.fill(testArray3, 50);
			System.out.println(Arrays.toString(testArray3));//[50, 50, 50, 50, 50, 50, 50, 50, 50, 50]
		// 8. Convert To List
			List list1 = Arrays.asList(testArray3);
			// printing the list
			System.out.println("The list is:" + list1);
		// 9. Binaray Search
			//binarySearch(int[] a, int key)
			//binarySearch(int[] a, int fromIndex, int toIndex, int key)
			Arrays.binarySearch(testArray3, 0, 5, 50);
		// 10. Compare 2 array
			Arrays.equals(testArray2,testArray3);
		/***** Char Array *******/
		char input[] = {'a','b','c'};
		System.out.println(input.length); //length is a variable
		
		String s = input.toString(); //NOT CORRECT 
		s = new String(input);
		System.out.println(s);
		System.out.println(s.length()); //length is  METHOD
		
	}
	
}

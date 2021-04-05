package coreJava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

public class SampleArray {

	@Test
	public void Test(){
		
		 // 1. Initialize
			int[] testArray1 = new int[5];
			int[] testArray2 = {1, 2, 3};
			int testArray4[] = new int[5];
		 // 2. Get Length
			System.out.println("The size of testArray1 is: " + testArray1.length);
		// 3. Access Element
			System.out.println("The first element is: " + testArray1[0]);
		// 4. Iterate all Elements
			System.out.print("[Version 1] The contents of testArray1 are:");
			for (int i = 0; i < testArray1.length; ++i) {
			    System.out.print(" " + testArray1[i]);
			}
			System.out.println();
			System.out.print("[Version 2] The contents of testArray2 are:");
			for (int item: testArray2) {
			    System.out.print(" " + item);
			}
		System.out.println();
		// 5. Modify Element
			testArray1[0] = 4;
		// 6. Sort
			Arrays.sort(testArray1);		   
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
		//11.So convert array to arrayList
		System.out.println("**** Merging 2 Arrays ****");
		System.out.println("****Array to List of Array Object****");
		String a[] = { "A", "E", "I" };
	    String b[] = { "O", "U" };
	    List list = new ArrayList(Arrays.asList(a));
	    list.addAll(Arrays.asList(b));
	    Object[] c = list.toArray();
	    System.out.println(Arrays.toString(c));
	    
	   
        //12.Convert int[] to List<Integer>
	    System.out.println("**** Merging 2 Arrays ****");
	    int[] nums1 = new int[]{1,3,4};
	    int[] nums2 = new int[]{5,6,7};
	  	System.out.println("**** Array to List of Array: To specific type : type of <T> ****");
	    List<Integer> list11 = Arrays.stream(nums1).boxed().collect(Collectors.toList());
        List<Integer> list21 = Arrays.stream(nums2).boxed().collect(Collectors.toList());
        //Merge to array List
        list11.addAll(list21);
        Collections.sort(list11);        
        System.out.println(list11);
        
		String s = input.toString(); //NOT CORRECT 
		s = new String(input);
		System.out.println(s);
		System.out.println(s.length()); //length is  METHOD
		
		double[] input_1 = {3,4.5,6,7,8,9,10};
		System.out.println(Arrays.binarySearch(input_1, 1));
		System.out.println(Arrays.binarySearch(input_1, 2));
		System.out.println(Arrays.binarySearch(input_1, 3));
		System.out.println(Arrays.binarySearch(input_1, 4));
		System.out.println(Arrays.binarySearch(input_1, 5));
		System.out.println(Arrays.binarySearch(input_1, 7));
		System.out.println(Arrays.binarySearch(input_1, 9));
		System.out.println(Arrays.binarySearch(input_1, 10));
		System.out.println(Arrays.binarySearch(input_1, 11));
		
	}
	

		public void ArrayListToArray() 
		{ 
			List<Integer> al = new ArrayList<Integer>(); 
			al.add(10); 
			al.add(20); 
			al.add(30); 
			al.add(40); 

			// ArrayList to Array Conversion 
			int[] arr = al.stream().mapToInt(i -> i).toArray(); 

			for (int x : arr) 
				System.out.print(x + " "); 
		}
	

	
}

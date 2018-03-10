package coreJava;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ListObject {

	public void Test(){
		
		List<Integer> intArray = new ArrayList<>();
		intArray.add(1);
		intArray.add(2);
		intArray.add(3);
		intArray.add(4);
		
		System.out.println(intArray.size());
		
		/****
		 * Convert List to Array Using toArray()
		 */
		List<String> myList = new ArrayList<String>();
		myList.add("Apple");
		myList.add("Banana");
		myList.add("Orange");
		Object[] myArray = myList.toArray();
		for (Object myObject : myArray) {
		   System.out.println(myObject);
		}
		
		List<String> myList1 = new ArrayList<String>();
		myList1.add("Apple");
		myList1.add("Banana");
		myList1.add("Orange");
		Object[] myArray1 = myList.toArray();
		myArray1[0] = "X";
		myArray1[1] = "Y";
		myArray1[2] = "Z";
		for (String myString:myList1) {
		   System.out.println(myString);
		}
		
		/*****
		 * Convert String List to an Integer Array
		 */
		List<String> myList2 = new ArrayList<String>();
		myList2.add("100");
		myList2.add("200");
		myList2.add("300");
		Integer[] myArray2 = new Integer[myList.size()];
		for (int i = 0; i < myList.size(); i++) {
		   myArray2[i] = Integer.valueOf(myList.get(i));
		}
		for (Integer myInt : myArray2) {
		   System.out.println(myInt);
		}
	}
}

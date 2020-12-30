package algorithms.array.medium;
import java.util.*;
import org.testng.annotations.*;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/qVDWPplVRP2
Given an array of numbers sorted in ascending order, find the element in the array that has the minimum difference with the given ‘key’.

Example 1:

Input: [4, 6, 10], key = 7
Output: 6
Explanation: The difference between the key '7' and '6' is minimum than any other number in the array 
Example 2:

Input: [4, 6, 10], key = 4
Output: 4
Example 3:

Input: [1, 3, 8, 10, 15], key = 12
Output: 10
Example 4:

Input: [4, 6, 10], key = 17
Output: 10

 */
public class Array19_minimumAbsDifference {
	@Test
	private void test() {
		int[] input_01 = {4, 6, 10};
		System.out.println(searchMinDiffElement(input_01,7));
		int[] input_02 = {1,3,6,10,15};
		int[] input_03 = {3,8,-10,23,19,-4,-14,27};

	}

	public static int searchMinDiffElement(int[] arr, int key) {
	    if (key < arr[0])
	      return arr[0];
	    if (key > arr[arr.length - 1])
	      return arr[arr.length - 1];
	      int start =0,  end = arr.length - 1;
	    while(start <= end){
	      int mid = start + (end - start)/2;
	      if(arr[mid] > key){
	        end = mid - 1;
	      }
	      else if(arr[mid] < key){
	        start = mid + 1;
	      }else{
	        return arr[mid];
	      }

	    }
	    if((arr[start] - key) < (key - arr[end])){
	      return arr[start];
	    }
	    return arr[end];
	  }
}

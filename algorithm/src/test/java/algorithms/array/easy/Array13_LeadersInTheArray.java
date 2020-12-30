package algorithms.array.easy;

import java.util.*;

import org.testng.annotations.Test;

/*
 * http://www.geeksforgeeks.org/leaders-in-an-array/
 * Write a program to print all the LEADERS in the array. 
 * An element is leader if it is greater than all the elements to its right side. 
 * And the rightmost element is always a leader. 
 * For example int the array {16, 17, 4, 3, 5, 2}, leaders are 17, 5 and 2.
 */


public class Array13_LeadersInTheArray {
	
	@Test
	private void test() {
		int[] input = {16, 17, 4, 3, 5, 2};
		int[] result = leaders(input);
		for(int i: result) {
			System.out.println(i);
		}
	}
	/*
	 * Time complexity is O(n)
	 * Space Complexity O(1)
	 */
	private int[] leaders(int[] input) {
		List<Integer> result = new ArrayList<>();
		int max_value = input[input.length-1];
		result.add(max_value);
		
		for(int i=input.length-2; i >=0; i--) {
			if(max_value < input[i]) {
				max_value = input[i];
				result.add(max_value);
			}
		}
	
		return result.stream().mapToInt(i -> i).toArray();
	}
}

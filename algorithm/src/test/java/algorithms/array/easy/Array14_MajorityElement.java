package algorithms.array.easy;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/majority-element/
 * 
 * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.

You may assume that the array is non-empty and the majority element always exist in the array.

Example 1:

Input: [3,2,3]
Output: 3
Example 2:

Input: [2,2,1,1,1,2,2]
Output: 2
 */
public class Array14_MajorityElement {
	
	@Test
	private void Test() {
		int[] input_01 = {2,2,1,1,1,2,2};
		Assert.assertEquals(majorityElement(input_01), 2);
		Assert.assertEquals(majorityElement_divideAndConquer(input_01), 2);
	}

	/*
	 * Boyer-Moore Voting Algorithm
	 * Time Complexity O(n)
	 */
	private int majorityElement(int[] input) {
		Integer candicate = null;
		int counter = 0;
		
		for(int i: input) {
			if(counter == 0)
				candicate = i;
			counter += (i == candicate ? 1 : -1);
		}
		return candicate;
	}
	
	/*
	 * Time complexity nLog(n)
	 */
	private int majorityElement_divideAndConquer(int[] input) {
		return majorityElementRec(input, 0, input.length-1);
	}
	
	private int majorityElementRec(int[] input,int start, int end) {
		if(start == end) {
			return input[start];
		}
		
		int mid = start + (end - start)/2;
		int left = majorityElementRec(input, start, mid);
		int right = majorityElementRec(input, mid+1, end);
		
		if(left == right) {
			return left;
		}
		int leftCount = CountInRange(input,left, start,end);
		int rightCount = CountInRange(input,right, start,end);
		
		return leftCount > rightCount ? left : right;
	}
	
	private int CountInRange(int[] input, int num, int start, int end) {
		int count = 0;
		for(int i=start; i <end; i++) {
			if(input[i] == num)
			count++;
		}
		return count;
	}
}

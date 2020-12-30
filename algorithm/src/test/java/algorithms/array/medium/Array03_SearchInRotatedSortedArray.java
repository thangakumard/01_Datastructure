package algorithms.array.medium;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * 
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 * 
 * You are given an integer array nums sorted in ascending order, and an integer target.

Suppose that nums is rotated at some pivot unknown to you beforehand (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

If target is found in the array return its index, otherwise, return -1.

 

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1

 */
public class Array03_SearchInRotatedSortedArray {
	
	@Test
	private void test() {
		int[] input = {4,5,6,7,0,1,2};
		Assert.assertEquals(4, search(input, 0));
		
	}
	
	/*
	 * Time complexity O(n)
	 * Space complexity O(1)
	 * 
	 */
	private int search(int[] input, int target) {
		
		int start = 0, end = input.length - 1;
		
		while(start <= end) {
			int mid = start + (end - start)/2;
			
			if(input[mid] == target) return mid;
			else if(input[mid] >= input[start]) {
				if(target >= input[start] && target < input[mid]) {
					end = mid -1;
				}else {
					start = mid + 1;
				}
			}else {
				if(target <= input[end] && target > input[mid]) {
					start = mid + 1;
				}else {
					end = mid - 1;
				}
			}
		}
		
		return -1;
	}

}

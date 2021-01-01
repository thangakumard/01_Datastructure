package algorithms.array.medium;

import org.testng.annotations.Test;
/*
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 * 
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not the kth distinct element.

	Example 1:
	
	Input: [3,2,1,5,6,4] and k = 2
	Output: 5
	Example 2:
	
	Input: [3,2,3,1,2,4,5,5,6] and k = 4
	Output: 4
	Note:
	You may assume k is always valid, 1 ≤ k ≤ array's length.


 */

public class Array02_KthLargestNumber {

	@Test
	public void test() {

		// int[] input = {3,2,1,5,6,4};

		int[] input = { 3, 2, 3, 1, 2, 4, 5, 5, 6 };
		System.out.println(findKthLargest(input, 9));
		int a = 10;
	}

	int result = -1;

	public int findKthLargest(int[] nums, int k) {

		int result = quickSort(nums, 0, nums.length - 1, k);

		return result;
	}

	private int quickSort(int[] input, int left, int right, int k) {
		int pivot = input[left];
		int initial_left = left;
		int initial_right = right;
		while (left < right) {
			while (pivot >= input[right] && left < right) {
				right--;
			}
			if (left != right) {
				input[left] = input[right];
				left++;
			}
			while (pivot <= input[left] && left < right) {
				left++;
			}
			if (left != right) {
				input[right] = input[left];
				right--;
			}
		}

		input[left] = pivot;
		int pivotIndex = left;
		int result = 0;
        if(k-1 == pivotIndex)
            return input[pivotIndex];
        if(k-1 <= pivotIndex-1 && initial_left <= pivotIndex-1){
            result = quickSort(input, initial_left,pivotIndex-1,k);
        }
        else if(k-1 >= pivotIndex+1 && pivotIndex+1 <= initial_right){
            result = quickSort(input,pivotIndex+1,initial_right,k);
        }
        
        return result;
	}

}

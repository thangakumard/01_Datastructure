package algorithms.array.medium.binarySearch;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*
 * 
 * https://leetcode.com/problems/find-peak-element/
 * 
 *  * https://www.geeksforgeeks.org/find-a-peak-in-a-given-array/
 * 
 * Given an array of integers. Find a peak element in it. 
 * An array element is peak if it is NOT smaller than its neighbors. For corner elements, 
 * we need to consider only one neighbor. For example, 
 * for input array {5, 10, 20, 15}, 20 is the only peak element. 
 * For input array {10, 20, 15, 2, 23, 90, 67}, there are two peak elements: 20 and 90. 
 * Note that we need to return any one peak element.

	Following corner cases give better idea about the problem.
	1) If input array is sorted in strictly increasing order, the last element is always a peak element. For example, 50 is peak element in {10, 20, 30, 40, 50}.
	2) If input array is sorted in strictly decreasing order, the first element is always a peak element. 100 is the peak element in {100, 80, 60, 50, 20}.
	3) If all elements of input array are same, every element is a peak element.
 *
 *
 */
public class BinarySearch06_PeekElement {

	
	@Test
	public void findPeekElement()
	{
		//int[] input = {1,3,9,12,15,3,2,4};
		int[] input = {10, 20, 15, 2, 23, 90, 67};

		Assertions.assertThat(findPeakElement(input)).isEqualTo(5); //Gives better solution
		Assertions.assertThat(Old_Solution(input,0,input.length-1,input.length)).isEqualTo(1);
	}
	
	/*
	 * Time complexity Log(n)
	 */
	 public int findPeakElement(int[] nums) {
	        int l = 0, r = nums.length - 1;
	        while (l < r) {
	            int mid = l + (r - l) / 2;
	            if (nums[mid] > nums[mid + 1])
	                r = mid;
	            else
	                l = mid + 1;
	        }
	        return l; //NOT the value return the index
	    }
	
	private int Old_Solution(int[] input, int left, int right, int length)
	{
		
		System.out.println("Left :" + left);
		System.out.println("right :" + right);
		
		int peek = 0;
		int mid = (right + left)/2;
		
		System.out.println("input[mid] :" + input[mid]);
		
        // Compare middle element with its neighbours (if neighbours exist)
		if((input[mid] > input[mid-1]) &&
				(mid == length-1 || input[mid] > input[mid+1]))
		{
			peek = mid;
		}
		 // If middle element is not peak and its left neighbour is greater 
        // than it, then left half must have a peak element
		else if(mid > 0 && input[mid-1] > input[mid])
		{
			return Old_Solution(input, left, mid-1, length);
		}
		 // If middle element is not peak and its right neighbour is greater
        // than it, then right half must have a peak element
		else if(mid >0 && input[mid+1] > input[mid])
		{
			return Old_Solution(input, mid+1, right, length);
		}
		 
		return peek;
	}
}

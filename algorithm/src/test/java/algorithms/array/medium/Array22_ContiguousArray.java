package algorithms.array.medium;
import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/contiguous-array/
 * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.

	Example 1:
	Input: [0,1]
	Output: 2
	Explanation: [0, 1] is the longest contiguous subarray with equal number of 0 and 1.
	Example 2:
	Input: [0,1,0]
	Output: 2
	Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
	Note: The length of the given binary array will not exceed 50,000.
 */
public class Array22_ContiguousArray {

	@Test
	private void test() {
		int[] input = {1,1,1,0,1,0};
		Assert.assertEquals(findMaxLength(input), 4);
	}
	
	public int findMaxLength(int[] nums) {
		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		map.put(0,-1);
		int max_length = 0;
		int counter = 0;
		
		for(int i=0; i< nums.length; i++) {
			if(nums[i] == 0) {
				counter += -1;
			}else
			{
				counter += 1;
			}
			if(map.containsKey(counter)) {
				max_length = Math.max(max_length, i-map.get(counter));
			}else {
				map.put(counter, i);//** IMPORTANT - Use index as value
			}
			
		}
		
		return max_length;
	}
}

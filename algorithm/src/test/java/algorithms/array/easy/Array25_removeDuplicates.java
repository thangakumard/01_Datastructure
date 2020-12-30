package algorithms.array.easy;

import org.testng.annotations.Test;
/*
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 * Given a sorted array nums, 
 * remove the duplicates in-place such that each element appears only once and returns the new length.

Do not allocate extra space for another array, 
you must do this by modifying the input array in-place with O(1) extra memory.
 */

public class Array25_removeDuplicates {
	
	@Test
	private void test() {
		int[] input = {0,0,1,1,1,2,2,3,3,4};
		int result = removeDuplicates(input);
		for(int i=0; i< result; i++) {
			System.out.println(input[i]);
		}
	}

	 public int removeDuplicates(int[] nums) {
	        if(nums.length == 1)
	            return 1;
	        
	        int k=1, temp =0;
	                
	        for(int i=1; i< nums.length; i++){
	            if(nums[i-1] != nums[i]){
	                nums[k] = nums[i];
	                k++;
	            }
	        }
	        return k;
	    }
}

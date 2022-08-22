package algorithms.array.medium;

import org.assertj.core.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
/********

https://leetcode.com/problems/non-decreasing-array/
 
Given an array nums with n integers, your task is to check 
if it could become non-decreasing by modifying at most one element.

We define an array is non-decreasing if nums[i] <= nums[i + 1] holds for every i (0-based) such that (0 <= i <= n - 2).

 

Example 1:

Input: nums = [4,2,3]
Output: true
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
Example 2:

Input: nums = [4,2,1]
Output: false
Explanation: You can't get a non-decreasing array by modify at most one element.
 

Constraints:

n == nums.length
1 <= n <= 104
-105 <= nums[i] <= 105
 
 */
public class Array32_NonDecreasingArray {

    /******* IMPORTANT Tests *********
     * [4,2,3]
     * [0,0,3]
     * [3,4,2,3] =====> important test
     * [1,1,1]
     */

	@Test
	private void checkPossibilityTest() {
		int[] input = new int[] {1,1,1};
        Assertions.assertThat(checkPossibility(input)).isTrue();

        input = new int[] {0,0,3};
        Assertions.assertThat(checkPossibility(input)).isTrue();

        input = new int[] {4,2,3};
        Assertions.assertThat(checkPossibility(input)).isTrue();

        input = new int[] {2,3,4,2,3};
        Assertions.assertThat(checkPossibility(input)).isFalse();
	}
	
	public boolean checkPossibility(int[] nums) {
        int numViolations = 0;
       for (int i = 1; i < nums.length; i++) {
           if (nums[i - 1] > nums[i]) {
               
               if (numViolations == 1) {
                   return false;
               }
               
               numViolations++;

               //If the violation is because of i-1 (created a peek) replace that with i
               if (i < 2 || nums[i - 2] <= nums[i]) {
                   nums[i - 1] = nums[i];
               } else {
                   //If the violation is because of i (created a dip) replace that with i-1
                   nums[i] = nums[i - 1];
               }
           }
       }
       
       return true;
   }
}

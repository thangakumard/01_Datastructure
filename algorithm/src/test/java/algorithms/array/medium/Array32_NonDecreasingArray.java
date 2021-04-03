package algorithms.array.medium;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Array32_NonDecreasingArray {

	@Test
	private void test() {
		int[] input = new int[] {4,2,3};
		Assert.assertEquals(checkPossibility(input), true);
		
	}
	public boolean checkPossibility(int[] nums) {
        int numViolations = 0;
       for (int i = 1; i < nums.length; i++) {
           
           if (nums[i - 1] > nums[i]) {
               
               if (numViolations == 1) {
                   return false;
               }
               
               numViolations++;
               
               if (i < 2 || nums[i - 2] <= nums[i]) {
                   nums[i - 1] = nums[i];
               } else {
                   nums[i] = nums[i - 1];
               }
           }
       }
       
       return true;
   }
}

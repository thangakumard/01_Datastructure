package algorithms.array.medium;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Array30_MaximumProductSubarray {

	@Test
	private void test() {
		int[] input1 = {2,3,-2,4};
		Assert.assertEquals(maxProduct(input1), 6);
		
		int[] input2 = {0,2};
		Assert.assertEquals(maxProduct(input2), 2);
	}
	
	public int maxProduct(int[] nums) {
        if(nums.length == 0) return 0;
        
        int max_so_far = nums[0];
        int min_so_far = nums[0];
        int result = max_so_far;
        
        for(int i=1; i < nums.length; i++){
            int curr = nums[i];
            
            int temp = Math.max(curr,Math.max(max_so_far * curr , min_so_far * curr));
            min_so_far = Math.min(curr,Math.min(max_so_far * curr , min_so_far * curr));
            
            max_so_far = temp;
            result = Math.max(result, max_so_far);
        }
        
        return result;
    }
}

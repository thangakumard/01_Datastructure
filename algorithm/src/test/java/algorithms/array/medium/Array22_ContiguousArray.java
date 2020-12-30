package algorithms.array.medium;
import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;


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
				map.put(counter, i);
			}
			
		}
		
		return max_length;
	}
}

package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SubArraySum {
	
	@Test
	public void getSubArray()
	{
		int[] a={1,1,1,1,1,2,-1,3};
		Assert.assertEquals(minSubArrayLen(5,a),4);
	}

	public int minSubArrayLen(int s, int[] nums) {
		if (nums == null || nums.length == 1)
			return 0;

		int result = nums.length;

		int start = 0;
		int sum = 0;
		int i = 0;
		boolean exists = false;

		while (i <= nums.length) {
			if (sum >= s) {
				exists = true; // mark if there exists such a subarray
				if (start == i - 1) {
					return 1;
				}

				result = Math.min(result, i - start);
				sum = sum - nums[start];
				start++;

			} else {
				if (i == nums.length)
					break;
				sum = sum + nums[i];
				i++;
			}
		}

		if (exists)
			return result;
		else
			return 0;
	}
}

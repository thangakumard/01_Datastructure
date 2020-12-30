package algorithms.array;

import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ThreeSum {

	@Test
	public void get3Sum() {
		int[] input = { 10, -2, -8, 5, 3, 2 };
		Assert.assertEquals(ThreeSumClosest(input, 0), 0);
	}

	public int ThreeSumClosest(int[] nums, int target) {
		int min = Integer.MAX_VALUE;
		int result = 0;
		Arrays.sort(nums);
		int size = nums.length - 1;
		int sum = 0;

		for (int i = 0; i < nums.length; i++) {
			int j = i + 1;
			while (j < size) {
				sum = nums[i] + nums[j] + nums[size];
				int diff = Math.abs(sum - target);

				if (diff == 0)
					return sum;

				if (diff < min) {
					min = diff;
					result = sum;
				}
				if (sum <= target) {
					j++;
				} else {
					size--;
				}
			}
		}

		return result;

	}
}

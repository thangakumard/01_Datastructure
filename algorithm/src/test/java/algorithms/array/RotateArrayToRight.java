package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RotateArrayToRight {

	@Test
	public void rotateArray() {
		int[] input = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] output = { 7, 8, 9, 1, 2, 3, 4, 5, 6 };
		int n = input.length;
		int rotateCount = 3;
		rotate(input, 0, n - rotateCount - 1);
		// {6,5,4,3,2,1,7,8,9} Rotate the left side 180 degree
		rotate(input, n - rotateCount, n - 1);
		// {6,5,4,3,2,1,9,8,7}Rotate the right side 180 degree
		rotate(input, 0, n - 1);
		// {7,8,9,1,2,3,4,5,6} Rotate the whole array 180 degree
		Assert.assertEquals(input, output);
	}

	private void rotate(int[] input, int start, int end) {
		int temp = 0;
		while (start < end) {
			temp = input[start];
			input[start++] = input[end];
			input[end--] = temp;
		}

		System.out.println("start :" + start);
		for (int i = 0; i < input.length; i++) {
			System.out.println(input[i]);
		}
	}
}

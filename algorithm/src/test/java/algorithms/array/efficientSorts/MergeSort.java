package algorithms.array.efficientSorts;

import org.testng.Assert;
import org.testng.annotations.Test;

public class MergeSort {

	@Test
	public void doMergeSort() {
		int[] input = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 9, 2 };
		int[] output = { 1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9 };

		splitAndSort(input, 0, input.length - 1);

		for (int i = 0; i < input.length; i++) {
			System.out.println("input : " + input[i]);
		}

		Assert.assertEquals(input, output, "Merge sort fails!!!");
	}

	public void splitAndSort(int[] input, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			splitAndSort(input, left, mid);
			splitAndSort(input, mid + 1, right);
			mergeAndSort(input, left, mid + 1, right);
		}
	}

	public void mergeAndSort(int[] input, int left, int mid, int right) {
		int array1LeftIndex = left;
		int array1RightIndex = mid - 1;
		int array2LeftIndex = mid;
		int array2RightIndex = right;

		int totalRecords = right - left + 1;

		int tempIndex = left;
		int[] temp = new int[input.length];
		while (array1LeftIndex <= array1RightIndex && array2LeftIndex <= array2RightIndex) {
			if (input[array1LeftIndex] < input[array2LeftIndex]) {
				temp[tempIndex] = input[array1LeftIndex];
				array1LeftIndex++;
			} else {
				temp[tempIndex] = input[array2LeftIndex];
				array2LeftIndex++;
			}
			tempIndex++;
		}

		while (array1LeftIndex <= array1RightIndex) {
			temp[tempIndex] = input[array1LeftIndex];
			array1LeftIndex++;
			tempIndex++;
		}

		while (array2LeftIndex <= array2RightIndex) {
			temp[tempIndex] = input[array2LeftIndex];
			array2LeftIndex++;
			tempIndex++;
		}

		for (int j = right; totalRecords > 0; totalRecords--) {
			input[j] = temp[j];
			j--;
		}

	}

}

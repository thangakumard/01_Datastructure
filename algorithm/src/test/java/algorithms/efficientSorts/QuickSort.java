package algorithms.efficientSorts;

import org.testng.Assert;
import org.testng.annotations.Test;

public class QuickSort {

	@Test
	public void doQuickSort() {
		int[] input = { 9, 8, 7, 6, 5, 4, 3, 2, 1, 9, 2 };
		int[] output = { 1, 2, 2, 3, 4, 5, 6, 7, 8, 9, 9 };

		quickSortRecursive(input, 0, input.length - 1);

		for (int i = 0; i < input.length; i++) {
			System.out.println("input : " + input[i]);
		}
		Assert.assertEquals(input, output, "Merge sort fails!!!");
	}

	public void quickSortRecursive(int[] input, int left, int right) {
		int pivotValue = input[left];
		int initialLeft = left;
		int initialRight = right;
		while (left < right) {
			while (input[right] >= pivotValue && left < right) {
				right--;
			}
			if (left != right) {
				input[left] = input[right];
				left++;
			}
			while (input[left] <= pivotValue && left < right) {
				left++;
			}
			if (left != right) {
				input[right] = input[left];
				right--;
			}

		}

		input[left] = pivotValue;
		int pivotIndex = left;

		if (initialLeft < pivotIndex - 1)
			quickSortRecursive(input, initialLeft, pivotIndex - 1);
		if (pivotIndex + 1 < initialRight)
			quickSortRecursive(input, pivotIndex + 1, initialRight);
	}

}

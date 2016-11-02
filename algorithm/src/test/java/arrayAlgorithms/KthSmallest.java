package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class KthSmallest {

	@Test
	public void getKthSmallest() {
		int[] input = { 9, 8, 7, 6, 5, 6, 4, 3, 2, 1, 2 }; // 1,2,2,3,4,5,6,6,7,8,9

		int pivotValue = doQuickSort(input, 0, input.length - 1, 6);

		System.out.println("pivot Value is :" + pivotValue);
		Assert.assertEquals(pivotValue, 6);

	}

	private int doQuickSort(int[] input, int left, int right, int k) {
		
		int initialLeft = left;
		int initialRight = right;
		int pivotValue = input[left];		
		int result = 0;
		
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

		System.out.println("Pivot index :" + pivotIndex);

		if (k == pivotIndex) {			
			return input[left];
		} 
		if (k <= pivotIndex - 1 && initialLeft <= pivotIndex - 1) {
			result = doQuickSort(input, initialLeft, pivotIndex - 1, k);
		} 
		else if (k >= pivotIndex + 1 && initialRight >= pivotIndex + 1) {
			result = doQuickSort(input, pivotIndex + 1, initialRight, k);
		}

		return result;

	}

}

package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
Given two integer arrays where second array is duplicate of first array with just 1 element missing. Find the element.
Example: 

Input:
Array1 - 9 7 8 5 4 6 2 3 1
Array2 - 2 4 3 9 1 8 5 6

Output:
7
*/

public class MissingNumberInDuplicateArrayUsingSum {

	@Test
	public void findMissingNumber() {

		int[] a = { 9, 7, 8, 5, 4, 6, 2, 3, 1 };
		int[] b = { 2, 4, 3, 9, 1, 8, 5, 6 };

		int i = getMissingNumber(a, b);

		System.out.println("Missing number is :" + i);

		Assert.assertEquals(i, 7);
	}

	private int getMissingNumber(int[] a, int[] b) {
		// TODO Auto-generated method stub

		if (a == null && b == null) {
			System.out.println("Invalid input!");
		} else if (a == null) {

			if (b.length == 1) {
				System.out.println("Missing number is :" + b[0]);
			} else {
				System.out.println("Invalid input!");
			}
		} else if (b == null) {
			if (a.length == 1) {
				System.out.println("Missing number is :" + a[0]);
			} else {
				System.out.println("Invalid input!");
			}
		}

		if (a.length == b.length) {
			System.out.println("Invalid input!");
		}

		int result = 0;
		if (a.length > b.length) {
			result = findUsingSum(a, b);
		} else {
			result = findUsingSum(b, a);
		}

		return result;
	}

	private int findUsingSum(int[] a, int[] b) {

		int result = 0;

		for (int i = 0; i < a.length; i++) {
			result += a[i];
		}

		for (int j = 0; j < b.length; j++) {
			result -= b[j];
		}

		return result;
	}

}

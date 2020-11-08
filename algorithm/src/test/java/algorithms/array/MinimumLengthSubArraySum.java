package algorithms.array;

import org.testng.Assert;
import org.testng.annotations.Test;

/*
 * Given an array A having positive and negative integers and a number k, find the minimum length sub array of A with sum = k.
 */

public class MinimumLengthSubArraySum {

	@Test
	public void getMinimumLengthSum() {
		int[] a = { 1, 10, 5, 6, 1, 3, 5, 5, 5 };
		int k = 15;

		int result = getResult(a, k);
		
		Assert.assertEquals(result, 2);

	}

	private int getResult(int[] a, int k) {

		int start = -1, end = -1;
		int minValue = Integer.MAX_VALUE;

		for (int i = 0; i < a.length; i++) {
			int currentSum = 0;
			for (int j = i; j < a.length && (j - i + 1) < minValue; j++) {

				currentSum += a[j];
				if (currentSum == k) {
					start = i;
					end = j;
					minValue = end - start + 1;
				}else if(currentSum > k){
					 break;
				}

			}
		}
		
		if(start == -1 || end == -1){
			System.out.println("No subarray exists with the sum : " + k);
			return 0;
		}
		
		while(start <= end){
			System.out.println(a[start]);
			start++;
		}

		return minValue;
	}

}

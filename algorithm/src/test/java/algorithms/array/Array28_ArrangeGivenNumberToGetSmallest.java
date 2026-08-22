package algorithms.array.medium;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/****
 * 
 * https://www.geeksforgeeks.org/arrange-given-numbers-to-form-the-smallest-number/
 * 
 * Given an array arr[] of integer elements, the task is to arrange them in such
 * a way that these numbers form the smallest possible number. For example, if
 * the given array is {5, 6, 2, 9, 21, 1} then the arrangement will be 1212569.
 * 
 * Examples:
 * 
 * Input: arr[] = {5, 6, 2, 9, 21, 1} Output: 1212569
 * 
 * Input: arr[] = {1, 2, 1, 12, 33, 211, 50} Output: 111221123350
 *
 */
public class Array28_ArrangeGivenNumberToGetSmallest {

	@Test
	private void test() {
		int[]  arr = {5, 6, 2, 9, 21, 1} ;
		Assertions.assertThat(ArrangeGivenNumberToGetSmallest(arr)).isEqualTo("1212569");

		int[]  arr1 = {1, 2, 1, 12, 33, 211, 50};
		Assertions.assertThat(ArrangeGivenNumberToGetSmallest(arr1)).isEqualTo("111221123350");
	}
	//This question was asked to me in an Amazon interview
	private String ArrangeGivenNumberToGetSmallest(int[] input) {
		
		for(int i=0; i < input.length; i++) {
			for(int j=i+1; j < input.length; j++) {
				if(!IsX1SmallerThanX2(input[i] +""+ input[j],input[j] +""+ input[i])){
					int temp = input[i];
					input[i] = input[j];
					input[j] = temp;
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i: input) {
			sb.append(i);
		}
		
		return sb.toString();
	}

	/**
	 * Returns 'True' if x1 < x2
	 * @param x1
	 * @param x2
	 * @return
	 */
	private boolean IsX1SmallerThanX2(String x1, String x2) {
		/*
		 * if x1 > x2, it returns positive number
		 * if x1 < x2, it returns negative number
		 * if x1 == x2, it returns 0
		 */
		return x1.compareTo(x2) < 0;
	}
}

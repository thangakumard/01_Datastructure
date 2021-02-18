package algorithms.array.medium;

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
		int[]  arr1 = {1, 2, 1, 12, 33, 211, 50};
		System.out.println(ArrangeGivenNumberToGetSmallest(arr));
		System.out.println(ArrangeGivenNumberToGetSmallest(arr1));
	}
	
	private String ArrangeGivenNumberToGetSmallest(int[] input) {
		
		for(int i=0; i < input.length; i++) {
			for(int j=i+1; j < input.length; j++) {
				if(!compareString(input[i] +""+ input[j],input[j] +""+ input[i])){
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
	
	private boolean compareString(String x1, String x2) {
		return x1.compareTo(x2) < 0;
	}
}

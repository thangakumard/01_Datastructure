package algorithms.matrix;

import org.testng.annotations.Test;

public class TwoDimensionalArray {
	
	@Test
	public void getLengthOfTwoDimensionalArray(){
		int A[][] = new int[3][5];
		System.out.println(A.length); //Number of rows in the array
		System.out.println(A[0].length); // Number of column in the array
	}

}

package algorithms.array.easy;

import org.testng.annotations.Test;

public class Array01_MoveAllZerosToLeft {

	@Test
	public void test() {
		int[] input = {1,2,0,0,6,0,5,7};
		moveZerosToLeft(input);
		for(int i: input) {
			System.out.print(i);
		}
	}
	/*
	 * Time complexity O(n)
	 * Space complexity O(1)
	 */
	private void moveZerosToLeft(int[] A) {
	    int l = A.length - 1;
	   int  write = l;

	   for(int read=l; read >= 0; read--){
	     if(A[read] != 0) {
	          A[write] = A[read];
	          write --;
	     }
	   }
	   while(write >= 0){
	     A[write] = 0;
	     write--;
	   }

	  }
}

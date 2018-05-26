package arrayAlgorithms;

import org.junit.Assert;
import org.testng.annotations.Test;

public class Array44_IsRectangleOverlap {

	@Test
	public void checkRectangleOverlap(){
		int a[] = new int[] {0,0,2,2};
		int b[] = new int[] {1,1,3,3};
		Assert.assertTrue(isRectangleOverlap(a, b));
		
		a = new int[] {8,20,12,20};
		b = new int[] {14,2,19,11};
		
		Assert.assertFalse(isRectangleOverlap(a, b));
		
	}
	
	private boolean isRectangleOverlap(int[] a, int[]b){
		return Math.max(a[0], b[0]) < Math.min(a[2], b[2]) &&
				Math.max(a[1], b[1]) < Math.min(a[3], b[3]);
	}
}

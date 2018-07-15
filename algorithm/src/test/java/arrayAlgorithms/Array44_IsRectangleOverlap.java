package arrayAlgorithms;

import org.junit.Assert;
import org.testng.annotations.Test;
/*******
 	https://www.geeksforgeeks.org/find-two-rectangles-overlap/ 
 	Given two rectangles, find if the given two rectangles overlap or not.
	
	Note that a rectangle can be represented by two coordinates, top left and bottom right. So mainly we are given following four coordinates.
	l1: Top Left coordinate of first rectangle.
	r1: Bottom Right coordinate of first rectangle.
	l2: Top Left coordinate of second rectangle.
	r2: Bottom Right coordinate of second rectangle.
 
 	We need to write a function bool doOverlap(l1, r1, l2, r2) that returns true if the two given rectangles overlap.
 */
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

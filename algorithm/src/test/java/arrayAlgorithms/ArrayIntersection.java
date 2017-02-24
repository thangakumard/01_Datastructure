package arrayAlgorithms;

import java.util.HashSet;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ArrayIntersection {

	/*Good explanation is in the below article
	 * http://articles.leetcode.com/here-is-phone-screening-question-from/
	 * http://stackoverflow.com/questions/15485641/hash-table-vs-sorted-array-which-to-use 
	 */
	
	/****using Array ******/
	//complexity (O)(m+n)
	@Test
	public void findIntersection(){
		
		int[] a = {1,2,3,4,5,6,7,8,9};
		int[] b = {5,6,10,11,12};
		
		int m = a.length;
		int n = b.length;
		int i= 0; int j = 0;
		while(i < m && j < n){
			if(a[i] < b[j]){
				i++;
			}
			else if(a[i] > b[j]){
				j++;
			}
			else
			{
				System.out.println(a[i]);
				i++;
				j++;
			}
		}
	}
	
	
	
	/*** using Haseset *******/
	// complexity (O)(m+n)
	
	@Test
	public void findIntersectionUsingHash() {

		int[] a = { 1, 2, 3, 10, 20, 30, 40 };
		int[] b = { 2, 3, 4, 5, 6, 7, 8 };

		HashSet<Integer> s1 = new HashSet<Integer>();
		HashSet<Integer> s2 = new HashSet<Integer>();

		for (int n : a) {

			s1.add(n);
		}

		for (int n : b) {
			if (s1.contains(n)) {

				s2.add(n);
			}
		}
		
		for(int n:s2)
		{
			System.out.println("intersections are :" );
			System.out.println(n);
		}
		
		Assert.assertTrue(true);

	}

}

package arrayAlgorithms;

import java.util.HashSet;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ArrayIntersection {

	
	@Test
	public void findIntersection() {

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

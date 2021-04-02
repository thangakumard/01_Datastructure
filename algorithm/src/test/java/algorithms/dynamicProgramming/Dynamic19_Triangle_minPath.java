package algorithms.dynamicProgramming;

import java.util.*;

import org.testng.annotations.*;

public class Dynamic19_Triangle_minPath {
	
	@Test
	private void test() {
		List<List<Integer>> input = new ArrayList<>();
		List<Integer> first = new ArrayList<>();
		first.add(-1);
		List<Integer> second = new ArrayList<>();
		second.add(2);
		second.add(3);
		List<Integer> third = new ArrayList<>();
		third.add(1);
		third.add(-1);
		third.add(-3);
		input.add(first);
		input.add(second);
		input.add(third);
		System.out.println("Minimum Path Value : " + minimumTotal(input));
	}

	public int minimumTotal(List<List<Integer>> triangle) {
        int[] A = new int[triangle.size() + 1];
       
       for(int i = triangle.size() - 1; i >= 0; i--) {
           for(int j = 0; j < triangle.get(i).size(); j++) {
               A[j] = Math.min(A[j], A[j+1]) + triangle.get(i).get(j);
           }
       }
       return A[0];
   }
}

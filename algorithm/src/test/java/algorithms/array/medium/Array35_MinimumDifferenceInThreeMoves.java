package algorithms.array.medium;

import java.util.*;
import org.testng.annotations.*;

/***
 * https://leetcode.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/
 */

public class Array35_MinimumDifferenceInThreeMoves {

	@Test
	public void Test() {
		int[] input_1 = new int[] {5,3,2,4};
		System.out.println(minDifference(input_1));
		int[] input_2 =	new int[] {6,6,0,1,1,4,6};
		System.out.println(minDifference(input_2));
	}
	
	public int minDifference(int[] A) {
        int n = A.length, res = Integer.MAX_VALUE;
        if (n < 5) return 0;
        Arrays.sort(A);
        for (int i = 0; i < 4; ++i) {
            res = Math.min(res, A[n - 4 + i] - A[i]);
        }
        return res;
    }
}

package algorithms.array.easy;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*****
 * 
 * https://leetcode.com/problems/sqrtx/description/ Implement int sqrt(int x).
 * 
 * Compute and return the square root of x, where x is guaranteed to be a
 * non-negative integer.
 * 
 * Since the return type is an integer, the decimal digits are truncated and
 * only the integer part of the result is returned.
 * 
 * Example 1:
 * 
 * Input: 4 Output: 2 Example 2:
 * 
 * Input: 8 Output: 2 Explanation: The square root of 8 is 2.82842..., and since
 * the decimal part is truncated, 2 is returned.
 * 
 * Constraints:
 * 
 * 0 <= x <= 231 - 1
 *
 */
public class Array26_Sqrt_of_X {

	@Test
	public void Test() {
		Assertions.assertThat(mySqrt(2147395599)).isEqualTo(46339);
	}

	public int mySqrt(int x) {
		if (x == 0 || x == 1)
			return x;

		int start = 1, end = x, mid =0, ans = 0;
		while (start <= end) {
			// Calculate the middle point using "start + (end - start) / 2" to avoid integer overflow.
			mid = start + (end - start) / 2;
			if((long) mid * mid > (long)x)
				end = mid -1;

			else if( (mid * mid) == x){
				return mid;
			}
			else
				start = mid+1;

		}

		return Math.round(end);
	}
}

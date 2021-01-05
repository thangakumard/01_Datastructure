package algorithms.array.easy;

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
		System.out.println(sqrt(2147483647));
	}

	public long sqrt(int x) {
		if (x == 0 || x == 1)
			return x;

		// Do Binary Search for floor(sqrt(x))
		long start = 1, end = x, ans = 0;
		while (start > 0 && start <= end) {
			System.out.println("Start : " + start + "end : " + end);
			long mid = (start + end) / 2;

			long squre = mid * mid;
			// If x is a perfect square
			if (squre == x)
				return mid;

			// Since we need floor, we update answer when mid*mid is
			// smaller than x, and move closer to sqrt(x)
			if (squre < x) {
				start = mid + 1;
				ans = mid;
			} else // If mid*mid is greater than x
				end = mid - 1;
		}
		return ans;
	}
}

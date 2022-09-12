package algorithms.string.math;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/******
 * 
 * https://leetcode.com/problems/multiply-strings/
 *
 * Given two non-negative integers num1 and num2 represented as strings, return
 * the product of num1 and num2, also represented as a string.
 * 
 * Note: You must not use any built-in BigInteger library or convert the inputs
 * to integer directly.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: num1 = "2", num2 = "3" Output: "6" Example 2:
 * 
 * Input: num1 = "123", num2 = "456" Output: "56088"
 * 
 * 
 * Constraints:
 * 
 * 1 <= num1.length, num2.length <= 200 num1 and num2 consist of digits only.
 * Both num1 and num2 do not contain any leading zero, except the number 0
 * itself.
 * 
 */

public class Add04_MultiplyString {

	@Test
	private void StringMultiply_test() {
		Assertions.assertThat(multiply("99", "99")).isEqualTo("9801");
		Assertions.assertThat(multiply("2", "3")).isEqualTo("6");
		Assertions.assertThat(multiply("123", "456")).isEqualTo("56088");
	}

	public String multiply(String num1, String num2) {
		if (num1.equals("0") || num2.equals("0"))
			return "0";
		int m = num1.length();
		int n = num2.length();
		int[] result = new int[m + n]; // size of 4
		for (int i = m - 1; i >= 0; i--) {
			for (int j = n - 1; j >= 0; j--) {
				int product = (num1.charAt(i) - '0') * (num2.charAt(j) - '0'); //81
				int sum = result[i + j + 1] + product; //Get the existing value in the index + current value
				result[i + j + 1] = sum % 10; // Assign the remainder to the current index
				result[i + j] = result[i + j] + sum / 10; //Keep the Quotient in the previous index
			}
		} 
		StringBuilder sb = new StringBuilder();
		for (int val : result) {
			if (sb.length() != 0 || val != 0) { //"2" * "3" = "06" => To avoid prefix "0" in the result
				sb.append(val);
			}
		}
		return (sb.length() == 0) ? "0" : sb.toString();

	}
}

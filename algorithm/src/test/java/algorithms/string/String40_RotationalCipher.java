package algorithms.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/****
 * https://leetcode.com/discuss/interview-question/1950476/rotational-cipher
 *
 * One simple way to encrypt a string is to "rotate" every alphanumeric character by a certain amount. Rotating a character means replacing it with another character that is a certain number of steps away in normal alphabetic or numerical order.
 * For example, if the string "Zebra-493?" is rotated 3 places, the resulting string is "Cheud-726?". Every alphabetic character is replaced with the character 3 letters higher (wrapping around from Z to A), and every numeric character replaced with the character 3 digits higher (wrapping around from 9 to 0). Note that the non-alphanumeric characters remain unchanged.
 * Given a string and a rotation factor, return an encrypted string.
 * Signature
 * string rotationalCipher(string input, int rotationFactor)
 * Input
 * 1 <= |input| <= 1,000,000
 * 0 <= rotationFactor <= 1,000,000
 * Output
 * Return the result of rotating input a number of times equal to rotationFactor.
 * Example 1
 * input = Zebra-493?
 * rotationFactor = 3
 * output = Cheud-726?
 * Example 2
 * input = abcdefghijklmNOPQRSTUVWXYZ0123456789
 * rotationFactor = 39
 * output = nopqrstuvwxyzABCDEFGHIJKLM9012345678
 */

public class String40_RotationalCipher {
	@Test
	private void test() {
		Assertions.assertThat(rotationalCipher_02("Zebra-493?", 3)).isEqualTo("Cheud-726?");
		Assertions.assertThat(rotationalCipher_02("abcdefghijklmNOPQRSTUVWXYZ0123456789", 39)).isEqualTo("nopqrstuvwxyzABCDEFGHIJKLM9012345678");
	}

	private String rotationalCipher_02(String input, int rotationFactor){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i < input.length(); i++){
			char c = input.charAt(i);
			if(Character.isLowerCase(c)){
				sb.append((char)((c - 'a' + rotationFactor) % 26 + 'a'));
			} else if (Character.isUpperCase(c)){
				sb.append((char)((c - 'A' + rotationFactor) % 26 + 'A'));
			} else if (Character.isDigit(c)) {
				sb.append(((c - '0') + rotationFactor) % 10);
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}

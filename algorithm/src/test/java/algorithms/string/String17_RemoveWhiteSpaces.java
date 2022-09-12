package algorithms.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class String17_RemoveWhiteSpaces {
	@Test
	private void test() {
		char[] input = "We love 	Java".toCharArray();
		Assertions.assertThat(removeWhiteSpaces(input)).isEqualTo("WeloveJava\u0000\u0000\u0000");
	}
	
	private String removeWhiteSpaces(char[] input) {
		if (input == null || input.length == 0 || input[0] == '\0') {
		      return "";
		}
		int read=0, write = 0;
		while(read < input.length) {
			if(!Character.isWhitespace(input[read])) {
				input[write] = input[read];
				write++;
			}
			read++;
		}
		while(write < input.length) {
			input[write] = Character.MIN_VALUE;
			write++;
		}
		return new String(input);
	}
}

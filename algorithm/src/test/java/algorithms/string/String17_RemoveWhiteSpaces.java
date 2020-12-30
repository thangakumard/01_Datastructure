package algorithms.string;

import org.testng.annotations.Test;

public class String17_RemoveWhiteSpaces {
	@Test
	private void test() {
		char[] input = "We love 	Java".toCharArray();
		;
		removeWhiteSpaces(null);
	}
	
	private void removeWhiteSpaces(char[] input) {
		if (input == null || input.length == 0 || input[0] == '\0') {
		      return;
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
		System.out.println(input);
	}
}

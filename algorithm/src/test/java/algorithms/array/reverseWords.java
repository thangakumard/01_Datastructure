package algorithms.array;

import org.testng.annotations.Test;

public class reverseWords {

	@Test
	public void getReverseWord() {
		char[] input = "god is everywhere".toCharArray();
		int i = 0;

		for (int j = 0; j < input.length; j++) {
			if (input[i] == ' ') {
				reverse(input, i, j - 1);
				i = j + 1;
			}

			System.out.println(String.valueOf(input));

			reverse(input, i, input.length - 1);
			System.out.println(input.toString());

			System.out.println(String.valueOf(input));

		}
		System.out.println("rever string is : ");

		System.out.println(String.valueOf(input));
	}

	private void reverse(char[] s, int left, int right) {
		char temp;
		while (left < right) {
			temp = s[left];
			s[left++] = s[right];
			s[right--] = temp;
		}
	}

}

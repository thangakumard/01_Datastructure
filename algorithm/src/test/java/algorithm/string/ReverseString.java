package algorithm.string;

import org.testng.Assert;
import org.testng.annotations.Test;




/*******
 * https://leetcode.com/problems/reverse-string/description/
 * Write a function that takes a string as input and returns the string reversed.

	Example:
	Given s = "hello", return "olleh".
 *
 */
public class ReverseString {
	
	@Test
	public void reverse(){
		Assert.assertEquals(reverseGivenString("hello"), "olleh");
		Assert.assertEquals(reverseGivenString("12345"), "54321");
	}
	
	private String reverseGivenString(String input){
		int length = input.length();
		int j = length-1;
		char c;
		char[] charInput = input.toCharArray();
		for(int i=0; i < j; i++){
			c = charInput[i];
			charInput[i] = charInput[j];
			charInput[j] = c;
			j--;
		}
		return new String(charInput);
	}
}

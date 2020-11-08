package algorithms.string;

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
//		Assert.assertEquals(reverseGivenString("hello"), "olleh");
//		Assert.assertEquals(reverseGivenString("12345"), "54321");
		Assert.assertEquals(reverseGivenString_02("hello"), "olleh");
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
	
	public String reverseGivenString_02(String input) {
		char[] s = input.toCharArray();
        for(int i=0,j=s.length-1; i < s.length/2 && j>-1; i++,j--){
            s[i] = (char)((int)s[i] ^ (int)s[j]);
            s[j] = (char)((int)s[i] ^ (int)s[j]);
            s[i] = (char)((int)s[i] ^ (int)s[j]);
        }
        return new String(s);
    }

}

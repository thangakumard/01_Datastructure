package algorithms.string.Reverse;

import org.testng.Assert;
import org.testng.annotations.Test;

/***
 * 
 * https://leetcode.com/problems/reverse-string/
 * 
	Write a function that reverses a string. The input string is given as an array of characters char[].
	
	Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
	
	You may assume all the characters consist of printable ascii characters.
	
	 
	
	Example 1:
	
	Input: ["h","e","l","l","o"]
	Output: ["o","l","l","e","h"]
	Example 2:
	
	Input: ["H","a","n","n","a","h"]
	Output: ["h","a","n","n","a","H"]
 *
 */
public class String05_ReverseString_1 {

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

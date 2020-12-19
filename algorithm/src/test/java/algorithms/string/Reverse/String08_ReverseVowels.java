package algorithms.string.Reverse;

import org.junit.Assert;
import org.testng.annotations.Test;
/*****
 * 
 * https://leetcode.com/problems/reverse-vowels-of-a-string/
 * Write a function that takes a string as input and reverse only the vowels of a string.

	Example 1:
	Given s = "hello", return "holle".
	
	Example 2:
	Given s = "leetcode", return "leotcede".
	
	Note:
	The vowels does not include the letter "y".
 *
 */
public class String08_ReverseVowels {
	
	@Test
	public void Reverse(){
		Assert.assertEquals("eppla", reverseVowel("a.b,."));
	}
	
	public String reverseVowel(String s){
		String vowels = "aeioeAEIOE";
		char[] input = s.toCharArray();
		int start = 0;
		int end = s.length()-1;
		char temp;
		while(start < end){
			while(start < end && !vowels.contains(input[start]+"")){
				start++;
			}
			while(start < end && !vowels.contains(input[end]+"")){
				end--;
			}
			temp = input[start];
			input[start] = input[end];
			input[end] = temp;
			start++;
			end--;
		}		
		return new String(input);
	}

}

package algorithms.string;

import org.junit.Assert;
import org.testng.annotations.Test;

/*****
 * 
 * https://leetcode.com/problems/valid-palindrome/
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

	Note: For the purpose of this problem, we define empty string as valid palindrome.
	
	Example 1:
	
	Input: "A man, a plan, a canal: Panama"
	Output: true
	Example 2:
	
	Input: "race a car"
	Output: false
 */
public class Palindrome {

	@Test
	public void isPalindrome(){
		Assert.assertTrue(palindromeHelper_01("A man, a plan, a canal: Panama"));
		Assert.assertFalse(palindromeHelper_01("race a car"));
		
		Assert.assertTrue(palindromeHelper_02("A man, a plan, a canal: Panama"));
		Assert.assertFalse(palindromeHelper_02("race a car"));
	}
	
	private boolean palindromeHelper_01(String s){
		char[] input = s.toLowerCase().toCharArray();
		int j = input.length-1;
		int i =0;
		while(i < j){
			while(!Character.isAlphabetic(input[i]) && i < j){
				i++;
			}
			while(!Character.isAlphabetic(input[j]) && j > i){
				j--;
			}
			if(input[i] != input[j])
				return false;
			i++;
			j--;
		}
		return true;
	}
	
	//Not the best one
	private boolean palindromeHelper_02(String s) {
		s = s.replaceAll("\\W", "").toLowerCase();
		StringBuilder sb = new StringBuilder(s);
		return s.contentEquals(sb.reverse());
	}
}

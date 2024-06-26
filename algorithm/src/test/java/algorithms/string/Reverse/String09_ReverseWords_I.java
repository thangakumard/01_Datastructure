package algorithms.string.Reverse;

import org.testng.Assert;
import org.testng.annotations.Test;

/*****
 * 
 * https://leetcode.com/problems/reverse-words-in-a-string-iii/description/
 * Given a string, you need to reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

	Example 1:
	Input: "Let's take LeetCode contest"
	Output: "s'teL ekat edoCteeL tsetnoc"
	Note: In the string, each word is separated by single space and there will not be any extra space in the string.
 */
public class String09_ReverseWords_I {
	
	@Test
	public void reverse(){
		Assert.assertEquals(approach01("Let's take LeetCode contest"), "s'teL ekat edoCteeL tsetnoc");
		Assert.assertEquals(approach02("Let's take LeetCode contest"), "s'teL ekat edoCteeL tsetnoc");
	}
	
	
	public String approach01(String s){
		String[] input = s.split(" ");
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i < input.length; i++){
			result.append(new StringBuilder(input[i]).reverse());
			result.append(" ");
		}		
		return result.toString().trim();
	}
	
	public String approach02(String s){
		StringBuilder word = new StringBuilder();
		StringBuilder answer = new StringBuilder();
		int length = s.length();
		char[] input = s.toCharArray();
		for(int i=0; i < length; i++){
			if(s.charAt(i)!= ' '){
				word.append(s.charAt(i));
			}
			else{
				answer.append(word.reverse());
				answer.append(" ");
				word.setLength(0);
			}
		}
		if(word.length() > 0) {
			answer.append(word.reverse());
			answer.append(" ");
			word.setLength(0);
		}
		return answer.toString().trim();
	}
}

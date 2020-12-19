package algorithms.string.palindrome;

import org.testng.annotations.Test;
/********
 * 
 * https://leetcode.com/problems/valid-palindrome-ii/description/
 * 
 * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.

Example 1:
Input: "aba"
Output: True
Example 2:
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
Note:
The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
 */
public class String02_Palindrome_II {

	@Test
	public void Test(){
		String s = "abcda";
		System.out.println(isPalindrome(s));
	}
	
	 public boolean isPalindrome(String s) {		 
		 for(int i=0; i < s.length(); i++){
			 if(s.charAt(i) != s.charAt(s.length()-i-1)){
				 return (validPalindrome(s, i+1, s.length()-1-i) ||
						 validPalindrome(s, i, s.length()-2-i));
			 }
		 }	       
	   return false;
	 }
	
	 private boolean validPalindrome(String s, int start,int end){		 
		 while(start < end){
			 if(s.charAt(start) != s.charAt(end)){
				 return false;
			 }
			 start++;
			 end--;
		 }
		 return true;
	 }
}



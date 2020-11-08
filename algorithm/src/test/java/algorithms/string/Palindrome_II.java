package algorithms.string;

import org.testng.annotations.Test;
/********
 * 
 * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
 * https://leetcode.com/problems/valid-palindrome-ii/description/
 */
public class Palindrome_II {

	@Test
	public void Test(){
		String s = "aman";
		System.out.println(isPalindrome(s));
	}
	
	 public boolean isPalindrome(String s) {		 
		 for(int i=0; i < s.length(); i++){
			 if(s.charAt(i) != s.charAt(s.length()-i-1)){
				 return (validPalindrome(s, i+1, s.length()-1-i) ||
						 validPalindrome(s, i, s.length()-2-i));
			 }
		 }	       
	   return true;
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



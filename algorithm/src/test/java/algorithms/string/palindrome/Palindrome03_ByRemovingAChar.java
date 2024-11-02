package algorithms.string.palindrome;
import org.testng.annotations.Test;
/****
 * 
 * https://www.geeksforgeeks.org/remove-character-string-make-palindrome/
   Given a string, we need to check whether it is possible to make this string a palindrome after removing exactly one character from this.

	Examples:
	
	Input  : str = �abcba�
	Output : Yes
	we can remove character �c� to make string palindrome
	
	Input  : str = �abcbea�
	Output : Yes
	we can remove character �e� to make string palindrome
	
	Input : str = �abecbea�
	It is not possible to make this string palindrome 
	just by removing one character 

 */
public class Palindrome03_ByRemovingAChar {
	
		@Test
		public void test_isPalindrome(){
			System.out.println(validPalindrome("acba"));
		}
	
	  public boolean validPalindrome(String s) {
	        
	        for(int i=0; i < s.length()/2; i++){
	            
	            if(s.charAt(i) != s.charAt(s.length()-i-1)){
	                return isPalindrome(s, i+1, s.length()-i-1) ||
	                    isPalindrome(s, i, s.length()-i-2);
	            }
	            
	        }
	        
	        return true;
	    }
	    
	    private boolean isPalindrome(String s, int start, int end){
	        
	        while(start <= end){
	            if(s.charAt(start) != s.charAt(end)){
	                return false;
	            }
	            start++;
	            end--;
	        }
	        return true;
	    }

}

package arrayAlgorithms;
import org.testng.annotations.Test;

public class Array52_Palindrome_Remove1Char {
	
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

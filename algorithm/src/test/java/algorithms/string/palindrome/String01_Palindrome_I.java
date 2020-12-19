package algorithms.string.palindrome;
import org.testng.annotations.Test;
/****
 * 
 * https://leetcode.com/problems/valid-palindrome/description/
 *
 */
public class String01_Palindrome_I {

	@Test
	public void Test(){
		String s = "A man, a plan, a canal: Panama";
		System.out.println(isPalindrome(s));
	}
	
	/*
	 * Time Complexity O(n)
	 * Space Complexity O(1)
	 */
	 public boolean isPalindrome(String s) {
	        
	        if(s == null || s.length() == 0)
	            return false;
	        s = s.toLowerCase();
	        int i= 0, j= s.length()-1;
	        while(i < j){
	            
	            while((i<j) && !Character.isLetterOrDigit(s.charAt(i))){
	                i++;
	            }
	            while((i<j) && !Character.isLetterOrDigit(s.charAt(j))){
	                j--;
	            }
	            if(i<j){
	                if(Character.compare(s.charAt(i), s.charAt(j)) != 0){
	                    return false;
	                }
	                i++;
	                j--;
	            }            
	        }
	        
	        return true;
	    }
}

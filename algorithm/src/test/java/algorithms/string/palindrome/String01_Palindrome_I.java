package algorithms.string.palindrome;
import org.testng.annotations.Test;
/****
 * 
 * https://leetcode.com/problems/valid-palindrome/description/
 *  
 *  Example 1:
	Input: s = "A man, a plan, a canal: Panama"
	Output: true
	Explanation: "amanaplanacanalpanama" is a palindrome.
	
	Example 2:
	Input: s = "race a car"
	Output: false
	Explanation: "raceacar" is not a palindrome.
	
	Example 3:
	Input: s = " "
	Output: true
	Explanation: s is an empty string "" after removing non-alphanumeric characters.
	Since an empty string reads the same forward and backward, it is a palindrome.
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
	 * This approach works better than using regular expression
	 *  s = s.replaceAll("[^A-Za-z0-9]","").toLowerCase();
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
	                if(s.charAt(i) != s.charAt(j)){
	                    return false;
	                }
	                i++;
	                j--;
	            }            
	        }
	        
	        return true;
	    }

	public boolean isPalindrome2(String s) {
		if(s == null || s.length() < 2) return true;

		s = s.toLowerCase();
		s = s.replaceAll("\\s","");
		s = s.replaceAll("[^A-Za-z0-9]","");
		int start = 0, end = s.length()-1;

		while(start < end){
			if(s.charAt(start) != s.charAt(end))
				return false;
			else{
				start++;
				end--;
			}
		}

		return true;
	}
}

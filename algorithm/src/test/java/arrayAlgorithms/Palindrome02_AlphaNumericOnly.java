package arrayAlgorithms;
import org.testng.annotations.Test;
/*****
 	https://leetcode.com/problems/valid-palindrome/description/
 
	Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
	
	Note: For the purpose of this problem, we define empty string as valid palindrome.
	
	Example 1:
	
	Input: "A man, a plan, a canal: Panama"
	Output: true
	Example 2:
	
	Input: "race a car"
	Output: false

 */
public class Palindrome02_AlphaNumericOnly {

	
	@Test
	public void isPalindrome(){
		System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
	}
	
	public boolean isPalindrome(String s) {
        
        char[] input = s.toLowerCase().toCharArray();
        
        int i=0, j = s.length()-1;
        
        while(i <= j){
            if(!Character.isLetterOrDigit(input[i])){
                i++;
            }
            if(!Character.isLetterOrDigit(input[j])){
                j--;
            }
            
            if(i <= j && Character.isLetterOrDigit(input[i]) && Character.isLetterOrDigit(input[j])){
                if(input[i] != input[j]){
                    return false;
                }
                else{
                    i++;
                    j--;
                }
            }
        }
        
        return true;
        
    }
}

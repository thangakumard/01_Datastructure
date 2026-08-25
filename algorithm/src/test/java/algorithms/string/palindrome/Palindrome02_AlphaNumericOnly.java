package algorithms.string.palindrome;
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
        int left = 0, right = s.length() - 1;

        while (left < right) {
            char cl = s.charAt(left);
            char cr = s.charAt(right);

            if (!Character.isLetterOrDigit(cl)) { left++; continue; }
            if (!Character.isLetterOrDigit(cr)) { right--; continue; }

            if (Character.toLowerCase(cl) != Character.toLowerCase(cr)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}

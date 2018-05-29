package arrayAlgorithms;
import org.testng.annotations.Test;
public class Array51_PalindromeAlphaNumericOnly {

	
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

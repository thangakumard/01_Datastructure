package algorithms.string;

import org.testng.Assert;
import org.testng.annotations.Test;

/*******
 * https://leetcode.com/problems/masking-personal-information/description/
 * Example 1:
	
	Input: "LeetCode@LeetCode.com"
	Output: "l*****e@leetcode.com"
	Explanation: All names are converted to lowercase, and the letters between the
	             first and last letter of the first name is replaced by 5 asterisks.
	             Therefore, "leetcode" -> "l*****e".
	Example 2:
	
	Input: "AB@qq.com"
	Output: "a*****b@qq.com"
	Explanation: There must be 5 asterisks between the first and last letter 
	             of the first name "ab". Therefore, "ab" -> "a*****b".
	Example 3:
	
	Input: "1(234)567-890"
	Output: "***-***-7890"
	Explanation: 10 digits in the phone number, which means all digits make up the local number.
	Example 4:
	
	Input: "86-(10)12345678"
	Output: "+**-***-***-5678"
	Explanation: 12 digits, 2 digits for country code and 10 digits for local number. 
	Notes:
	
	S.length <= 40.
	Emails have length at least 8.
	Phone numbers have length at least 10.
 *
 */

public class MaskPersonalInformation {
	
	@Test
	 public void MaskTest(){
		 Assert.assertEquals(maskPII("thanga@gmail.com"),"t*****a@gmail.com");
		 Assert.assertEquals(maskPII("aa@gmail.com"),"a*****a@gmail.com");
		 Assert.assertEquals(maskPII("1(234)567-890"),"***-***-7890");
		 Assert.assertEquals(maskPII("86-(10)12345678"),"+**-***-***-5678");
	 }
	
	 public String maskPII(String S) {                
	        if(S.indexOf('@') > -1){
	            return maskEmail(S);
	        }
	        else{
	            return maskPhoneNumber(S);
	        }        
	    }
	    
	    private String maskEmail(String S){
//	        S = S.toLowerCase(); 
//	        if(S.indexOf('@') == 2){
//	            StringBuilder email = new StringBuilder();
//	            email.append(S.charAt(0));
//	            email.append("*****");
//	            email.append(S.charAt(1));
//	            email.append(S.substring(2,S.length()));
//	            S = email.toString();
//	        }
//	        else{
//	            String toReplace = S.substring(1,S.indexOf('@')-1);
//	            S = S.replaceFirst(toReplace, "*****");           
//	        }
//	        return S;
	    	return S.substring(0,1) + "*****" + S.substring(S.indexOf('@')-1).toLowerCase();
	    }
	    
	    private String maskPhoneNumber(String S){
	    	String digits = S.replaceAll("\\D+", "");
            String local = "***-***-" + digits.substring(digits.length() - 4);
            if(digits.length() == 10)
            	return local;
            String ans = "+";
            for(int i=0; i < digits.length()-10; i++)
            	ans += "*";
            return ans + "-" + local;
	    }
	    
	    private String maskPhoneNumber1(String S){
	        String input = S;
	        S = S.replace("(","");
	        S = S.replace(")","");
	        S = S.replace(" ","");
	        S = S.replace("+","");
	        String phoneNumbers = S.replace("-","");       
	        
	        
	        if(phoneNumbers.length()  == 10){
	            S = "***-***-" + phoneNumbers.substring(phoneNumbers.length()-4,phoneNumbers.length());
	        }
	        else  if(phoneNumbers.length() > 10){
	            if(phoneNumbers.length() == 12){
	                S = "+**-***-***-" + phoneNumbers.substring(phoneNumbers.length()-4,phoneNumbers.length());
	            }
	            else{
	                int countryCode = phoneNumbers.length() - 10;
	                StringBuilder number = new StringBuilder();
	                number.append("+");
	                for(int i=0; i< countryCode; i++){
	                    number.append("*");
	                }
	                number.append("-***-***-");
	                number.append(phoneNumbers.substring(phoneNumbers.length()-4,phoneNumbers.length()));
	                S = number.toString();
	            }
	        }
	        return S;
	    }
}

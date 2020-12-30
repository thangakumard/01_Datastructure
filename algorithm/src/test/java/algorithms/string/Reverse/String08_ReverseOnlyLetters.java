package algorithms.string.Reverse;

/*
 * https://leetcode.com/problems/reverse-only-letters/
 * 
 * Given a string S, return the "reversed" string where all characters that are not a letter stay in the same place, and all letters reverse their positions.

 

Example 1:

Input: "ab-cd"
Output: "dc-ba"
Example 2:

Input: "a-bC-dEf-ghIj"
Output: "j-Ih-gfE-dCba"
Example 3:

Input: "Test1ng-Leet=code-Q!"
Output: "Qedo1ct-eeLg=ntse-T!"
 

Note:

S.length <= 100
33 <= S[i].ASCIIcode <= 122 
S doesn't contain \ or "

 */
public class String08_ReverseOnlyLetters {

	public String reverseOnlyLetters(String S) {
        char[] input = S.toCharArray();
        int left = 0, right = S.length() -1;
        while(left < right){
            while(left < right && !Character.isAlphabetic(input[left]))
                left++;
            while(left < right && !Character.isAlphabetic(input[right]))
                right--;
            if(left < right){
                char temp = input[left];
                input[left] = input[right];
                input[right] = temp;
                left++;
                right--;
            }
        }
        return new String(input);
    }
	
	 public String reverseOnlyLetters_fromLeetcode(String S) {
	        StringBuilder sb = new StringBuilder(S);
	        for (int i = 0, j = S.length() - 1; i < j;) {
	            if (!Character.isLetter(sb.charAt(i))) {
	                ++i;
	            } else if (!Character.isLetter(sb.charAt(j))) {
	                --j;
	            } else {
	                sb.setCharAt(i, S.charAt(j));
	                sb.setCharAt(j--, S.charAt(i++));
	            }
	        }
	        return sb.toString();
	    }
}


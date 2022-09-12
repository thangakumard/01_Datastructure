package algorithms.string;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/*******
 * 
 * https://leetcode.com/problems/detect-capital/
 * 
Given a word, you need to judge whether the usage of capitals in it is right or not.

We define the usage of capitals in a word to be right when one of the following cases holds:

All letters in this word are capitals, like "USA".
All letters in this word are not capitals, like "leetcode".
Only the first letter in this word is capital, like "Google".
Otherwise, we define that this word doesn't use capitals in a right way.
 

Example 1:

Input: "USA"
Output: True
 

Example 2:

Input: "FlaG"
Output: False
 *
 */
public class String37_DetectCapitalUse_I {

    @Test
    public void detectCapitalUseTest(){
        Assertions.assertThat(detectCapitalUse_01("USA")).isTrue();
        Assertions.assertThat(detectCapitalUse_01("uSA")).isFalse();
        Assertions.assertThat(detectCapitalUse_01("Leetcode")).isTrue();
        Assertions.assertThat(detectCapitalUse_01("leetcode")).isTrue();
        Assertions.assertThat(detectCapitalUse_01("leetCode")).isFalse();

    }
    public boolean detectCapitalUse_01(String word) {
        int upperCaseCount = 0;
        for(char c: word.toCharArray()){
            if(Character.isUpperCase(c)){
                upperCaseCount++;
            }
        }
        
        if(upperCaseCount == 0 || upperCaseCount == word.length() || 
           (upperCaseCount == 1 && Character.isUpperCase(word.charAt(0)))){
            return true;
        }
        return false;
    }

    public boolean detectCapitalUse_02(String word) {
        if(word.length() == 1){
            return true;
        }
        if(word.charAt(0) - 'a' >= 0){
            return isAllSmall(word);
        }
        else if(word.charAt(0) - 'a' < 0){
            if(word.charAt(1) - 'a' >= 0){
                return isAllCamelCase(word);
            }else{
                return isAllUpper(word);
            }
        }
        return true;
    }

    private boolean isAllSmall(String s){
        for(char c: s.toCharArray()){
            if(c - 'a' < 0){
                return false;
            }
        }
        return true;
    }
    private boolean isAllUpper(String s){
        for(char c: s.toCharArray()){
            if(c - 'a' >= 0){
                return false;
            }
        }
        return true;
    }
    private boolean isAllCamelCase(String s){
        for(int i=2; i < s.length(); i++){
            if(s.charAt(i) - 'a' < 0){
                return false;
            }
        }
        return true;
    }

}

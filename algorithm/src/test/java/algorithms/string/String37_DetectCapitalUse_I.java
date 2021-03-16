package algorithms.string;

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

    public boolean detectCapitalUse(String word) {
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
}

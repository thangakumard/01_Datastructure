package algorithms.string.parenthesis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 * https://leetcode.com/problems/remove-invalid-parentheses/description/
 *
 * Given a string s that contains parentheses and letters, remove the minimum number of invalid parentheses to make the input string valid.
 *
 * Return a list of unique strings that are valid with the minimum number of removals. You may return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "()())()"
 * Output: ["(())()","()()()"]
 * Example 2:
 *
 * Input: s = "(a)())()"
 * Output: ["(a())()","(a)()()"]
 * Example 3:
 *
 * Input: s = ")("
 * Output: [""]
 *
 *
 * Constraints:
 *
 * 1 <= s.length <= 25
 * s consists of lowercase English letters and parentheses '(' and ')'.
 * There will be at most 20 parentheses in s.
 */
public class Parenthesis03_removeinValidParentheses {
    private boolean isValid(String s){
        char[] input = s.toCharArray();
        int counter = 0;
        for(char c: input){
            if(c == '('){
                counter++;
            }else if (c == ')'){
                counter--;
            }
            if(counter < 0){
                return false;
            }
        }
        return counter == 0;
    }

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();
        Set<String> input = new HashSet<>();
        input.add(s);

        while(true){

            for(String str: input){
                if(isValid(str)){
                    result.add(str);
                }
            }

            if(!result.isEmpty()){
                return result;
            }

            Set<String> nextLevel = new HashSet<>();

            for(String str: input){
                for(int i=0; i < str.length(); i++){
                    if(str.charAt(i) == '(' || str.charAt(i) == ')'){
                        String newString = str.substring(0, i) + str.substring(i+1);
                        nextLevel.add(newString);
                    }
                }
            }
            input = nextLevel;
        }

    }
}

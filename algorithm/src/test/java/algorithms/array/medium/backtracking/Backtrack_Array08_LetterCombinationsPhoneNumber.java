package algorithms.array.medium.backtracking;

import java.util.*;;
/***
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/ Given a
 * string containing digits from 2-9 inclusive, return all possible letter
 * combinations that the number could represent. Return the answer in any order.
 * 
 * A mapping of digit to letters (just like on the telephone buttons) is given
 * below. Note that 1 does not map to any letters.
 * 
 * 
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: digits = "23" Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * Example 2:
 * 
 * Input: digits = "" Output: [] Example 3:
 * 
 * Input: digits = "2" Output: ["a","b","c"]
 * 
 * 
 * Constraints:
 * 
 * 0 <= digits.length <= 4 digits[i] is a digit in the range ['2', '9'].
 * 
 */
public class Backtrack_Array08_LetterCombinationsPhoneNumber {

public List<String> letterCombinations(String digits) {
        
        List<String> result = new ArrayList<>();
        if(digits == null || digits.isEmpty() || digits.trim().isEmpty())
            return result;
        HashMap<Integer, String> dial_map = new HashMap<Integer, String>();
        dial_map.put(2, "abc");
        dial_map.put(3, "def");
        dial_map.put(4, "ghi");
        dial_map.put(5, "jkl");
        dial_map.put(6, "mno");
        dial_map.put(7, "pqrs");
        dial_map.put(8, "tuv");
        dial_map.put(9, "wxyz");
        
        String[] input = new String[digits.length()];
        int i =0;
        for(char number: digits.toCharArray()){
             input[i++] = dial_map.get(Character.getNumericValue(number));
        }
        
        backtrack(result, "", input, digits.length(), 0);
        
        return result;
        
    }
    
    private void backtrack(List<String> result, String current, String[] input, int length, int start){
        if(start == length){
            result.add(current);
            return;
        }
        
        String letters = input[start];
        for(int i=0; i < letters.length(); i++){
            backtrack(result, current + letters.charAt(i), input, length, start+1);
        }
    }
}

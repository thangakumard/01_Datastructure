package algorithms.array.medium.backtracking.combination;

import java.util.*;

import org.testng.annotations.Test;;
/***
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 * https://www.youtube.com/watch?v=21OuwqIC56E&list=PLi9RQVmJD2fapKJ4DnZzAn55NJfo5IM1c&index=14
 * 
 *  Given a
 * string containing digits from 2-9 inclusive, return all possible letter
 * combinations that the number could represent. Return the answer in any order.
 * 
 * A mapping of digit to letters (just like on the telephone buttons) is given
 * below. Note that 1 does not map to any letters.
 * 
 * Example 1:
 * Input: digits = "23" Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *
 * Example 2:
 * Input: digits = "" Output: []
 *
 * Example 3:
 * Input: digits = "2" Output: ["a","b","c"]
 *
 * Constraints:
 * 0 <= digits.length <= 4 digits[i] is a digit in the range ['2', '9'].
 * 
 */
public class Backtrack_Array07_CombinationsPhoneNumber {
	
	@Test
	private void test() {
		System.out.println(letterCombinations("23"));
	}

public List<String> letterCombinations(String digits) {
        
        List<String> result = new ArrayList<>();
        if(digits == null || digits.isEmpty() || digits.trim().isEmpty())
            return result;
        HashMap<Integer, String> dial_map = new HashMap<>();
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
        backtrack(result, "", input, 0);
        return result;
    }

    /**
     * Time : O((4 ^ N) * N) === O(4 ^ N)
     * N is the length of digits.
     * Note that 4 in this expression is referring to the maximum value length in the hash map
     *
     * Space: O(4 ^ N)
     */
    private void backtrack(List<String> result, String current, String[] input, int start){
        if(start == input.length){
            result.add(current);
            return;
        }
        
        String letters = input[start];
        for(int i=0; i < letters.length(); i++){
            backtrack(result, current + letters.charAt(i), input, start+1);
        }
    }
}

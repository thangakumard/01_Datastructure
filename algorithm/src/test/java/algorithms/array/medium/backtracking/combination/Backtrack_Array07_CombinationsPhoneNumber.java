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
/**
    ------------
    Time complexity:
    ------------
     Time complexity : O(4^N * N)
     Why ? Each digit maps to at most 4 letters:
     7 -> pqrs (4)
    9 -> wxyz (4)
    others -> 3. Worst case assume 4 each.
    So, we have 4 ^ N Choices.
    Backtracking inner for loop takes O(N). So Time complexity is O(4^N * N)
    ------------
    Space complexity:
    ------------
    O(4^N * N)  (output)
    O(N)        (recursion stack)

    Total → O(4^N * N)
    Space complexity: O(N)
 */
public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        // Edge case: empty string
        if (digits == null || digits.length() == 0) {
            return result;
        }

        // Mapping of digits to letters
        String[] mapping = {
                "",     // 0
                "",     // 1
                "abc",  // 2
                "def",  // 3
                "ghi",  // 4
                "jkl",  // 5
                "mno",  // 6
                "pqrs", // 7
                "tuv",  // 8
                "wxyz"  // 9
        };

        backtrack(digits, 0, new StringBuilder(), result, mapping);
        return result;
    }

    private void backtrack(String digits, int index, StringBuilder current,
                           List<String> result, String[] mapping) {
        // Base case: processed all digits
        if (index == digits.length()) {
            result.add(current.toString());
            return;
        }

        // Get the letters for current digit
        char digit = digits.charAt(index);
        String letters = mapping[digit - '0'];

        // Try each letter for this digit
        for (char letter : letters.toCharArray()) {
            current.append(letter);
            backtrack(digits, index + 1, current, result, mapping);
            current.deleteCharAt(current.length() - 1); // Backtrack
        }
    }

}

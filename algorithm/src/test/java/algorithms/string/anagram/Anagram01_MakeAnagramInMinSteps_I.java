package algorithms.string.anagram;

import java.util.HashMap;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/
 * Given two equal-size strings s and t.
 * In one step you can choose any character of t and replace it with another character.
	Return the minimum number of steps to make t an anagram of s.
	An Anagram of a string is a string that contains the same characters with a different (or the same) ordering.
	
	Example 1:
	Input: s = "bab", t = "aba"
	Output: 1
	Explanation: Replace the first 'a' in t with b, t = "bba" which is anagram of s.

	Example 2:
	Input: s = "leetcode", t = "practice"
	Output: 5
	Explanation: Replace 'p', 'r', 'a', 'i' and 'c' from t with proper characters to make t anagram of s.

	Example 3:
	Input: s = "anagram", t = "mangaar"
	Output: 0
	Explanation: "anagram" and "mangaar" are anagrams. 

	Example 4:
	Input: s = "xxyyzz", t = "xxyyzz"
	Output: 0

	Example 5:
	Input: s = "friend", t = "family"
	Output: 4
	
	Constraints:
	1 <= s.length <= 50000
	s.length == t.length
	s and t contain lower-case English letters only.

 */
public class Anagram01_MakeAnagramInMinSteps_I {
	
	@Test
	private void test() {
		String A = "abc";
		String B = "xyz";
        Assertions.assertThat(minSteps(A, B)).isEqualTo(3);
	}

	public int minSteps(String s, String t) {
        int[] char_count = new int[26];
        char[] first_input = s.toCharArray();
        char[] second_input = t.toCharArray();
        
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for(int i=0; i < first_input.length; i++){
            char_count[first_input[i] - 'a']++;
        }
        
        for(int i=0; i < second_input.length; i++){
            char_count[second_input[i] - 'a']--;
        }
        
        int min_swap = 0;
        for(int i=0; i < 26; i++){
            if(char_count[i] < 0){
               min_swap += Math.abs(char_count[i]);
            }
        }
        
        return min_swap;
        
    }
}

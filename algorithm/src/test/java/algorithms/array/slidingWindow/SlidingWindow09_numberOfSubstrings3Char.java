package algorithms.array.medium.slidingWindow;

/**
 * https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/
 * Given a string s consisting only of characters a, b and c.
 * Return the number of substrings containing at least one occurrence of all these characters a, b and c.
 *
 * Example 1:
 * Input: s = "abcabc"
 * Output: 10
 * Explanation: The substrings containing at least one occurrence of the characters a, b and c are "abc", "abca", "abcab", "abcabc", "bca", "bcab", "bcabc", "cab", "cabc" and "abc" (again).

 * Example 2:
 * Input: s = "aaacb"
 * Output: 3
 * Explanation: The substrings containing at least one occurrence of the characters a, b and c are "aaacb", "aacb" and "acb".

 * Example 3:
 * Input: s = "abc"
 * Output: 1
 *
 * Constraints:
 * 3 <= s.length <= 5 x 10^4
 * s only consists of a, b or c characters.
 */
public class SlidingWindow09_numberOfSubstrings3Char {
    public int numberOfSubstrings(String s) {
        int[] counter = new int[3];
        int i = 0, result = 0;
        for(int j=0; j < s.length(); j++){
            counter[s.charAt(j) - 'a']++;
            while(counter[0] > 0 && counter[1] > 0 && counter[2] > 0){
                counter[s.charAt(i) - 'a']--;
                i++;
            }
            result += i;
        }
        return result;
    }
}

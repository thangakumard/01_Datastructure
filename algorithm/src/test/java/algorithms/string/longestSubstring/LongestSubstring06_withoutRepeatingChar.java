package algorithms.string.longestSubstring;

import java.util.HashMap;
import java.util.Map;

/***
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Given a string s, find the length of the longest substring without duplicate characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3. Note that "bca" and "cab" are also correct answers.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 *
 * Constraints:
 *
 * 0 <= s.length <= 5 * 104
 * s consists of English letters, digits, symbols and spaces.
 */
public class LongestSubstring06_withoutRepeatingChar {
    public int lengthOfLongestSubstring(String s) {
        // Map to store the most recent index of each character
        Map<Character, Integer> charIndex = new HashMap<>();
        int maxLen = 0;
        int left = 0;  // Left pointer of the window

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If character is in current window, move left pointer
            if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
                left = charIndex.get(c) + 1;
            }

            // Update character's most recent index
            charIndex.put(c, right);

            // Update max length
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}

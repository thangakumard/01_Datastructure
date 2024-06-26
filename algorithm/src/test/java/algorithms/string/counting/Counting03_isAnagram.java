package algorithms.string.counting;

/**
 * https://leetcode.com/problems/valid-anagram/description/
 *
 * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
 *
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.
 *
 *
 *
 * Example 1:
 *
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * Example 2:
 *
 * Input: s = "rat", t = "car"
 * Output: false
 *
 *
 * Constraints:
 *
 * 1 <= s.length, t.length <= 5 * 104
 * s and t consist of lowercase English letters.
 *
 */
public class Counting03_isAnagram {
    public boolean isAnagram(String s, String t) {
        int[] counter = new int[26];
        char[] arryS = s.toCharArray();
        char[] arrT = t.toCharArray();

        for(Character c: arryS){
            counter[c - 'a']++;
        }
        for(Character c: arrT){
            if(counter[c - 'a'] == 0) return false;
            counter[c - 'a']--;
        }
        for(int i=0; i < 26; i++){
            if(counter[i] != 0) return false;
        }

        return true;
    }
}

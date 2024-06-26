package algorithms.string.counting;

/***
 * https://leetcode.com/problems/ransom-note/description/?envType=study-plan-v2&envId=top-interview-150
 *
 * Given two strings ransomNote and magazine, return true if ransomNote can be constructed by using the letters from magazine and false otherwise.
 *
 * Each letter in magazine can only be used once in ransomNote.
 *
 *
 *
 * Example 1:
 *
 * Input: ransomNote = "a", magazine = "b"
 * Output: false
 * Example 2:
 *
 * Input: ransomNote = "aa", magazine = "ab"
 * Output: false
 * Example 3:
 *
 * Input: ransomNote = "aa", magazine = "aab"
 * Output: true
 *
 *
 * Constraints:
 *
 * 1 <= ransomNote.length, magazine.length <= 105
 * ransomNote and magazine consist of lowercase English letters.
 */
public class Counting01_RansomNote {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] counter = new int[26];
        char[] arrMagazine = magazine.toCharArray();
        char[] arrRansomNote = ransomNote.toCharArray();
        for(Character c: arrMagazine){
            counter[c - 'a']++;
        }
        for(Character c: arrRansomNote){
            if(counter[c - 'a'] == 0) return false;
            counter[c - 'a']--;
        }
        return true;
    }
}

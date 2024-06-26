package algorithms.string.counting;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/isomorphic-strings/
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
 * All occurrences of a character must be replaced with another character while preserving the order of characters.
 * No two characters may map to the same character, but a character may map to itself.
 *
 * Example 1:
 * Input: s = "egg", t = "add"
 * Output: true

 * Example 2:
 * Input: s = "foo", t = "bar"
 * Output: false

 * Example 3:
 * Input: s = "paper", t = "title"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 5 * 104
 * t.length == s.length
 * s and t consist of any valid ascii character.
 */

public class Counting02_Isomorphic {
    @Test
    public void checkIsomorphic()
    {
        Assertions.assertThat(isIsomorphic("booaabbcc", "tooccttgg")).isTrue();
        Assertions.assertThat(isIsomorphic("bbabaa", "aaabbb")).isFalse();

        Assertions.assertThat(isIsomorphic_Approach2("booaabbcc", "tooccttgg")).isTrue();
        Assertions.assertThat(isIsomorphic_Approach2("bbabaa", "aaabbb")).isFalse();
    }



    public boolean isIsomorphic_Approach2(String s, String t) {
        // Create arrays to store the index of characters in both strings
        int[] indexS = new int[200]; // Stores index of characters in string s
        int[] indexT = new int[200]; // Stores index of characters in string t

        // Get the length of both strings
        int len = s.length();

        // If the lengths of the two strings are different, they can't be isomorphic
        if(len != t.length()) {
            return false;
        }

        // Iterate through each character of the strings
        for(int i = 0; i < len; i++) {
            // Check if the index of the current character in string s
            // is different from the index of the corresponding character in string t
            if(indexS[s.charAt(i)] != indexT[t.charAt(i)]) {
                return false; // If different, strings are not isomorphic
            }

            // Update the indices of characters in both strings
            indexS[s.charAt(i)] = i + 1; // updating index of current character
            indexT[t.charAt(i)] = i + 1; // updating index of current character
        }

        // If the loop completes without returning false, strings are isomorphic
        return true;
    }

    public Boolean isIsomorphic(String s1, String s2)
    {
        if(s1 == null || s2 == null)
            return false;
        if(s1.length() != s2.length())
            return false;

        char c1, c2;
        HashMap<Character,Character> map = new HashMap<Character,Character>();

        for(int i=0; i < s1.length(); i++)
        {
            c1 = s1.charAt(i);
            c2 = s2.charAt(i);

            if(map.containsKey(c1))
            {
                if(map.get(c1) != c2)
                    return false;
            }
            else{
                if(map.containsValue(c2))
                    return false;

                map.put(c1, c2);
            }
        }
        return true;
    }
}

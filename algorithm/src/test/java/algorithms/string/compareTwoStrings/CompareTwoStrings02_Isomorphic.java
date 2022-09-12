package algorithms.string.compareTwoStrings;

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

public class CompareTwoStrings02_Isomorphic {
    @Test
    public void checkIsomorphic()
    {
        Assertions.assertThat(isIsomorphic("booaabbcc", "tooccttgg")).isTrue();
        Assertions.assertThat(isIsomorphic("bbabaa", "aaabbb")).isFalse();
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

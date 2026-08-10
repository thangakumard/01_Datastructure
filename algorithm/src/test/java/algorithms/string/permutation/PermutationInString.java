package algorithms.string.permutation;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*
 * LeetCode 567 - Permutation in String
 * https://leetcode.com/problems/permutation-in-string/
 *
 * Given two strings s1 and s2, return true if s2 contains a permutation of s1.
 * Uses a sliding window of length s1.length() over s2, tracking character frequency matches.
 */
public class PermutationInString {

    private PermutationInString sol;

    @BeforeClass
    public void setUp() {
        sol = new PermutationInString();
    }

    @Test
    public void testPermutationInMiddle() {
        // "ba" is a permutation of "ab" and exists in "eidbaooo"
        Assert.assertTrue(sol.checkInclusion("ab", "eidbaooo"));
    }

    @Test
    public void testNoPermutationPresent() {
        Assert.assertFalse(sol.checkInclusion("ab", "eidboaoo"));
    }

    @Test
    public void testS1LongerThanS2() {
        Assert.assertFalse(sol.checkInclusion("abc", "ab"));
    }

    @Test
    public void testEqualStringsArePermutation() {
        Assert.assertTrue(sol.checkInclusion("abc", "abc"));
    }

    @Test
    public void testPermutationAtStart() {
        Assert.assertTrue(sol.checkInclusion("cba", "cbaxyz"));
    }

    @Test
    public void testPermutationAtEnd() {
        Assert.assertTrue(sol.checkInclusion("xyz", "abcxzy"));
    }

    @Test
    public void testSingleCharMatch() {
        Assert.assertTrue(sol.checkInclusion("a", "bca"));
    }

    @Test
    public void testSingleCharNoMatch() {
        Assert.assertFalse(sol.checkInclusion("z", "abcde"));
    }

    @Test
    public void testDuplicateCharsInS1() {
        // "aab" permutation like "aba" should be found
        Assert.assertTrue(sol.checkInclusion("aab", "xyaabz"));
    }

    @Test
    public void testDuplicateCharsNotSatisfied() {
        // s2 has only one 'a', but s1 needs two
        Assert.assertFalse(sol.checkInclusion("aa", "ab"));
    }

    @Test
    public void testS1SameLengthAsS2Permutation() {
        Assert.assertTrue(sol.checkInclusion("dcba", "abcd"));
    }

    @Test
    public void testS1SameLengthAsS2NoPermutation() {
        Assert.assertFalse(sol.checkInclusion("abcd", "abce"));
    }

    /***
     * Time: O(n2), where n2 = len(s2). Each character enters and leaves the window exactly once, and each enter/leave does O(1) work (the matches trick avoids the O(26) array comparison at every position). Building need is O(n1), dominated by O(n2).
     * Space: O(1) — two fixed 26-length arrays, independent of input size. No auxiliary space grows with n1 or n2.
     */
    public boolean checkInclusion(String s1, String s2) {
        int n1 = s1.length(), n2 = s2.length();
        if (n1 > n2) return false;

        int[] need = new int[26];
        int[] window = new int[26];
        for (char c : s1.toCharArray()) need[c - 'a']++;

        int matches = 0;
        for (int i = 0; i < 26; i++) {
            if (need[i] == window[i]) matches++;
        }

        int left = 0;
        for (int right = 0; right < n2; right++) {
            int addIdx = s2.charAt(right) - 'a';
            if (need[addIdx] == window[addIdx]) matches--;
            window[addIdx]++;
            if (need[addIdx] == window[addIdx]) matches++;

            if (right - left + 1 > n1) {
                int remIdx = s2.charAt(left) - 'a';
                if (need[remIdx] == window[remIdx]) matches--;
                window[remIdx]--;
                if (need[remIdx] == window[remIdx]) matches++;
                left++;
            }

            if (matches == 26) return true;
        }
        return false;
    }
}

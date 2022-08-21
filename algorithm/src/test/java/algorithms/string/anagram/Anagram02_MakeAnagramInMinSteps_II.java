package algorithms.string.anagram;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/*
https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram-ii/

You are given two strings s and t. In one step, you can append any character to either s or t.
Return the minimum number of steps to make s and t anagrams of each other.
An anagram of a string is a string that contains the same characters with a different (or the same) ordering.

Example 1:
Input: s = "leetcode", t = "coats"
Output: 7
Explanation:
- In 2 steps, we can append the letters in "as" onto s = "leetcode", forming s = "leetcodeas".
- In 5 steps, we can append the letters in "leede" onto t = "coats", forming t = "coatsleede".
"leetcodeas" and "coatsleede" are now anagrams of each other.
We used a total of 2 + 5 = 7 steps.
It can be shown that there is no way to make them anagrams of each other with less than 7 steps.

Example 2:
Input: s = "night", t = "thing"
Output: 0
Explanation: The given strings are already anagrams of each other. Thus, we do not need any further steps.


Constraints:
1 <= s.length, t.length <= 2 * 105
s and t consist of lowercase English letters.


 */
public class Anagram02_MakeAnagramInMinSteps_II {

    @Test
    private void test() {
        String A = "gctcxyuluxjuxnsvmomavutrrfb";
        String B = "qijrjrhqqjxjtprybrzpyfyqtzf";
        Assertions.assertThat(minSteps(A, B)).isEqualTo(36);
    }

    public int minSteps(String s, String t) {
        char[] firstInput = s.toCharArray();
        char[] secondInput = t.toCharArray();

        int[] Char_count = new int[26];

        for(int i=0; i < firstInput.length; i++){
            Char_count[firstInput[i] - 'a'] ++;
        }

        for(int i=0; i < secondInput.length; i++){
            Char_count[secondInput[i] - 'a'] --;
        }

        int minSteps = 0;

        for(int i=0; i < 26; i++){
            minSteps += Math.abs(Char_count[i]);
        }

        return minSteps;
    }
}

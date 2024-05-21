package algorithms.string;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * https://leetcode.com/problems/count-pairs-of-similar-strings/description/
 * You are given a 0-indexed string array words.
 *
 * Two strings are similar if they consist of the same characters.
 *
 * For example, "abca" and "cba" are similar since both consist of characters 'a', 'b', and 'c'.
 * However, "abacba" and "bcfd" are not similar since they do not consist of the same characters.
 * Return the number of pairs (i, j) such that 0 <= i < j <= word.length - 1 and the two strings words[i] and words[j] are similar.
 *
 *
 *
 * Example 1:
 *
 * Input: words = ["aba","aabb","abcd","bac","aabc"]
 * Output: 2
 * Explanation: There are 2 pairs that satisfy the conditions:
 * - i = 0 and j = 1 : both words[0] and words[1] only consist of characters 'a' and 'b'.
 * - i = 3 and j = 4 : both words[3] and words[4] only consist of characters 'a', 'b', and 'c'.
 * Example 2:
 *
 * Input: words = ["aabb","ab","ba"]
 * Output: 3
 * Explanation: There are 3 pairs that satisfy the conditions:
 * - i = 0 and j = 1 : both words[0] and words[1] only consist of characters 'a' and 'b'.
 * - i = 0 and j = 2 : both words[0] and words[2] only consist of characters 'a' and 'b'.
 * - i = 1 and j = 2 : both words[1] and words[2] only consist of characters 'a' and 'b'.
 * Example 3:
 *
 * Input: words = ["nba","cba","dba"]
 * Output: 0
 * Explanation: Since there does not exist any pair that satisfies the conditions, we return 0.
 *
 *
 * Constraints:
 *
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 100
 * words[i] consist of only lowercase English letters.
 */
public class String43_similarPairs {

    @Test
    public void Test(){
        String[] input = new String[]{"aba","aabb","abcd","bac","aabc"};
        Assertions.assertThat(similarPairs(input)).isEqualTo(2);
    }

    public int similarPairs(String[] words) {
        int result = 0;
        int n = words.length;
        int[][] freq = new int[n][26];

        for(int i=0; i < n; i++){
            freq[i] = calculateFrequencyArray(words[i]);
        }

        for(int i=0; i < n-1; i++){
            for(int j = i+1; j < n; j++){
                if(hasSameCharacters(freq[i], freq[j])){
                    result++;
                }
            }
        }

        return result;
    }

    private int[] calculateFrequencyArray(String s){
        int[] arr = new int[26];

        for(char ch : s.toCharArray()){
            arr[ch - 'a'] = 1;
        }
        return arr;
    }

    private boolean hasSameCharacters(int[] arr1, int[] arr2){
        for(int i=0; i < 26; i++){
            if(arr1[i] != arr2[i]) return false;
        }
        return true;
    }
}

package algorithms.array.medium.wordDistance;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

/**
 * https://leetcode.com/problems/shortest-word-distance/description/
 *
 * Given an array of strings wordsDict and two different strings that already exist in the array word1 and word2,
 * return the shortest distance between these two words in the list.
 *
 * Example 1:
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"],
 * word1 = "coding", word2 = "practice"
 * Output: 3
 *
 * Example 2:
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"],
 * word1 = "makes", word2 = "coding"
 * Output: 1
 *
 * Constraints:
 *
 * 2 <= wordsDict.length <= 3 * 104
 * 1 <= wordsDict[i].length <= 10
 * wordsDict[i] consists of lowercase English letters.
 * word1 and word2 are in wordsDict.
 * word1 != word2
 */
public class ShortestWordDistance_01 {

    @Test
    public void test() {
        Assertions.assertThat(shortestDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "coding", "practice")).isEqualTo(3);
        Assertions.assertThat(shortestDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"}, "coding", "makes")).isEqualTo(1);
    }

        public int shortestDistance(String[] wordsDict, String word1, String word2) {
        int w1 = -1, w2 = -1, minDistance = Integer.MAX_VALUE;

        for(int i=0; i < wordsDict.length; i++){
            if(wordsDict[i].equals(word1)){
                w1 = i;
            }
            else if(wordsDict[i].equals(word2)){
                w2 = i;
            }
            if(w1 != -1 && w2 != -1){
                minDistance = Math.min(minDistance, Math.abs(w1-w2));
            }
        }

        return minDistance;
    }
}

package algorithms.array.medium.hashMap;

import java.util.HashMap;

/**
 * https://leetcode.com/problems/number-of-good-ways-to-split-a-string/description/
 *
 * You are given a string s.
 * A split is called good if you can split s into two non-empty strings sleft and sright where their concatenation is equal to s (i.e., sleft + sright = s) and the number of distinct letters in sleft and sright is the same.
 * Return the number of good splits you can make in s.
 *
 * Example 1:
 *
 * Input: s = "aacaba"
 * Output: 2
 * Explanation: There are 5 ways to split "aacaba" and 2 of them are good.
 * ("a", "acaba") Left string and right string contains 1 and 3 different letters respectively.
 * ("aa", "caba") Left string and right string contains 1 and 3 different letters respectively.
 * ("aac", "aba") Left string and right string contains 2 and 2 different letters respectively (good split).
 * ("aaca", "ba") Left string and right string contains 2 and 2 different letters respectively (good split).
 * ("aacab", "a") Left string and right string contains 3 and 1 different letters respectively.
 * Example 2:
 *
 * Input: s = "abcd"
 * Output: 1
 * Explanation: Split the string as follows ("ab", "cd").
 *
 * Constraints:
 * 1 <= s.length <= 105
 * s consists of only lowercase English letters.
 */

/**
 * Time O(N)
 * Space O(N)
 */
public class Map04_NumberOfWaysStringSplit {
    public int numSplits(String s) {
        HashMap<Character, Integer> set1 = new HashMap<>();
        HashMap<Character, Integer> set2 = new HashMap<>();

        for(char c: s.toCharArray()){
            set1.put(c, set1.getOrDefault(c, 0) + 1);
        }
        int counter = 0;

        for(int i=0; i < s.length(); i++){
            Character c = s.charAt(i);
            set1.put(c, set1.getOrDefault(c, 0) - 1);
            set2.put(c, set2.getOrDefault(c, 0) + 1);

            if(set1.getOrDefault(c, 0) == 0){
                set1.remove(c);
            }
            if(set1.size() == set2.size()){
                counter++;
            }
        }

        return counter;
    }
}

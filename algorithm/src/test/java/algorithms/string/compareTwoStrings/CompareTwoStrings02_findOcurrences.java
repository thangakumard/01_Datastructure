package algorithms.string.compareTwoStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/occurrences-after-bigram/description/
 *
 * Given two strings first and second, consider occurrences in some text of the form "first second third", where second comes immediately after first, and third comes immediately after second.
 *
 * Return an array of all the words third for each occurrence of "first second third".
 *
 *
 *
 * Example 1:
 *
 * Input: text = "alice is a good girl she is a good student", first = "a", second = "good"
 * Output: ["girl","student"]
 *
 * Example 2:
 * Input: text = "we will we will rock you", first = "we", second = "will"
 * Output: ["we","rock"]
 *
 * Constraints:
 *
 * 1 <= text.length <= 1000
 * text consists of lowercase English letters and spaces.
 * All the words in text are separated by a single space.
 * 1 <= first.length, second.length <= 10
 * first and second consist of lowercase English letters.
 * text will not have any leading or trailing spaces.
 */
public class CompareTwoStrings02_findOcurrences {
    public String[] findOcurrences(String text, String first, String second) {

        List<String> lstResult = new ArrayList<>();

        String[] input = text.split(" ");
        for (int i=1; i < input.length-1; i++) {
            if(input[i-1].equals(first) && input[i].equals(second)){
                lstResult.add(input[i+1]);
            }
        }

        return lstResult.toArray(String[]::new);
    }
}

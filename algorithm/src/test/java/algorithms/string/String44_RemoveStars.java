package algorithms.string;

/***
 * https://leetcode.com/problems/removing-stars-from-a-string
 */
public class String44_RemoveStars {
    public String removeStars(String s) {
        char[] chars = s.toCharArray();
        int i = 0; // Write pointer

        for (char c : chars) {
            if (c == '*') {
                i--; // Move pointer back to "delete" the previous character
            } else {
                chars[i++] = c; // Overwrite character at pointer
            }
        }
        return new String(chars, 0, i);
    }
}

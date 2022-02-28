package algorithms.array.medium;
import org.testng.annotations.*;

/**
 * https://leetcode.com/problems/sentence-screen-fitting/
 *
 * Given a rows x cols screen and a sentence represented as a list of strings, return the number of times the given sentence can be fitted on the screen.
 * The order of words in the sentence must remain unchanged, and a word cannot be split into two lines. A single space must separate two consecutive words in a line.
 */

public class Array34_SentenceScreenFitting {
	
	@Test
	private void Test() {
		String[] input = new String[] {"hello","world"};
		System.out.println(wordsTyping(input,2,8));
	}

	public int wordsTyping(String[] sentence, int rows, int cols) {
	    String s = String.join(" ", sentence) + " ";
	    int len = s.length(), count = 0;//s="hello world "
	    int[] map = new int[len];
	    for (int i = 1; i < len; ++i) {
	        map[i] = s.charAt(i) == ' ' ? 1 : map[i-1] - 1;
	    }
	    //map values = 0,-1,-2,-3,-4,1,0,-1,-2,-3,-4,1
	    for (int i = 0; i < rows; ++i) {
	        count += cols;
	        count += map[count % len];
	    }
	    return count / len;
	}
}

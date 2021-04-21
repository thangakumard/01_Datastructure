package algorithms.array.medium;
import org.testng.annotations.*;

public class Array34_SentenceScreenFitting {
	
	@Test
	private void Test() {
		String[] input = new String[] {"hello","world"};
		System.out.println(wordsTyping(input,2,8));
	}

	public int wordsTyping(String[] sentence, int rows, int cols) {
	    String s = String.join(" ", sentence) + " ";
	    int len = s.length(), count = 0;
	    int[] map = new int[len];
	    for (int i = 1; i < len; ++i) {
	        map[i] = s.charAt(i) == ' ' ? 1 : map[i-1] - 1;
	    }
	    for (int i = 0; i < rows; ++i) {
	        count += cols;
	        count += map[count % len];
	    }
	    return count / len;
	}
}

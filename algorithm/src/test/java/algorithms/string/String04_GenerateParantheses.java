package algorithms.string;
import java.util.*;

import org.testng.annotations.Test;
/****
 * https://leetcode.com/problems/generate-parentheses/ 
 * Given n pairs of
 * parentheses, write a function to generate all combinations of well-formed
 * parentheses.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: n = 3 Output: ["((()))","(()())","(())()","()(())","()()()"] Example
 * 2:
 * 
 * Input: n = 1 Output: ["()"]
 * 
 * 
 * Constraints:
 * 
 * 1 <= n <= 8
 *
 */

public class String04_GenerateParantheses {

	@Test
	 public void test() {
		List<String> result = this.generateValidParentheses(2);
		System.out.println("All combinations of balanced parentheses are: " + result);

		result = this.generateValidParentheses(3);
		System.out.println("All combinations of balanced parentheses are: " + result);
	 }
	 
	  public static List<String> generateValidParentheses(int num) {
	    List<String> result = new ArrayList<>();
	    backtrack(result, "", 0, 0, num);
	    return result;
	  }

	  private static void backtrack(List<String> result, String current_string,
									int open, int close, int requiredPair){
	    if(current_string.length() == requiredPair * 2)
	    {
	      result.add(current_string);
	      return;
	    }
	    if(open<requiredPair) backtrack(result, current_string + "(", open+1, close, requiredPair);
	    if(close<open) backtrack(result, current_string + ")", open, close+1, requiredPair);
	  }	
}

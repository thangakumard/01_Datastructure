package algorithms.string;
import java.util.*;

import org.testng.annotations.Test;

public class String04_GenerateParantheses {

	@Test
	 public void test() {
		    List<String> result = this.generateValidParentheses(2);
		    System.out.println("All combinations of balanced parentheses are: " + result);

		    result = this.generateValidParentheses(3);
		    System.out.println("All combinations of balanced parentheses are: " + result);
	 }
	 
	  public static List<String> generateValidParentheses(int num) {
	    List<String> result = new ArrayList<String>();
	    backtrack(result, "", 0, 0, num);
	    return result;
	  }

	  private static void backtrack(List<String> result, String current_string,int open, int close, int max){
	    if(current_string.length() == max * 2)
	    {
	      result.add(current_string);
	      return;
	    }
	    if(open<max) backtrack(result, current_string + "(", open+1, close, max);
	    if(close<open) backtrack(result, current_string + ")", open, close+1, max);
	  }	
}

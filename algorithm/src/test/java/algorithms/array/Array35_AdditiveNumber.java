package algorithms.array;
import org.testng.Assert;
import org.testng.annotations.Test;

/****
 * 
	https://leetcode.com/problems/additive-number/description/
	
	Example 1:
	
	Input: "112358"
	Output: true 
	Explanation: The digits can form an additive sequence: 1, 1, 2, 3, 5, 8. 
	             1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
	Example 2:
	
	Input: "199100199"
	Output: true 
	Explanation: The additive sequence is: 1, 99, 100, 199. 
	             1 + 99 = 100, 99 + 100 = 199
 *
 */
public class Array35_AdditiveNumber {

	 public boolean isAdditiveNumber(String num) {
	        for (int i = 1; i <= num.length() / 2; ++i) {
	            if (num.charAt(0) == '0' && i > 1) continue;
	            for (int j = i + 1; j < num.length(); ++j) {
	                if (num.charAt(i) == '0' && j - i > 1) continue;
	                if (dfs(num, 0, i, j)) return true;
	            }
	        }
	        return false;
	    }
	 
	 private static boolean dfs(String num, int i, int j, int k) {
	        long num1 = Long.parseLong(num.substring(i, j));
	        long num2 = Long.parseLong(num.substring(j, k));
	        final String addition = String.valueOf(num1 + num2);

	        if (!num.substring(k).startsWith(addition)) return false;
	        if (k + addition.length() == num.length()) return true;
	        return dfs(num, j, k, k + addition.length());
	    }
	 
	 @Test
	public void Test(){
		//Assert.assertTrue(isAdditiveNumber("1235813"));
		//Assert.assertTrue(isAdditiveNumber("199100199"));
		Assert.assertFalse(isAdditiveNumber("123456"));
	 }
}

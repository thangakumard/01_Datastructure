package arrayAlgorithms;


import org.testng.Assert;
import org.testng.annotations.Test;
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

package arrayAlgorithms;

import org.junit.Assert;
import org.testng.annotations.Test;

public class Palindrome {
	
	@Test	
	public void PalindromeCheck()
	{
		char[] input = "madam".toCharArray();
		Boolean isPalindrome = true;
		
		for(int i=0; i < input.length/2; i++)
		{		
			
			if(input[i] != input[(input.length-1)-i])
				isPalindrome = false;
		}
		Assert.assertTrue(isPalindrome);
	}
}

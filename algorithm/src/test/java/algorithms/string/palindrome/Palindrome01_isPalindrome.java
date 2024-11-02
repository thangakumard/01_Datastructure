package algorithms.string.palindrome;

import org.junit.Assert;
import org.testng.annotations.Test;

public class Palindrome01_isPalindrome {
	
	@Test	
	public void PalindromeCheck()
	{
		// To remove unwanted chars
		//String s = "adsfAsdf56453sd$%".replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		//System.out.println(s);
		
		char[] input = "Mad09am".toCharArray();

		Boolean isPalindrome = true;
		
		for(int i=0; i < input.length/2; i++)
		{		
			if(input[i] != input[(input.length-1)-i])
				isPalindrome = false;
		}
		Assert.assertTrue(isPalindrome);
	}
}

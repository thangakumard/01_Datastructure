package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LastWordLength {
	
	@Test
	public void calculateLastWordLength(){
		
		String s = "This is my test sentence";
		int i = s.length()-1;
		int flag = 0;
		while(i > 0)
		{
			if(s.charAt(i) == ' '){
				break;
			}
			flag ++;
			i--;
		}
		
		System.out.println("Length of last word :" + flag);
		Assert.assertEquals(flag, 8);
	}
}

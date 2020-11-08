package algorithms.array;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Isomorphic {

	@Test
	public void checkIsomorphic()
	{
		Assert.assertTrue(isIsomorphic("booaabbcc","tooccttgg"));
	}
	
	public Boolean isIsomorphic(String s1, String s2)
	{
		if(s1 == null || s2 == null)
			return false;
		
		if(s1.length() != s2.length())
			return false;
		
		char c1, c2;
		
		HashMap<Character,Character> map = new HashMap<Character,Character>();
		
		for(int i=0; i < s1.length(); i++)
		{
			c1 = s1.charAt(i);
			c2 = s2.charAt(i);
			
			if(map.containsKey(c1))
			{
				if(map.get(c1) != c2)
					return false;				
			}
			else{
				if(map.containsValue(c2))
					return false;
				
				map.put(c1, c2);
			}
		}
		
		return true;
	}
	
	
	
}

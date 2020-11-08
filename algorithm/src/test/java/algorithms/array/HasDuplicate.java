package algorithms.array;

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HasDuplicate {
	
	@Test
	public void CheckDuplicate()
	{
		int[] input = { 1,2,3,4,2,1};
		Boolean hasDuplicate = false;
		HashSet<Integer> set = new HashSet<Integer>();
		
		for(int n: input)
		{
			if(!set.add(n))
			{
				hasDuplicate = true;
				break;
			}
		}
		Assert.assertTrue(hasDuplicate);
	}

}

package arrayAlgorithms;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PeekElement {

	
	@Test
	public void findPeekElement()
	{
		int[] input = {1,3,9,12,15,3,2,4};
		int peek = getPeek(input,0,input.length-1,input.length);
		
		Assert.assertEquals(peek, 15);
		
	}
	
	private int getPeek(int[] input, int left, int right, int length)
	{
		
		System.out.println("Left :" + left);
		System.out.println("right :" + right);
		
		int peek = 0;
		int mid = (right + left)/2;
		
		System.out.println("input[mid] :" + input[mid]);
		
        // Compare middle element with its neighbours (if neighbours exist)
		if((input[mid] > input[mid-1]) &&
				(mid == length-1 || input[mid] > input[mid+1]))
		{
			peek = input[mid];
		}
		 // If middle element is not peak and its left neighbour is greater 
        // than it, then left half must have a peak element
		else if(mid > 0 && input[mid-1] > input[mid])
		{
			return getPeek(input, left, mid-1, length);
		}
		 // If middle element is not peak and its right neighbour is greater
        // than it, then right half must have a peak element
		else if(mid >0 && input[mid+1] > input[mid])
		{
			return getPeek(input, mid+1, right, length);
		}
		 
		return peek;
	}
	
}

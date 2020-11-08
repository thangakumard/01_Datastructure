package algorithms.simpleSorts;

import org.testng.Assert;
import org.testng.annotations.Test;


public class SortingAlgorithms {

	
	//Time Complexity
	// 		Best Case 	 : O(n)
	//		Average Case : O(n^2)
	// 		Worst Case 	 : O(n^2)
	// Space Complexity
	//      Worst Case : O(1)
	@Test
	public void bubbleSort()
	{
		int[] input = {9,8,7,6,5,4,3,2,1};
		int[] output = {1,2,3,4,5,6,7,8,9};
		int temp = 0;
		
		try
		{
		for(int i=1; i < input.length; i++)
		{
			for(int j=0; j < input.length-i; j++)
			{
				if(input[j] > input[j+1])
				{
					temp = input[j];
					input[j] = input[j+1];
					input[j+1]= temp;
				}
			}
		}
		}
		catch(Exception ex)
		{
			Assert.fail(ex.getMessage());
		}		
		Assert.assertEquals(input, output, "Bubble sort fails !");
	}
	
	//Time Complexity
		// 		Best Case 	 : O(n)
		//		Average Case : O(n^2)
		// 		Worst Case 	 : O(n^2)
		// Space Complexity
		//      Worst Case : O(1)
	@Test
	public void insertionSort()
	{
		int[] input = {9,8,7,6,5,4,3,2,1};
		int[] output = {1,2,3,4,5,6,7,8,9};
		int temp = 0;
		int j = 0;
		
		for(int i=1; i<input.length; i++)
		{
			j = i;
			while(j > 0)
			{
				if(input[j-1] > input[j])
				{
					temp = input[j-1];
					input[j-1] = input[j];
					input[j] = temp;
				}
				j--;
			}
		}		
		Assert.assertEquals(input, output, "Inserion sort fails !");
	}
	
	//Time Complexity
		// 		Best Case 	 : O(n^2)
		//		Average Case : O(n^2)
		// 		Worst Case 	 : O(n^2)
		// Space Complexity
		//      Worst Case : O(log (n))
	@Test
	public void selectionSort()
	{
		int[] input = {9,8,7,6,5,4,3,2,1};
		int[] output = {1,2,3,4,5,6,7,8,9};
		int temp = 0;
		int minimum = 0;
		
		for(int i=0; i < input.length-1; i++)
		{
			minimum = i;
			for(int j=i+1; j <input.length;j++ )
			{
				if(input[minimum] > input[j])
				{
					minimum = j;
				}
			}
			
			if(minimum != i)
			{
				temp = input[i];
				input[i] = input[minimum];
				input[minimum] = temp;
			}
		}
		
		System.out.println("The first element is:" + input[0]);
		
		Assert.assertEquals(input, output, "Selection sort fails !");
	}
}

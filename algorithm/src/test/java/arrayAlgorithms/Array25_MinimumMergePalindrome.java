package arrayAlgorithms;

import org.testng.annotations.Test;

public class Array25_MinimumMergePalindrome {
	@Test
	public void approach01(){
		
		int[] input = {9,1,2,3,9};
		
		int size = input.length;
		 int merge = 0;
		
		for(int i=0, j = size-1; i <= j;){
			
			if(input[i] == input[j]){
				i++;
				j--;
				
			}
			
			else if(input[i] < input[j]){
				input[i] = input[i] + input[i+1];
				i++;
				merge++;
			}
			
			else if(input[i] > input[j]){
				input[j] = input[j] + input[j-1];
				j--;
				merge++;
			}
			
		}
		
		System.out.println("Minimum merge operation is :" + merge);
		
	}
	
}

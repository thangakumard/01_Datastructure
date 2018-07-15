package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/find-minimum-number-of-merge-operations-to-make-an-array-palindrome/
/*****
 * 
 	Input : arr[] = {15, 4, 15}
	Output : 0
	Array is already a palindrome. So we
	do not need any merge operation.
	
	Input : arr[] = {1, 4, 5, 1}
	Output : 1
	We can make given array palindrome with
	minimum one merging (merging 4 and 5 to
	make 9)
	
	Input : arr[] = {11, 14, 15, 99}
	Output : 3
	We need to merge all elements to make
	a palindrome.
 *
 */
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

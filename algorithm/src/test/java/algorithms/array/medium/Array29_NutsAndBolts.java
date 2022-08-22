package algorithms.array.medium;

import org.testng.annotations.Test;

/*****
 * https://www.geeksforgeeks.org/nuts-bolts-problem-lock-key-problem/
 * 
Given a set of N nuts of different sizes and N bolts of different sizes.
 There is a one-one mapping between nuts and bolts. Match nuts and bolts efficiently.

Comparison of a nut to another nut or a bolt to another bolt is not allowed. 
It means nut can only be compared with bolt and bolt can only be compared with nut to see which one is bigger/smaller.
The elements should follow the following order ! # $ % & * @ ^ ~ .

Example 1:

Input: 
N = 5
nuts[] = {@, %, $, #, ^}
bolts[] = {%, @, #, $ ^}
Output: 
# $ % @ ^
# $ % @ ^

 Example 2:
Input: 
N = 9
nuts[] = {^, &, %, @, #, *, $, ~, !}
bolts[] = {~, #, @, %, &, *, $ ,^, !}
Output: 
! # $ % & * @ ^ ~
! # $ % & * @ ^ ~
Your Task:  
You don't need to read input or print anything. Your task is to complete the function matchPairs() which takes an array of characters nuts[], bolts[] and n as parameters and returns void. You need to change the array itswelf.

Expected Time Complexity: O(NlogN)
Expected Auxiliary Space: O(1)

Constraints:
1 <= N <= 9
Array of Nuts/Bolts can only consist of the following elements:{'@', '#', '$', '%', '^', '&', '~', '*', '!'}.


 This algorithm first performs a partition by picking last element of bolts array as a pivot,
 rearranging the array of nuts, and returns the partition index ‘i’ such that all nuts smaller than nuts[i] are on the left side
 and all nuts greater than nuts[i] are on the right side.
 Next using the nuts[i] we can partition the array of bolts. Partitioning operations can easily be implemented in O(n).
 This operation also makes nuts and bolts array nicely partitioned. Now we apply this partitioning recursively on the left and right sub-array of nuts and bolts.
 As we apply to partition on both nuts and bolts so the total time complexity will be? (2*nlogn) = (nlogn) on average.
 Here for the sake of simplicity, we have chosen last element always as a pivot. We can do a randomized quick sort too.

 */
public class Array29_NutsAndBolts {
	
	@Test
	private void test() {
		char nuts[] = {'@', '#', '$', '%', '^', '&'};
        char bolts[] = {'$', '%', '&', '^', '@', '#'};
        
        matchPairs(nuts, bolts, 0, nuts.length-1);
        
        for (char ch : nuts){
            System.out.print(ch + " ");
        }
        System.out.print("\n");
        
        for (char ch : bolts){
            System.out.print(ch + " ");
        }
        System.out.print("\n");
	}
	
	private void matchPairs(char[] nuts, char[] bolts, int start, int end) {
		
		if(start < end) {

			// 1. Pick last element of bolts array as a pivot
			// 2. Rearrange the array of nuts, and returns the partition index ‘i’ such that all nuts smaller than nuts[i] are on the left side
			// all nuts greater than nuts[i] are on the right side
			int pivot_index = partition(nuts, start, end, bolts[end]);

			//using the nuts[i] we can partition the array of bolts
			partition(bolts, start, end, nuts[pivot_index]);
			
			matchPairs(nuts,bolts, start, pivot_index-1);
			matchPairs(nuts,bolts,  pivot_index+1,end);
			
		}
		
	}
	
	private int partition(char[] input, int start, int end, char pivot) {
		
		int i = start;
		char temp;
		
		for(int j=start; j < end; j++) {
			// Move the values less than pivot to the left
			if(input[j] < pivot) {
				temp = input[i];
				input[i] = input[j];
				input[j] = temp;
				i++;
			}
			//If the value matches the pivot value, SWAP with the last value
			else if(input[j] == pivot) {
				temp = input[j];
				input[j] = input[end];
				input[end] = temp;
				j--;
			}
		}

		//i is the pivot index. Swap that with the last element
		temp = input[i];
		input[i] = input[end];
		input[end] = temp;
		return i;
	}
	

}

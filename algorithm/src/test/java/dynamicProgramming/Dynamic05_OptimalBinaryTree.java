package dynamicProgramming;

import org.testng.annotations.*;

public class Dynamic05_OptimalBinaryTree {

	
	/***
	 * Given a sorted array keys[0.. n-1] of search keys and an array freq[0.. n-1] of frequency counts, 
	 * where freq[i] is the number of searches to keys[i]. 
	 * Construct a binary search tree of all keys such that 
	 * the total cost of all the searches is as small as possible.
	 * Input:  keys[] = {10, 12, 16, 21}, freq[] = {4, 2, 6, 3}
	 * 
	 *  The time complexity of the above solution is O(n^4). 
	 *  The time complexity can be easily reduced to O(n^3) 
	 *  by pre-calculating sum of frequencies instead of calling sum() again and again.
	 */
	
	@Test
	public void Test(){		
		int[] keys = {10,12,16,21};
		int[] freq = {4,2,6,3};
		
		System.out.println(optimalBinaryTree(keys, freq));		
	}
	
	public int optimalBinaryTree(int[] input, int[] freq){
		int[][] temp = new int[input.length][input.length];
		
		for(int i=0; i< input.length; i++){ //for length 1 it will take the corresponding requency
			temp[i][i] = freq[i]; 
		}
		
		
		for(int l=2; l<= input.length; l++){
			for(int i=0; i <= input.length-l; i++){
				int j = i + l -1;
				
				temp[i][j] = Integer.MAX_VALUE;
				int sum = getSum(freq, i , j);
				
				for(int k=i; k <= j; k++ ){
					int val = sum + (k-1 < i ? 0 : temp[i][k-1]) +
							(k+1 > j ? 0 : temp[k+1][j]);
					
					if(val < temp[i][j]){
						temp[i][j] = val;
					}
				}
			}
		}
		return temp[0][input.length-1];
	}
	
	private int getSum(int[] freq, int i, int j)
	{
		int sum =0;
		for(int k=i; k<= j; k++){
			sum += freq[k];
		}
		return sum;
	}
}

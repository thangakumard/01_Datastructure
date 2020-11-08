package algorithms.array;

import org.testng.annotations.Test;
/******
 * 
 * https://www.geeksforgeeks.org/count-triplets-with-sum-smaller-that-a-given-value/
	Given an array of distinct integers and a sum value. Find count of triplets with sum smaller than given sum value. Expected Time Complexity is O(n2).
	
	Examples:
	
	Input : arr[] = {-2, 0, 1, 3}
	        sum = 2.
	Output : 2
	Explanation :  Below are triplets with sum less than 2
	               (-2, 0, 1) and (-2, 0, 3) 
	
	Input : arr[] = {5, 1, 3, 4, 7}
	        sum = 12.
	Output : 4
	Explanation :  Below are triplets with sum less than 4
	               (1, 3, 4), (1, 3, 5), (1, 3, 7) and 
	               (1, 4, 5)
               
 */

public class Array34_TripletsSumSmallerThanX {

	@Test
	public void approach01(){
		int[] a = {5, 1, 3, 4, 7};
		int x = 12;
		int size = a.length;
		int ans = 0;
		sort(a,0, size-1);

		for(int i=0; i < size ; i++){
			System.out.println(a[i]);
		}

		int j =0;
		int counter = 0;
		for(int i =0; i< a.length-2; i ++){

			j = i+1;
			int k = size-1;
			while(j < k){

				if(a[i] + a[j] + a[k] >= x){
					k--;
				}
				else{
					ans += k - j;
					j++;
				}
			}
		}
		
		System.out.println("Number of triplet < x are :" + ans);
	}


	public void sort(int[] input, int left,int right){

		int InitialLeft = left;
		int InitialRight = right;
		int pivotVaule = input[left];

		while(left < right){

			while(pivotVaule <= input[right] && left < right){
				right--;
			}
			if(left < right){
				input[left] = input[right];
				left++;

			}
			while(pivotVaule >= input[left] && left < right){
				left ++;
			}
			if(left < right){
				input[right] = input[left];
				right--;
			}
		}

		input[left] = pivotVaule;
		int pivotIndex = left;


		if(InitialLeft < pivotIndex-1) sort(input, InitialLeft, pivotIndex-1);
		if(InitialRight > pivotIndex+1) sort(input, pivotIndex+1, InitialRight);
	}

}

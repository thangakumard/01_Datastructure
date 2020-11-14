package algorithms.array;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/maximum-length-bitonic-subarray/
/*******
    Given an array A[0 � n-1] containing n positive integers, a subarray A[i � j] is bitonic if there is a k with i <= k <= j such that A[i] <= A[i + 1] � = A[k + 1] >= .. A[j � 1] > = A[j]. Write a function that takes an array as argument and returns the length of the maximum length bitonic subarray.
	Expected time complexity of the solution is O(n)
	
	Simple Examples
	1) A[] = {12, 4, 78, 90, 45, 23}, the maximum length bitonic subarray is {4, 78, 90, 45, 23} which is of length 5.
	
	2) A[] = {20, 4, 1, 2, 3, 4, 2, 10}, the maximum length bitonic subarray is {1, 2, 3, 4, 2} which is of length 5.
	
	Extreme Examples
	1) A[] = {10}, the single element is bitnoic, so output is 1.		
	2) A[] = {10, 20, 30, 40}, the complete array itself is bitonic, so output is 4.		
	3) A[] = {40, 30, 20, 10}, the complete array itself is bitonic, so output is 4.
 */
public class Array23_BitonicSubarray {
	public int bitonic_01(int arr[]){
        int lis[] = new int[arr.length];
        int lds[] = new int[arr.length];
        for(int i=0; i < arr.length; i++){
            lis[i] = 1;
            lds[i] = 1;
        }
        for(int i=1 ; i < arr.length; i++){
            for(int j=0; j < i ; j++){
                if(arr[i] > arr[j]){
                    lis[i] = Math.max(lis[i], lis[j] + 1);
                }
            }
        }
        
        for(int i = arr.length-2; i >=0 ; i--){
            for(int j = arr.length-1; j > i; j--){
                if(arr[i] > arr[j]){
                    lds[i] = Math.max(lds[i], lds[j] + 1);
                }
            }
        }
        int max = 0;
        for(int i=0; i < arr.length; i++){
            if(max < lis[i] + lds[i]-1){
                max = lis[i] + lds[i] -1;
            }
        }
        return max;
    }
	
	static int bitonic_02(int arr[]) 
    { 
		int n = arr.length;
        int[] inc = new int[n]; // Length of increasing subarray ending  
                                // at all indexes 
        int[] dec = new int[n]; // Length of decreasing subarray starting 
                                // at all indexes 
        int max; 
  
        // Length of increasing sequence ending at first index is 1 
        inc[0] = 1; 
  
        // Length of increasing sequence starting at first index is 1 
        dec[n-1] = 1; 
  
        // Step 1) Construct increasing sequence array 
        for (int i = 1; i < n; i++) 
           inc[i] = (arr[i] >= arr[i-1])? inc[i-1] + 1: 1; 
  
        // Step 2) Construct decreasing sequence array 
        for (int i = n-2; i >= 0; i--) 
            dec[i] = (arr[i] >= arr[i+1])? dec[i+1] + 1: 1; 
  
        // Step 3) Find the length of maximum length bitonic sequence 
        max = inc[0] + dec[0] - 1; 
        for (int i = 1; i < n; i++) 
            if (inc[i] + dec[i] - 1 > max) 
                max = inc[i] + dec[i] - 1; 
  
        return max; 
    } 
    
	@Test
    public void approach01(){        
        int[] arr = {1,4,3,7,2,1,8,11,13,0};
        int r = bitonic_01(arr);
        System.out.println("Bitonic Subarray :" + r);
        
        int b = bitonic_02(arr);
        System.out.println("Bitonic Subarray :" + b);
    
    }	
}

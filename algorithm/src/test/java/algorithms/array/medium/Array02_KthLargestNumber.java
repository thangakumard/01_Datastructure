package algorithms.array.medium;

import org.testng.annotations.Test;

public class Array02_KthLargestNumber {
	
	
	@Test
	public void test() {
		
		//int[] input = {3,2,1,5,6,4};
		
		int[] input = {3,2,3,1,2,4,5,5,6};
		System.out.println(findKthLargest(input, 9));
		int a = 10;
	}
	
	   int result = -1;
	   public int findKthLargest(int[] nums, int k) {
	        
	        int result = quickSort(nums, 0, nums.length-1,k);
	        
	        return result;
	    }
	    
	    private int quickSort(int[] input, int left, int right, int k){
	        int pivot = input[left];
	        int initial_left = left;
	        int initial_right = right;
	        while (left < right) {
		        while(pivot >= input [right] && left < right){
		            right--;
		        }
		        if(left != right){
		            input[left] = input[right];
		            left++;
		        }
		        while(pivot <= input[left] && left < right){
		            left++;
		        }
		        if(left != right){
		            input[right] = input[left];
		            right--;
		        }
	        }
	        
	        input[left] = pivot;
	        int pivotIndex = left;
	        if(pivotIndex == k-1)
	        	return pivot;
	        

	        if(initial_left < pivotIndex - 1){
	        	result = quickSort(input, initial_left, pivotIndex - 1, k);
	        }
	        if(result > 0) return result;
	        else if(pivotIndex + 1 < initial_right)
	        {
	        	return quickSort(input, pivotIndex + 1,  initial_right, k);
	        }
	        return result;
	    }
	    
}

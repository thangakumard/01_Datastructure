package arrayAlgorithms;

import org.testng.annotations.Test;

public class Array40_Sort_RotatedSorted {

	@Test
	public void Test(){
		
		int[] input = {40,50,60,70,80,10,20,30};
		int findElement = 20;
		sortArray(input, findElement);
		for(int i=0;i < input.length; i++){
			System.out.println(input[i]);
		}
	}

	public void sortArray(int[] input, int element){
		
		//Find the pivot index
		int pivot = findPivot(input,0, input.length-1);
		reverse(input, 0, pivot-1);
		reverse(input, pivot, input.length-1);
		reverse(input, 0, input.length-1);		
	}
	
	//Find the pivot index
	public int findPivot(int[] input, int start, int end){
		
		int mid = (start + end)/2;
		
		if(input[mid] > input[mid+1]){
			return mid+1;
		}
		if(input[start] > input[mid]){
			return findPivot(input, start, mid-1);
		}
		else{
			return findPivot(input, mid+1, end);
		}
	}
	
	private void reverse(int[] input, int start, int end){
		
		int temp = 0;
		while(start < end){			
			temp = input[start];
			input[start] = input[end];
			input[end] = temp;
			
			start++;
			end--;
		}
		
	}
}

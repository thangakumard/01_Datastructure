package arrayAlgorithms;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/find-minimum-difference-pair/
public class Array19_ElementsWithGivenDifference {
	
	@Test
	//Time complexity is O(nlogn) * O(n)
	public void approach01(){
		
		int[] input = {1,2,10,9,6,5,15,16,-4};
		int x = 3;
		
		sort(input, 0, input.length-1);
		for(int i=0; i < input.length; i++){
			System.out.println(input[i]);
		}
		
		int i = 0;
		int size = input.length;
		int diff = 0;
		while(i < size-1){
			
			diff = Math.abs(input[i+1] - input[i]);
			if(diff == x){
				System.out.println(input[i] + " and " + input[i+1]);
			}
			i++;
		}
		
		
	}
	
	public void sort(int[] input,int left, int right){
		
		int pivotValue = input[left];
		int initialLeft = left;
		int initialRight = right;
		
		while(left < right){
			while(pivotValue < input[right] && left < right){
				right --;
			}
			if(left < right){
				input[left] = input[right];
				left++;
			}
			
			while(pivotValue > input[left] && left < right){
				left++;
			}
			if(left < right){
				input[right] = input[left];
				right--;
			}
		}
		
		input[left] = pivotValue;
		int pivotIndex = left;
		
		if(pivotIndex-1 > initialLeft){
			sort(input, initialLeft, pivotIndex-1);
		}
		if(pivotIndex+1 < initialRight) {
			sort(input,pivotIndex+1, initialRight);
		}
		
	}

}

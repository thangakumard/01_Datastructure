package algorithms.array.search;
import org.testng.annotations.*;

public class search01_sortedRotatedArray {

	@Test
	public void Test(){
		
		int[] input = {40,50,60,70,80,10,20,30};
		int findElement = 20;
		System.out.println(findElement(input, findElement));
	}

	public int findElement(int[] input, int element){
		
		//Find the pivot index
		int pivot = findPivot(input,0, input.length-1);
		
		//if pivot element is the required element return the pivot index
		if(input[pivot] == element)
			return pivot;
		
		//if required element is greater than pivot element and less than last element of array
		//Then the required element is in the second part of the array
		if(input[pivot] < element && element < input[input.length-1]){
			return binarySearch(input, pivot, input.length-1, element);
		}
		else{//Then the required element is in the first part of the array
			return binarySearch(input, 0, pivot-1, element);
		}
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
	
	//Find the binary search
	private int binarySearch(int[] input,int start, int end, int key){
		
		int mid = (start + end)/2;
		if(input[mid] == key){
			return mid;
		}
		if(start < end){
		if(input[mid+1] < key){
			return binarySearch(input, mid+1, end, key);
		}
		else {
			return binarySearch(input, start, mid-1, key);
		}		
		}
		else{
			return -1;
		}
	}
	
}

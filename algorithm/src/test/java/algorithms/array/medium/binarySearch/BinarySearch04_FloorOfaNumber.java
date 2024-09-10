package algorithms.array.medium.binarySearch;

import org.testng.annotations.Test;

public class BinarySearch04_FloorOfaNumber {

	 @Test
	  public void main() {
	    System.out.println(this.searchFloorOfANumber(new int[] { 4, 6, 10 }, 6));
	    System.out.println(this.searchFloorOfANumber(new int[] { 1, 3, 8, 10, 15, 15 }, 12));
	    System.out.println(this.searchFloorOfANumber(new int[] { 4, 6, 10 }, 17));
	    System.out.println(this.searchFloorOfANumber(new int[] { 4, 6, 10 }, -1));
	  }
	 
	 private int searchFloorOfANumber(int[] input, int key) {
		 if(input[0] > key) {
			 return -1;
		 }
		 int start = 0, end = input.length-1;
		 
		 while(start <= end) {
			 int mid = start + (end - start)/2;
			 if(input[mid] > key) {
				 end = mid - 1;
			 }
			 else if(input[mid] < key) {
				 start = mid + 1;
			 }else {
				 return input[mid];
			 }
		 }
		 return input[end];
	 }
	 
}

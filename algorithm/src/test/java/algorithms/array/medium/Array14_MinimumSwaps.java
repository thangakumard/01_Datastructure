package algorithms.array.medium;

import org.testng.annotations.Test;

public class Array14_MinimumSwaps {
	@Test
	private void test() {
		int[] input = {4,3,1,2};
		minimumSwaps(input);
	}
 private int minimumSwaps(int[] arr) {
        
        if(arr.length < 2) return 0;
        int min_swap = 0;
        for(int i=0; i < arr.length; i++){
            while(arr[i]-1 != i){
                int temp = arr[i];
                arr[i] = arr[arr[i]-1];
                arr[temp-1] = temp;
                min_swap++;
            }
        }
        return min_swap;
    }

}

package algorithms.array;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class KthLargestElement {

    @Test
    public void KthLargestElementTest()
    {
        int[] input = {2,4,1,5,3};
        Assertions.assertThat(quickSort(input, 0, input.length-1, 3)).isEqualTo(3);

    }

    private int quickSort(int input[], int left, int right, int k){
        int initialLeft = left;
        int initialRight = right;
        int pivotValue = input[left];
        int result = 0;

        while(left < right){

            while(input[right] <= pivotValue && left < right){
                right--;
            }
            if(left != right){
                input[left] = input[right];
                left++;
            }
            while(input[left] >= pivotValue && left < right){
                left++;
            }
            if(left != right){
                input[right] = input[left];
                right--;
            }
        }
        input[left] = pivotValue;
        int pivotIndex = left;
        if(k-1 == pivotIndex){
            return  input[left];
        }

        if(initialLeft <= pivotIndex-1 && k-1 <= pivotIndex-1){
            result = quickSort(input, initialLeft, pivotIndex-1 ,k);
        }
        if(initialRight >= pivotIndex+1 && k-1 >= pivotIndex+1){
            result = quickSort(input, pivotIndex+1, initialRight ,k);
        }

        return result;
    }
}

package algorithms.array.medium.binarySearch;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class BinarySearch01_SearchInRotatedSortedArray {
    @Test
    private void test() {
        int[] input = { 4, 5, 6, 7, 0, 1, 2 };
        Assertions.assertThat(search(input, 0)).isEqualTo(4);

    }

    /*
     * Time complexity O(n)
     * Space complexity O(1)
     * Approach : Binary search
     */
    private int search(int[] input, int target) {

        int start = 0, end = input.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (input[mid] == target)
                return mid;
            else if(input[mid] >= input[start]){
                if(input[start] <= target && target < input[mid]){
                    end = mid-1;
                }else{
                    start = mid+1;
                }
            }else{
                if(input[mid] < target && target <= input[end]){
                    start = mid+1;
                }else{
                    end = mid-1;
                }
            }
        }

        return -1;
    }
}

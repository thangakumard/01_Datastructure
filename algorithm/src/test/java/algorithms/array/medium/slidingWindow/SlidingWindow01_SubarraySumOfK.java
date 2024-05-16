package algorithms.array.medium.slidingWindow;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class SlidingWindow01_SubarraySumOfK {

    @Test
    public void subarraySumTest(){
        int[] input = {1,2,3,7,5};
        ArrayList<Integer> result = new ArrayList<>();
        result.add(2);result.add(4);
        Assertions.assertThat(subarraySum(input,12)).isEqualTo(result);
    }

    static ArrayList<Integer> subarraySum(int[] arr, int s)
    {
        ArrayList<Integer> result = new ArrayList<>();
        int sumSoFar = 0,  left = 0, right = 0;

        while(right < arr.length && left < arr.length){
            sumSoFar += arr[right];
            if(sumSoFar > s){
                sumSoFar = sumSoFar - arr[left];
                left ++;
            }
            if(sumSoFar == s) {
                result.add(left + 1);
                result.add(right + 1);
                return result;
            }
            right++;
        }
        return result;
    }
}

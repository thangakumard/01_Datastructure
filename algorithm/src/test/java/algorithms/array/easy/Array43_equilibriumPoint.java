package algorithms.array.easy;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class Array43_equilibriumPoint {
    @Test
    public void equilibriumPointTest(){
        long input[] = {1,3,5,2,2};
        Assertions.assertThat(equilibriumPoint(input)).isEqualTo(3);
    }
    public int equilibriumPoint(long arr[]) {

        long total = 0, leftSum = 0;
        for(int i=0; i < arr.length; i++){
            total += arr[i];
        }
        for(int i=0; i < arr.length; i++){
            if(leftSum == total - leftSum - arr[i]) return i+1;
            leftSum += arr[i];
        }
        return -1;
    }
}

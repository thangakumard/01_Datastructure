package algorithms.array.easy;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;

public class Array42_WaveArray {

    @Test
    private void convertToWaveTest(){
        int[] input = {1,2,3,4,5};
        convertToWave(input);
        Assertions.assertThat(input).isEqualTo(new int[]{2,1,4,3,5});
    }

    public  void convertToWave(int[] a) {
        Arrays.sort(a);
        int l =0;
        if(a.length % 2 == 0)
            l = a.length;
        else
            l = a.length-1;
        for(int i=0; i < l; i=i+2){
            int temp = a[i+1];
            a[i+1] = a[i];
            a[i] = temp;
        }

    }
}

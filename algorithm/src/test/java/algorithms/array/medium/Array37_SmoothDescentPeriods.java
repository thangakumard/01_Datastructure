package algorithms.array.medium;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Array37_SmoothDescentPeriods {

    @Test
    public void test(){
        //int[] input = { 3, 2, 1, 4 };
        int[] input = { 8, 6, 7, 7 };
        long result = getDescentPeriods(input);
        System.out.println(result);
    }
    public long getDescentPeriods(int[] prices) {
        long counter = 1;
        long smooth = 1;

        for(int i=1; i < prices.length; i++){
            if(prices[i-1] - prices[i] == 1){
                smooth += 1;
                counter += smooth;
            }else{
                counter++;
                smooth = 1;
            }
        }
        return counter;
    }
}

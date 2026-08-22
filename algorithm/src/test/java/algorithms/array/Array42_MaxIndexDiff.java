package algorithms.array.medium;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class Array42_MaxIndexDiff {

    @Test
    public void maxIndexDiffTest(){
        int arr[] = {34, 8, 10, 3, 2, 80, 30, 33, 1};
        Assertions.assertThat(maxIndexDiff(arr)).isEqualTo(6);
    }
    public int maxIndexDiff(int[] input){
        int []leftMin = new int[input.length];
        leftMin[0] = input[0];
        for(int i = 1; i < input.length; i++)
            leftMin[i] = Math.min(leftMin[i - 1] , input[i]);

        int maxDist = Integer.MIN_VALUE;
        int i = input.length - 1, j = input.length - 1;
        while(i >= 0 && j >= 0)
        {
            if(input[j] >= leftMin[i]) //*** important to use equal >=
            {
                maxDist = Math.max( maxDist, j - i );
                i--;
            }
            else
                j--;
        }

        return maxDist;
    }
}

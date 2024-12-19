package myInterviews;

import org.testng.annotations.Test;

/**
 * When dice A and dice B are at the same time 5 times, you can switch the value of dice B to dice A and vice versa
 * on an ith roll to make
 * all the values in dice A the same or all the values in dice B the same.
 * Return the number of swap we make to achieve this .
 * If this is not possible return -1
 */
public class Microsoft01_Dice_Make_Same_Numbers {

    @Test
    public void diceSwapTest(){
        int[] A = {1,2,2,3,2};
        int[] B = {2,1,1,2,1};
        System.out.println(getSwapNumber(A,B));
    }

    private int getSwapNumber(int[] A, int[] B){
        int makeAllMatchA = minSwap(A,B,A[0]);
        int makeAllMatchB = minSwap(B,A,B[0]);
        int minSwap =  Math.min(makeAllMatchA, makeAllMatchB);
        return minSwap != Integer.MAX_VALUE ? minSwap : -1;
    }

    private int minSwap(int[] A, int[] B, int target){
        int aSwap =0, bSwap =0;
        for(int i=0; i < A.length; i++){
            if(A[i] != target && B[i] != target){
                return Integer.MAX_VALUE;
            }
            if(A[i] != target && B[i] == target){
                aSwap++;
            }
            else if(A[i] == target && B[i] != target){
                bSwap++;
            }
        }
        return Math.min(aSwap, bSwap);
    }
}

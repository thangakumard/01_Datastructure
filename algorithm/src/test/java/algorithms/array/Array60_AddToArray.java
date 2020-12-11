package algorithms.array;

import java.util.*;

/*
 * https://leetcode.com/problems/add-to-array-form-of-integer/
 * 
 * For a non-negative integer X, the array-form of X is an array of its digits in left to right order.  For example, if X = 1231, then the array form is [1,2,3,1].

	Given the array-form A of a non-negative integer X, return the array-form of the integer X+K.
	
	 
	
	Example 1:
	
	Input: A = [1,2,0,0], K = 34
	Output: [1,2,3,4]
	Explanation: 1200 + 34 = 1234
 * 
 */

public class Array60_AddToArray {
	
	public List<Integer> addToArrayForm(int[] A, int K) {
	     
        List<Integer> result = new ArrayList<Integer>();
        for(int i=A.length-1; i> -1; i--){
            result.add((A[i]+K)%10);
            K = (A[i]+K) / 10;
        }
        
        while(K > 0){
           result.add(K%10);
           K = K/10;
        }
        Collections.reverse(result);
        return result;
    }
}

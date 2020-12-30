package algorithms.array.easy;

import java.util.*;

/*
 * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
 */
public class Array04_findDisappearedNumbers {

	public List<Integer> findDisappearedNumbers(int[] nums) {
        int[] index = new int[nums.length];
        List<Integer> result = new ArrayList<>();
        for(int i=0; i< nums.length; i++){
            index[nums[i]-1]++;
        }
        for(int i=0; i<nums.length; i++){
            if(index[i] == 0)result.add(i+1);
        }
        return result;
    }
}

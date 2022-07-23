package algorithms.array.easy;

import java.util.*;

/****
Reference: https://www.youtube.com/watch?v=eHqwoWiMDfY

https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/

Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it. That is, for each nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].

Return the answer in an array.

Example 1:
Input: nums = [8,1,2,2,3]
Output: [4,0,1,1,3]
Explanation: 
For nums[0]=8 there exist four smaller numbers than it (1, 2, 2 and 3). 
For nums[1]=1 does not exist any smaller number than it.
For nums[2]=2 there exist one smaller number than it (1). 
For nums[3]=2 there exist one smaller number than it (1). 
For nums[4]=3 there exist three smaller numbers than it (1, 2 and 2).

Example 2:
Input: nums = [6,5,4,8]
Output: [2,1,0,3]

Example 3:
Input: nums = [7,7,7,7]
Output: [0,0,0,0]
****/

public class Array40_SmallerThanCurrent {
      public int[] smallerNumbersThanCurrent(int[] nums) {
        
        int[] result = new int[nums.length];     
        int[] counter = new int[101];
        
        //Take count of number of occurence
        for(int i=0; i < nums.length; i++){
            counter[nums[i]] += 1; 
        }
        
        //Take count of numbers on the left. 
        //(This will help to get number of smaller number for the current)
        for(int i=1; i < counter.length; i++){
            counter[i] += counter[i-1];
        }
        
        //Build the result array.
        //The value in the previous index of counter is the number of smaller number than the current
        for(int i=0; i < nums.length; i++){
            if(nums[i] != 0) {
               result[i] = counter[nums[i]-1]; 
            }   
        }
        
        return result;
    }
}

package algorithms.array.medium;

import java.util.*;
/*
 * https://leetcode.com/problems/subsets-ii/
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: [1,2,2]
Output:
[
  [2],
  [1],
  [1,2,2],
  [2,2],
  [1,2],
  []
]
 */

public class Array09_SubsetsII_WithDuplicates {

	public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int index, int[]nums, List<Integer> current, List<List<Integer>> result){
    	result.add(new ArrayList<>(current));
        
        for(int i=index; i< nums.length; i++){
            if(i>index && nums[i]==nums[i-1]) continue;
            current.add(nums[i]);
            backtrack(i+1, nums, current, result);
            current.remove(current.size() -1);
        }
    }
}

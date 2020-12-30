package algorithms.array.medium;

import java.util.*;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/subsets/
 * Given an integer array nums, return all possible subsets (the power set).

	The solution set must not contain duplicate subsets.
	
	 
	
	Example 1:
	
	Input: nums = [1,2,3]
	Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
	Example 2:
	
	Input: nums = [0]
	Output: [[],[0]]
	 
	
	Constraints:
	
	1 <= nums.length <= 10
	-10 <= nums[i] <= 10
 */
public class Array08_SubsetsWithoutDuplicate {

	@Test
	private void test() {
		int[] input = {1,2,3};
		System.out.println(subsets(input));
		String a = "";
		
	}
        
	/*
	 * Time complexity )(n * 2 ^ n)
	 */
    public List<List<Integer>> subsets(int[] nums) {
    	List<List<Integer>> result = new ArrayList<>();
    	backtrack(0, nums, new ArrayList<Integer>(), result);
    	for(List<Integer> ls: result) {
    		System.out.println(ls);
    	}
    	return result;
    }
    
    private void backtrack(int index, int[] input,List<Integer> current, List<List<Integer>> result) {
    
    	result.add(new ArrayList<>(current));
    	for(int i = index; i < input.length; i++) {
    		current.add(input[i]);
    		backtrack(i+1, input,current,result);
    		current.remove(current.size()-1);
    	}

    }
}

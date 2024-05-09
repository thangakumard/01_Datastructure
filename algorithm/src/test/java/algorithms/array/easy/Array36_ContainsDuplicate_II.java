package algorithms.array.easy;

import java.util.HashMap;
/*****
 * 
 * https://leetcode.com/problems/contains-duplicate-ii/
 * 
 * Given an array of integers and an integer k, 
 * find out whether there are two distinct indices i and j in the array 
 * such that nums[i] = nums[j] and the absolute difference between i and j is at most k.

	Example 1:
	Input: nums = [1,2,3,1], k = 3
	Output: true

	Example 2:
	Input: nums = [1,0,1,1], k = 1
	Output: true

	Example 3:
	Input: nums = [1,2,3,1,2,3], k = 2
	Output: false
 *
 */

public class Array36_ContainsDuplicate_II {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        HashMap<Integer, Integer> mapNums = new HashMap<Integer, Integer>();
        
        for(int i=0; i < nums.length; i++){
            int current = nums[i];
            if(mapNums.containsKey(current) && i - mapNums.get(current) <= k){
                return true;
            }
            else{
                mapNums.put(current, i);
            }
        }
        return false;
    }
}

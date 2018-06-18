package arrayAlgorithms;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

/*******
 * https://leetcode.com/problems/k-diff-pairs-in-an-array/description/
 * Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. 
 * Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute difference is k.
 	Input: [1, 3, 1, 5, 4], k = 0
	Output: 1
	Explanation: There is one 0-diff pair in the array, (1, 1).
 
	Input: [3, 1, 4, 1, 5], k = 2
	Output: 2
	Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
	Although we have two 1s in the input, we should only return the number of unique pairs.
 *
 *
 *
 *
 */
public class Array57_K_Diff_Pairs {

	@Test
	public void findPairsWithKDiff(){
		int[] input = new int[] {1,3,1,5,4};
		System.out.println(findPairs(input, 0));
		
		input = new int[] {1,3,1,5,4};
		System.out.println(findPairs(input, 2));
	}
	
	 public int findPairs(int[] nums, int k) {
	        if(k < 0)
	            return 0;
	        
	        HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
	        
	        for(int i=0; i< nums.length; i++){
	            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
	        }
	        
	        int counter = 0;
	        for(Map.Entry<Integer, Integer> input: map.entrySet()){
	            if(k == 0){
	                if(input.getValue() >= 2){
	                    counter++;
	                }
	            }
	            else{
	                if(map.containsKey(input.getKey() + k)){
	                    counter++;
	                }
	            }
	        }
	        return counter;
	    }
}

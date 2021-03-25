package algorithms.array.easy;
import java.util.*;

import org.testng.annotations.Test;
/******

	https://leetcode.com/problems/minimum-absolute-difference/
	
	Given an array of distinct integers arr, find all pairs of elements with the minimum absolute difference of any two elements. 
	
	Return a list of pairs in ascending order(with respect to pairs), each pair [a, b] follows
	
	a, b are from arr
	a < b
	b - a equals to the minimum absolute difference of any two elements in arr
	 
	
	Example 1:
	
	Input: arr = [4,2,1,3]
	Output: [[1,2],[2,3],[3,4]]
	Explanation: The minimum absolute difference is 1. List all pairs with difference equal to 1 in ascending order.
	Example 2:
	
	Input: arr = [1,3,6,10,15]
	Output: [[1,3]]
	Example 3:
	
	Input: arr = [3,8,-10,23,19,-4,-14,27]
	Output: [[-14,-10],[19,23],[23,27]]
	 
	
	Constraints:
	
	2 <= arr.length <= 10^5
	-10^6 <= arr[i] <= 10^6
 *
 */

public class Array09_minimumAbsDifference {

	@Test
	private void test() {
			 int[] input_01 = {4,2,1,3};
		     System.out.println(minimumAbsDifference(input_01));
		     int[] input_02 = {1,3,6,10,15};
		     System.out.println(minimumAbsDifference(input_02));
		     int[] input_03 = {4,2,1,3};
		     System.out.println(minimumAbsDifference(input_03));
	}
	
	 public List<List<Integer>> minimumAbsDifference(int[] arr) {
	        Arrays.sort(arr);
	        int min_diff = Integer.MAX_VALUE;
	        List<List<Integer>> result = new ArrayList<>();      
	        
	        for(int i=1; i< arr.length; i++){
	            int diff = Math.abs(arr[i-1] - arr[i]);
	            if(diff < min_diff){
	                result.clear();
	                result.add(Arrays.asList(arr[i-1], arr[i]));
	                min_diff = Math.min(min_diff, diff);
	            }else if(diff == min_diff){
	                result.add(Arrays.asList(arr[i-1], arr[i]));
	            }
	        }
	        
	        return result;
	    }
}

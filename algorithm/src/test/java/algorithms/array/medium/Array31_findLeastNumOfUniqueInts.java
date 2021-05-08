package algorithms.array.medium;

import java.util.*;
import org.testng.*;
import org.testng.annotations.Test;
/*******
https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/

Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly k elements.


Example 1:
Input: arr = [5,5,4], k = 1
Output: 1
Explanation: Remove the single 4, only 5 is left.

Example 2:
Input: arr = [4,3,1,1,3,3,2], k = 3
Output: 2
Explanation: Remove 4, 2 and either one of the two 1s or three 3s. 1 and 3 will be left.
 

Constraints:

1 <= arr.length <= 10^5
1 <= arr[i] <= 10^9
0 <= k <= arr.length
 *
 */

public class Array31_findLeastNumOfUniqueInts {
	
	@Test
	private void test() {
		int[] input = new int[] {4,3,1,1,3,3,2};
		Assert.assertEquals(2,findLeastNumOfUniqueInts(input, 3));
		
	}

	 public int findLeastNumOfUniqueInts(int[] arr, int k) {
	        HashMap<Integer, Integer> mapInput = new HashMap<Integer, Integer>();
	        for(int i=0; i < arr.length; i++){
	            int count = mapInput.getOrDefault(arr[i], 0) + 1;
	            mapInput.put(arr[i], count);
	        }
	        
	        PriorityQueue<Integer> minQueue = new PriorityQueue<Integer>((a,b) -> mapInput.get(a) - mapInput.get(b));
	        minQueue.addAll(mapInput.keySet());
	        
	        for(int i=0; i < k; i++){
	            int key = minQueue.remove();
	            
	            int count = mapInput.get(key) - 1;
	            if(count == 0){
	                mapInput.remove(key);
	            }else{
	                mapInput.put(key,count);
	                minQueue.add(key);
	            }
	        }
	        
	        return mapInput.size();
	    }
}

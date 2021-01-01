package algorithms.array.medium;

import java.util.*;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/merge-intervals/
 * 
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

 

	Example 1:
	
	Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
	Output: [[1,6],[8,10],[15,18]]
	Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
	Example 2:
	
	Input: intervals = [[1,4],[4,5]]
	Output: [[1,5]]
	Explanation: Intervals [1,4] and [4,5] are considered overlapping.
	 
	
	Constraints:
	
	1 <= intervals.length <= 104
	intervals[i].length == 2
	0 <= starti <= endi <= 104
 */
public class Array15_MergeInterval {
	
	@Test
	private void test() {
		LinkedList<int[]> result = new LinkedList<int[]>();
		result.add(new int[]{1,3});
		result.add(new int[]{2,6});
		result.add(new int[]{8,10});
		result.add(new int[]{15,18});
		System.out.println(mergeInterval(result.toArray(new int[result.size()][])));
		
	}
	
	private int[][] mergeInterval(int[][] input){
		Arrays.sort(input, (a,b) -> Integer.compare(a[0], b[0]));
		
		LinkedList<int[]> result = new LinkedList<int[]>();
		
		for(int[] interval: input) {
			if(result.isEmpty() || result.getLast()[1] < interval[0]) {
				result.add(interval);
			}else {
				result.getLast()[1] = Math.min(result.getLast()[1], interval[1]);
			}
		}
		return result.toArray(new int[result.size()][]);
	}

}

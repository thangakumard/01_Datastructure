package algorithms.companies.amazon.amazon;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.annotations.Test;

import java.util.*;

/*
 * 
 * https://leetcode.com/problems/merge-(s/
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
	 */

public class Amazon_09_MergeInterval {
	
	@Test
	private void test() {
		int[][] input = {{1,3},{2,6},{8,10},{15,18}};
		@SuppressWarnings("unused")
		int[][] output = merge(input);
	}

	public int[][] merge(int[][] intervals){
		
		Arrays.sort(intervals, (a,b) -> Integer.compare(a[0], b[0]));
		List<int[]> result = new ArrayList<int[]>();
		result.add(intervals[0]);
		int[] current_Interval = intervals[0];
		
		for(int[] interval: intervals) {
			int current_end = current_Interval[1];
			
			int next_begin = interval[0];
			int next_end = interval[1];
			
			if(current_end >= next_begin) {
				current_Interval[1] = Math.max(current_end, next_end);
			}else {
				current_Interval = interval;
				result.add(current_Interval);
			}
		}
		return result.toArray(new int[result.size()][]);
	}
}

package algorithms.array.meetingScheduler;

import java.util.*;

import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/merge-intervals/
 * 
 * Given an array of intervals where intervals[i] = [starti, endi],
 * merge all overlapping intervals,
 * and return an array of the non-overlapping intervals that cover all the intervals in the input.

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
public class Array01_MergeInterval_Greedy {
	
	@Test
	private void test() {
		int[][] intervals= {{2,15},{36,45},{9,29},{16,23},{4,9}};
		int[][] result = merge(intervals);
		for(int[] interval: result) {
			System.out.println(interval[0] + "," + interval[1]);
		}
	}

	/**
	 * Algorithm: Sorting + Greedy Linear Scan
	 * Time Complexity: O(n log n) ,
	 * Space Complexity: O(1) extra space beyond the sort.
	 */
	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, (a,b)-> Integer.compare(a[0],b[0])); //O(n log n)
		List<int[]> result = new ArrayList<>();
		int[] current = intervals[0];
		for(int i=1; i< intervals.length; i++){
			int[] next = intervals[i];
			if(current[1] >= next[0]){
				current[1] = Math.max(current[1], next[1]);
			}else{
				result.add(current);
				current = next;
			}
		}
		result.add(current);
		return result.toArray(new int[result.size()][]);
	}
}

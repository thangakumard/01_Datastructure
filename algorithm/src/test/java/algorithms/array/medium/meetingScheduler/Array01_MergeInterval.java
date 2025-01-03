package algorithms.array.medium.meetingScheduler;

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
public class Array01_MergeInterval {
	
	@Test
	private void test() {
//		LinkedList<int[]> inputIntervals = new LinkedList<int[]>();
//		inputIntervals.add(new int[]{1,3});
//		inputIntervals.add(new int[]{2,6});
//		inputIntervals.add(new int[]{8,10});
//		inputIntervals.add(new int[]{15,18});
//		System.out.println(mergeInterval(inputIntervals.toArray(new int[inputIntervals.size()][])));
//		
		
		int[][] intervals= {{2,15},{36,45},{9,29},{16,23},{4,9}};
		int[][] result = mergeInterval(intervals);
		for(int[] interval: result) {
			System.out.println(interval[0] + "," + interval[1]);
		}
	}

	/*** C# ***
	 * public int[][] Merge(int[][] intervals) {
	 *         Array.Sort(intervals, (a,b) => {return a[0] - b[0];});
	 *         LinkedList<int[]> mergedIntervals = new LinkedList<int[]>();
	 *
	 *         foreach(int[] interval in intervals){
	 *             if(mergedIntervals.Count > 0 && mergedIntervals.Last.Value[1] >= interval[0]){
	 *                 mergedIntervals.Last.Value[1] = Math.Max(mergedIntervals.Last.Value[1], interval[1]);
	 *             }else{
	 *                 mergedIntervals.AddLast(interval);
	 *             }
	 *         }
	 *
	 *         return mergedIntervals.ToArray();
	 *     }
	 */
	
	private int[][] mergeInterval(int[][] input){
		//Arrays.sort(input, (a,b) -> Integer.compare(a[0], b[0]));
		Arrays.sort(input, (a,b) -> a[0] - b[0]);
		LinkedList<int[]> result = new LinkedList<>();
		for(int[] interval: input) {
			if(result.isEmpty() || result.getLast()[1] < interval[0]) {
				//If no overlap add to the result
				result.add(interval);
			}else {
				//If it has overlap merge the time window
				result.getLast()[1] = Math.max(result.getLast()[1], interval[1]);
			}
		}
		return result.toArray(new int[result.size()][]);
	}

}

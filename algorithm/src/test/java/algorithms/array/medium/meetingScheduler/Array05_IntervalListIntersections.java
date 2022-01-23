package algorithms.array.medium.meetingScheduler;

import java.util.*;
import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/interval-list-intersections/
 *
 * You are given two lists of closed intervals, firstList and secondList, where firstList[i] = [starti, endi] and secondList[j] = [startj, endj]. Each list of intervals is pairwise disjoint and in sorted order.
 * Return the intersection of these two interval lists.
 * A closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.
 * The intersection of two closed intervals is a set of real numbers that are either empty or represented as a closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].
 *
 * Example 1:
 * Input: firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
 * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
 *
 *  Example 2:
 * Input: firstList = [[1,3],[5,9]], secondList = []
 * Output: []
 *
 *
 * Constraints:
 * 0 <= firstList.length, secondList.length <= 1000
 * firstList.length + secondList.length >= 1
 * 0 <= starti < endi <= 109
 * endi < starti+1
 * 0 <= startj < endj <= 109
 * endj < startj+1
 *
 */
public class Array05_IntervalListIntersections {
	
	
	@Test
	private void test() {
		/*
		 * [[0,2],[5,10],[13,23],[24,25]]
			[[1,5],[8,12],[15,24],[25,26]]
		 */
		int[][] intervals1= {{0,2},{5,10},{13,23},{24,25}};
		int[][] intervals2= {{1,5},{8,12},{15,24},{25,26}};
		
		int[][] result = intervalIntersection(intervals1, intervals2);
		for(int[] interval: result) {
			System.out.print("{" + interval[0] + "," + interval[1] + "},");
		}
	}

public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        
        List<int[]> result = new ArrayList<>();
        
        int i =0, j =0;
        
        while(i < firstList.length && j < secondList.length){
            int intersect_start = Math.max(firstList[i][0], secondList[j][0]);
            int intersect_end = Math.min(firstList[i][1], secondList[j][1]);
            
            if(intersect_start <= intersect_end)
                result.add(new int[]{intersect_start,intersect_end});
            
            if(firstList[i][1] < secondList[j][1]){
                i++;
            }else{
                j++;
            }
        }
        return result.toArray(new int[result.size()][]);
    }
}

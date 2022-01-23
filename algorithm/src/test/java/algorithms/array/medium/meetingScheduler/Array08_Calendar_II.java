package algorithms.array.medium.meetingScheduler;

import java.util.ArrayList;
import java.util.List;

/***
 * https://leetcode.com/problems/my-calendar-ii/
 *
 *  DOUBLE BOOKING IS ALLOWED
 *
 * Example 1:
 * Input
 * ["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
 * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
 * Output
 * [null, true, true, true, false, true, true]
 */

public class Array08_Calendar_II {

	class MyCalendarTwo {
	    
	    List<int[]> calendar;
	    List<int[]> overlaps;

	    public MyCalendarTwo() {
	        calendar = new ArrayList<>();
	        overlaps = new ArrayList<>();
	    }
	    
	    public boolean book(int start, int end) {
	        for(int[] overlap: overlaps){
	            if(overlap[0] < end && start < overlap[1]){
	                return false;
	            }
	        }
	        
	        for(int[] slot: calendar){
	            if(slot[0] < end && start < slot[1]){
	                overlaps.add(new int[] {Math.max(start, slot[0]),
	                                       Math.min(end, slot[1])});
	            }
	        }
	        calendar.add(new int[]{start,end});
	        return true;
	    }
	}

}

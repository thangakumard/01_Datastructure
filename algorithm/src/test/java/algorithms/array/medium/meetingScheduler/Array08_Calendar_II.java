package algorithms.array.medium.meetingScheduler;

import java.util.ArrayList;
import java.util.List;

public class Array08_Calendar_II {

	class MyCalendarTwo {
	    
	    List<int[]> calendar;
	    List<int[]> overlaps;

	    public MyCalendarTwo() {
	        calendar = new ArrayList<>();
	        overlaps = new ArrayList<>();
	    }
	    
	    public boolean book(int start, int end) {
	        for(int[] slot: overlaps){
	            if(slot[0] < end && start < slot[1]){
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

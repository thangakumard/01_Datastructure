package algorithms.array.medium.meetingScheduler;

import java.util.*;

/******
 * 
 * https://leetcode.com/problems/meeting-scheduler/
 *
 * Given the availability time slots arrays slots1 and slots2 of two people and
 * a meeting duration duration, return the earliest time slot that works for
 * both of them and is of duration duration.
 * 
 * If there is no common time slot that satisfies the requirements, return an
 * empty array.
 * 
 * The format of a time slot is an array of two elements [start, end]
 * representing an inclusive time range from start to end.
 * 
 * It is guaranteed that no two availability slots of the same person intersect
 * with each other. That is, for any two time slots [start1, end1] and [start2,
 * end2] of the same person, either start1 > end2 or start2 > end1.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]],
 * duration = 8 Output: [60,68] Example 2:
 * 
 * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]],
 * duration = 12 Output: []
 * 
 * 
 * Constraints:
 * 
 * 1 <= slots1.length, slots2.length <= 104 slots1[i].length, slots2[i].length
 * == 2 slots1[i][0] < slots1[i][1] slots2[i][0] < slots2[i][1] 0 <=
 * slots1[i][j], slots2[i][j] <= 109 1 <= duration <= 106
 */
public class Array04_MeetingScheduler {

	 public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
	        
	        Arrays.sort(slots1, (a,b) -> a[0] - b[0]);
	        Arrays.sort(slots2, (a,b) -> a[0] - b[0]);
	        
	        int l1 = slots1.length, l2 = slots2.length,i=0,j=0;
	        List<Integer> result = new ArrayList<>();
	        
	        while(i < l1 && j < l2){
				int intersect_start = Math.max(slots1[i][0], slots2[j][0]);
				int intersect_end = Math.min(slots1[i][1], slots2[j][1]);
	            
	            if(intersect_end - intersect_start >= duration){
	                 result.add(intersect_start);
	                 result.add(intersect_start + duration);
	                 return result;
	            }
	            if(slots1[i][1] < slots2[j][1]){ //IMPORTANT to compare the end time
	                i++;
	            }else{
	                j++;
	            }
	        }
	        
	        return result;
	    }
}

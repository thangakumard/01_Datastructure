package algorithms.array.medium.meetingScheduler;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Arrays;

/***
 * https://leetcode.com/problems/meeting-rooms/
 *  Given an array of meeting time
 * intervals where intervals[i] = [starti, endi], determine if a person could
 * attend all meetings.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: intervals = [[0,30],[5,10],[15,20]] Output: false Example 2:
 * 
 * Input: intervals = [[7,10],[2,4]] Output: true
 * 
 * 
 * Constraints:
 * 
 * 0 <= intervals.length <= 104 intervals[i].length == 2 0 <= starti < endi <=
 * 106
 *
 */
public class Array02_MeetingRooms_I {

    @Test
    private void test() {
        int[][] intervals= {{0,30},{60,240},{90,120}};
        Assertions.assertThat(canAttendMeetings(intervals)).isFalse();
    }

    public boolean canAttendMeetings(int[][] intervals) {
        if(intervals == null || intervals.length < 2)
            return true;

        Arrays.sort(intervals, (a,b) -> Integer.compare(a[0],b[0]));

        for(int i =1; i < intervals.length; i++){
            if(intervals[i-1][1] > intervals[i][0]){
                return false;
            }
        }
        return true;
    }
}

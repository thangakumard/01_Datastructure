package algorithms.array.meetingScheduler;

import java.util.*;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
/*
https://leetcode.com/problems/meeting-rooms-ii/

Given an array of meeting time intervals
where intervals[i] = [starti, endi],
return the minimum number of conference rooms required.

Example 1:
Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2

Example 2:
Input: intervals = [[7,10],[2,4]]
Output: 1

Constraints:
1 <= intervals.length <= 104
0 <= starti < endi <= 106
Accepted
565,681
Submissions

 */

public class Array03_MeetingRooms_II {

	@Test
	private void test() {
		int[][] intervals= {{9,10},{4,9},{4,17}}; //IMPORTANT TEST CASE
        Assertions.assertThat(minMeetingRooms(intervals)).isEqualTo(2);
		//int[][] intervals= {{2,15},{36,45},{9,29},{16,23},{4,9}};
        //int[][] intervals= {{9,10},{9,11},{11,12}};

	}

    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        // Min-heap of end times for rooms currently occupied
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int[] interval : intervals) {
            // Earliest-ending room is free by this meeting's start -> reuse it
            if (!minHeap.isEmpty() && minHeap.peek() <= interval[0]) {
                minHeap.poll();
            }
            minHeap.offer(interval[1]);
        }

        return minHeap.size();
    }
}

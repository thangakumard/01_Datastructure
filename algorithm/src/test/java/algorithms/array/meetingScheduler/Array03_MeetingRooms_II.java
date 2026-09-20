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

    /***
     * This method splits the meeting intervals into two separate arrays:
     * one for start times and one for end times.
     * We sort both arrays independently and use a two-pointer approach to track room availability.
     * If a meeting starts before the earliest ending meeting finishes,
     * if(start_time <= end_time)
     *      we can reuse a room.
     * else
     *      we allocate a new room.
     *
     * Time: O (n log n)
     * Space: O(n)
     */
    public int minMeetingRooms_Chronological_Ordering(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        int n = intervals.length;
        int[] startTimes = new int[n];
        int[] endTimes = new int[n];

        for (int i = 0; i < n; i++) {
            startTimes[i] = intervals[i][0];
            endTimes[i] = intervals[i][1];
        }

        // Sort both arrays independently
        Arrays.sort(startTimes);
        Arrays.sort(endTimes);

        int roomsAllocated = 0;
        int endPointer = 0;

        // Iterate through all meetings by their start times
        for (int startPointer = 0; startPointer < n; startPointer++) {
            // If there is a meeting that has ended by the time the current meeting starts
            if (startTimes[startPointer] < endTimes[endPointer]) {
                // No room is free, we must allocate a new one
                roomsAllocated++;
            } else {
                // Free up a room, meaning we increment the end pointer
                endPointer++;
            }
        }

        return roomsAllocated;
    }

    /***
     * Time: O (n log n)
     * Space: O(n)
     */
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

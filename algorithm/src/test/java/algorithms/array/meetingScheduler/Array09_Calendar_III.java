package algorithms.array.meetingScheduler;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Array09_Calendar_III {

    @Test
    public void test_MyCalendarThree(){
        MyCalendarTwo myCalendarThree = new MyCalendarTwo();
        System.out.println(myCalendarThree.book(10, 20));
        System.out.println(myCalendarThree.book(50, 60));
        System.out.println(myCalendarThree.book(10, 40));
        System.out.println(myCalendarThree.book(5, 15));
        System.out.println(myCalendarThree.book(5, 40));
        System.out.println(myCalendarThree.book(5, 55));
    }
}

/***
 * Time: O(n) per call (scan both lists) → O(n²) total across n calls.
 * Space: O(n). This is worth defending if asked —
 * it looks like overlaps could blow up quadratically since a single call can add up to O(n) new entries.
 * But because we never re-add a region that's already flagged as double-booked (that path returns false before any mutation),
 * the total number of overlap segments ever created across all calls is bounded by O(n), not O(n²).
 * I'd walk through a small adversarial example if pushed on this.
 */
class MyCalendarTwo {
    private final List<int[]> bookings;
    private final List<int[]> overlaps;

    public MyCalendarTwo() {
        bookings = new ArrayList<>();
        overlaps = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        for (int[] o : overlaps) {
            if (start < o[1] && o[0] < end) {
                return false; // would create a triple booking
            }
        }
        for (int[] b : bookings) {
            if (start < b[1] && b[0] < end) {
                overlaps.add(new int[]{Math.max(start, b[0]), Math.min(end, b[1])});
            }
        }
        bookings.add(new int[]{start, end});
        return true;
    }
}
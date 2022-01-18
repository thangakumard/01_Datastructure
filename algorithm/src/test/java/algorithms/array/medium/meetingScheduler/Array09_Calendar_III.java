package algorithms.array.medium.meetingScheduler;

import org.testng.annotations.Test;

import java.util.TreeMap;

public class Array09_Calendar_III {

    @Test
    public void test_MyCalendarThree(){
        MyCalendarThree myCalendarThree = new MyCalendarThree();
        System.out.println(myCalendarThree.book(10, 20));
        System.out.println(myCalendarThree.book(50, 60));
        System.out.println(myCalendarThree.book(10, 40));
        System.out.println(myCalendarThree.book(5, 15));
        System.out.println(myCalendarThree.book(5, 40));
        System.out.println(myCalendarThree.book(5, 55));
    }
}

class MyCalendarThree {
    TreeMap<Integer, Integer> delta;

    public MyCalendarThree() {
        delta = new TreeMap();
    }

    public int book(int start, int end) {
        delta.put(start, delta.getOrDefault(start, 0) + 1);
        delta.put(end, delta.getOrDefault(end, 0) - 1);

        int active = 0, ans = 0;
        for (int d: delta.values()) {
            active += d;
            if (active > ans) ans = active;
        }
        return ans;
    }
}
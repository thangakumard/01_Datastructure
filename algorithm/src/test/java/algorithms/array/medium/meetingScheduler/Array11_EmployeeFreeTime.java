package algorithms.array.medium.meetingScheduler;

import org.testng.annotations.Test;

import java.util.*;

/***
 * https://leetcode.com/problems/employee-free-time/
 *
 * We are given a list schedule of employees, which represents the working time for each employee.
 * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
 * Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.
 * (Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).  Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.
 *
 * Example 1:
 * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
 * Output: [[3,4]]
 * Explanation: There are a total of three employees, and all common
 * free time intervals would be [-inf, 1], [3, 4], [10, inf].
 * We discard any intervals that contain inf as they aren't finite.

 * Example 2:
 * Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
 * Output: [[5,6],[7,9]]

 * Constraints:
 * 1 <= schedule.length , schedule[i].length <= 50
 * 0 <= schedule[i].start < schedule[i].end <= 10^8
 */
public class Array11_EmployeeFreeTime {

    @Test
    public void test(){
        List<Interval> schedule1 = new ArrayList<>();
        List<Interval> schedule2 = new ArrayList<>();
        List<Interval> schedule3 = new ArrayList<>();
        List<List<Interval>> schedule = new ArrayList<>();

        Interval I1 = new Interval();
        I1.start = 1;
        I1.end = 2;
        schedule1.add(I1);
        Interval I2 = new Interval();
        I2.start = 5;
        I2.end = 6;
        schedule1.add(I2);

        Interval I3 = new Interval();
        I3.start = 1;
        I3.end = 3;
        schedule2.add(I3);

        Interval I4 = new Interval();
        I4.start = 4;
        I4.end = 10;
        schedule3.add(I4);

        schedule.add(schedule1);
        schedule.add(schedule2);
        schedule.add(schedule3);

        List<Interval> result = employeeFreeTime(schedule);
        for(Interval intv: result){
            System.out.println(intv.start + "," + intv.end);
        }
    }
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> intervals = new ArrayList<>();
        for (int i = 0; i < schedule.size(); i++) {
            for (int j = 0; j < schedule.get(i).size(); j++) {
                intervals.add(schedule.get(i).get(j));
            }
        }
        Collections.sort(intervals, (a, b) -> a.start - b.start);

        for(Interval intv: intervals){
            System.out.println(intv.start + "," + intv.end);
        }
        System.out.println("*************");

        Interval temp = intervals.get(0);
        List<Interval> res = new ArrayList<>();
        for (int i = 1; i < intervals.size(); i++) {
            if (intervals.get(i).start > temp.end) {
                res.add(new Interval(temp.end, intervals.get(i).start));
            }
            temp = temp.end < intervals.get(i).end ? intervals.get(i) : temp;
        }
        return res;
    }
}

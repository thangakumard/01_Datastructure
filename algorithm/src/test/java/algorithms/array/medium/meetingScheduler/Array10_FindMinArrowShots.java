package algorithms.array.medium.meetingScheduler;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/******
 * https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/
 */
public class Array10_FindMinArrowShots {

    @Test
    public void test(){
        ArrayList<int[]> lstInput = new ArrayList<>();
        lstInput.add(new int[]{10,16});
        lstInput.add(new int[]{2,8});
        lstInput.add(new int[]{1,6});
        lstInput.add(new int[]{7,12});
        findMinArrowShots(lstInput.toArray(new int[lstInput.size()][]));
        /***
         * Fails with the input
         * [[3,9],[7,12],[3,8],[6,8],[9,10],[2,9],[0,9],[3,9],[0,6],[2,8]]
         */
    }


    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        for(int[] interval: points){
            if(!pq.isEmpty() && pq.peek()[0] <= interval[0] && pq.peek()[1] >= interval[0]){

                continue;
            }
            pq.add(interval);
        }
        return pq.size();
    }
}

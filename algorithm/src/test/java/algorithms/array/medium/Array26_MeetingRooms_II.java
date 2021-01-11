package algorithms.array.medium;

import java.util.*;

import org.testng.annotations.Test;

public class Array26_MeetingRooms_II {

	@Test
	private void test() {
		int[][] intervals= {{9,10},{4,9},{4,17}};
		System.out.println(minMeetingRooms_2(intervals));
	}
	
	public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals,(a,b)->(a[0]-b[0]));
        PriorityQueue<int[]> pq=new PriorityQueue<>((a,b)->(a[1]-b[1]));
        for(int[] i:intervals){
            if(!pq.isEmpty()&&pq.peek()[1]<=i[0]){
                pq.poll();
            }
            pq.add(i);
        }
        return pq.size();
    }
	
	public int minMeetingRooms_2(int[][] intervals) {
        Map<Integer, Integer> m = new TreeMap<>();
        for (int[] t : intervals) {
            m.put(t[0], m.getOrDefault(t[0], 0) + 1);
            m.put(t[1], m.getOrDefault(t[1], 0) - 1);
        }
        int res = 0, cur = 0;
        for (int v : m.values()) {
            res = Math.max(res, cur += v);
        }
        return res;
    }
	
}

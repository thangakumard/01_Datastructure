package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.testng.annotations.Test;

public class TaskScheduler {

	@Test
	public void Test(){
		String input = "AABCCCEEE"; 
		System.out.println("Least Interval :" + leastInterval(input.toCharArray(), 2));
	}
	
	    public int leastInterval(char[] tasks, int n) {
	        int[] map = new int[26];
	        for (char c: tasks)
	            map[c - 'A']++;
	        PriorityQueue < Integer > queue = new PriorityQueue < > (26, Collections.reverseOrder());
	        for (int f: map) {
	            if (f > 0)
	                queue.add(f);
	        }
	        int time = 0;
	        while (!queue.isEmpty()) {
	            int i = 0;
	            List < Integer > temp = new ArrayList < > ();
	            while (i <= n) {
	                if (!queue.isEmpty()) {
	                    if (queue.peek() > 1)
	                        temp.add(queue.poll() - 1);
	                    else
	                        queue.poll();
	                }
	                time++;
	                if (queue.isEmpty() && temp.size() == 0)
	                    break;
	                i++;
	            }
	            for (int l: temp)
	                queue.add(l);
	        }
	        return time;
	    }
 
}

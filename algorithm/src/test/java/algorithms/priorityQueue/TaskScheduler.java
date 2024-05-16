package algorithms.priorityQueue;

import java.util.*;
/********
 https://leetcode.com/problems/task-scheduler/description/
 
 https://www.youtube.com/watch?v=eGf-26OTI-A
 
 Given a char array representing tasks CPU need to do. It contains capital letters A to Z where different letters represent different tasks.Tasks could be done without original order. Each task could be done in one interval. For each interval, CPU could finish one task or just be idle.

	However, there is a non-negative cooling interval n that means between two same tasks, there must be at least n intervals that CPU are doing different tasks or just be idle.
	
	You need to return the least number of intervals the CPU will take to finish all the given tasks.
	
	Example 1:
	Input: tasks = ["A","A","A","B","B","B"], n = 2
	Output: 8
	Explanation: A -> B -> idle -> A -> B -> idle -> A -> B.
	Note:
	The number of tasks is in the range [1, 10000].
	The integer n is in the range [0, 100].
 */

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class TaskScheduler {

	@Test
	public void Test(){
		String input = "AAABBB";
		System.out.println("Least Interval :" + leastInterval(input.toCharArray(), 2));

		Assertions.assertThat(leastInterval_queue(input.toCharArray(), 2)).isEqualTo(8);
	}
	
	public int leastInterval(char[] tasks, int n) {
	       // frequencies of the tasks
        int[] frequencies = new int[26];
        for (int t : tasks) {
            frequencies[t - 'A']++;
        }

        // max frequency
        int f_max = 0;
        for (int f : frequencies) {
            f_max = Math.max(f_max, f);
        }
        
        // count the most frequent tasks
        int n_max = 0;
        for (int f : frequencies) {
            if (f == f_max) n_max++;
        }
        
        return Math.max(tasks.length, (f_max - 1) * (n + 1) + n_max);
	}

	public int leastInterval_queue(char[] tasks, int n){
		HashMap<Character, Integer> charMap = new HashMap<>();
		for(char c: tasks){
			charMap.put(c , charMap.getOrDefault(c, 0)+1);
		}

		PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
		maxHeap.addAll(charMap.values());

		int cycle = 0;
		while(!maxHeap.isEmpty()){
			List<Integer> temp = new ArrayList<>();
			for (int i=0; i <= n ; i++){
				if(!maxHeap.isEmpty()){
					temp.add(maxHeap.poll());
				}
			}
			for(int i: temp){
				if(--i > 0){
					maxHeap.add(i);
				}
			}
			cycle += maxHeap.isEmpty() ? temp.size() : n + 1;
		}
		return cycle;
	}
 
}

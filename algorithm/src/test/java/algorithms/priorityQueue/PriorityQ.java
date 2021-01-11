package algorithms.priorityQueue;

import java.util.PriorityQueue;

public class PriorityQ {

	
	public static void main(String args[])
	{
		String[] input = {"eat","tea","tan","ate","nat","bat"};
		PriorityQueue<String> pq = new PriorityQueue<>();

		for(String s: input) {
			pq.add(s);
		}

		System.out.println(pq);
	}
}

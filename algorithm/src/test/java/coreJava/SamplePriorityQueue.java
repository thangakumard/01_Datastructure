package coreJava;

import java.util.PriorityQueue;

import org.testng.annotations.Test;

public class SamplePriorityQueue {

	
	
	@Test
	private void test() {
		System.out.println("Min PriorityQueue :");
		PriorityQueue<Integer> minQueue = new PriorityQueue<Integer>();
		minQueue.add(100);
		minQueue.add(10);
		minQueue.add(90);
		minQueue.add(20);
		minQueue.add(80);
		
		while(!minQueue.isEmpty()) {
			System.out.print(minQueue.poll() + " ");
		}
		
		System.out.println(" ");
		System.out.println("Max PriorityQueue :");
		PriorityQueue<Integer> maxQueue = new PriorityQueue<Integer>((a,b)->(b-a));
		maxQueue.add(100);
		maxQueue.add(10);
		maxQueue.add(90);
		maxQueue.add(20);
		maxQueue.add(80);
		
		while(!maxQueue.isEmpty()) {
			System.out.print(maxQueue.poll() + " ");
		}
		
		/******  IMPORTANT DONOT update Update PriorityQueue ********/
		//https://stackoverflow.com/questions/1871253/updating-java-priorityqueue-when-its-elements-change-priority
		//https://www.quora.com/How-do-I-update-a-priority-queue
		System.out.println(" ");
		System.out.println("DONOT update Update PriorityQueue :");
		
		PriorityQueue<int[]> pQueue_update = new PriorityQueue<int[]>((a,b) -> (a[0]-b[0]));
		int[] a = {20,20};
		pQueue_update.add(a);
		
		int[] b = {100,100};
		pQueue_update.add(b);
		
		int[] c = {10,10};
		pQueue_update.add(c);
		
		int[] d = {50,50};
		pQueue_update.add(d);
		
		
		System.out.println("pQueue_update peek(): " + pQueue_update.peek()[0]);
		pQueue_update.peek()[0] = 1000;
		
		System.out.println("pQueue_update peek() After update: " + pQueue_update.peek()[0]);
		
		while(!pQueue_update.isEmpty()) {
			System.out.print(pQueue_update.poll()[0] + " ");
		}

		
	}
	
	
}

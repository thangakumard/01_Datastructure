package coreJava.collections.queue;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Priority Queue is an extension of queue with following properties.
 *
 * Every item has a priority associated with it.
 * An element with high priority is dequeued before an element with low priority.
 * If two elements have the same priority, they are served according to their order in the queue.
 */
public class Queue02_priorityQueue {

    @Test
    public void int_sort(){
        int[] input = {1,1,2,2,3,3,4,5};

        PriorityQueue<int[]> subsequences = new PriorityQueue<>((int[] subsequence1, int[] subsequence2) -> {
            if (subsequence1[1] == subsequence2[1]) {
                return (subsequence1[1] - subsequence1[0]) - (subsequence2[1] - subsequence2[0]);
            }
            return (subsequence1[1] - subsequence2[1]);
        });

        subsequences.add(new int[]{1, 2});
        subsequences.add(new int[]{2, 5});
        subsequences.add(new int[]{10, 20});
        subsequences.add(new int[]{50, 40});

        while(!subsequences.isEmpty()){
            System.out.println(subsequences.peek()[0]);
            System.out.println(subsequences.peek()[1]);
            subsequences.poll();
        }

    }

    @Test
    public void int_priorityQueue_ascending(){
        // Creating empty priority queue
        PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>();

        // Adding items to the pQueue using add()
        pQueue.add(10);
        pQueue.add(20);
        pQueue.add(15);

        // Printing the top element of PriorityQueue
        System.out.println(pQueue.peek());

        // Printing the top element and removing it
        // from the PriorityQueue container
        System.out.println(pQueue.poll());

        // Printing the top element again
        System.out.println(pQueue.peek());
    }

    @Test
    public void int_priorityQueue_descending(){
        // Creating empty priority queue
        PriorityQueue<Integer> pQueue
                = new PriorityQueue<Integer>(Collections.reverseOrder());

        // Adding items to the pQueue using add()
        pQueue.add(10);
        pQueue.add(20);
        pQueue.add(15);
        pQueue.add(5);

        // Printing the top element of PriorityQueue
        System.out.println(pQueue.peek());

        // Printing the top element and removing it
        // from the PriorityQueue container
        System.out.println(pQueue.poll());

        // Printing the top element again
        System.out.println(pQueue.peek());
    }
    @Test
    private void string_PriorityQueue(){
            PriorityQueue<String> pq = new PriorityQueue<>();

            pq.add("Geeks");
            pq.add("For");
            pq.add("Geeks");

            System.out.println(pq);
    }


}

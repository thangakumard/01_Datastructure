package coreJava.collections.queue;

import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.Queue;

public class Queue01_queue {

    @Test
    public void string_queue(){
        Queue<String> queue = new LinkedList<>();

        //Add elements
        queue.add("element 1");
        queue.offer("element 2");
        queue.offer("element 3");
        queue.offer("element 4");
        queue.offer("element 5");

        //access via new for-loop
        for(String element : queue) {
            //do something with each element
            System.out.println(element);
        }

        boolean containsElement2 = queue.contains("element 2");
        System.out.println("containsElement2 :" + containsElement2);

        //peek
        String firstElement = queue.peek();

        //Remove element from queue
        String element1 = queue.poll();
        /**
         * remove() method retrieves and removes the head of this queue. This method differs from poll() only in that it throws an exception if this queue is empty.
         * Returns:
         * the head of this queue
         * Throws:
         * NoSuchElementException – if this queue is empty
         */
        String element2 = queue.remove();

        //Remove all the elements from the queue
        queue.clear();

    }
}

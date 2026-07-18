package coreJava.collections.queue;

import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Queue01_linkedListAsQueue {

    @Test
    public void linkedListAsQueueSample(){
        //DECLARED AS Queue INTERFACE -> only Queue/Collection methods available
        Queue<Integer> queue1 = new LinkedList<>();

        //OFFER -> inserts at the tail, returns boolean (preferred over add() for capacity-bound queues)
        queue1.offer(10);
        queue1.offer(20);
        queue1.offer(30);
        queue1.offer(40);
        System.out.println("queue1 after offer(10,20,30,40) :" + queue1);

        //ADD -> inserts at the tail, throws IllegalStateException if capacity-restricted queue is full
        queue1.add(50);
        System.out.println("queue1 after add(50) :" + queue1);

        //SIZE
        System.out.println("queue1.size() : " + queue1.size());

        //PEEK -> retrieves (does NOT remove) the head; returns null if queue is empty
        System.out.println("queue1.peek() : " + queue1.peek());

        //ELEMENT -> retrieves (does NOT remove) the head; throws NoSuchElementException if empty
        System.out.println("queue1.element() : " + queue1.element());

        //POLL -> retrieves AND removes the head; returns null if queue is empty
        System.out.println("queue1.poll() : " + queue1.poll());
        System.out.println("queue1 after poll() :" + queue1);

        //REMOVE -> retrieves AND removes the head; throws NoSuchElementException if empty
        System.out.println("queue1.remove() : " + queue1.remove());
        System.out.println("queue1 after remove() :" + queue1);

        //DEMONSTRATE EXCEPTION vs SPECIAL-VALUE BEHAVIOR ON EMPTY QUEUE
        Queue<Integer> emptyQueue = new LinkedList<>();
        System.out.println("emptyQueue.poll() on empty queue (returns null) : " + emptyQueue.poll());
        System.out.println("emptyQueue.peek() on empty queue (returns null) : " + emptyQueue.peek());
        try{
            emptyQueue.remove(); // throws NoSuchElementException
        }catch(NoSuchElementException e){
            System.out.println("emptyQueue.remove() on empty queue threw NoSuchElementException as expected");
        }
        try{
            emptyQueue.element(); // throws NoSuchElementException
        }catch(NoSuchElementException e){
            System.out.println("emptyQueue.element() on empty queue threw NoSuchElementException as expected");
        }

        //ISEMPTY
        System.out.println("Is queue1 isempty :" + queue1.isEmpty());

        //CONTAINS
        System.out.println("queue1.contains(30) :" + queue1.contains(30));

        //ADD ALL (Collection method - adds all elements from another collection)
        Queue<Integer> queue2 = new LinkedList<>();
        queue2.add(100);
        queue2.add(200);
        queue1.addAll(queue2);
        System.out.println("queue1 after addAll(queue2) :" + queue1);

        //CONTAINS ALL
        System.out.println("queue1.containsAll(queue2) :" + queue1.containsAll(queue2));

        //REMOVE(Object) (Collection method - removes a single matching instance)
        queue1.remove(Integer.valueOf(200));
        System.out.println("queue1 after remove(Object 200) :" + queue1);

        //REMOVE ALL
        queue1.removeAll(queue2);
        System.out.println("queue1 after removeAll(queue2) :" + queue1);

        //RETAIN ALL
        Queue<Integer> queue3 = new LinkedList<>();
        queue3.offer(30);
        queue3.offer(40);
        queue3.offer(999); //element not in queue1, demonstrates filtering
        queue1.retainAll(queue3);
        System.out.println("queue1 after retainAll(queue3) :" + queue1);

        //ITERATOR -> order of iteration is NOT guaranteed to be queue order by the interface,
        //but LinkedList's implementation iterates head-to-tail (insertion/FIFO order)
        System.out.println("Iterator over queue1 :");
        Iterator<Integer> itr = queue1.iterator();
        while(itr.hasNext()){
            System.out.print(itr.next() + ", ");
        }
        System.out.println("");

        //FOR EACH (default Iterable method)
        System.out.println("forEach over queue1 :");
        queue1.forEach(item -> System.out.print(item + ", "));
        System.out.println("");

        //TO ARRAY
        Object[] arr = queue1.toArray();
        Integer[] intArr = queue1.toArray(new Integer[0]);
        System.out.println("queue1.toArray() length :" + arr.length);

        //STREAM (default Collection method)
        int sum = queue1.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum of queue1 via stream() :" + sum);

        //SIMULATE A TYPICAL FIFO PROCESSING LOOP
        Queue<String> taskQueue = new LinkedList<>();
        taskQueue.offer("Task-A");
        taskQueue.offer("Task-B");
        taskQueue.offer("Task-C");
        System.out.println("Processing taskQueue in FIFO order :");
        while(!taskQueue.isEmpty()){
            System.out.println("Processing : " + taskQueue.poll());
        }

        //CLEAR
        queue1.clear();
        System.out.println("queue1 after clear() : isEmpty=" + queue1.isEmpty());
    }
}

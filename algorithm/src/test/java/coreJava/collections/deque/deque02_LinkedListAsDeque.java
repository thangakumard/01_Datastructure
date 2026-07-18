package coreJava.collections.deque;

import org.junit.Test;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class deque02_LinkedListAsDeque {
    @Test
    public void linkedListAsDequeSample(){
        //DECLARED AS Deque INTERFACE -> only Deque/Queue/Collection methods available
        Deque<Integer> deque1 = new LinkedList<>();

        //***************************** INSERTION ********************************
        //ADD FIRST / ADD LAST -> throws IllegalStateException if capacity-restricted deque is full
        deque1.addFirst(20);
        deque1.addFirst(10);
        deque1.addLast(30);
        deque1.addLast(40);
        System.out.println("deque1 after addFirst/addLast :" + deque1);

        //OFFER FIRST / OFFER LAST -> returns boolean instead of throwing
        deque1.offerFirst(5);
        deque1.offerLast(50);
        System.out.println("deque1 after offerFirst(5)/offerLast(50) :" + deque1);

        //***************************** EXAMINATION (no removal) ********************************
        //GET FIRST / GET LAST -> throws NoSuchElementException if empty
        System.out.println("deque1.getFirst() : " + deque1.getFirst());
        System.out.println("deque1.getLast() : " + deque1.getLast());

        //PEEK FIRST / PEEK LAST -> returns null if empty
        System.out.println("deque1.peekFirst() : " + deque1.peekFirst());
        System.out.println("deque1.peekLast() : " + deque1.peekLast());

        //***************************** REMOVAL ********************************
        //REMOVE FIRST / REMOVE LAST -> throws NoSuchElementException if empty
        System.out.println("deque1.removeFirst() : " + deque1.removeFirst());
        System.out.println("deque1.removeLast() : " + deque1.removeLast());
        System.out.println("deque1 after removeFirst()/removeLast() :" + deque1);

        //POLL FIRST / POLL LAST -> returns null if empty
        System.out.println("deque1.pollFirst() : " + deque1.pollFirst());
        System.out.println("deque1.pollLast() : " + deque1.pollLast());
        System.out.println("deque1 after pollFirst()/pollLast() :" + deque1);

        //DEMONSTRATE EXCEPTION vs SPECIAL-VALUE BEHAVIOR ON EMPTY DEQUE
        Deque<Integer> emptyDeque = new LinkedList<>();
        System.out.println("emptyDeque.pollFirst() on empty (returns null) : " + emptyDeque.pollFirst());
        System.out.println("emptyDeque.peekLast() on empty (returns null) : " + emptyDeque.peekLast());
        try{
            emptyDeque.removeFirst(); // throws NoSuchElementException
        }catch(NoSuchElementException e){
            System.out.println("emptyDeque.removeFirst() on empty threw NoSuchElementException as expected");
        }
        try{
            emptyDeque.getLast(); // throws NoSuchElementException
        }catch(NoSuchElementException e){
            System.out.println("emptyDeque.getLast() on empty threw NoSuchElementException as expected");
        }

        //***************************** QUEUE (FIFO) METHODS INHERITED VIA DEQUE ********************************
        Deque<Integer> queueStyle = new LinkedList<>();
        queueStyle.offer(1);    // equivalent to offerLast(1)
        queueStyle.offer(2);
        queueStyle.offer(3);
        System.out.println("queueStyle (Deque used as Queue) :" + queueStyle);
        System.out.println("queueStyle.poll() (removes head, equivalent to pollFirst()) : " + queueStyle.poll());
        System.out.println("queueStyle.peek() (equivalent to peekFirst()) : " + queueStyle.peek());
        System.out.println("queueStyle.element() (equivalent to getFirst()) : " + queueStyle.element());

        //***************************** STACK (LIFO) METHODS ********************************
        Deque<Integer> stackStyle = new LinkedList<>();
        stackStyle.push(1); // equivalent to addFirst(1)
        stackStyle.push(2);
        stackStyle.push(3);
        System.out.println("stackStyle after push(1,2,3) :" + stackStyle);
        System.out.println("stackStyle.pop() (removes head, equivalent to removeFirst()) : " + stackStyle.pop());
        System.out.println("stackStyle after pop() :" + stackStyle);
        System.out.println("stackStyle.peek() (equivalent to peekFirst(), top of stack) : " + stackStyle.peek());

        //***************************** REMOVE BY VALUE / OCCURRENCE ********************************
        Deque<Integer> dupDeque = new LinkedList<>();
        dupDeque.add(7);
        dupDeque.add(9);
        dupDeque.add(7);
        dupDeque.add(9);
        dupDeque.removeFirstOccurrence(7);
        System.out.println("dupDeque after removeFirstOccurrence(7) :" + dupDeque);
        dupDeque.removeLastOccurrence(9);
        System.out.println("dupDeque after removeLastOccurrence(9) :" + dupDeque);

        //generic remove(Object) removes the first occurrence, same as removeFirstOccurrence
        dupDeque.remove(Integer.valueOf(9));
        System.out.println("dupDeque after remove(Object 9) :" + dupDeque);

        //***************************** ITERATION ********************************
        //ITERATOR -> traverses head to tail (first to last)
        Deque<Integer> deque2 = new LinkedList<>();
        deque2.add(100);
        deque2.add(200);
        deque2.add(300);
        System.out.println("Iterator over deque2 (head to tail) :");
        Iterator<Integer> itr = deque2.iterator();
        while(itr.hasNext()){
            System.out.print(itr.next() + ", ");
        }
        System.out.println("");

        //DESCENDING ITERATOR -> traverses tail to head (last to first)
        System.out.println("descendingIterator over deque2 (tail to head) :");
        Iterator<Integer> descItr = deque2.descendingIterator();
        while(descItr.hasNext()){
            System.out.print(descItr.next() + ", ");
        }
        System.out.println("");

        //FOR EACH (default Iterable method)
        System.out.println("forEach over deque2 :");
        deque2.forEach(item -> System.out.print(item + ", "));
        System.out.println("");

        //***************************** COLLECTION-INHERITED METHODS ********************************
        //SIZE / ISEMPTY
        System.out.println("deque2.size() : " + deque2.size());
        System.out.println("deque2.isEmpty() : " + deque2.isEmpty());

        //CONTAINS / CONTAINS ALL
        System.out.println("deque2.contains(200) : " + deque2.contains(200));
        Deque<Integer> deque3 = new LinkedList<>();
        deque3.add(100);
        deque3.add(200);
        System.out.println("deque2.containsAll(deque3) : " + deque2.containsAll(deque3));

        //ADD ALL
        deque2.addAll(deque3);
        System.out.println("deque2 after addAll(deque3) :" + deque2);

        //REMOVE ALL / RETAIN ALL
        Deque<Integer> toRemove = new LinkedList<>();
        toRemove.add(300);
        deque2.removeAll(toRemove);
        System.out.println("deque2 after removeAll({300}) :" + deque2);

        deque2.retainAll(deque3);
        System.out.println("deque2 after retainAll(deque3) :" + deque2);

        //TO ARRAY
        Object[] arr = deque2.toArray();
        Integer[] intArr = deque2.toArray(new Integer[0]);
        System.out.println("deque2.toArray() length : " + arr.length);

        //STREAM (default Collection method)
        int sum = deque2.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum of deque2 via stream() : " + sum);

        //CLEAR
        deque2.clear();
        System.out.println("deque2 after clear() : isEmpty=" + deque2.isEmpty());
    }
}

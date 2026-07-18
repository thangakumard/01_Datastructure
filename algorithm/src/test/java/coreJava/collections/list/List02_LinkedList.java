package coreJava.collections.list;


import org.testng.annotations.Test;

import java.util.*;

/********
 * Reference : https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
 * Doubly-linked list implementation of the List and Deque interfaces. 
 * Implements all optional list operations, and permits all elements, including null.
 * In addition to implementing the List interface, LinkedList provides uniform
 * named methods to get, remove and insert an element at the beginning and end of the list.
 * It also implements the Deque interface, so it can be used as a Queue (FIFO) or a Stack (LIFO).
 * (Unlike ArrayList, LinkedList does NOT provide random access in constant time - get(index)
 * is O(n) since it has to traverse the list.)
 *
 */
public class List02_LinkedList {

    @Test
    public void linkedListSample(){
        LinkedList<Integer> list1 = new LinkedList<>();
        //ADD
        list1.add(10);
        list1.add(20);
        list1.add(30);
        list1.add(40);

        //ADD ALL
        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(100);
        list2.add(200);
        list2.add(300);
        list2.add(400);

        list1.addAll(list2);

        //ADD ALL - BY INDEX
        LinkedList<Integer> list3 = new LinkedList<>();
        list3.add(1000);
        list3.add(2000);
        list3.add(3000);
        list3.add(4000);
        list3.addAll(0, list3);

        //SIZE OF LIST
        System.out.println("list1.size() : " + list1.size());

        //***************************** LINKEDLIST SPECIFIC METHODS (Deque) ********************************
        //ADD FIRST / ADD LAST
        list1.addFirst(1);
        list1.addLast(999);
        System.out.println("list1 after addFirst(1) and addLast(999) :" + list1);

        //GET FIRST / GET LAST
        System.out.println("list1.getFirst() : " + list1.getFirst());
        System.out.println("list1.getLast() : " + list1.getLast());

        //PEEK - retrieves head, does not remove, returns null if empty
        System.out.println("list1.peek() : " + list1.peek());
        System.out.println("list1.peekFirst() : " + list1.peekFirst());
        System.out.println("list1.peekLast() : " + list1.peekLast());

        //ELEMENT - retrieves head, does not remove, throws exception if empty
        System.out.println("list1.element() : " + list1.element());

        //OFFER - adds to the tail (Queue behavior), returns boolean
        list1.offer(500);
        list1.offerFirst(-1);
        list1.offerLast(501);
        System.out.println("list1 after offer/offerFirst/offerLast :" + list1);

        //POLL - retrieves and removes head, returns null if empty
        System.out.println("list1.poll() : " + list1.poll());
        System.out.println("list1.pollFirst() : " + list1.pollFirst());
        System.out.println("list1.pollLast() : " + list1.pollLast());
        System.out.println("list1 after polling :" + list1);

        //PUSH / POP - Stack behavior (LIFO), operates on the head
        list1.push(777);
        System.out.println("list1 after push(777) :" + list1);
        System.out.println("list1.pop() : " + list1.pop());

        //REMOVE FIRST / REMOVE LAST
        list1.removeFirst();
        list1.removeLast();
        System.out.println("list1 after removeFirst() and removeLast() :" + list1);

        //REMOVE FIRST OCCURRENCE / LAST OCCURRENCE
        list1.add(20); //duplicate for demo
        list1.removeFirstOccurrence(20);
        list1.removeLastOccurrence(20);

        //DESCENDING ITERATOR
        System.out.println("list1 iterated in reverse using descendingIterator :");
        Iterator<Integer> descItr = list1.descendingIterator();
        while(descItr.hasNext()){
            System.out.print(descItr.next() + ", ");
        }
        System.out.println("");

        //***************************** STANDARD LIST INTERFACE METHODS ********************************
        //REMOVE BY INDEX
        list1.remove(0);

        //REMOVE BY OBJECT
        list1.remove(list1.get(1)); // REMOVE BY OBJECT

        //REMOVE ALL
        List<Integer> listToRemove = new LinkedList<>();
        listToRemove.add(30);
        listToRemove.add(40);
        list1.remove(listToRemove);

        //ISEMPTY
        System.out.println("Is list1 isempty :" + list1.isEmpty());

        //ITERATE
        System.out.println("Iterator loops elements in list1 :");
        Iterator<Integer> i = list1.iterator();
        while(i.hasNext()){
            System.out.print(i.next() + ", ");
        }
        System.out.println("");

        //LIST ITERATOR - can traverse both directions and modify during iteration
        System.out.println("ListIterator loops elements in list1 forward then backward :");
        ListIterator<Integer> li = list1.listIterator();
        while(li.hasNext()){
            System.out.print(li.next() + ", ");
        }
        System.out.println("");
        while(li.hasPrevious()){
            System.out.print(li.previous() + ", ");
        }
        System.out.println("");

        //FOR EACH
        System.out.println("for loops elements in list1 :");
        for(int item: list1){
            System.out.print(item + ", ");
        }
        System.out.println("");

        //CONTAINS
        System.out.println("list1.contains(100) :" + list1.contains(100));

        //CONTAINS ALL
        System.out.println("list1.containsAll(list2) :" + list1.containsAll(list2));

        //INDEX OF
        System.out.println("list1.indexOf(200) :" + list1.indexOf(200));

        //LAST INDEX OF
        System.out.println("list1.lastIndexOf(40):" + list1.lastIndexOf(40));

        //GET (O(n) traversal since LinkedList has no random access)
        System.out.println("To get index value of required index : " + list1.get(0));

        //EQUALS
        List<Integer> list4 = new LinkedList<>();
        list4.add(100);
        list4.add(200);
        list4.add(300);
        list4.add(400);
        System.out.println("list2.equals(list4) :" + list2.equals(list4));

        //RETAIN ALL -> Keeps the items in the list2, if it is present in list1. Removes all other elements
        list1.retainAll(list2);

        //SET => To update an element in the required index
        if(!list1.isEmpty()){
            list1.set(0, 23);
        }
        System.out.println("Update/Set the 0th index value as 23 :" + list1);

        //SUBLIST => To use portion of the list
        List<Integer> subList1 = list3.subList(1, 3);
        System.out.println("Portion of list3 :");
        for(int item: subList1){
            System.out.print(item + ", ");
        }
        System.out.println("");

        //***************************** TYPE CONVERSIONS ********************************
        //ARRAY TO LINKEDLIST
        System.out.println("Convert Array to LinkedList :");
        Integer[] array2 = new Integer[] {-1,-2,-3,-4};
        LinkedList<Integer> negativeList = new LinkedList<>(Arrays.asList(array2));
        for(Object item: negativeList){
            System.out.print(item + ", ");
        }
        System.out.println("");
        //to get integer value from the converted list
        int val = negativeList.get(0);

        //LINKEDLIST TO ARRAY
        System.out.println("Convert LinkedList To Array:");
        int[] myArray = list3.stream().mapToInt(arr -> arr).toArray();
        for(int item: myArray){
            System.out.print(item + ", ");
        }
        System.out.println("");

        //LINKEDLIST <-> ARRAYLIST
        List<Integer> arrayListFromLinked = new java.util.ArrayList<>(list3);
        LinkedList<Integer> linkedFromArrayList = new LinkedList<>(arrayListFromLinked);

        //LINKEDLIST AS QUEUE (interface reference)
        Queue<Integer> queueRef = new LinkedList<>();
        queueRef.offer(1);
        queueRef.offer(2);
        System.out.println("Queue via LinkedList - poll() :" + queueRef.poll());

        //LINKEDLIST AS DEQUE (interface reference)
        Deque<Integer> dequeRef = new LinkedList<>();
        dequeRef.addFirst(1);
        dequeRef.addLast(2);
        System.out.println("Deque via LinkedList :" + dequeRef);

        //LIST OF LIST
        List<List<Integer>> listOfList = new LinkedList<>();
        listOfList.add(list2);

        //***************************** COLLECTION METHODS ********************************
        //SORT
        Collections.sort(list1);
        System.out.println("list1 after Collections.sort(list1):");
        for(int item: list1){
            System.out.print(item + ", ");
        }
        System.out.println("");

        //REVERSE
        Collections.reverse(list1);
        System.out.println("list1 after Collections.reverse(list1):");
        for(int item: list1){
            System.out.print(item + ", ");
        }
        System.out.println("");
    }
}


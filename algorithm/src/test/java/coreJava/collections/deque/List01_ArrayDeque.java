package coreJava.collections.deque;
import java.util.ArrayDeque;
import java.util.Deque;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;

/***
 * ******* Why is ArrayDeque better than LinkedList ??? **********
 * https://stackoverflow.com/questions/6163166/why-is-arraydeque-better-than-linkedlist
 */
public class List01_ArrayDeque {

	/**
	 https://www.baeldung.com/java-array-deque

	 An ArrayDeque (also known as an “Array Double Ended Queue”, pronounced as “ArrayDeck”) is a special kind of a growable array
	 that allows us to add or remove an element from both sides.
	 An ArrayDeque implementation can be used as a Stack (Last-In-First-Out) or
	 a Queue(First-In-First-Out)


	 => It's not thread-safe
	 => Null elements are not accepted
	 => Works significantly faster than the synchronized Stack
	 => Is a faster queue than LinkedList due to the better locality of reference
	 => Most operations have amortized constant time complexity
	 => An Iterator returned by an ArrayDeque is fail-fast
	 => ArrayDeque automatically doubles the size of an array when head and tail pointer meets each other while adding an element

	 Operation					Method
	 =========					======
	 Insertion from Head		offerFirst(e)
	 Removal from Head			pollFirst()
	 Retrieval from Head		peekFirst()

	 Insertion from Tail		offerLast(e)
	 Removal from Tail			pollLast()
	 Retrieval from Tail		peekLast()


	 add(E e) has the capability of throwing an exception if an element can't be added into the queue. This happens in case the queue is full.
	 offer(E e) will return a special value (in this case, a boolean) if the value can't be added into the queue. This is useful if you're dealing with a size-limited queue but do not want to throw an exception.

	 */
	
	@Test
	public void Queue_ArrayDeque(){		
		
		//*********** With integer values
		Deque<Integer> intQue = new ArrayDeque<>();
		intQue.offerLast(1); //Adds the value at the end of the Deque
		intQue.offerLast(2);
		intQue.offerLast(3);
		intQue.offerLast(4);
		
		while(!intQue.isEmpty()){
			System.out.println(intQue.peekFirst()); //Retrieval from Head
			System.out.println(intQue.pollFirst()); //Removal from Head
		}		
		
		//With Objects
		Deque<ListNode> nodeQue = new ArrayDeque<>();
		nodeQue.offerLast(new ListNode(10));
		nodeQue.offerLast(new ListNode(20));
		nodeQue.offerLast(new ListNode(30));
		//nodeQue.offerLast(null); // Will throw Null pointer exception.But linkedList will not throw exception for null values
		nodeQue.offerLast(new ListNode(40));
		
		while(!nodeQue.isEmpty()){
			System.out.println(nodeQue.peekFirst()); //Retrieval from Head
			System.out.println(nodeQue.pollFirst().value);
		}
	}

	@Test
	public void Stack_ArrayDeque(){
		Deque<Integer> stack = new ArrayDeque<>();
		stack.offerFirst(1); //Adds the value at the front of the Deque
		stack.offerFirst(2);

		while(!stack.isEmpty()){
			System.out.println(stack.peekFirst()); //Retrieval from Head
			System.out.println(stack.pollFirst());
		}
	}
}

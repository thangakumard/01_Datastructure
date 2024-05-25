package coreJava.collections.deque_list;
import java.util.LinkedList;
import java.util.Queue;
import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;

public class List01_LinkedList {

	/**
	 * 			Throws exception	Returns special value
	 * Insert		add(e)				offer(e)
	 * Remove		remove()			poll()
	 * Examine		element()			peek()
	 */

	@Test
	public void Queue_LinkedList(){
		//With Objects
		Queue<ListNode> nodeQue = new LinkedList<>();
		nodeQue.offer(new ListNode(10));
		nodeQue.offer(new ListNode(20));
		nodeQue.offer(new ListNode(30));
		nodeQue.offer(new ListNode(40));

		nodeQue.add(new ListNode(30)); // When using a capacity-restricted queue, this method is generally preferable to add(E), which can fail to insert an element only by throwing an exception.
		nodeQue.offer(null); // Will throw Null pointer exception.But linkedList will not throw exception for null values

		while(!nodeQue.isEmpty()){
					System.out.println(nodeQue.peek().value);
					System.out.println(nodeQue.poll().value);
				}
	}

	@Test
	public void Queue_LinkedList_with_Size_Limit() {
		//With Objects
		Queue<ListNode> nodeQue = new LinkedList<ListNode>();
		nodeQue.offer(new ListNode(10));
		nodeQue.offer(new ListNode(20));
		nodeQue.offer(new ListNode(30));
		nodeQue.offer(new ListNode(40));

		nodeQue.add(new ListNode(30)); // When using a capacity-restricted queue, this method is generally preferable to add(E), which can fail to insert an element only by throwing an exception.
		nodeQue.offer(null); // Will throw Null pointer exception.But linkedList will not throw exception for null values

		while(!nodeQue.isEmpty()){
			System.out.println(nodeQue.peek().value);
			System.out.println(nodeQue.poll().value);
		}
	}
}

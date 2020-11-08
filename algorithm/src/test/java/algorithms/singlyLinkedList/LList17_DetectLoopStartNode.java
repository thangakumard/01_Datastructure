package algorithms.singlyLinkedList;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LList17_DetectLoopStartNode {

	
	@Test
	public void testRemoveLoop(){
		
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(1));
		list.push(new Node(2));
		
		list.head.next.next = list.head;

		System.out.println("Loop starting node is :" + findLoopStartNode(list.head).value);
	}
	
	private Node findLoopStartNode(Node head){
		
		if(head == null) return null;
		Node pointer2 = detectLoop(head);
		
		if(pointer2 == null) return null;
		
		while(head != pointer2){
			head = head.next;
			pointer2 = pointer2.next;
		}
		
		return pointer2;
	}
	
	private Node detectLoop(Node head){
		
		Node slow = head;
		Node fast = head;
		
		while(fast != null && fast.next != null){
			slow = slow.next;
			fast = fast.next.next;
			
			if(slow == fast) return slow;
		}		
		return null;
	}
	
}

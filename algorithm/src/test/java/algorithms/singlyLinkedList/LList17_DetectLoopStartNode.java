package algorithms.singlyLinkedList;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.annotations.Test;

public class LList17_DetectLoopStartNode {

	
	@Test
	public void testRemoveLoop(){
		
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(2));
		
		list.head.next.next = list.head;

		System.out.println("Loop starting node is :" + findLoopStartNode(list.head).value);
	}
	
	private ListNode findLoopStartNode(ListNode head){
		
		if(head == null) return null;
		ListNode pointer2 = detectLoop(head);
		
		if(pointer2 == null) return null;
		
		while(head != pointer2){
			head = head.next;
			pointer2 = pointer2.next;
		}
		
		return pointer2;
	}
	
	private ListNode detectLoop(ListNode head){
		
		ListNode slow = head;
		ListNode fast = head;
		
		while(fast != null && fast.next != null){
			slow = slow.next;
			fast = fast.next.next;
			
			if(slow == fast) return slow;
		}		
		return null;
	}
	
}

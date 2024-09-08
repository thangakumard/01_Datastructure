package algorithms.singlyLinkedList.medium.swap;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/***
 * 
	 * https://leetcode.com/problems/reorder-list/
 * Given a singly linked list L:
 * L0→L1→…→Ln-1→Ln, reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…
 * 
 * You may not modify the values in the list's nodes, only nodes itself may be
 * changed.
 * 
 * Example 1:
 * 
 * Given 1->2->3->4, reorder it to 1->4->2->3. Example 2:
 * 
 * Given 1->2->3->4->5, reorder it to 1->5->2->4->3.
 * 
 * 
 *
 */
public class Swap03_ReorderList {
	
	/**
	  This problem is a combination of these three easy problems:

		Middle of the Linked List.
		
		Reverse Linked List.
		
		Merge Two Sorted Lists.
	 */
	
	@Test
	public void test() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(20));
		list.push(new ListNode(30));
		list.push(new ListNode(40));
		list.push(new ListNode(50));
		list.push(new ListNode(60));
		list.push(new ListNode(70));
		list.push(new ListNode(80));
		list.push(new ListNode(90));
		list.push(new ListNode(100));
		
		reorderList(list.head);
		
		printList(list.head);
	}

		public void reorderList(ListNode head) {
		if(head == null) return;

		// find the middle of linked list [Problem 876]
		// in 1->2->3->4->5->6 find 4
		ListNode slow = head, fast = head;
		while(fast != null && fast.next != null){
			slow = slow.next;
			fast = fast.next.next;
		}

		// reverse the second part of the list [Problem 206]
		// convert 1->2->3->4->5->6 into 1->2->3->4 and 6->5->4
		// reverse the second half in-place
		ListNode currentNode = slow;
		ListNode prev = null, next = null;
		while(currentNode != null){
			next = currentNode.next;
			currentNode.next = prev;

			prev = currentNode;
			currentNode = next;
		}

		// merge two sorted linked lists [Problem 21]
		// merge 1->2->3->4 and 6->5->4 into 1->6->2->5->3->4
		ListNode first = head, second = prev;
		while (second.next != null) {
			next = first.next;
			first.next = second;
			first = next;

			next = second.next;
			second.next = first;
			second = next;
		}
	}
	 	
	 	void printList(ListNode node) {
	        while (node != null) {
	            System.out.print(node.value + " ");
	            node = node.next;
	        }
	    }

}

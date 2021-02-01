package algorithms.singlyLinkedList.easy;

import algorithms.singlyLinkedList.ListNode;

public class LList04_RemoveLinkedListElements {

	public ListNode removeElements(ListNode head, int val) {
	    if (head == null) return null;
	        head.next = removeElements(head.next, val);
	        return head.value == val ? head.next : head;
	    }
}

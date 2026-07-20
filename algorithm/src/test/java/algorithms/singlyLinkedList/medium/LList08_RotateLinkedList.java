package algorithms.singlyLinkedList.medium;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/*
 * https://leetcode.com/problems/rotate-list/solution/
 * 
 * Given the head of a linked list, rotate the list to the right by k places.

	Example 1:
	
	Input: head = [1,2,3,4,5], k = 2
	Output: [4,5,1,2,3]
	Example 2:
	
	
	Input: head = [0,1,2], k = 4
	Output: [2,0,1]
	 
	
	Constraints:
	
	The number of nodes in the list is in the range [0, 500].
	-100 <= Node.val <= 100
	0 <= k <= 2 * 109

 */

public class LList08_RotateLinkedList {
	
	@Test
	private void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(20));
		list1.push(new ListNode(30));	
		list1.push(new ListNode(40));
		list1.push(new ListNode(50));
		ListNode currentNode = rotateRight(list1.head, 2);
		printList(currentNode);
	}

	/*
	 * Time complexity : O(n)
	 * Space Complexity : O(1)
	 */
	public ListNode rotateRight(ListNode head, int k) {
		if (head == null || head.next == null || k == 0) {
			return head;
		}

		// Find length and tail
		ListNode tail = head;
		int length = 1;

		while (tail.next != null) {
			tail = tail.next;
			length++;
		}

		k %= length;
		if (k == 0) {
			return head;
		}

		// Form a circular list
		tail.next = head;

		// Find the new tail
		ListNode newTail = head;
		for (int i = 0; i < length - k - 1; i++) {
			newTail = newTail.next;
		}

		// Break the circle
		ListNode newHead = newTail.next;
		newTail.next = null;

		return newHead;
	}
	 
	 void printList(ListNode node) {
	        while (node != null) {
	            System.out.print(node.value + "->");
	            node = node.next;
	        }
	    }
}

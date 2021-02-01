package algorithms.singlyLinkedList.medium;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

/*
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * 
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

	k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
	
	Follow up:
	
	Could you solve the problem in O(1) extra memory space?
	You may not alter the values in the list's nodes, only nodes itself may be changed.
	 
	
	Example 1:
	
	
	Input: head = [1,2,3,4,5], k = 2
	Output: [2,1,4,3,5]
	Example 2:
	
	
	Input: head = [1,2,3,4,5], k = 3
	Output: [3,2,1,4,5]
	Example 3:
	
	Input: head = [1,2,3,4,5], k = 1
	Output: [1,2,3,4,5]
	Example 4:
	
	Input: head = [1], k = 1
	Output: [1]
	 
	
	Constraints:
	
	The number of nodes in the list is in the range sz.
	1 <= sz <= 5000
	0 <= Node.val <= 1000
	1 <= k <= sz

 */
public class LList17_ReverseAlternateKNodesSinglyLinkedList {

	@Test
	private void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));
		list1.push(new ListNode(20));
		list1.push(new ListNode(30));
		list1.push(new ListNode(40));
		list1.push(new ListNode(50));
		list1.push(new ListNode(60));
		ListNode currentNode = reverseKGroup(list1.head, 2);
		printList(currentNode);
	}

	/*
	 * Time complexity O(n)
	 * Space complexity O(1)
	 */
	public ListNode reverseKGroup(ListNode head, int k) {
		if (k <= 1 || head == null) {
			return head;
		}

		int count = 0;
		ListNode currentNode = head;
		while (currentNode != null) {
			count++;
			currentNode = currentNode.next;
		}
		ListNode reversed = null;
		ListNode prevTail = null;

		while (head != null && k > 0 && count >= k) {
			ListNode currentHead = null;
			ListNode currentTail = head;

			int n = k;
			while (head != null && n > 0) {
				ListNode temp = head.next;
				head.next = currentHead;
				currentHead = head;

				head = temp;
				n--;
			}

			if (reversed == null) {
				reversed = currentHead;
			}

			if (prevTail != null) {
				prevTail.next = currentHead;
			}
			prevTail = currentTail;
			count = 0;
			currentNode = head;
			while (currentNode != null) {
				count++;
				currentNode = currentNode.next;
			}
			if (count < k) {
				currentTail.next = head;
			}
		}

		return reversed;
	}

	void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + "->");
			node = node.next;
		}
	}
}

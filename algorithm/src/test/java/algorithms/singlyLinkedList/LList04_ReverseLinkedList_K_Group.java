package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

/***
 * 
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 *
 * Given a linked list, reverse the nodes of a linked list k at a time and
 * return its modified list.
 * 
 * k is a positive integer and is less than or equal to the length of the linked
 * list. If the number of nodes is not a multiple of k then left-out nodes, in
 * the end, should remain as it is.
 * 
 * Follow up:
 * 
 * Could you solve the problem in O(1) extra memory space? You may not alter the
 * values in the list's nodes, only nodes itself may be changed.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 2 Output: [2,1,4,3,5] Example 2:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 3 Output: [3,2,1,4,5] Example 3:
 * 
 * Input: head = [1,2,3,4,5], k = 1 Output: [1,2,3,4,5] Example 4:
 * 
 * Input: head = [1], k = 1 Output: [1]
 * 
 * 
 * Constraints:
 * 
 * The number of nodes in the list is in the range sz. 1 <= sz <= 5000 0 <=
 * Node.val <= 1000 1 <= k <= sz
 */
public class LList04_ReverseLinkedList_K_Group {
	
	@Test
	private void test() {

		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(2));
		list.push(new ListNode(3));
		list.push(new ListNode(4));
		list.push(new ListNode(5));
		list.push(new ListNode(6));
		list.push(new ListNode(7));

		ListNode result = reverseKGroup(list.head, 2);
		printList(result);
	}
	
	public ListNode reverseKGroup(ListNode head, int k) {
		int count = 0;
		ListNode currentNode = head;
		while (count < k && currentNode != null) {
			count++;
			currentNode = currentNode.next;
		}

		if (count == k) {
			ListNode reversedHead = reverseLinkedList(head, k);
			head.next = reverseKGroup(currentNode, k);
			return reversedHead;
		}

		return head;
	}

	public ListNode reverseLinkedList(ListNode head, int k) {

		ListNode prev = null;
		ListNode currentNode = head;

		while (k > 0) {

			ListNode next = currentNode.next;
			currentNode.next = prev;
			prev = currentNode;
			currentNode = next;

			k--;
		}

		return prev;
	}
	
	public void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
	}

}

package algorithms.singlyLinkedList.medium;

import org.testng.annotations.*;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/***
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * 
 * Given the head of a linked list, remove the nth node from the end of the list
 * and return its head.
 * 
 * Follow up: Could you do this in one pass?
 * 
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], n = 2 Output: [1,2,3,5] Example 2:
 * 
 * Input: head = [1], n = 1 Output: [] Example 3:
 * 
 * Input: head = [1,2], n = 1 Output: [1]
 * 
 * 
 * Constraints:
 * 
 * The number of nodes in the list is sz. 1 <= sz <= 30 0 <= Node.val <= 100 1
 * <= n <= sz
 *
 * 
 */
public class LList14_NthNodeFromEnd {

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

		ListNode currentNode = list.head;

		while (currentNode != null) {
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();

		currentNode = nthNodeFromEnd(list.head, 2);
		System.out.println("REQUIRED NODE IS :" + currentNode.value);
		System.out.println();
	}

	public ListNode nthNodeFromEnd(ListNode head, int n) {
		if (head == null || n <= 0) {
			return null;
		} else {
			ListNode currentNode = head;
			ListNode refNode = head;
			int count = 0;
			while (count < n && refNode != null) {
				refNode = refNode.next;
				count++;
			}
			while (refNode != null) {
				refNode = refNode.next;
				currentNode = currentNode.next;
			}
			return currentNode;
		}
	}
}

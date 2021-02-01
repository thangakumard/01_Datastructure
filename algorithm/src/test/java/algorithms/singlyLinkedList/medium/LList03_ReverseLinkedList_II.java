package algorithms.singlyLinkedList.medium;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

/***
 * https://leetcode.com/problems/reverse-linked-list-ii/ 
 * Reverse a linked list
 * from position m to n. Do it in one-pass.
 * 
 * Note: 1 ≤ m ≤ n ≤ length of list.
 * 
 * Example:
 * 
 * Input: 1->2->3->4->5->NULL, m = 2, n = 4 Output: 1->4->3->2->5->NULL
 *
 */

public class LList03_ReverseLinkedList_II {

	// Object level variables since we need the changes
	// to persist across recursive calls and Java is pass by value.
	private boolean stop;
	private ListNode left;

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

		reverseBetween(list.head, 1, 4);
		printList(list.head);
	}

	public ListNode reverseBetween(ListNode head, int m, int n) {
		this.left = head;
		this.stop = false;
		this.recurseAndReverse(head, m, n);
		return head;
	}

	public void recurseAndReverse(ListNode right, int m, int n) {

		// base case. Don't proceed any further
		if (n == 1) {
			return;
		}

		// Keep moving the right pointer one step forward until (n == 1)
		right = right.next;

		// Keep moving left pointer to the right until we reach the proper node
		// from where the reversal is to start.
		if (m > 1) {
			this.left = this.left.next;
		}

		// Recurse with m and n reduced.
		this.recurseAndReverse(right, m - 1, n - 1);

		// In case both the pointers cross each other or become equal, we
		// stop i.e. don't swap data any further. We are done reversing at this
		// point.
		if (this.left == right || right.next == this.left) {
			this.stop = true;
		}

		// Until the boolean stop is false, swap data between the two pointers
		if (!this.stop) {
			int t = this.left.value;
			this.left.value = right.value;
			right.value = t;

			// Move left one step to the right.
			// The right pointer moves one step back via backtracking.
			this.left = this.left.next;
		}
	}

	public void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
	}

}

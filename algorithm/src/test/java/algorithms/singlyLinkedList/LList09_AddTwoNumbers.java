package algorithms.singlyLinkedList;

import java.util.LinkedList;
import org.testng.annotations.Test;

public class LList09_AddTwoNumbers {
	/*
	 * 
	 * https://leetcode.com/problems/add-two-numbers/ You are given two non-empty
	 * linked lists representing two non-negative integers. The digits are stored in
	 * reverse order, and each of their nodes contains a single digit. Add the two
	 * numbers and return the sum as a linked list.
	 * 
	 * You may assume the two numbers do not contain any leading zero, except the
	 * number 0 itself.
	 * 
	 * 
	 * 
	 * Example 1:
	 * 
	 * 
	 * Input: l1 = [2,4,3], l2 = [5,6,4] Output: [7,0,8] Explanation: 342 + 465 =
	 * 807. Example 2:
	 * 
	 * Input: l1 = [0], l2 = [0] Output: [0] Example 3:
	 * 
	 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9] Output: [8,9,9,9,0,0,0,1]
	 * 
	 * 
	 * Constraints:
	 * 
	 * The number of nodes in each linked list is in the range [1, 100]. 0 <=
	 * Node.val <= 9 It is guaranteed that the list represents a number that does
	 * not have leading zeros.
	 */
	@Test
	public void Test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(9));
		list1.push(new ListNode(9));
		list1.push(new ListNode(1));

		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(9));
		list2.push(new ListNode(9));
		list2.push(new ListNode(1));

		ListNode head = addTwoNumbers(list1.head, list2.head);
		System.out.println("Addtion is :");
		printList(head);
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result = null;
		ListNode last = null;
		int carry = 0, sum = 0;

		while (l1 != null | l2 != null || carry > 0) {
			int first_number = l1 != null ? l1.value : 0;
			int second_number = l2 != null ? l2.value : 0;

			sum = first_number + second_number + carry;
			carry = sum / 10;
			sum = sum % 10;
			ListNode new_node = new ListNode(sum);
			if (result == null) {
				result = new_node;
			} else {
				last.next = new_node;
			}
			last = new_node;

			if (l1 != null) {
				l1 = l1.next;
			}
			if (l2 != null) {
				l2 = l2.next;
			}
		}
		return result;
	}

	void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + "->");
			node = node.next;
		}
	}
}

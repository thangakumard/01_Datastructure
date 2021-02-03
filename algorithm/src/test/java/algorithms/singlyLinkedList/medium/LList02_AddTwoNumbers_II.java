package algorithms.singlyLinkedList.medium;

import java.util.LinkedList;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

import java.util.*;

/****
 * 
 * https://leetcode.com/problems/add-two-numbers-ii/
 *
 * You are given two non-empty linked lists representing two non-negative
 * integers. The most significant digit comes first and each of their nodes
 * contain a single digit. Add the two numbers and return it as a linked list.
 * 
 * You may assume the two numbers do not contain any leading zero, except the
 * number 0 itself.
 * 
 * Follow up: What if you cannot modify the input lists? In other words,
 * reversing the lists is not allowed.
 * 
 * Example:
 * 
 * Input: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 8 -> 0 -> 7
 */

public class LList02_AddTwoNumbers_II {

	@Test
	public void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(3));
		list1.push(new ListNode(3));
		list1.push(new ListNode(3));

		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(7));
		list2.push(new ListNode(8));
		list2.push(new ListNode(9));
		list2.push(new ListNode(4));
		
		ListNode result1 = addTwoNumbers(list1.head, list2.head);
		ListNode result2 = addTwoNumbers_approach_01(list1.head, list2.head);
		printList(result1);
		printList(result2);
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		// find the length of both lists
		int n1 = 0, n2 = 0;
		ListNode curr1 = l1, curr2 = l2;
		while (curr1 != null) {
			curr1 = curr1.next;
			++n1;
		}
		while (curr2 != null) {
			curr2 = curr2.next;
			++n2;
		}

		// parse both lists
		// and sum the corresponding positions
		// without taking carry into account
		// 3->3->3 + 7->7 --> 3->10->10--> 10->10->3
		curr1 = l1;
		curr2 = l2;
		ListNode head = null;
		while (n1 > 0 && n2 > 0) {
			int val = 0;
			if (n1 >= n2) {
				val += curr1.value;
				curr1 = curr1.next;
				--n1;
			}
			if (n1 < n2) {
				val += curr2.value;
				curr2 = curr2.next;
				--n2;
			}

			// update the result: add to front
			ListNode curr = new ListNode(val);
			curr.next = head;
			head = curr;
		}

		//printList(head);

		// take the carry into account
		// to have all elements to be less than 10
		// 10->10->3 --> 0->1->4 --> 4->1->0
		curr1 = head;
		head = null;
		int carry = 0;
		while (curr1 != null) {
			// current sum and carry
			int val = (curr1.value + carry) % 10;
			carry = (curr1.value + carry) / 10;

			// update the result: add to front
			ListNode curr = new ListNode(val);
			//System.out.println("curr: " + val);
			curr.next = head;
			head = curr;

			// move to the next elements in the list
			curr1 = curr1.next;
		}

		// add the last carry
		if (carry != 0) {
			ListNode curr = new ListNode(carry);
			curr.next = head;
			head = curr;
		}

		return head;
	}

	/************* USING 2 STACK ****************/

	Stack<Integer> l1_stack = new Stack<Integer>();
	Stack<Integer> l2_stack = new Stack<Integer>();
	Stack<Integer> result = new Stack<Integer>();
	int carry = 0;

	public ListNode addTwoNumbers_approach_01(ListNode l1, ListNode l2) {
		//recursive(l1, l1_stack);
		//recursive(l2, l2_stack);
		while (l1 != null) {
			l1_stack.push(l1.value);
			l1 = l1.next;
		} 
		while (l2 != null) {
			l2_stack.push(l2.value);
			l2 = l2.next;
		} 
		ListNode dummy = new ListNode(0);
		ListNode head = dummy;
		while (!l1_stack.isEmpty() || !l2_stack.isEmpty() || carry > 0) {
			int v1 = !l1_stack.isEmpty() ? l1_stack.pop() : 0;
			int v2 = !l2_stack.isEmpty() ? l2_stack.pop() : 0;

			int sum = v1 + v2 + carry;
			result.push(sum % 10);
			carry = sum / 10;
		}
		while (!result.isEmpty()) {
			head.next = new ListNode(result.pop());
			head = head.next;
		}

		return dummy.next;
	}


	public void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
	}
}

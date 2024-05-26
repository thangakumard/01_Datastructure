package algorithms.singlyLinkedList.medium;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

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
		ListNode result2 = addTwoNumbers_usingStack(list1.head, list2.head);
		printList(result1);
		printList(result2);
	}

	/************* USING 2 STACK ****************/

	public ListNode addTwoNumbers_usingStack(ListNode l1, ListNode l2) {

		Stack<Integer> stack1 = new Stack<>();
		Stack<Integer> stack2 = new Stack<>();

		while(l1 != null){
			stack1.push(l1.value);
			l1 = l1.next;
		}

		while(l2 != null){
			stack2.push(l2.value);
			l2 = l2.next;
		}

		int val1 =0, val2 =0, carry = 0, sum = 0;
		ListNode currentNode = new ListNode(0);


		while(!stack1.isEmpty() || !stack2.isEmpty() || carry > 0)
		{
			val1 = !stack1.isEmpty() ? stack1.pop() : 0;
			val2 = !stack2.isEmpty() ? stack2.pop() : 0;

			sum = carry + val1 + val2;
			carry = sum / 10;

			currentNode.value = sum % 10;

			ListNode head = new ListNode(carry);
			head.next = currentNode;
			currentNode = head;
		}

		return currentNode.next;
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



	public void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
	}
}

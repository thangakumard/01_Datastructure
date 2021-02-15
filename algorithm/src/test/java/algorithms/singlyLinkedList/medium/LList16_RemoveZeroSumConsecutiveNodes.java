package algorithms.singlyLinkedList.medium;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

/*
 * https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/
 * 
 * Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.

	After doing so, return the head of the final linked list.  You may return any such answer.
	
	 
	
	(Note that in the examples below, all sequences are serializations of ListNode objects.)
	
	Example 1:
	
	Input: head = [1,2,-3,3,1]
	Output: [3,1]
	Note: The answer [1,2,1] would also be accepted.
	Example 2:
	
	Input: head = [1,2,3,-3,4]
	Output: [1,2,4]
	Example 3:
	
	Input: head = [1,2,3,-3,-2]
	Output: [1]
	 
	
	Constraints:
	
	The given linked list will contain between 1 and 1000 nodes.
	Each node in the linked list has -1000 <= node.val <= 1000.
 */
public class LList16_RemoveZeroSumConsecutiveNodes {

	@Test
	private void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
//		list1.push(new ListNode(5));
//		list1.push(new ListNode(-3));
//		list1.push(new ListNode(-4));
//		list1.push(new ListNode(1));
//		list1.push(new ListNode(6));
//		list1.push(new ListNode(-2));
//		list1.push(new ListNode(-5));
		
		list1.push(new ListNode(1));
		list1.push(new ListNode(2));
		list1.push(new ListNode(-3));
		list1.push(new ListNode(3));
		list1.push(new ListNode(1));

		ListNode currentNode = removeZeroSumSublists(list1.head);
	}
		
		
	public ListNode removeZeroSumSublists(ListNode head) {
		int prefix = 0;
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		Map<Integer, ListNode> seen = new HashMap<>();
		seen.put(0, dummy);
		for (ListNode i = dummy; i != null; i = i.next) {
			prefix += i.value;
			seen.put(prefix, i);
		}
		prefix = 0;
		for (ListNode i = dummy; i != null; i = i.next) {
			prefix += i.value;
			i.next = seen.get(prefix).next;
		}
		return dummy.next;
	}
}

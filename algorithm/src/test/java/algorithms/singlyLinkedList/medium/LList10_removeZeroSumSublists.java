package algorithms.singlyLinkedList.medium;

import java.util.*;

/*****
 * https://leetcode.com/problems/remove-zero-sum-consecutive-nodes-from-linked-list/
 
 Given the head of a linked list, we repeatedly delete consecutive sequences of nodes that sum to 0 until there are no such sequences.

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

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

public class LList10_removeZeroSumSublists {

	@Test
	public void test() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(-3));//7
		list.push(new ListNode(1)); //8
		list.push(new ListNode(-1));//7
		list.push(new ListNode(3));// 10
		list.head = removeZeroSumSublists(list.head);

		ListNode currentNode = list.head;
		while (currentNode != null) {
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
	}

	public ListNode removeZeroSumSublists(ListNode head) {
		int prefix = 0;
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		Map<Integer, ListNode> seen = new HashMap<>();
		seen.put(0, dummy);
		ListNode i = dummy;
		while (i != null) {
			prefix += i.value;
			seen.put(prefix, i);
			i = i.next;
		}
		prefix = 0;
		i = dummy;
		while (i != null) {
			prefix += i.value;
			i.next = seen.get(prefix).next;
			i = i.next;
		}
		return dummy.next;
	}

}

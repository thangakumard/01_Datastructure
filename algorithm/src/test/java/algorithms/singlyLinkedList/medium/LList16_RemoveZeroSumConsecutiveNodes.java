package algorithms.singlyLinkedList.medium;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

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
		ListNode sentinel = new ListNode(0);
		ListNode currentNode = sentinel;
		sentinel.next = head;
		int sum = 0;

		Map<Integer,ListNode> nodeMap = new HashMap<>();
		nodeMap.put(0, sentinel);

		while (currentNode!= null){
			sum += currentNode.value;
			nodeMap.put(sum,currentNode);
			currentNode = currentNode.next;
		}

		sum =0;
		currentNode = sentinel; //**IMPORTANT to rest currentNode and sum

		while (currentNode != null){
			sum += currentNode.value;
			currentNode.next =  nodeMap.get(sum).next;
			currentNode = currentNode.next;
		}

		return sentinel.next;
	}
}

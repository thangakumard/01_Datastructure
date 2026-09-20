package algorithms.priorityQueue;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.annotations.Test;

import java.util.PriorityQueue;

/****
 * 
 * https://leetcode.com/problems/merge-k-sorted-lists/ 
 * You are given an array of
 * k linked-lists lists, each linked-list is sorted in ascending order.
 * 
 * Merge all the linked-lists into one sorted linked-list and return it.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: lists = [[1,4,5],[1,3,4],[2,6]] Output: [1,1,2,3,4,4,5,6] Explanation:
 * The linked-lists are: [ 1->4->5, 1->3->4, 2->6 ] merging them into one sorted
 * list: 1->1->2->3->4->4->5->6 Example 2:
 * 
 * Input: lists = [] Output: [] Example 3:
 * 
 * Input: lists = [[]] Output: []
 * 
 * 
 * Constraints:
 * 
 * k == lists.length 0 <= k <= 10^4 0 <= lists[i].length <= 500 -10^4 <=
 * lists[i][j] <= 10^4 lists[i] is sorted in ascending order. The sum of
 * lists[i].length won't exceed 10^4.
 *
 */

public class LList15_MergeKSortedLists {

	@Test
	private void test() {

		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));
		list1.push(new ListNode(30));
		list1.push(new ListNode(50));
		list1.push(new ListNode(70));
		list1.push(new ListNode(90));

		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(20));
		list2.push(new ListNode(40));
		list2.push(new ListNode(60));
		list2.push(new ListNode(80));
		list2.push(new ListNode(100));

		ListNode[] lists = new ListNode[2];
		lists[0] = list1.head;
		lists[1] = list2.head;

		ListNode result = mergeKLists(lists);

		System.out.println(" ");
		System.out.println("linked list after removal: ");
		printList(result);

	}

	/**
	 * Approach 1: Min Heap (Priority Queue) - OPTIMAL
	 *
	 * Strategy:
	 * - Maintain a min heap of size k, where each element is the head of a list
	 * - Always extract the node with minimum value
	 * - Add its next node back to the heap
	 * - This ensures we always pick the smallest node among all current list heads
	 *
	 * Why this is optimal:
	 * - Total nodes processed: n*k
	 * - Each heap operation (add/remove): O(log k)
	 * - Total: O(n*k*log k)
	 * - This is the theoretical lower bound for comparing nodes across k lists
	 *
	 * Time: O(n*k*log k)
	 * Space: O(k) for the heap
	 */
	public ListNode mergeKLists(ListNode[] lists) {
		if (lists == null || lists.length == 0) return null;

		// Min heap comparator: compare by node value
		PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
				(a, b) -> Integer.compare(a.value, b.value)
		);

		// Add the head of each non-empty list to the heap
		for (ListNode list : lists) {
			if (list != null) {
				minHeap.offer(list);
			}
		}

		ListNode dummy = new ListNode(0);
		ListNode current = dummy;

		// Extract minimum, add it to result, and insert next node from same list
		while (!minHeap.isEmpty()) {
			ListNode minNode = minHeap.poll();  // O(log k)
			current.next = minNode;
			current = current.next;

			// Add next node from the same list
			if (minNode.next != null) {
				minHeap.offer(minNode.next);  // O(log k)
			}
		}

		return dummy.next;
	}

	/***
	 * The Problem
	 * You're storing all n·k nodes in the heap, then sorting them. This is inefficient because:
	 * You rebuild the entire list unnecessarily
	 * The heap grows to size n·k instead of staying at k
	 * Each operation costs log(n·k) instead of log(k)
	 *
	 * This takes time	O(n·k·log(n·k)) instead of 	O(n·k·log(k))
	 * Space O(n·k) instead of O(k)
	 * So use approach 1 above
	 */
	public ListNode mergeKLists_inefficient(ListNode[] lists) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();

		for (ListNode head : lists) {
			while (head != null) {
				minHeap.add(head.value);
				head = head.next;
			}
		}

		ListNode dummy = new ListNode(-1);
		ListNode head = dummy;

		while (!minHeap.isEmpty()) {
			head.next = new ListNode(minHeap.remove());
			head = head.next;
		}

		return dummy.next;
	}

	void printList(ListNode node) {
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
	}
}

package algorithms.singlyLinkedList;

import java.util.PriorityQueue;

import org.testng.annotations.Test;

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

public class LList27_MergeKSortedLists {

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

	public ListNode mergeKLists(ListNode[] lists) {
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();

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

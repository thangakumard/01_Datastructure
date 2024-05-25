package algorithms.singlyLinkedList.deleteNode;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.annotations.Test;

/***
 * https://leetcode.com/problems/remove-linked-list-elements/description/
 * Given the head of a linked list and an integer val, remove all the nodes of the linked list that has Node.val == val, and return the new head.
 *
 * Example 1:
 * Input: head = [1,2,6,3,4,5,6], val = 6
 * Output: [1,2,3,4,5]
 *
 *  Example 2:
 * Input: head = [], val = 1
 * Output: []
 * Example 3:
 * Input: head = [7,7,7,7], val = 7
 * Output: []
 *
 * Constraints:
 * The number of nodes in the list is in the range [0, 104].
 * 1 <= Node.val <= 50
 * 0 <= val <= 50
 */
public class Delete01_RemoveLinkedListElements {

	@Test
	public void Test(){
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));
		list1.push(new ListNode(20));
		list1.push(new ListNode(30));
		list1.push(new ListNode(40));
		list1.push(new ListNode(50));
		list1.push(new ListNode(60));
		list1.push(new ListNode(70));

		removeElements_recursive(list1.head, 30);
		removeElements_iterative(list1.head, 30);
	}

	public ListNode removeElements_recursive(ListNode head, int val) {
	    if (head == null) return null;
			head.next = removeElements_recursive(head.next, val);
	        return head.value == val ? head.next : head;
	}

	public ListNode removeElements_iterative(ListNode head, int val) {

		ListNode currentNode = head;

		while(head != null && head.value == val){
			head = head.next;
		}

		while(currentNode != null && currentNode.next != null){
			if(currentNode.next.value == val){
				currentNode.next = currentNode.next.next;
			}else{
				currentNode = currentNode.next;
			}
		}


		return head;

	}
}

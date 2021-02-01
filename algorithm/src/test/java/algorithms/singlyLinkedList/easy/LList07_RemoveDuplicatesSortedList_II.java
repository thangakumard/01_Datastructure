package algorithms.singlyLinkedList.easy;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

public class LList07_RemoveDuplicatesSortedList_II {
	/*
	 * https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
	 * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the original list.

		Return the linked list sorted as well.
		
		Example 1:
		
		Input: 1->2->3->3->4->4->5
		Output: 1->2->5
		Example 2:
		
		Input: 1->1->1->2->3
		Output: 2->3

	 */
	@Test
	private void test() {
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(1));
		list.push(new ListNode(2));
		list.push(new ListNode(2));
		list.push(new ListNode(3));
		list.push(new ListNode(4));
		list.push(new ListNode(4));
		ListNode currentNode = deleteDuplicates(list.head);
		
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
	}
	
	/*
	 * Time complexity O(n)
	 * Space Complexity O(1)
	 */
	public ListNode deleteDuplicates(ListNode head) {
        ListNode sentinel = new ListNode(0);
        sentinel.next = head;
        
        ListNode prev = sentinel; 

        while(head != null)
        {
            if(head.next != null && head.value == head.next.value){
                while(head.next != null && head.value == head.next.value){
                    head = head.next;
                }
                prev.next = head.next;
            }else{
                prev = prev.next;
            }
            head = head.next;
        }

        return sentinel.next;
    }

}

package algorithms.singlyLinkedList.rabbit;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/**
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/description/
 *
 * Given the head of a linked list, remove the nth node from the end of the list and return its head.
 * Example 1:
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 *
 * Example 2:
 * Input: head = [1], n = 1
 * Output: []
 *
 * Example 3:
 * Input: head = [1,2], n = 1
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 *
 * Follow up: Could you do this in one pass?
 */
public class Rabbit04_removeNthFromEnd {
	
	@Test
	private void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(20));
		list1.push(new ListNode(30));		
		list1.push(new ListNode(40));		
		list1.push(new ListNode(50));
		
		list1.head = removeNthFromEnd(list1.head, 2);
		System.out.println(" ");
        System.out.println("linked list after removal: ");
        printList(list1.head);		
	}

	/*
	 * Time complexity O(L)
	 * Space complexity O(1)
	 */
	private ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode sentinel = new ListNode(0);
        sentinel.next = head;
    
        ListNode fast = sentinel;
        ListNode slow = sentinel;
        
        for(int i=0; i < n; i++){
            fast = fast.next;
        }
        
        while(fast != null){
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return sentinel.next;
    }
	
	 // prints content of double linked list
    void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
}

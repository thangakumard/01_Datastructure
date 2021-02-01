package algorithms.singlyLinkedList.medium;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

/*
 * https://leetcode.com/problems/rotate-list/solution/
 * 
 * Given the head of a linked list, rotate the list to the right by k places.

	Example 1:
	
	Input: head = [1,2,3,4,5], k = 2
	Output: [4,5,1,2,3]
	Example 2:
	
	
	Input: head = [0,1,2], k = 4
	Output: [2,0,1]
	 
	
	Constraints:
	
	The number of nodes in the list is in the range [0, 500].
	-100 <= Node.val <= 100
	0 <= k <= 2 * 109

 */

public class LList08_RotateLinkedList {
	
	@Test
	private void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(20));
		list1.push(new ListNode(30));	
		list1.push(new ListNode(40));
		list1.push(new ListNode(50));
		ListNode currentNode = rotateRight(list1.head, 2);
		printList(currentNode);
	}

	/*
	 * Time complexity : O(n)
	 * Space Complexity : O(1)
	 */
	 public ListNode rotateRight(ListNode head, int k) {
	        //base case
	        if(head == null) return null;
	        if(head.next == null) return head;
	        
	        //Find the tail and point to head (make a circular list)
	        int count;
	        ListNode old_tail = head;
	        for(count =1; old_tail.next != null; count++){
	            old_tail = old_tail.next;
	        }
	        old_tail.next = head;
	        
	        //find the new tail : (n-k) % (n-1)
	        ListNode new_tail = head;
	        for(int i=0; i< count-k%count-1; i++){
	            new_tail = new_tail.next;
	        }
	        ListNode new_head = new_tail.next;
	        new_tail.next = null;
	        
	        return new_head;
	    }
	 
	 void printList(ListNode node) {
	        while (node != null) {
	            System.out.print(node.value + "->");
	            node = node.next;
	        }
	    }
}

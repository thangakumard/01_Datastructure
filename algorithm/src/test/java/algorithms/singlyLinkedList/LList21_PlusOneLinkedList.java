package algorithms.singlyLinkedList;

/*
 * https://leetcode.com/problems/plus-one-linked-list/
 * 
 * Given a non-negative integer represented as a linked list of digits, plus one to the integer.

   The digits are stored such that the most significant digit is at the head of the list.
	
	 
	
	Example 1:
	
	Input: head = [1,2,3]
	Output: [1,2,4]
	Example 2:
	
	Input: head = [0]
	Output: [1]
 */
public class LList21_PlusOneLinkedList {
	
	public ListNode plusOneListIterative(ListNode head) {
		
		/*
		 * 1. Have a sentinel node (dummy head)
		 * 2. Keep a node instance to keep most recent NOT 9 value node
		 * 3. If NOT 9 node found 
		 * 			and it's next node is not null definitely the next nodes will have value 9 change the value to 0
		 *    If all the node values are 9,
		 *    	increment the value of sentinel and change all other node value to 0
		 *   
		 */
		
		//Have a sentinel node
		ListNode sentinel = new ListNode(0);
		sentinel.next = head;
		ListNode nonNine = sentinel;
		
		//if NOT 9 node found 
		while(head != null) {
			if(head.value != 9)
				nonNine = head;
			head = head.next;
		}
		
		nonNine.value ++;
		nonNine = nonNine.next;
		
		while(nonNine != null) {
			nonNine = nonNine.next;
			nonNine.value = 0;
		}
		
		return sentinel.value == 1 ? sentinel : sentinel.next;
	}
	
}

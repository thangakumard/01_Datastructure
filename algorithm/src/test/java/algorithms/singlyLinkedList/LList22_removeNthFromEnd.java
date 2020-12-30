package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

/*
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * 
 */
public class LList22_removeNthFromEnd {
	
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
        
        for(int i=1; i <= n+1; i++){
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

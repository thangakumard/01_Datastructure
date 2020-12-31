package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

public class LList23_IntersectionOfTwoLinkedLists {

	/*
	 * https://leetcode.com/problems/intersection-of-two-linked-lists/
	 * 
	 */
	@Test
	private void test() {
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(20));
		list1.push(new ListNode(30));		
		ListNode node1 = new ListNode(40);		
		ListNode node2 = new ListNode(50);
		list1.push(node1);
		list1.push(node2);
		
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(10));		
		list2.push(new ListNode(20));
		list2.push(new ListNode(30));		
		list2.push(node1);
		
		
		list1.head = getIntersectionNode(list1.head,list2.head);
		System.out.println(" ");
        System.out.println("linked list after removal: ");
        printList(list1.head);		
	}

	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		
		if(headA == null || headB == null)
			return null;
		
		ListNode a = headA;
		ListNode b = headB;
		
		while(a != b) {
			a = a == null ? headB : a.next;
			b = b == null ? headA : b.next;
		}
		return a;
	}
	
	 // prints content of double linked list
    void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
	
}

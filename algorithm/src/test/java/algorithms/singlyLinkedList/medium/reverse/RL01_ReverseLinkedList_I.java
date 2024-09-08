package algorithms.singlyLinkedList.medium.reverse;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

public class RL01_ReverseLinkedList_I {
	
	@Test
	public void ReverseTheList(){
	
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(9));
		list.push(new ListNode(8));
		list.push(new ListNode(7));
		list.push(new ListNode(6));
		list.push(new ListNode(5));
		
	    
        System.out.println("Given Linked list");
        printList(list.head);
        list.head = reverseList(list.head);
        System.out.println(" ");
        System.out.println("Reversed linked list ");
        printList(list.head);		
	}

	public ListNode reverseList(ListNode head) {
		//1->2->3->4->5
		// currentNode = 1
		ListNode previous = null, next, currentNode = head;
		while(currentNode != null){
			next = currentNode.next; // next = 2->3->4->5 //3->4->5
			currentNode.next = previous; // 1->null(previous) //2->1
			previous = currentNode; // previous = 1-> // previous = 2->1
			currentNode = next;// currentNode = 2->3-4->5 // currentNode = >3-4->5
		}
		head = previous;
		return head;
	}
	
    void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }

}

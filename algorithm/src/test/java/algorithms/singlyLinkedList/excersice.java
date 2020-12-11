package algorithms.singlyLinkedList;

import org.testng.Assert;
import org.testng.annotations.Test;

public class excersice {

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
        list.head = reverse(list.head);
        System.out.println(" ");
        System.out.println("Reversed linked list ");
        printList(list.head);		
	}
	
	public ListNode reverse(ListNode node){
		ListNode prev = null;
		ListNode next = null;
		ListNode currNode = node;
		
		while(currNode != null) {
			next = currNode.next;
			currNode.next = prev;
			
			prev = currNode;
			currNode = next;
		}
		
		return prev;
		
	}
	
	 // prints content of double linked list
    void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
    }
}

package algorithms.singlyLinkedList;

import java.util.LinkedList;
import org.testng.annotations.Test;
public class LList09_AddTwoNumbers {
	
	@Test
	public void Test(){
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new Node(2));
		list1.push(new Node(4));
		list1.push(new Node(3));
		
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new Node(5));
		list2.push(new Node(6));
		list2.push(new Node(4));
		
		Node head = addTwoNumbers(list1.head, list2.head);
		 System.out.println("Addtion is :");
	     printList(head);
	}
	
	public Node addTwoNumbers(Node l1, Node l2) {
        Node temp = new Node(0);
        Node head = temp;
        int carry = 0;
         while(l1 != null && l2 != null){
            
            head.next = new Node((l1.value + l2.value + carry)%10);
            carry  = (l1.value + l2.value + carry)/10;
            head = head.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while(l1 != null){            
            head.next = new Node((l1.value + carry)%10);
            carry  = (l1.value + carry)/10;
            head = head.next;
            l1 = l1.next; 
        }
         while(l2 != null){            
            head.next = new Node((l2.value + carry)%10);
            carry  = (l2.value + carry)/10;
            head = head.next;
            l2 = l2.next; 
        }
        if(carry > 0){
            head.next = new Node(carry);
        }
        return temp.next;
    }
	
	 void printList(Node node) {
	        while (node != null) {
	            System.out.print(node.value + "->");
	            node = node.next;
	        }
	    }
}

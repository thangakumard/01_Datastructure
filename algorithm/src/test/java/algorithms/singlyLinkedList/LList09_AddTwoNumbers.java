package algorithms.singlyLinkedList;

import java.util.LinkedList;
import org.testng.annotations.Test;
public class LList09_AddTwoNumbers {
	
	@Test
	public void Test(){
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(2));
		list1.push(new ListNode(4));
		list1.push(new ListNode(3));
		
		
		SinglyLinkedList list2 = new SinglyLinkedList();
		list2.push(new ListNode(5));
		list2.push(new ListNode(6));
		list2.push(new ListNode(4));
		
		ListNode head = addTwoNumbers(list1.head, list2.head);
		 System.out.println("Addtion is :");
	     printList(head);
	}
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode temp = new ListNode(0);
        ListNode head = temp;
        int carry = 0;
         while(l1 != null && l2 != null){
            
            head.next = new ListNode((l1.value + l2.value + carry)%10);
            carry  = (l1.value + l2.value + carry)/10;
            head = head.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        while(l1 != null){            
            head.next = new ListNode((l1.value + carry)%10);
            carry  = (l1.value + carry)/10;
            head = head.next;
            l1 = l1.next; 
        }
         while(l2 != null){            
            head.next = new ListNode((l2.value + carry)%10);
            carry  = (l2.value + carry)/10;
            head = head.next;
            l2 = l2.next; 
        }
        if(carry > 0){
            head.next = new ListNode(carry);
        }
        return temp.next;
    }
	
	 void printList(ListNode node) {
	        while (node != null) {
	            System.out.print(node.value + "->");
	            node = node.next;
	        }
	    }
}

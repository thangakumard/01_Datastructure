package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/add-1-number-represented-linked-list/
public class LList08_Add1toNumber {
	
	@Test
	public void test()
	{
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(9));
		list.push(new ListNode(9));
		list.push(new ListNode(9));
		
		Add1(list.head);
	}
	
	public void Add1(ListNode node){
		
		ListNode head = ReverseList(node);
		
		printList(head);
		 
		ListNode addition = Add1AndCarryFwd(head);
		
		printList(addition);
		
		ListNode headAfterAddition = ReverseList(addition);
		
		printList(headAfterAddition);
	}
	
	public void printList(ListNode node){
		
		while(node != null){
			System.out.println(node.value + " ");
			node = node.next;
		}
	}
	
	public ListNode Add1AndCarryFwd(ListNode node){
		
		ListNode currentNode = node;
		int addition = 0;
		int carry = 1;
		while(currentNode != null){
			addition = currentNode.value + carry;			
			carry = addition / 10;
			currentNode.value = addition % 10;
			currentNode = currentNode.next;
		}
		
		return node;
	}
	

	
	public ListNode ReverseList(ListNode node){
		ListNode previous = null;
		ListNode next = null;
		ListNode CurrentNode = node;
		while(CurrentNode != null){			
			
			next = CurrentNode.next;
			CurrentNode.next = previous;
			previous = CurrentNode;
			CurrentNode = next;
		}
		node = previous;
		return node;		
	}	
	
}

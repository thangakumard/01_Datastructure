package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/add-1-number-represented-linked-list/
public class LList08_Add1toNumber {
	
	@Test
	public void test()
	{
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(1));
		list.push(new Node(9));
		list.push(new Node(9));
		list.push(new Node(9));
		
		Add1(list.head);
	}
	
	public void Add1(Node node){
		
		Node head = ReverseList(node);
		
		printList(head);
		 
		Node addition = Add1AndCarryFwd(head);
		
		printList(addition);
		
		Node headAfterAddition = ReverseList(addition);
		
		printList(headAfterAddition);
	}
	
	public void printList(Node node){
		
		while(node != null){
			System.out.println(node.value + " ");
			node = node.next;
		}
	}
	
	public Node Add1AndCarryFwd(Node node){
		
		Node currentNode = node;
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
	

	
	public Node ReverseList(Node node){
		Node previous = null;
		Node next = null;
		Node CurrentNode = node;
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

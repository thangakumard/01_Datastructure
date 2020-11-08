package algorithms.singlyLinkedList;

import org.testng.Assert;
import org.testng.annotations.Test;

public class excersice {

	@Test
	public void Sample(){
	
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(10));
		list.push(new Node(20));
		list.push(new Node(30));
		list.push(new Node(40));
		list.push(new Node(50));
		list.push(new Node(60));
		list.push(new Node(70));
		list.push(new Node(80));
		list.push(new Node(90));
		list.push(new Node(100));
		list.push(new Node(110));
		
		Node currentNode = reverseLinkedList(list.head);
		
		while(currentNode != null){
			System.out.println(currentNode.value);
			currentNode = currentNode.next;
		}
		
	}

	private Node reverseLinkedList(Node head) {
		Node prev = null;
		Node next = null;
		Node current = head;
		
		if(head !=null){
			while(current != null){
				next = current.next;
				current.next = prev;
				
				prev =current;
				current = next;
			}
		}
		return prev;
	}
}

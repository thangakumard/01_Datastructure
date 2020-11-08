package algorithms.singlyLinkedList;

import org.testng.annotations.Test;

public class LList14_RemoveDuplicateFromSorted {


	@Test
	public void test(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(10));
		list.push(new Node(10));
		list.push(new Node(30));
		list.push(new Node(30));
		list.push(new Node(50));
		list.push(new Node(60));
		list.push(new Node(70));
		list.push(new Node(70));
		list.push(new Node(70));
		list.push(new Node(100));		
		
		Node currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
		
		currentNode = removeDuplicateFromSortedLinkedList(list.head);		
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
	}
	
	private Node removeDuplicateFromSortedLinkedList(Node head){
		if(head == null){
			return null;
		}
		Node currentNode = head;		
		while(currentNode != null && currentNode.next != null){
			if(currentNode.value == currentNode.next.value){
				currentNode.next = currentNode.next.next;
			}
			else
			{
				currentNode = currentNode.next;
			}		
			
		}
		
		return head;
	}
}

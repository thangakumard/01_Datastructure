package algorithms.singlyLinkedList;

import org.testng.annotations.*;

public class LList15_InsertInSortedList {

	@Test
	public void test(){
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
		
		Node currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
		System.out.print("NULL");
		System.out.println();
		
		insertInSortedList(list.head, 45);
		insertInSortedList(list.head, 55);
		insertInSortedList(list.head, 65);
		
		currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;			
		}
		System.out.print("NULL");
		System.out.println();
	}
	
	public Node insertInSortedList(Node head, int value){
		Node newNode = new Node(value);
		if(head == null){
			head = newNode;
		}else{
			Node currentNode = head;
			Node previousNode = head;
			while(currentNode != null && currentNode.value < value) {				
				previousNode = currentNode;
				currentNode = currentNode.next;
			}			
			previousNode.next = newNode;
			newNode.next = currentNode;
		}
		return head;
	}
	
}

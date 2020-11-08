package algorithms.doublyLinkedList;

import org.testng.annotations.*;


public class LList01_InsertNode {

	@Test
	public void test(){
		DoublyLinkedList list = new DoublyLinkedList();
		insertAtHead(list, 10);
		insertAtHead(list, 20);
		insertAtHead(list, 30);
		insertAtHead(list, 40);
		
		insertAtTail(list, 50);
		insertAtTail(list, 60);
		insertAtTail(list, 70);
		insertAtTail(list, 80);
		insertAtTail(list, 90);
		
		LList02_PrintInBothDirections printNodes = new LList02_PrintInBothDirections();
		printNodes.printForward(list);
	}
	
	public Node insertAtHead(DoublyLinkedList list, int value){
		if(list == null){
			return null;
		}
		else{
			Node newNode = new Node(value);
			if(list.isEmpty()){
				list.tail = newNode;
			}
			else{
				list.head.previous = newNode;				
			}
			newNode.next =list.head;
			list.head = newNode;
			list.length++;
		}
		return list.head;
	}
	
	public Node insertAtTail(DoublyLinkedList list, int value){
		if(list == null){
			return null;
		}
		else{
			Node newNode = new Node(value);
			if(list.isEmpty()){				
				list.head = newNode;
			}else{
				list.tail.next = newNode;
				newNode.previous = list.tail;
			}
			list.tail = newNode;
			list.length++;
			return list.head;
		}
	}

}

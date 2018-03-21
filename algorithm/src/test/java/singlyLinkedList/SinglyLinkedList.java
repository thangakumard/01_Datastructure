package singlyLinkedList;

import java.util.LinkedList;

public class SinglyLinkedList {

	Node head;
 
	public void push(Node node){
		if(head == null){
			head = node;
		}else {
			Node currentNode = head;
			Node tailNode = head;
			while(currentNode != null){
				tailNode = currentNode;
				currentNode = currentNode.next;
			}
			tailNode.next = node;
		}
	}
	
	public Node insertAtBeginning(Node head, int data){
		Node currentNode = new Node(data);
		if(head == null){
			return currentNode;
		}
		currentNode.next = head;
		head = currentNode;
		return head;		
	}
	
	public Node insertAtEnd(Node head, int data){
		Node currentNode = new Node(data);
		if(head != null){
			head = currentNode;			
		}
		else{
			Node temp = head;
			while(temp.next != null){
				temp = temp.next;
			}
			temp.next = currentNode;			
		}
		return head;
	}
	
	public Node insertAtIndex(Node head, int index, int data){
		Node currentNode = new Node(data);		
		if(index <= 0 || index > noOfNodes(head)+1){
			System.out.println("INVALID INDEX !");
		}
		if(index == 1){
			currentNode.next = head;
			head = currentNode;
		}else{
			int counter = 1;
			Node temp = head;
			while(counter < index -1){
				temp = temp.next;
				counter++;
			}
			currentNode.next = temp.next;
			temp.next = currentNode;			
		}		
		return head;
	}
	
	public int noOfNodes(Node head){
		if(head == null){
			return 0;
		}else{
			int counter = 0;
			while(head != null){
				counter ++;
				head = head.next;
			}
			return counter;
		}
	}
	
	public void remove(Node node){
		
		Node temp = node.next;
		node.value = temp.value;
		node.next = temp.next.next;
		temp = null;
	}
}

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
	
	public void remove(Node node){
		
		Node temp = node.next;
		node.value = temp.value;
		node.next = temp.next.next;
		temp = null;
	}
	
}

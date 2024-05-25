package algorithms.singlyLinkedList.base;

import algorithms.singlyLinkedList.base.ListNode;

public class SinglyLinkedList {

	public ListNode head;
 
	public void push(ListNode node){
		if(head == null){
			head = node;
		}else {
			ListNode currentNode = head;
			ListNode tailNode = head;
			while(currentNode != null){
				tailNode = currentNode;
				currentNode = currentNode.next;
			}
			tailNode.next = node;
		}
	}
	
	public ListNode insertAtBeginning(ListNode head, int data){
		ListNode currentNode = new ListNode(data);
		if(head == null){
			return currentNode;
		}
		currentNode.next = head;
		head = currentNode;
		return head;		
	}
	
	public ListNode insertAtEnd(ListNode head, int data){
		ListNode currentNode = new ListNode(data);
		if(head != null){
			head = currentNode;			
		}
		else{
			ListNode temp = head;
			while(temp.next != null){
				temp = temp.next;
			}
			temp.next = currentNode;			
		}
		return head;
	}
	
	public ListNode insertAtIndex(ListNode head, int index, int data){
		ListNode currentNode = new ListNode(data);		
		if(index <= 0 || index > noOfNodes(head)+1){
			System.out.println("INVALID INDEX !");
		}
		if(index == 1){
			currentNode.next = head;
			head = currentNode;
		}else{
			int counter = 1;
			ListNode temp = head;
			while(counter < index -1){
				temp = temp.next;
				counter++;
			}
			currentNode.next = temp.next;
			temp.next = currentNode;			
		}		
		return head;
	}
	
	public int noOfNodes(ListNode head){
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
	
	public void remove(ListNode node){
		
		ListNode temp = node.next;
		node.value = temp.value;
		node.next = temp.next.next;
		temp = null;
	}
}

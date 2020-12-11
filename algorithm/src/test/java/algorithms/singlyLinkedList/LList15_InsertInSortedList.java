package algorithms.singlyLinkedList;

import org.testng.annotations.*;

public class LList15_InsertInSortedList {

	@Test
	public void test(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(20));
		list.push(new ListNode(30));
		list.push(new ListNode(40));
		list.push(new ListNode(50));
		list.push(new ListNode(60));
		list.push(new ListNode(70));
		list.push(new ListNode(80));
		list.push(new ListNode(90));
		list.push(new ListNode(100));
		
		ListNode currentNode = list.head;
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
	
	public ListNode insertInSortedList(ListNode head, int value){
		ListNode newNode = new ListNode(value);
		if(head == null){
			head = newNode;
		}else{
			ListNode currentNode = head;
			ListNode previousNode = head;
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

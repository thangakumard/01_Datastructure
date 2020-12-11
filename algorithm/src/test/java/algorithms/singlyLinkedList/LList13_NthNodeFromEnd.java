package algorithms.singlyLinkedList;

import org.testng.annotations.*;

import com.sun.jna.platform.win32.OaIdl.CURRENCY;

public class LList13_NthNodeFromEnd {

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
		System.out.println("NULL");
		System.out.println();		
		
		currentNode = nthNodeFromEnd(list.head,2);
		System.out.println("REQUIRED NODE IS :" + currentNode.value);
		System.out.println();
	}
	
	public ListNode nthNodeFromEnd(ListNode head,int n){
		if(head == null || n <= 0){
			return null;
		}
		else{
			ListNode currentNode = head;
			ListNode refNode = head;
			int count = 0;		
			while(count < n && refNode != null){
				refNode = refNode.next;
				count++;
			}			
			while(refNode != null){
				refNode = refNode.next;
				currentNode = currentNode.next;
			}			
			return currentNode;
		}	
	}
}

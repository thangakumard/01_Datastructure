package singlyLinkedList;

import org.testng.annotations.*;

import com.sun.jna.platform.win32.OaIdl.CURRENCY;

public class LList13_NthNodeFromEnd {

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
		System.out.println("NULL");
		System.out.println();		
		
		currentNode = nthNodeFromEnd(list.head,2);
		System.out.println("REQUIRED NODE IS :" + currentNode.value);
		System.out.println();
	}
	
	public Node nthNodeFromEnd(Node head,int n){
		if(head == null || n <= 0){
			return null;
		}
		else{
			Node currentNode = head;
			Node refNode = head;
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

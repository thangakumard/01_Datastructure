package singlyLinkedList;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LList01_MiddleNode {

	@Test
	public void getMiddleNode(){
		
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
		
		Node middle = findMiddleNode(list.head);
		System.out.println(middle.value);
		Assert.assertEquals(middle.value, 60);
	}
	
	public Node findMiddleNode(Node head){
		if(head == null){
			return null;
		}
		Node fast = head;
		Node slow = head;	
		while(fast != null && fast.next != null ){
			fast = fast.next.next;//30;50;70;90;null
			slow = slow.next;//20;30;40;50;60			
		}
		return slow;
	}
	
}

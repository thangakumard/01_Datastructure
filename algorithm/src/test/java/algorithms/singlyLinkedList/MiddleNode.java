package algorithms.singlyLinkedList;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MiddleNode {

	@Test
	public void getMiddleNode(){
		
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
		
		ListNode middle = findMiddleNode(list.head);
		System.out.println(middle.value);
		Assert.assertEquals(middle.value, 60);
	}
	
	public ListNode findMiddleNode(ListNode head){
		ListNode fast = head;
		ListNode slow = head;
		ListNode currentNode = head;
		
		while(currentNode != null && currentNode.next != null ){
			fast = fast.next.next;//30;50;70;90;null
			slow = slow.next;//20;30;40;50;60
			currentNode = fast;
		}
		return slow;
	}
	
}

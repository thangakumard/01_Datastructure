package algorithms.singlyLinkedList;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LList01_MiddleNode {

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
		Assert.assertEquals(middle.value, 50);
	}
	
	public ListNode findMiddleNode(ListNode head){
		if(head == null){
			return null;
		}
		ListNode fast = head;
		ListNode slow = head;	
		while(fast != null && fast.next != null ){
			fast = fast.next.next;//30;50;70;90;null
			if(fast != null)
			slow = slow.next;//20;30;40;50;60			
		}
		return slow;
	}
	
}

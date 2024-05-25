package algorithms.singlyLinkedList.rabbit;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/****
 * https://leetcode.com/problems/middle-of-the-linked-list/
 */
public class Rabbit01_MiddleNode {

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
		
		ListNode middle = middleNode(list.head);
		Assertions.assertThat(middle.value).isEqualTo(60);
	}


	public ListNode middleNode(ListNode head) {
		ListNode slowNode = head, fastNode = head;

		while(fastNode != null && fastNode.next != null){
			slowNode = slowNode.next;
			fastNode = fastNode.next.next;
		}
		return slowNode;
	}

//	public ListNode middleNode(ListNode head) {
//		ListNode slowNode = head, fastNode = head;
//
//		while(fastNode != null && fastNode.next != null){
//			slowNode = slowNode.next;
//			fastNode = fastNode.next.next;
//		}
//		return slowNode;
//	}
	
}

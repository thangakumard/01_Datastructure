package algorithms.singlyLinkedList;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.annotations.Test;

public class LList03_PrintNodesInReverse {

	@Test
	public void test()
	{
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(1));
		list.push(new ListNode(2));
		list.push(new ListNode(3));
		list.push(new ListNode(4));
		list.push(new ListNode(5));
		list.push(new ListNode(6));
		list.push(new ListNode(7));
		
		printInReverse(list.head);
	}
	
	public void printInReverse(ListNode node) {
		if(node.next == null) {
			return;
		}
		printInReverse(node.next);
		System.out.println(node.value);
	}
	
}

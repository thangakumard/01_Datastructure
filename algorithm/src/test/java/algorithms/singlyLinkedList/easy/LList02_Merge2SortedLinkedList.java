package algorithms.singlyLinkedList.easy;

import org.testng.annotations.*;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/***
 * https://leetcode.com/problems/merge-two-sorted-lists/description/
 */
public class LList02_Merge2SortedLinkedList {
	
	@Test
	public void test(){
		
		SinglyLinkedList list1 = new SinglyLinkedList();
		list1.push(new ListNode(10));		
		list1.push(new ListNode(30));
		list1.push(new ListNode(40));		
		list1.push(new ListNode(60));		
		list1.push(new ListNode(90));
		
		
		SinglyLinkedList list2 = new SinglyLinkedList();		
		list2.push(new ListNode(20));		
		list2.push(new ListNode(50));		
		list2.push(new ListNode(70));
		list2.push(new ListNode(80));		
		list2.push(new ListNode(100));
		
		ListNode newHead = mergeTwoLists(list1.head, list2.head);
		
		while(newHead != null){
			System.out.print(newHead.value + " --> ");
			newHead = newHead.next;
		}
		System.out.print("NULL");
		System.out.println();
	}

	public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
		if(list1 == null)
			return list2;
		if(list2 == null)
			return list1;

		ListNode root = new ListNode(0);
		ListNode currentNode = root;

		while(list1 != null && list2 != null){
			if(list1.value < list2.value){
				currentNode.next = list1;
				list1 = list1.next;
			}
			else {
				currentNode.next = list2;
				list2 = list2.next;
			}
			currentNode = currentNode.next;
		}

		while(list1 != null){
			currentNode.next = list1;
			list1 = list1.next;
			currentNode = currentNode.next;
		}
		while(list2 != null){
			currentNode.next = list2;
			list2 = list2.next;
			currentNode = currentNode.next;
		}
		return root.next;
	}
}

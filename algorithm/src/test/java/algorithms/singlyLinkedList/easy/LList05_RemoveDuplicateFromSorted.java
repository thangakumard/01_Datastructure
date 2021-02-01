package algorithms.singlyLinkedList.easy;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

public class LList05_RemoveDuplicateFromSorted {

	/*
	 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
	 * 
	 */

	@Test
	public void test(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(10));
		list.push(new ListNode(30));
		list.push(new ListNode(30));
		list.push(new ListNode(50));
		list.push(new ListNode(60));
		list.push(new ListNode(70));
		list.push(new ListNode(70));
		list.push(new ListNode(70));
		list.push(new ListNode(100));		
		
		ListNode currentNode = list.head;
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
		
		currentNode = removeDuplicateFromSortedLinkedList(list.head);		
		while(currentNode != null){
			System.out.print(currentNode.value + " -->");
			currentNode = currentNode.next;
		}
		System.out.println("NULL");
		System.out.println();
	}
	
	/*
	 * Time complexity O(n)
	 * Space Complexity O(1)
	 */
	private ListNode removeDuplicateFromSortedLinkedList(ListNode head){
		if(head == null){
			return null;
		}
		ListNode currentNode = head;		
		while(currentNode != null && currentNode.next != null){
			if(currentNode.value == currentNode.next.value){
				currentNode.next = currentNode.next.next;
			}
			else
			{
				currentNode = currentNode.next;
			}		
			
		}
		
		return head;
	}
}

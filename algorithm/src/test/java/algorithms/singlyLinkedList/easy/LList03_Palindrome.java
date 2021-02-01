package algorithms.singlyLinkedList.easy;

import org.testng.Assert;
import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;

public class LList03_Palindrome {

	ListNode leftPointer = null;
	
	@Test
	public void validatePalindrom_BestApproach(){
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode('A'));
		list.push(new ListNode('B'));
		list.push(new ListNode('A'));
		list.push(new ListNode('B'));
		list.push(new ListNode('A'));
		list.push(new ListNode('B'));
		list.push(new ListNode('A'));
		
		leftPointer = list.head;
		System.out.println("validatePalindrom_BestApproach :" + recursiveCheck(list.head));
	}
	
	//Solution 1
	private boolean recursiveCheck(ListNode currentNode){
		if(currentNode == null)
			return true;
		
		boolean isSublistPalindrome = recursiveCheck(currentNode.next);
		if(!isSublistPalindrome){
			return false;
		}
		
		boolean isEqual = leftPointer.charValue == currentNode.charValue;
		leftPointer = leftPointer.next;
		return isEqual;
	}
	
	@Test
	public void validatePalindrome_Approach2() {

		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode('A'));
		list.push(new ListNode('B'));
		list.push(new ListNode('A'));
		list.push(new ListNode('B'));
		list.push(new ListNode('A'));
		list.push(new ListNode('B'));
		list.push(new ListNode('A'));
		
		ListNode middle = list.head;
		ListNode jumper = list.head;
		ListNode head2 = null;

		while (jumper != null && jumper.next != null) {
			jumper = jumper.next.next;
			middle = middle.next;
		}
		if (jumper != null) {
			head2 = middle.next; // For Odd number of nodes, we have to ignore the middle node
		} else {
			head2 = middle;   // For even number of nodes
		}

		ListNode reversed = getReverseList(head2);

		Assert.assertEquals(isPalindrome(list.head, reversed), true);

	}

	//Solution 2
	public boolean isPalindrome(ListNode head1, ListNode head2) {

		Boolean isValid = true;

		ListNode temp1 = head1;
		ListNode temp2 = head2;

		while (temp1 != null && temp2 != null) {

			if (temp1.charValue == temp2.charValue) {

				temp1 = temp1.next;
				temp2 = temp2.next;
			} else {
				return false;
			}
		}

	

		return isValid;
	}

	public ListNode getReverseList(ListNode head) {

		ListNode prev = null;
		ListNode next = null;
		ListNode current = head;

		while (current != null) {
			next = current.next;
			current.next = prev;

			prev = current;
			current = next;

		}

		return prev;
	}

}

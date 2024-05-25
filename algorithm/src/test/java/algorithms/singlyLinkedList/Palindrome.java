package algorithms.singlyLinkedList;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Palindrome {

	@Test
	public void validatePalindrome() {

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
			head2 = middle.next;
		} else {
			head2 = middle;
		}

		ListNode reversed = getReverseList(head2);

		Assert.assertEquals(isPalindrome(list.head, reversed), true);

	}

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

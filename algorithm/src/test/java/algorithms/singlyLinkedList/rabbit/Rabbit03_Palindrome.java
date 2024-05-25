package algorithms.singlyLinkedList.rabbit;

import org.testng.Assert;
import org.testng.annotations.Test;

import algorithms.singlyLinkedList.base.ListNode;
import algorithms.singlyLinkedList.base.SinglyLinkedList;

/**
 * https://leetcode.com/problems/palindrome-linked-list
 * Given the head of a singly linked list, return true if it is a palindrome or false otherwise.
 *
 * Example 1:
 * Input: head = [1,2,2,1]
 * Output: true
 *
 * Example 2:
 * Input: head = [1,2]
 * Output: false
 *
 * Constraints:
 * The number of nodes in the list is in the range [1, 105].
 * 0 <= Node.val <= 9
 * Follow up: Could you do it in O(n) time and O(1) space?
 */
public class Rabbit03_Palindrome {

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
		
		ListNode secondHalf = getMiddleNode(list.head);
		ListNode reversed = getReverseList(secondHalf);
		Assert.assertEquals(isPalindrome(list.head, reversed), true);
	}
	private ListNode getMiddleNode(ListNode head){
		ListNode slowNode = head, fastNode = head;
		while(fastNode != null && fastNode.next != null){
			slowNode = slowNode.next;
			fastNode = fastNode.next.next;
		}
		return slowNode;
	}
	private ListNode getReverseList(ListNode head){
		ListNode next = null, previous = null, currentNode = head;
		while(currentNode != null){
			next = currentNode.next;
			currentNode.next = previous;

			previous = currentNode;
			currentNode = next;
		}
		return previous;
	}

	private boolean isPalindrome(ListNode list1 , ListNode list2){
		ListNode temp1 = list1, temp2 = list2;

		while(temp1 != null && temp2 != null){
			if(temp1.charValue != temp2.charValue)
				return false;

			temp1 = temp1.next;
			temp2 = temp2.next;
		}
		return true;
	}

}

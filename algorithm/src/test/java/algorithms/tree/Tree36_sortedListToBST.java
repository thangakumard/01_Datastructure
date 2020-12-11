package algorithms.tree;


import java.util.Stack;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.ListNode;
import algorithms.singlyLinkedList.SinglyLinkedList;


public class Tree36_sortedListToBST {

	@Test
	public void test() {
		
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new ListNode(10));
		list.push(new ListNode(20));
		list.push(new ListNode(30));
		list.push(new ListNode(40));
		list.push(new ListNode(50));
		list.push(new ListNode(60));

		TreeNode root = buildBSTFromList(list.head);
		printInOrder(root);
		
		
	}
	
	private TreeNode buildBSTFromList(ListNode head) {
		
		if(head == null) return null;
		
		ListNode middleNode = getmiddleNode(head);
		
		TreeNode root = new TreeNode(middleNode.value);
		if(head == middleNode)
			return root;
		root.left = buildBSTFromList(head);
		root.right = buildBSTFromList(middleNode.next);
		
		return root;
		
	}
	
	private ListNode getmiddleNode(ListNode head) {
		ListNode slow = head;
		ListNode fast = head;
		ListNode previous = slow;
		
		if(fast.next != null && fast.next.next != null) {
			previous = slow;
			slow = slow.next;
			fast = fast.next.next;
		}
		
		if(previous != null) {
			previous.next = null;
		}
		
		return slow;
	}
	
	private void printInOrder(TreeNode root) {
		
		Stack<TreeNode> stack = new Stack<TreeNode>();
		TreeNode current = null;
		while(true) {
			if(root != null) {
				stack.push(root);
				root = root.left;
			}else {
				if(stack.isEmpty()) break;
				
				current = stack.pop();
				System.out.println(current.data);
				root = current.right;
			}
			
		}
		
	}
	
}

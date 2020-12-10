package algorithms.tree;


import java.util.Stack;

import org.testng.annotations.Test;

import algorithms.singlyLinkedList.Node;
import algorithms.singlyLinkedList.SinglyLinkedList;


public class Tree36_sortedListToBST {

	@Test
	public void test() {
		
		SinglyLinkedList list = new SinglyLinkedList();
		list.push(new Node(10));
		list.push(new Node(20));
		list.push(new Node(30));
		list.push(new Node(40));
		list.push(new Node(50));
		list.push(new Node(60));

		TreeNode root = buildBSTFromList(list.head);
		printInOrder(root);
		
		
	}
	
	private TreeNode buildBSTFromList(Node head) {
		
		if(head == null) return null;
		
		Node middleNode = getmiddleNode(head);
		
		TreeNode root = new TreeNode(middleNode.value);
		if(head == middleNode)
			return root;
		root.left = buildBSTFromList(head);
		root.right = buildBSTFromList(middleNode.next);
		
		return root;
		
	}
	
	private Node getmiddleNode(Node head) {
		Node slow = head;
		Node fast = head;
		Node previous = slow;
		
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

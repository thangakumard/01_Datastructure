package tree;

import java.util.*;

import org.testng.annotations.Test;

public class Tree07_PreOrderIterative {
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the nodes 
    				1
    		2				3
    	4		5

		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
		preOrderTraversal(tree.root);
	}
	
	ArrayDeque<Node> stack = new ArrayDeque<Node>();
	private void preOrderTraversal(Node root){
		Node currentNode = null;
		if(root == null) return;
		stack.addFirst(root);
		while(!stack.isEmpty()){
			currentNode = stack.pop();
			System.out.print(currentNode.data);
			
			if(currentNode.right != null){
				stack.addFirst(currentNode.right);
			}
			
			if(currentNode.left != null){
				stack.addFirst(currentNode.left);
			}
				
		}
		
	}
}

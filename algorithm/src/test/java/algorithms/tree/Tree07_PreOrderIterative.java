package algorithms.tree;

import java.util.*;

import org.testng.annotations.Test;

public class Tree07_PreOrderIterative {
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				1
    		2				3
    	4		5

		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		preOrderTraversal(tree.root);
	}
	
	ArrayDeque<TreeNode> stack = new ArrayDeque<TreeNode>();
	private void preOrderTraversal(TreeNode root){
		TreeNode currentTreeNode = null;
		if(root == null) return;
		stack.addFirst(root);
		while(!stack.isEmpty()){
			currentTreeNode = stack.pop();
			System.out.print(currentTreeNode.data);
			
			if(currentTreeNode.right != null){
				stack.addFirst(currentTreeNode.right);
			}
			
			if(currentTreeNode.left != null){
				stack.addFirst(currentTreeNode.left);
			}
				
		}
		
	}
}

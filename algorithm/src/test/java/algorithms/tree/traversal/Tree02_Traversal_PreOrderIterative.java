package algorithms.tree.traversal;

import java.util.*;
/*
 * https://leetcode.com/problems/binary-tree-preorder-traversal/
 */

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class Tree02_Traversal_PreOrderIterative {
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
	
	ArrayDeque<TreeNode> stack = new ArrayDeque<>();
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

package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;
import algorithms.tree.BinarySearchTree.BST02_BSTFromSortedArray;

/*
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
 * 
 */
public class BinaryTree09_PushAllNodestoRight {
	
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
		tree.root.left.right.right = new TreeNode(7);
		
		
		flatten(tree.root);
	}

	public void flatten(TreeNode root) {
		if (root != null) {
			Stack<TreeNode> stackNodes = new Stack<TreeNode>();
			stackNodes.push(root);

			while (!stackNodes.isEmpty()) {
				TreeNode currentNode = stackNodes.pop();

				if (currentNode.right != null) {
					stackNodes.push(currentNode.right);
				}

				if (currentNode.left != null) {
					stackNodes.push(currentNode.left);
				}

				if (!stackNodes.isEmpty()) {
					currentNode.right = stackNodes.peek();
				}

				currentNode.left = null; /* Important step ***/
			}
		}
	}
}

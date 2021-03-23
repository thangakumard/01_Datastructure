package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

public class BST03_InorderSuccessorBST {
	/*
	 * https://leetcode.com/problems/inorder-successor-in-bst/
	 */
	public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
		if (root == null)
			return null;
		if (root.data <= p.data) { //move right until root.data <= p.data
			return inorderSuccessor(root.right, p);
		} else {
			TreeNode left = inorderSuccessor(root.left, p); //The root node in the inorder trasersal of the root.left is the result
			return left != null ? left : root;
		}
	}
}

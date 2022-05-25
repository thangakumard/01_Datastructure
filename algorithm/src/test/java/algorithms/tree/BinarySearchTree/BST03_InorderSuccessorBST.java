package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

public class BST03_InorderSuccessorBST {
	/*
	 * https://leetcode.com/problems/inorder-successor-in-bst/
   
    The successor of a node p is the node with the smallest key greater than p.val.

    Input: root = [2,1,3], p = 1
    Output: 2
    Explanation: 1's in-order successor node is 2. Note that both p and the return value is of TreeNode type.

    Input: root = [5,3,6,2,4,null,null,1], p = 6
    Output: null
    Explanation: There is no in-order successor of the current node, so the answer is null.
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

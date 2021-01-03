package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/inorder-successor-in-bst/discuss/72653/Share-my-Java-recursive-solution
 */
public class BST04_InorderPredecessorBST {

	public TreeNode predecessor(TreeNode root, TreeNode p) {
		if (root == null)
			return null;

		if (root.data >= p.data) {
			return predecessor(root.left, p);
		} else {
			TreeNode right = predecessor(root.right, p);
			return (right != null) ? right : root;
		}
	}
}

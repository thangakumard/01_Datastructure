package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/inorder-successor-in-bst/discuss/72653/Share-my-Java-recursive-solution
 */
public class BST04_InorderPredecessorBST {

	public TreeNode predecessor(TreeNode root, TreeNode p) {
		if (root == null)
			return null;

		if (root.data >= p.data) { //move to left until root.data >= p.data
			return predecessor(root.left, p);
		} else {
			TreeNode right = predecessor(root.right, p); // root node will be the result if it is not null
			return (right != null) ? right : root;
		}
	}
}

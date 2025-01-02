package algorithms.tree.BinarySearchTree;

/*********
 * https://leetcode.com/problems/merge-two-binary-trees/
 You are given two binary trees root1 and root2.

Imagine that when you put one of them to cover the other, some nodes of the two trees are overlapped while the others are not. 
You need to merge the two trees into a new binary tree. 
The merge rule is that if two nodes overlap, then sum node values up as the new value of the merged node. 
Otherwise, the NOT null node will be used as the node of the new tree.

Return the merged tree.

Note: The merging process must start from the root nodes of both trees.

 

Example 1:


Input: root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
Output: [3,4,5,5,4,null,7]
Example 2:

Input: root1 = [1], root2 = [1,2]
Output: [2,2]
 

Constraints:

The number of nodes in both trees is in the range [0, 2000].
-104 <= Node.val <= 104
 */

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BST08_MergeTwoBST {

	@Test
	public void mergeBST() {
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);

		BinaryTree t2 = new BinaryTree();
		t2.root = new TreeNode(1);
		t2.root.left = new TreeNode(2);
		t2.root.right = new TreeNode(3);
		t2.root.left.left = new TreeNode(4);
		t2.root.left.right = new TreeNode(5);

		TreeNode root = mergeTwoTrees(t1.root, t2.root);
		inorderTraversal(root);
	}

	/**
	 * Time: O(N)
	 * Space: O(N)
	 */
	private TreeNode mergeTwoTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null)
			return t2;
		if (t2 == null)
			return t1;

		t1.data += t2.data;
		t1.left = mergeTwoTrees(t1.left, t2.left);
		t1.right = mergeTwoTrees(t1.right, t2.right);

		return t1;
	}

	private void inorderTraversal(TreeNode root) {
		if (root == null)
			return;
		inorderTraversal(root.left);
		System.out.print(root.data + ",");
		inorderTraversal(root.right);
	}
}

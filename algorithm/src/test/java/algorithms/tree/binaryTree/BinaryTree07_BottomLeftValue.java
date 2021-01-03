package algorithms.tree.binaryTree;

import java.util.*;

import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/find-bottom-left-tree-value/
 * 
 */
public class BinaryTree07_BottomLeftValue {

	public int findBottomLeftValue(TreeNode root) {
		if (root == null)
			return -1;
		Queue<TreeNode> queueTreeNode = new LinkedList<>();
		queueTreeNode.add(root);
		while (!queueTreeNode.isEmpty()) {
			root = queueTreeNode.poll();
			if (root.right != null)
				queueTreeNode.add(root.right);
			if (root.left != null)
				queueTreeNode.add(root.left);
		}
		return root.data;
	}
}

package algorithms.tree.binaryTree;

import java.util.*;

import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
 * 
 */
public class BinaryTree09_PushAllNodestoRight {

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

				currentNode.left = null;
			}
		}
	}
}

package algorithms.tree.binaryTree.bfs;

import java.util.LinkedList;
import java.util.Queue;

import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/cousins-in-binary-tree/
 * 
 * In a binary tree, the root node is at depth 0, and children of each depth k node are at depth k+1.

	Two nodes of a binary tree are cousins if they have the same depth, but have different parents.
	
	We are given the root of a binary tree with unique values, and the values x and y of two different nodes in the tree.
	
	Return true if and only if the nodes corresponding to the values x and y are cousins.
	
	 
	
	Example 1:
	
	
	Input: root = [1,2,3,4], x = 4, y = 3
	Output: false
	Example 2:
	
	
	Input: root = [1,2,3,null,4,null,5], x = 5, y = 4
	Output: true
	Example 3:
	
	
	
	Input: root = [1,2,3,null,4], x = 2, y = 3
	Output: false
	 
	
	Constraints:
	
	The number of nodes in the tree will be between 2 and 100.
	Each node has a unique integer value from 1 to 100.
 */

public class BinaryTree12_IsCousins {

	public boolean isCousins(TreeNode root, int x, int y) {
		if (root == null)
			return false;

		Queue<TreeNode> queueNodes = new LinkedList<>();
		queueNodes.add(root);
		boolean xfound = false, yfound = false, isCousins = false;

		while (!queueNodes.isEmpty()) {
			xfound = false;
			yfound = false;
			isCousins = false;
			int size = queueNodes.size();

			for (int i = 0; i < size; i++) {
				TreeNode currentNode = queueNodes.poll();
				
				//Check x and y has the same parent - if yes return FALSE
				if (currentNode.left != null && currentNode.right != null) {
					if (currentNode.left.data == x && currentNode.right.data == y)
						return false;
					if (currentNode.left.data == y && currentNode.right.data == x)
						return false;
				}

				if (currentNode.data == x)
					xfound = true;
				if (currentNode.data == y)
					yfound = true;

				//If both the values are found return true
				if (xfound && yfound)
					return true;
				
				// If one of the value is found but if the queue is empty return FALSE;
				if (xfound || yfound) {
					if (queueNodes.isEmpty())
						return false;
				}

				if (currentNode.left != null) {
					queueNodes.add(currentNode.left);
				}
				if (currentNode.right != null) {
					queueNodes.add(currentNode.right);
				}

			}
		}

		return isCousins;
	}

}

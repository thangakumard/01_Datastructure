package algorithms.tree.BinarySearchTree;

import java.util.Stack;

import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/validate-binary-search-tree/
 * 
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).

	A valid BST is defined as follows:
	
	The left subtree of a node contains only nodes with keys less than the node's key.
	The right subtree of a node contains only nodes with keys greater than the node's key.
	Both the left and right subtrees must also be binary search trees.
	 
	
	Example 1:
	
	
	Input: root = [2,1,3]
	Output: true
	Example 2:
	
	
	Input: root = [5,1,4,null,null,3,6]
	Output: false
	Explanation: The root node's value is 5 but its right child's value is 4.
	 
	
	Constraints:
	
	The number of nodes in the tree is in the range [1, 104].
	-231 <= Node.val <= 231 - 1
 */
public class BST05_ValidateBinarySearchTree {
	/*************** NOTE ********
	 *  Think about test cases with same node value [1,1]
	 *  Think about test cases with node value = Integer.MIN_VALUE
	*************/

	public boolean isValidBST(TreeNode root) {
		if (root == null)
			return true;
		Stack<TreeNode> stackNodes = new Stack<>();
		long previousValue = Long.MIN_VALUE;
		while (true) {
			if (root != null) {
				stackNodes.push(root);
				root = root.left;
			} else {
				if (stackNodes.isEmpty())
					break;

				TreeNode temp = stackNodes.pop();
				if (previousValue >= temp.data) {
					return false;
				}
				previousValue = temp.data;
				root = temp.right;
			}
		}
		return true;
	}
}

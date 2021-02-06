package algorithms.tree.binaryTree;

import java.util.*;

import algorithms.tree.TreeNode;

/***
 * https://leetcode.com/problems/binary-tree-paths/
 * 
 * Given a binary tree, return all root-to-leaf paths.
 * 
 * Note: A leaf is a node with no children.
 * 
 * Example:
 * 
 * Input:
 * 
 * 1 / \ 2 3 \ 5
 * 
 * Output: ["1->2->5", "1->3"]
 * 
 * Explanation: All root-to-leaf paths are: 1->2->5, 1->3
 *
 */
public class BinaryTree31_BinaryTreePaths {

	public List<String> binaryTreePaths(TreeNode root) {
		List<String> result = new ArrayList<>();
		getPaths(root, result, "");
		return result;
	}

	private void getPaths(TreeNode root, List<String> result, String path) {
		if (root == null)
			return;

		path += path.isEmpty() ? String.valueOf(root.data) : "->" + String.valueOf(root.data);

		if (root.left == null && root.right == null) {
			result.add(path);
			return;
		} else {
			getPaths(root.left, result, path);
			getPaths(root.right, result, path);
		}
	}
}

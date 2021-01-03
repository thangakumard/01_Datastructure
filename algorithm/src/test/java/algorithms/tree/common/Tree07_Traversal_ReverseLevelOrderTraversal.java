package algorithms.tree.common;

import java.util.*;

import algorithms.tree.TreeNode;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/N81zMVZJXoz
 * https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
 */

public class Tree07_Traversal_ReverseLevelOrderTraversal {

	public static List<List<Integer>> traverse(TreeNode root) {
		List<List<Integer>> result = new LinkedList<List<Integer>>();
		Queue<TreeNode> queueNodes = new LinkedList<>();
		queueNodes.add(root);

		while (!queueNodes.isEmpty()) {
			List<Integer> levelNodes = new ArrayList<>();
			int size = queueNodes.size();

			for (int i = 0; i < size; i++) {
				TreeNode currentNode = queueNodes.poll();
				levelNodes.add(currentNode.data);
				if (currentNode.left != null)
					queueNodes.add(currentNode.left);
				if (currentNode.right != null)
					queueNodes.add(currentNode.right);
			}
			result.add(0, levelNodes);
		}
		return result;
	}
}

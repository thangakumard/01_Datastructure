package algorithms.tree.binaryTree.bfs;

import java.util.*;

import algorithms.tree.TreeNode;

public class BinaryTree06_AverageLevelsBinaryTree {

	/*
	 * https://leetcode.com/problems/average-of-levels-in-binary-tree/
	 */

	/***
	 * Time: O(n) Each node is enqueued and dequeued exactly once
	 * Space: O(w) Where w is the max width of the tree (widest level);
	 * queue never holds more than one level at a time.
	 * Worst case (perfect tree) w ≈ n/2, so O(n)
	 * @param root
	 * @return
	 */

	public List<Double> averageOfLevels(TreeNode root) {

		if (root == null)
			return null;

		Queue<TreeNode> queueNode = new LinkedList<>();
		queueNode.add(root);
		List<Double> result = new ArrayList<>();

		while (!queueNode.isEmpty()) {

			int size = queueNode.size();
			long sum = 0;
			for (int i = 0; i < size; i++) {
				TreeNode temp = queueNode.poll();
				sum += temp.data;

				if (temp.left != null)
					queueNode.add(temp.left);

				if (temp.right != null)
					queueNode.add(temp.right);
			}
			result.add((double) sum / size);

		}

		return result;
	}
}

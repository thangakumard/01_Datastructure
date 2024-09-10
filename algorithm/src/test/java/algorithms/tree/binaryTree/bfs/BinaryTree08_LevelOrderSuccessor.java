package algorithms.tree.binaryTree.bfs;

import java.util.*;

import algorithms.tree.TreeNode;
/*
 * https://www.educative.io/module/lesson/data-structures-in-java/q2zwZoKvmmk
 */

public class BinaryTree08_LevelOrderSuccessor {

	public TreeNode findSuccessor(TreeNode root, int key) {
		if (root == null)
			return null;
		Queue<TreeNode> queueNodes = new LinkedList<>();
		queueNodes.add(root);
		while (!queueNodes.isEmpty()) {
			TreeNode currentNode = queueNodes.poll();

			if (currentNode.left != null)
				queueNodes.add(currentNode.left);
			if (currentNode.right != null)
				queueNodes.add(currentNode.right);
			if (currentNode.data == key)
				break;
		}
		return queueNodes.peek();
	}
}

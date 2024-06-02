package algorithms.tree.binaryTree.levelOrder;

import java.util.*;

/*
 * https://leetcode.com/problems/populating-next-right-pointers-in-each-Node/
 */
public class BinaryTree11_PopulatingNextRightPointers {

	class Node {
		public int val;
		public Node left;
		public Node right;
		public Node next;

		public Node() {
		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, Node _left, Node _right, Node _next) {
			val = _val;
			left = _left;
			right = _right;
			next = _next;
		}
	};

	public Node connect(Node root) {
		if (root == null)
			return null;
		Queue<Node> queueNodes = new LinkedList<>();
		queueNodes.add(root);

		while (!queueNodes.isEmpty()) {

			int size = queueNodes.size();
			for (int i = 0; i < size; i++) {
				Node currentNode = queueNodes.poll();

				if (i < size - 1) {
					currentNode.next = queueNodes.peek();
				}

				if (currentNode.left != null)
					queueNodes.add(currentNode.left);
				if (currentNode.right != null)
					queueNodes.add(currentNode.right);
			}
		}
		return root;
	}
}

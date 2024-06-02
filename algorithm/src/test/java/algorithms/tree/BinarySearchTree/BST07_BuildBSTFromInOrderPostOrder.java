package algorithms.tree.BinarySearchTree;

import java.util.HashMap;
import java.util.Stack;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/******
 * 
 * https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
 *
 * Given inorder and postorder traversal of a tree, construct the binary tree.
 * 
 * Note: You may assume that duplicates do not exist in the tree.
 * 
 * For example, given
 * 
 * inorder = [9,3,15,20,7] postorder = [9,15,7,20,3]
 */
public class BST07_BuildBSTFromInOrderPostOrder {

	@Test
	public void buildBinaryTree() {
		BinaryTree tree = new BinaryTree();
		int[] inorder = new int[] { 9, 3, 15, 20, 7 };
		int[] postorder = new int[] { 9, 15, 7, 20, 3 };

		TreeNode root = buildTree(inorder, postorder);
		inorderIterative(root);
	}

	public void inorderIterative(TreeNode root) {
		if (root == null)
			return;

		Stack<TreeNode> stack = new Stack<>();

		while (true) {
			if (root != null) {
				stack.push(root);
				root = root.left;
			} else {
				if (stack.isEmpty())
					break;

				root = stack.pop();
				System.out.print(root.data + " ");
				root = root.right;
			}
		}
	}

	/********** Pseudocode *************
	 * Keep In-order node values and index in the HashMap
	 * start with postorder array's last index => which is root node of the tree
	 * Find the index of the root node in the inorder traversal using the HashMap we built
	 * root.right will be all the elements right of the root index in the inorder array
	 * root.left will be all the elements left of the root index in the inorder array 
	 *  
	 * 
	 * */
	
	
	HashMap<Integer, Integer> inorderMap = new HashMap<Integer, Integer>();
	int postIndex = 0;

	private TreeNode buildTree(int[] inorder, int[] postorder) {

		for (int i = 0; i < inorder.length; i++) {
			inorderMap.put(inorder[i], i);
		}
		postIndex = postorder.length - 1;
		return helper(postorder, 0, inorder.length - 1);
	}

	private TreeNode helper(int[] postorder, int start, int end) {
		if (start > end)
			return null;

		TreeNode currentNode = new TreeNode(postorder[postIndex]);
		int rootIndex = inorderMap.get(postorder[postIndex]);
		postIndex--;

		currentNode.right = helper(postorder, rootIndex + 1, end);
		currentNode.left = helper(postorder, start, rootIndex - 1);
		return currentNode;
	}

}

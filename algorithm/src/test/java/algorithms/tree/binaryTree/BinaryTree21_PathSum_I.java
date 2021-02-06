package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/******
 * https://leetcode.com/problems/path-sum/
 * 
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \      \
7    2      1
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 */
public class BinaryTree21_PathSum_I {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	      5
	     / \
	    4   8
	   /   / \
	  11  13  4
	 /  \      \
	7    2      1
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(5);
		tree.root.left = new TreeNode(4);
		tree.root.right = new TreeNode(8);
		tree.root.left.left = new TreeNode(11);
		tree.root.left.left.left = new TreeNode(7);
		tree.root.left.left.right = new TreeNode(2);

		tree.root.right.left = new TreeNode(13);
		tree.root.right.right = new TreeNode(4);
		tree.root.right.right.right = new TreeNode(1);
 
		System.out.println(hasPathSum(tree.root, 22));
	}
	
	public boolean hasPathSum(TreeNode root, int sum) {
		if (root == null)
			return false;

		if (root.left == null && root.right == null && sum - root.data == 0) {
			return true;
		}

		return hasPathSum(root.left, sum - root.data) || hasPathSum(root.right, sum - root.data);
	}

}

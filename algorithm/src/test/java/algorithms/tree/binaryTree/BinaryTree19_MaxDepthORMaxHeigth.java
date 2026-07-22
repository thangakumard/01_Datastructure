package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * 
 */
public class BinaryTree19_MaxDepthORMaxHeigth {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				100
	    		50					200
	    	20		70       150			
	    				80							
	    			75       85
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(100);
		tree.root.left = new TreeNode(50);
		tree.root.right = new TreeNode(200);
		tree.root.left.left = new TreeNode(20);
		tree.root.left.right = new TreeNode(70);
		tree.root.left.left.right = new TreeNode(30);
		tree.root.left.right.right = new TreeNode(80);
		tree.root.left.right.right.left = new TreeNode(75);
		tree.root.left.right.right.right = new TreeNode(85);
		tree.root.right.left = new TreeNode(150);

 
		System.out.println(maxDepth(tree.root));
	}

	/***
	 * Time complexity:
	 * ================
	 * O(N), where N is the number of nodes.
	 * Space complexity:
	 * =================
	 * Worst case: O(N) => For completely unbalanced, e.g. each node has only left child node,
	 * Best Case: O(log N) => For completely balanced Tree
	 * @param root
	 * @return
	 */
	public int maxDepth(TreeNode root) {
		if (root == null)
			return 0;
		return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
	}
}

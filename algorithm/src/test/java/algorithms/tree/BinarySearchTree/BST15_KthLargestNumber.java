package algorithms.tree.BinarySearchTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/***
 *
 * https://www.geeksforgeeks.org/kth-largest-element-bst-using-constant-extra-space/
 *
 * With Space Complexity of O(1)
 */
public class BST15_KthLargestNumber {

	
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				100
	    		50					200
	    	20		70       150			300
	    				80							400
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
		tree.root.right.right = new TreeNode(300);
		tree.root.right.right.right = new TreeNode(400);
 
		System.out.println(findNthHighestInBST(tree.root, 3).data);
	}
	
	public static int currentCount = 0;
	
	

	public static TreeNode findNthHighestInBST(TreeNode node, int n) {

		if (node == null) {
			return null;
		}

		TreeNode result = findNthHighestInBST(node.right, n);

		if (result != null) {
			return result;
		}

		currentCount++;
		if (n == currentCount) {
			return node;
		}

		result = findNthHighestInBST(node.left, n);

		if (result != null) {
			return result;
		}

		return null;
	}
}

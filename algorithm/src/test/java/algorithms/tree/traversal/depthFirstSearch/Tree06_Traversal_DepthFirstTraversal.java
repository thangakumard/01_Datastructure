package algorithms.tree.traversal.depthFirstSearch;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class Tree06_Traversal_DepthFirstTraversal {

	@Test
	public void test() {
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				10
	    		5				15
	    	2		8       13		20
	    				9				25
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(15);
		tree.root.left.left = new TreeNode(2);
		tree.root.left.right = new TreeNode(8);
		tree.root.left.right.right = new TreeNode(9);
		tree.root.right.left = new TreeNode(13);
		tree.root.right.right = new TreeNode(20);
		tree.root.right.right.right = new TreeNode(25);

		System.out.println("PreOrderTraversal :");
		PreOrderTraversal(tree.root);
		System.out.println("");System.out.println("InOrderTraversal :");
		InOrderTraversal(tree.root);
		System.out.println("");System.out.println("PostOrderTraversal :");
		PostOrderTraversal(tree.root);
	}

	public void PreOrderTraversal(TreeNode TreeNode) {
		if (TreeNode == null)
			return;

		// print the data of the tree
		System.out.print(TreeNode.data + " ");

		// then recur on the left subtree
		PreOrderTraversal(TreeNode.left);

		// then recur on the right subtree
		PreOrderTraversal(TreeNode.right);

	}

	public void InOrderTraversal(TreeNode TreeNode) {
		if (TreeNode == null)
			return;

		// then recur on the left subtree
		InOrderTraversal(TreeNode.left);

		// print the data of the tree
		System.out.print(TreeNode.data + " ");

		// then recur on the right subtree
		InOrderTraversal(TreeNode.right);

	}

	public void PostOrderTraversal(TreeNode TreeNode) {
		if (TreeNode == null)
			return;

		// then recur on the left subtree
		PostOrderTraversal(TreeNode.left);

		// then recur on the right subtree
		PostOrderTraversal(TreeNode.right);

		// print the data of the tree
		System.out.print(TreeNode.data + " ");

	}
}

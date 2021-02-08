package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree34_ConvertToMirrorBinaryTree {

	
	/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		5				15
    	2		8       13		20
    				9				25
		*/

	@Test
	public void convertToMirrorImage(){
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
		
		inOrderTraversal(tree.root);
		mirrorBinaryTree(tree.root);
		System.out.println(" ");
		inOrderTraversal(tree.root);
		
		
	}
	
	public  void mirrorBinaryTree(TreeNode root) {
	    if(root == null) return;

	    TreeNode temp = root.left;
	    root.left = root.right;
	    root.right = temp;

	    if(root.left != null) mirrorBinaryTree(root.left);
	    if(root.right != null) mirrorBinaryTree(root.right);
	  }
	
	private void inOrderTraversal(TreeNode root) {
		if(root == null) return;
		inOrderTraversal(root.left);
		System.out.print(root.data + " ");
		inOrderTraversal(root.right);
	}
}

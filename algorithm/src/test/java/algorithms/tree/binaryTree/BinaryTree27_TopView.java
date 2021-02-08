package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree27_TopView {
	
	/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		5				15
    	2		8       13		20
    				9				25
		*/

	@Test
	public void topView(){
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
		
		printTopView(tree.root);
	}
	
	public void printTopView(TreeNode node) {
		left_view(node.left);
		System.out.print(node.data + " ");
		right_view(node.right);
	}
	
	private void left_view(TreeNode node) {
		if(node == null) return;
		left_view(node.left);
		System.out.print(node.data + " ");
	}
	
	private void right_view(TreeNode node) {
		if(node == null) return;
		System.out.print(node.data + " ");
		right_view(node.right);
	}
}

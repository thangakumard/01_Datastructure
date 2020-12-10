package algorithms.tree;

import org.testng.annotations.Test;

public class Tree06_PreOrderRecursive {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				1
    		2				3
    	4		5
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		preorder(tree.root);
	}
	
	private void preorder(TreeNode root){
		if(root == null) return;
		System.out.print(root.data + " ");
		preorder(root.left);		
		preorder(root.right);
	}
}

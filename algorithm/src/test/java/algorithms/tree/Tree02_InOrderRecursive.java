package algorithms.tree;

import org.testng.annotations.Test;

public class Tree02_InOrderRecursive {

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
		inorder(tree.root);
	}
	
	private void inorder(TreeNode root){
		if(root == null) return;
		
		inorder(root.left);
		System.out.print(root.data + " ");
		inorder(root.right);
	}
}

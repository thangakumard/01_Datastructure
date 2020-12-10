package algorithms.tree;

import org.testng.annotations.Test;

public class Tree03_PostOrderRecursive {
	
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
		postOrderRecursive(tree.root);
	}
	
	void postOrderRecursive(TreeNode TreeNode){
		
		if(TreeNode == null) return;
		postOrderRecursive(TreeNode.left);
		
		postOrderRecursive(TreeNode.right);
		
		System.out.println(TreeNode.data);
		
	}

}

package algorithms.tree;

import org.testng.annotations.Test;

public class Tree06_PreOrderRecursive {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the nodes 
    				1
    		2				3
    	4		5
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
		preorder(tree.root);
	}
	
	private void preorder(Node root){
		if(root == null) return;
		System.out.print(root.data + " ");
		preorder(root.left);		
		preorder(root.right);
	}
}

package tree;

import org.testng.annotations.Test;

public class Tree02_InOrderRecursive {

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
		inorder(tree.root);
	}
	
	private void inorder(Node root){
		if(root == null) return;
		
		inorder(root.left);
		System.out.print(root.data + " ");
		inorder(root.right);
	}
}

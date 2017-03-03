package tree;

import org.testng.annotations.Test;

public class Tree10_HeightOfBST {

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
		int height = heightOfBST(tree.root);
		System.out.println("Height is : " + height);
	}
	
	int heightOfBST(Node root){
		if(root == null)
			return 0;
		
		int leftHeight = heightOfBST(root.left);
		int rightHeight = heightOfBST(root.right);
		return 1 + Math.max(leftHeight, rightHeight);
	}
}

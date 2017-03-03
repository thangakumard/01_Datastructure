package tree;

import org.testng.annotations.Test;

public class Tree03_PostOrderRecursive {
	
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
		postOrderRecursive(tree.root);
	}
	
	void postOrderRecursive(Node node){
		
		if(node == null) return;
		postOrderRecursive(node.left);
		
		postOrderRecursive(node.right);
		
		System.out.println(node.data);
		
	}

}

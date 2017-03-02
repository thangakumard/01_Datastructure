package tree;

import org.testng.annotations.Test;

public class DepthFirstTraversal {

	@Test
	public void test()
	{
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(1);
		
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
		
		PreOrderTraversal(tree.root);
		InOrderTraversal(tree.root);
		PostOrderTraversal(tree.root);
	}
	
	public void PreOrderTraversal(Node node){
		if(node == null)
			return;
		
		//print the data of the tree		
		System.out.println(node.data + " ");
		
		//then recur on the left subtree
		PreOrderTraversal(node.left);
		
		//then recur on the right subtree
		PreOrderTraversal(node.right);	
		
	}
	
	public void InOrderTraversal(Node node){
		if(node == null)
			return;
			
		//then recur on the left subtree
		InOrderTraversal(node.left);

		//print the data of the tree		
		System.out.println(node.data + " ");
		
		//then recur on the right subtree
		InOrderTraversal(node.right);	
		
	}
	
	public void PostOrderTraversal(Node node){
		if(node == null)
			return;
		
		//then recur on the left subtree
		PostOrderTraversal(node.left);
		
		//then recur on the right subtree
		PostOrderTraversal(node.right);	

		//print the data of the tree		
		System.out.println(node.data + " ");
		
	}
}

package algorithms.tree;

import org.testng.annotations.Test;

public class Tree09_DepthFirstTraversal {

	@Test
	public void test()
	{
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		
		PreOrderTraversal(tree.root);
		InOrderTraversal(tree.root);
		PostOrderTraversal(tree.root);
	}
	
	public void PreOrderTraversal(TreeNode TreeNode){
		if(TreeNode == null)
			return;
		
		//print the data of the tree		
		System.out.println(TreeNode.data + " ");
		
		//then recur on the left subtree
		PreOrderTraversal(TreeNode.left);
		
		//then recur on the right subtree
		PreOrderTraversal(TreeNode.right);	
		
	}
	
	public void InOrderTraversal(TreeNode TreeNode){
		if(TreeNode == null)
			return;
			
		//then recur on the left subtree
		InOrderTraversal(TreeNode.left);

		//print the data of the tree		
		System.out.println(TreeNode.data + " ");
		
		//then recur on the right subtree
		InOrderTraversal(TreeNode.right);	
		
	}
	
	public void PostOrderTraversal(TreeNode TreeNode){
		if(TreeNode == null)
			return;
		
		//then recur on the left subtree
		PostOrderTraversal(TreeNode.left);
		
		//then recur on the right subtree
		PostOrderTraversal(TreeNode.right);	

		//print the data of the tree		
		System.out.println(TreeNode.data + " ");
		
	}
}

package algorithms.tree;

import org.testng.annotations.Test;

public class Tree23_MergeTwoBST {
	
	@Test
	public void mergeBST(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		
		BinaryTree t2 = new BinaryTree();
		t2.root = new TreeNode(1);
		t2.root.left = new TreeNode(2);
		t2.root.right = new TreeNode(3);
		t2.root.left.left = new TreeNode(4);
		t2.root.left.right = new TreeNode(5);
		
		TreeNode root = mergeTwoTrees(t1.root, t2.root);
		inorderTraversal(root);
	}
	
	private TreeNode mergeTwoTrees(TreeNode t1, TreeNode t2){
		if(t1 == null)
			return t2;
		if(t2 == null)
			return t1;
		
		t1.data += t2.data;
		t1.left = mergeTwoTrees(t1.left, t2.left);
		t1.right = mergeTwoTrees(t1.right, t2.right);
		
		return t1;
	}
	
	private void inorderTraversal(TreeNode root){
		if(root == null)
			return;
		inorderTraversal(root.left);
		System.out.print(root.data + ",");
		inorderTraversal(root.right);
	}
}

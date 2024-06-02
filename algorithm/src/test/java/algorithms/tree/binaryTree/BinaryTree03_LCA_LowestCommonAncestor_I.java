package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * 
 */
public class BinaryTree03_LCA_LowestCommonAncestor_I {

	@Test
	public void test(){
			
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(3);
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(1);
		tree.root.left.left = new TreeNode(6);
		tree.root.left.right = new TreeNode(2);
		tree.root.left.right.left = new TreeNode(7);
		tree.root.left.right.right = new TreeNode(4);
		tree.root.right.left = new TreeNode(0);
		tree.root.right.right = new TreeNode(8);
		
		
		TreeNode lca1 = LCAOfBinaryTree(tree.root, 5,10);		
		System.out.println("LCAOfBinaryTree(tree.root, 5, 10) :" + lca1.data);
	}
	
	
	
	private TreeNode LCAOfBinaryTree(TreeNode root, int x, int y){		
		if(root == null)
			return null;
		
		if(root.data == x || root.data == y)
			return root;
		
		TreeNode left = LCAOfBinaryTree(root.left, x, y);
		TreeNode right = LCAOfBinaryTree(root.right, x, y);
		
		if(left != null && right != null)
			return root;
		
		return (left != null ? left : right); 
		
	}
}

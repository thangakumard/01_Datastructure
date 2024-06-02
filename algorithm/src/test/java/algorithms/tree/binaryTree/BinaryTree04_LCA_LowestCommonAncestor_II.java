package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-ii/
 * 
 */

public class BinaryTree04_LCA_LowestCommonAncestor_II {

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
		
		
//		TreeNode lca1 = LCAOfBinaryTree(tree.root, 5,10);		
//		System.out.println("LCAOfBinaryTree(tree.root, 5, 10) :" + lca1 == null ? "null" : lca1.data);
//		System.out.println(LCAOfBinaryTree(tree.root, 5,10));
//
//		TreeNode lca2 = LCAOfBinaryTree(tree.root, 5,4);
//		System.out.println("LCAOfBinaryTree(tree.root, 5, 4) :" + lca2.data);
	}
	
	int counter = 0;
	
	private TreeNode LCAOfBinaryTree(TreeNode TreeNode, TreeNode x, TreeNode y){
	
		 TreeNode node = helper(TreeNode, x, y);
		 return counter == 2 ? node : null;
	}
	
	private TreeNode helper(TreeNode root, TreeNode p, TreeNode q){
		if(root == null) return null;

		TreeNode left = helper(root.left, p, q);
		TreeNode right = helper(root.right, p, q);

		if(root == p || root == q){
			counter++;
			return root;
		}
		return left == null ? right : right == null ? left : root;
	}
}

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
		System.out.println(LCAOfBinaryTree(tree.root, 5,10));
		
		TreeNode lca2 = LCAOfBinaryTree(tree.root, 5,4);		
		System.out.println("LCAOfBinaryTree(tree.root, 5, 4) :" + lca2.data);
	}
	
	int counter = 0;
	
	private TreeNode LCAOfBinaryTree(TreeNode TreeNode, int x, int y){
	
		 TreeNode node = LCA_II(TreeNode, x, y);
		 return counter == 2 ? node : null;
	}
	
	private TreeNode LCA_II(TreeNode root, int x, int y){		
		if(root == null)
			return root;
		
		/***
		It is important to have line 57 and 58 before line 60.
		To make the below test pass
		[3,5,1,6,2,0,8,null,null,7,4]
		5
		4
		**/
		TreeNode left = LCAOfBinaryTree(root.left, x, y);
		TreeNode right = LCAOfBinaryTree(root.right, x, y);
		
		if(root.data == x || root.data == y) {
			counter++; // need this for (5,10) and (5,4) case
			return root;
		}
		
		if(left != null && right != null)
			return root;
		
		return (left != null ? left : right); 
		
	}
}

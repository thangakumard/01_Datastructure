package algorithms.tree.BinarySearchTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

//https://www.youtube.com/watch?v=TIoCCStdiFo
/*****
 * 
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined 
between two nodes p and q as the lowest node in T that has both p and q as descendants 
(where we allow a node to be a descendant of itself).”

Example 1:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.

Example 2:
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself according to the LCA definition.

Example 3:
Input: root = [2,1], p = 2, q = 1
Output: 2
 
Constraints:
The number of nodes in the tree is in the range [2, 105].
-109 <= Node.val <= 109
All Node.val are unique.
p != q
p and q will exist in the BST.
 */
public class BST01_LCA_LowestCommonAncestor {

	@Test
	public void test(){
	
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		inOrderTraversal(tree.root);
		
		TreeNode lca = lcAOfTreeNodes(tree.root, 6,10);		
		System.out.println("lcAOfTreeNodes(tree.root, 6,10) :" + lca.data);
		
		TreeNode lca1 = lcAOfTreeNodes(tree.root, 8,10);		
		System.out.println(" lcAOfTreeNodes(tree.root, 8,10) :" + lca1.data);
	}
	
	/**
	 * Find the middle number and keep it as root TreeNode
	 * Repeat that for left array
	 * Repeat that for right array	
	 */
	TreeNode buildBST(int[] input, int left, int right){
		
		if(left > right)
			return null;
		
		int middle = (left + right)/2;
		
		TreeNode TreeNode = new TreeNode(input[middle]);
		
		TreeNode.left = buildBST(input, left, middle-1);
		TreeNode.right = buildBST(input, middle+1, right);
		
		return TreeNode;		
	}
	
	void inOrderTraversal(TreeNode TreeNode){
		if(TreeNode == null)
			return;
		inOrderTraversal(TreeNode.left);
		System.out.print(TreeNode.data +" ");
		inOrderTraversal(TreeNode.right);
	}
	
	TreeNode lcAOfTreeNodes(TreeNode root,int n1, int n2){
		
		if(root == null)
			return null;
		
		if(root.data > Math.max(n1, n2))
			return lcAOfTreeNodes(root.left, n1, n2);
		
		if( root.data < Math.min(n1, n2))
			return lcAOfTreeNodes(root.right, n1, n2);
		
		return root;
	}
	
}

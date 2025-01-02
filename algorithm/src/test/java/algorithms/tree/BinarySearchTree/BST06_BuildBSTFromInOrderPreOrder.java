package algorithms.tree.BinarySearchTree;

import java.util.HashMap;

import org.testng.annotations.Test;

import algorithms.tree.TreeNode;

/**
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/
 */

public class BST06_BuildBSTFromInOrderPreOrder {

	@Test
	public void buildTree(){
		int[] preorder = new int[]{3,9,20,15,7};
		int[] inorder = new int[]{9,3,15,20,7};
		TreeNode root = buildTree(inorder,preorder);
		printInOrder(root);
	}
	
	private void printInOrder(TreeNode root){
		if(root == null)
			return;
		printInOrder(root.left);
		System.out.print(root.data + " ");
		printInOrder(root.right);
	}
	/********** Pseudocode *************
	 * Use HashMap to Keep In-order node values as Key and index as Value
	 * start with preorder array's 0th index => which is root node of the tree
	 * Find the index of the root node in the inorder traversal using the HashMap we built
	 * root.left will be all the elements left of the root index in the inorder array 
	 * root.right will be all the elements right of the root index in the inorder array 
	 * */

	/**
	 * HINT : Keep 2 global variables
	 * 1. inorder in HashMap
	 * 2. preorder index in a variable
	 */
	
	HashMap<Integer,Integer> inOrderMap = new HashMap<>();
	int preOrderIndex = 0;
	
	private TreeNode buildTree(int[] inorder, int[] preorder) {
		
		for(int i=0; i < inorder.length; i++) {
			inOrderMap.put(inorder[i], i);
		}
		return helper(preorder, 0, inorder.length-1);
	}
	
	private TreeNode helper(int[] preorder, int start, int end) {
		
		if(start > end)
			return null;
		
		TreeNode root = new TreeNode(preorder[preOrderIndex]);
		int inOrderIndex = inOrderMap.get(preorder[preOrderIndex]);
		preOrderIndex++;
		root.left = helper(preorder, start, inOrderIndex-1);
		root.right = helper(preorder, inOrderIndex+1, end);
		
		return root;
	}
}

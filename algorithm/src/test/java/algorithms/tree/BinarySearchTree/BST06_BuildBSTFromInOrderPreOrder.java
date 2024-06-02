package algorithms.tree.BinarySearchTree;

import java.util.HashMap;

import org.testng.annotations.Test;

import algorithms.tree.TreeNode;

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
	 * Keep In-order node values and index in the HashMap
	 * start with preorder array's 0th index => which is root node of the tree
	 * Find the index of the root node in the inorder traversal using the HashMap we built
	 * root.left will be all the elements left of the root index in the inorder array 
	 * root.right will be all the elements right of the root index in the inorder array 
	 * */
	
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
		
		TreeNode currentNode = new TreeNode(preorder[preOrderIndex]);
		int inOrderIndex = inOrderMap.get(preorder[preOrderIndex]);
		preOrderIndex++;
		currentNode.left = helper(preorder, start, inOrderIndex-1);
		currentNode.right = helper(preorder, inOrderIndex+1, end);
		
		return currentNode;
	}
}

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
	
	HashMap<Integer,Integer> inOrderMap = new HashMap<Integer,Integer>();
	
	private TreeNode buildTree(int[] inorder, int[] preorder) {
		
		for(int i=0; i < inorder.length; i++) {
			inOrderMap.put(inorder[i], i);
		}
		return helper(inorder, preorder, 0, inorder.length-1,0);
	}
	
	private TreeNode helper(int[] inorder, int[] preorder, int start, int end,int preOrderIndex) {
		
		if(start > end)
			return null;
		
		TreeNode currentNode = new TreeNode(preorder[preOrderIndex]);
		int inOrderIndex = inOrderMap.get(preorder[preOrderIndex]);
		
		currentNode.left = helper(inorder, preorder, start, inOrderIndex-1, preOrderIndex+1);
		currentNode.right = helper(inorder, preorder, inOrderIndex+1, end, preOrderIndex+1 + (inOrderIndex-start));
		
		return currentNode;
	}
}

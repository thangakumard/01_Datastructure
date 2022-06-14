package algorithms.tree.BinarySearchTree;

import java.util.Stack;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*****
 * https://leetcode.com/problems/convert-bst-to-greater-tree/
 * Given the root of a Binary Search Tree (BST), 
 * convert it to a Greater Tree such that every key of the original BST is changed 
 * to the original key plus sum of all keys greater than the original key in BST.

As a reminder, a binary search tree is a tree that satisfies these constraints:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
Note: This question is the same as 1038: 
https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/

 *
 */
public class BST13__BST_to_GreaterTree {

	@Test
	public void successor() {
		int[] input = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length - 1);
		System.out.println("Recursive Order: \n");
		System.out.println("Input: "); inOrderTraversal(tree.root);
		convertBST_recursive(tree.root);
		System.out.println("");
		System.out.println("Output: "); inOrderTraversal(tree.root);
		
		System.out.println("");
		tree.root = buildBST(input, 0, input.length - 1);
		System.out.println("Iterative Order: \n");
		System.out.println("Input: "); inOrderTraversal(tree.root);
		convertBST_iterative(tree.root);
		System.out.println("");
		System.out.println("Output: "); inOrderTraversal(tree.root);
		System.out.println("");
	}
	
	private TreeNode buildBST(int[] input,int start, int end){
		if(start > end) return null;
		
		int mid = (start+end)/2;
		TreeNode root = new TreeNode(input[mid]);
		root.left = buildBST(input, start, mid-1);
		root.right = buildBST(input, mid+1, end);
		return root;
	}
	
	private void inOrderTraversal(TreeNode root){
		if(root == null)
			return;
		inOrderTraversal(root.left);
		System.out.print(root.data+",");
		inOrderTraversal(root.right);
	}
	
	 private int sum = 0;
	 private TreeNode convertBST_recursive(TreeNode root) {
	        if(root != null){
	        	convertBST_recursive(root.right);
	            sum += root.data;
	            root.data = sum;
	            convertBST_recursive(root.left);
	        }
	        return root;
	    }
	 
	 public TreeNode convertBST_iterative(TreeNode root) {
		 sum = 0;
	        Stack<TreeNode> stack = new Stack<TreeNode>();
	        TreeNode currentNode = root;
	        
	        while(!stack.isEmpty() || currentNode != null){
	            while(currentNode != null){
	                stack.add(currentNode);
	                currentNode = currentNode.right;
	            }
	            
	            currentNode = stack.pop();
	            sum += currentNode.data;
	            currentNode.data = sum;
	            
	            currentNode = currentNode.left;
	        }
	        
	        return root;
	    }
}

package algorithms.tree.BinarySearchTree;
import org.junit.Assert;
import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;
/******
 * a binary tree in which the depth of the two subtrees of every TreeNode never differ by more than 1.
 *https://leetcode.com/problems/balanced-binary-tree/description/
 */
public class BST09_IsBalanced {

	
	@Test
	public void isBalanced(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		Assert.assertTrue(isBalanced(t1.root));
	}

	/***
	 * Time O(n)
	 * Space O(n)
	 */
	public boolean isBalanced(TreeNode root) {
		return checkHeight(root) != -1;
	}

	private int checkHeight(TreeNode node) {
		if (node == null) return 0;

		int leftHeight = checkHeight(node.left);
		if (leftHeight == -1) return -1; // left subtree already unbalanced

		int rightHeight = checkHeight(node.right);
		if (rightHeight == -1) return -1; // right subtree already unbalanced

		if (Math.abs(leftHeight - rightHeight) > 1) return -1; // this node unbalanced

		return Math.max(leftHeight, rightHeight) + 1;
	}
	/***
	 * Time O(n^2)
	 * Space O(n)
	 */
	 public boolean isBalanced_II(TreeNode root) {
	        if(root == null) return true;
	        return Math.abs(heightOfBST(root.left) - heightOfBST(root.right)) < 2
	            && isBalanced_II(root.left)
	            && isBalanced_II(root.right);
	    }
	    
	    public int heightOfBST(TreeNode root){
	        if(root == null)return 0;
	        
	        return Math.max(heightOfBST(root.left), heightOfBST(root.right)) + 1;
	    }
}

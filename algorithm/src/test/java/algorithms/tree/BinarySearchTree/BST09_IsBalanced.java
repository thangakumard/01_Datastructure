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

	boolean result = true;
	
	@Test
	public void isBalanced(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		isBalancedTree(t1.root);
		Assert.assertTrue(result);
	}
	
	private int isBalancedTree(TreeNode root){
		if(root == null)
			return 0;
		int l = isBalancedTree(root.left);
		int r = isBalancedTree(root.right);
		
		if(Math.abs(l-r) > 1){
			result = false;
		}
		
		return 1+Math.max(l, r);
	}
}

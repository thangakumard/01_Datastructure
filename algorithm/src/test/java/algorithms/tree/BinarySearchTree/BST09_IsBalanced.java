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
	
	 public boolean isBalanced(TreeNode root) {
	        if(root == null) return true;
	        return Math.abs(heightOfBST(root.left) - heightOfBST(root.right)) < 2
	            && isBalanced(root.left)
	            && isBalanced(root.right);
	    }
	    
	    public int heightOfBST(TreeNode root){
	        if(root == null)return 0;
	        
	        return Math.max(heightOfBST(root.left), heightOfBST(root.right)) + 1;
	    }
}

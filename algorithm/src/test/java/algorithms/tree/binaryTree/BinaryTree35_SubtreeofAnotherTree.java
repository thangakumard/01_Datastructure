package algorithms.tree.binaryTree;

import algorithms.tree.TreeNode;

/*
 * 
 * 
 * https://leetcode.com/problems/subtree-of-another-tree/
 * 
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure 
 * and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. 
 * The tree s could also be considered as a subtree of itself.
 */
public class BinaryTree35_SubtreeofAnotherTree {

	public boolean isSubtree(TreeNode s, TreeNode t) {
		if(s == null)
			return false;
		else if(isSameTree(s,t)) {
			return true;
		}else {
			return isSubtree(s.left, t) || isSubtree(s.right, t); //IMPORTRAN HERE YOU NEED TO CALL isSubtree method NOT isSameTree method
		}
    }
    
    private boolean isSameTree(TreeNode x, TreeNode y) {
    	if(x == null || y == null) {
    		return x == null && y == null;
    	}
    	else if(x.data == y.data) {
    		return isSameTree(x.left, y.left) && isSameTree(x.right, y.right);
    	}
    	else {
    		return false;
    	}
    }
}

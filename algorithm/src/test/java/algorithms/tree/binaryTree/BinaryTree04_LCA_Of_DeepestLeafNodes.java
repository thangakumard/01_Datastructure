

package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/***
https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
Given the root of a binary tree, return the lowest common ancestor of its deepest leaves.

**/
class BinaryTree04_LCA_Of_DeepestLeafNodes {
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        if(root == null)
            return root;
        
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        if(leftHeight == rightHeight)
            return root;
        if(leftHeight > rightHeight)
            return lcaDeepestLeaves(root.left);
        else
            return lcaDeepestLeaves(root.right);
    }
    
    private int getHeight(TreeNode root){
        if(root == null)
            return 0;
        
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        
        return Math.max(leftHeight, rightHeight) + 1;
    }
}

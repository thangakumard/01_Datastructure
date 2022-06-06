package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iv
 * 
 */


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class BinaryTree04_LCA_LowestCommonAncestor_IV {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        
        if(root == null) return null;
        
        for(TreeNode currentNode: nodes){
            if(root.data == currentNode.data){
                return root;
            }
        }
        
        TreeNode left = lowestCommonAncestor(root.left, nodes);
        TreeNode right = lowestCommonAncestor(root.right, nodes);
        
        if(left != null && right != null)
            return root;
        
        return left!= null ? left : right;
    }
}

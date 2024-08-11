package algorithms.tree.binaryTree;

import algorithms.tree.TreeNode;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree-iv
 * Given the root of a binary tree and an array of TreeNode objects nodes,
 * return the lowest common ancestor (LCA) of all the nodes in nodes. All the nodes will exist in the tree, and all values of the tree's nodes are unique.
 *
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], nodes = [7,6,2,4]
 * Output: 5
 * Explanation: The lowest common ancestor of the nodes 7, 6, 2, and 4 is node 5.
 * /

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

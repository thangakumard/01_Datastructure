package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

/**
 * https://leetcode.com/problems/minimum-distance-between-bst-nodes/description/
 */
public class BST20_MinimumDifference {
    int result = Integer.MAX_VALUE;
    TreeNode prev = null;
    public int minDiffInBST(TreeNode root) {
        if(root.left != null) minDiffInBST(root.left);
        if(prev != null) result = Math.min(result, root.val - prev.val);
        prev = root;
        if(root.right != null) minDiffInBST(root.right);
        return result;
    }
}

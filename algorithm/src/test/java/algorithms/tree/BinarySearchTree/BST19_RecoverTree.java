package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

/**
 * https://leetcode.com/problems/recover-binary-search-tree/
 * You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.
 *
 * Example 1:
 * Input: root = [1,3,null,null,2]
 * Output: [3,1,null,null,2]
 * Explanation: 3 cannot be a left child of 1 because 3 > 1. Swapping 1 and 3 makes the BST valid.
 *
 * Example 2:
 * Input: root = [3,1,4,null,null,2]
 * Output: [2,1,4,null,null,3]
 * Explanation: 2 cannot be in the right subtree of 3 because 2 < 3. Swapping 2 and 3 makes the BST valid.
 * Constraints:
 *
 * The number of nodes in the tree is in the range [2, 1000].
 * -231 <= Node.val <= 231 - 1
 */
public class BST19_RecoverTree {
    TreeNode prev = null, first = null, second = null;

    /**
     * Time: O(n)
     * Space: O(n)
     */
    private void inOrder(TreeNode root){
        if(root == null) return;
        inOrder(root.left);

        if(prev != null && prev.val > root.val){
            if(first == null)
                first = prev;
            second = root;
        }
        prev = root;
        inOrder(root.right);
    }

    public void recoverTree(TreeNode root) {
        if(root == null) return;
        inOrder(root);

        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
}

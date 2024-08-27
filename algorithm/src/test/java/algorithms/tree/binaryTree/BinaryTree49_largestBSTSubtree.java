package algorithms.tree.binaryTree;

import algorithms.tree.TreeNode;

/**
 * https://leetcode.com/problems/largest-bst-subtree/description
 *
 * Given the root of a binary tree, find the largest
 * subtree
 * , which is also a Binary Search Tree (BST), where the largest means subtree has the largest number of nodes.
 *
 * A Binary Search Tree (BST) is a tree in which all the nodes follow the below-mentioned properties:
 *
 * The left subtree values are less than the value of their parent (root) node's value.
 * The right subtree values are greater than the value of their parent (root) node's value.
 * Note: A subtree must include all of its descendants.
 *
 *
 *
 * Example 1:
 * Input: root = [10,5,15,1,8,null,7]
 * Output: 3
 * Explanation: The Largest BST Subtree in this case is the highlighted one. The return value is the subtree's size, which is 3.
 *
 * Example 2:
 * Input: root = [4,2,7,2,3,5,null,2,null,null,null,null,null,1]
 * Output: 2
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 104].
 * -104 <= Node.val <= 104
 *
 *
 * Follow up: Can you figure out ways to solve it with O(n) time complexity?
 */
public class BinaryTree49_largestBSTSubtree {
    public int largestBSTSubtree(TreeNode root) {
        if(root == null) return 0;
        if(isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE)){
            return size(root);
        }
        return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
    }

    private boolean isBST(TreeNode node, int min, int max){
        if(node == null) return true;
        if(node.data <= min || node.data >= max) return false;
        return isBST(node.left, min, node.data) && isBST(node.right, node.data, max);
    }

    private int size(TreeNode node){
        if(node == null) return 0;
        return size(node.left) + size(node.right) +1;
    }
}

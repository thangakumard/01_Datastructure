package algorithms.tree.BinarySearchTree;

import algorithms.tree.TreeNode;

import java.util.ArrayList;
import java.util.List;

/***
 * https://leetcode.com/problems/balance-a-binary-search-tree/description/
 * Given the root of a binary search tree, return a balanced binary search tree with the same node values. If there is more than one answer, return any of them.
 * A binary search tree is balanced if the depth of the two subtrees of every node never differs by more than 1.
 *
 * Example 1:
 * Input: root = [1,null,2,null,3,null,4,null,null]
 * Output: [2,1,3,null,null,null,4]
 * Explanation: This is not the only correct answer, [3,1,4,null,2] is also correct.
 *
 * Example 2:
 * Input: root = [2,1,3]
 * Output: [2,1,3]

 * Constraints:
 * The number of nodes in the tree is in the range [1, 104].
 * 1 <= Node.val <= 105
 */
public class BST16_BalanceBST {
    List<Integer> list = new ArrayList<>();

    public TreeNode balanceBST(TreeNode root) {
        inorder(root);
        return buildBST(0, list.size()-1);
    }

    private void inorder(TreeNode root){
        if(root == null) return;
        inorder(root.left);
        list.add(root.data);
        inorder(root.right);
    }

    private TreeNode buildBST(int start, int end){
        if(start > end) return null;
        int mid = start + (end - start)/2;

        TreeNode root = new TreeNode(list.get(mid));
        root.left = buildBST(start, mid-1);
        root.right = buildBST(mid+1,end);

        return root;
    }


}

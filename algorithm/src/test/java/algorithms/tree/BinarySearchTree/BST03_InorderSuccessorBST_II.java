package algorithms.tree.BinarySearchTree;
import algorithms.tree.TreeNodeWithParent;

/**
 * https://leetcode.com/problems/inorder-successor-in-bst-ii/description/
 */
public class BST03_InorderSuccessorBST_II {
    public TreeNodeWithParent inorderSuccessor(TreeNodeWithParent node) {
        if(node.right != null){
            node = node.right;
            while (node.left != null){
                node = node.left;
            }
            return node;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }
}

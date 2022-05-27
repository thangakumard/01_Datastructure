package algorithms.tree.binaryTree;
import org.testng.annotations.*;
import algorithms.tree.TreeNode;

/*
Given the root of a binary tree, return the number of uni-value subtrees.
A uni-value subtree means all nodes of the subtree have the same value.

Input: root = [5,1,5,5,5,null,5]
Output: 4

Input: root = [5,5,5,5,5,null,5]
Output: 6
*/

public class BinaryTree40_IsFlipEquivalentBinaryTrees {
  
  int counter = 0;
    public int countUnivalSubtrees(TreeNode root) {
        if(root == null) return 0;
        is_uniTree(root);
        return counter;
    }
    
    private boolean is_uniTree(TreeNode node){
        if(node.left == null && node.right == null)
        {
            counter++;
            return true;
        }
        boolean is_unival = true;
        
        if(node.left != null){
            is_unival = is_uniTree(node.left) && is_unival && node.left.val == node.val;
        }
        if(node.right != null){
            is_unival = is_uniTree(node.right) && is_unival && node.right.val == node.val;
        }
        
        if(is_unival)
            counter++;
        
        return is_unival;
    }
}

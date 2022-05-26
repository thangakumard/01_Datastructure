package algorithms.tree.binaryTree;

/***
 * https://leetcode.com/problems/symmetric-tree/
 * 
 * 
Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).

For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

    1
   / \
  2   2
 / \ / \
3  4 4  3
 

But the following [1,2,2,null,3,null,3] is not:

    1
   / \
  2   2
   \   \
   3    3
 

Follow up: Solve it both recursively and iteratively.
 */
import algorithms.tree.TreeNode;

public class BinaryTree30_isSymmetric {

	public boolean isSymmetric(TreeNode root) {
        if(root == null)
            return true;
        else
            return isSymmetricSubTree(root.left, root.right);
    }
    
    private boolean isSymmetricSubTree(TreeNode left, TreeNode right){
        if(left == null && right == null) return true;
        
        if(left != null && right != null)
            return (left.val == right.val && isSymmetricSubTree(left.left, right.right) &&
                   isSymmetricSubTree(left.right, right.left));
        
        return false;
    }
}

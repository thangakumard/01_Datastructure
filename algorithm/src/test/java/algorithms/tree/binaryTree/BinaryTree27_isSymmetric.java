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

public class BinaryTree27_isSymmetric {

	public boolean isSymmetric(TreeNode root) {
        if(root == null)
            return true;
        else
            return isSymmetric_tree(root.left, root.right);
    }
    
    private boolean isSymmetric_tree(TreeNode x, TreeNode y){
        if(x == null && y == null)
            return true;
        else if(x == null || y == null)
            return false;
        else if(x.data != y.data) 
            return false;
        return isSymmetric_tree(x.left, y.right) && isSymmetric_tree(x.right, y.left);
    }
}

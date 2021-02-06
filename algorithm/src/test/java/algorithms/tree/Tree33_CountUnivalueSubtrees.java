package algorithms.tree;

/*
 * 
 * https://leetcode.com/problems/count-univalue-subtrees/
 * 
 * Given the root of a binary tree, return the number of uni-value subtrees.

 * A uni-value subtree means all nodes of the subtree have the same value.
 */
public class Tree33_CountUnivalueSubtrees {
	int count =0;
    private boolean is_uni(TreeNode node){
        if(node.left == null && node.right == null){
            count++;
            return true;
        }
        
        boolean is_unival = true;
        if(node.left != null){
            is_unival = is_uni(node.left) && is_unival && node.left.data == node.data;
        }
        if(node.right != null){
            is_unival = is_uni(node.right) && is_unival && node.right.data == node.data;
        }
        
        if(!is_unival) return false;
        count++;
        return true;
    }
    
    public int countUnivalSubtrees(TreeNode root) {
        if(root == null) return 0;
            is_uni(root);
        return count;
    }

}

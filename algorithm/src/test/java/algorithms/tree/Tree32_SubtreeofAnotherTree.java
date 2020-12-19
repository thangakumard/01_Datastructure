package algorithms.tree;

/*
 * 
 * 
 * https://leetcode.com/problems/subtree-of-another-tree/
 * 
 * Given two non-empty binary trees s and t, check whether tree t has exactly the same structure 
 * and node values with a subtree of s. A subtree of s is a tree consists of a node in s and all of this node's descendants. 
 * The tree s could also be considered as a subtree of itself.
 */
public class Tree32_SubtreeofAnotherTree {

	public boolean isSubtree(TreeNode s, TreeNode t) {
        return traverse(s,t);
    }
    
    private boolean equals(TreeNode x, TreeNode y){
        if(x==null && y == null)
            return true;
        if(x==null || y == null){
            return false;
        }
        return x.data == y.data && equals(x.left, y.left) && equals(x.right, y.right);
    }
    
    private boolean traverse(TreeNode s, TreeNode t){
        return s!=null && (equals(s,t) || traverse(s.left,t) || traverse(s.right,t));
    }
}

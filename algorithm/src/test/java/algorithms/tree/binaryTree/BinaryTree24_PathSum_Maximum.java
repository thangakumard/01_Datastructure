package algorithms.tree.binaryTree;
/*
 * 
 * 
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 * 
 * Given a non-empty binary tree, find the maximum path sum.
For this problem, a path is defined as any node sequence from some starting node to any node 
in the tree along the parent-child connections. 
The path must contain at least one node and does not need to go through the root.
 */

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*****
 *    
 *    10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

 * @author thangakumar
 *
 */
public class BinaryTree24_PathSum_Maximum {

int max_sum = Integer.MIN_VALUE;
    
@Test
public void test(){
		
	BinaryTree tree = new BinaryTree();
	tree.root = new TreeNode(10);
	
	tree.root.left = new TreeNode(5);
	tree.root.right = new TreeNode(-3);
	tree.root.right.right = new TreeNode(11);
	
	tree.root.left.left = new TreeNode(3);
	tree.root.left.right = new TreeNode(2);
	
	tree.root.left.left.left = new TreeNode(3);
	tree.root.left.left.right = new TreeNode(-2);
	tree.root.left.right.right = new TreeNode(1);

	
	System.out.println("Max Path Count :" + maxPathSum(tree.root));
}

    public int maxPathSum(TreeNode root) {
        max_gain(root);
        return max_sum;
    }
    
    private int max_gain(TreeNode root){
        if(root == null) return 0;
        
        //left tree sum
        int left_gain = Math.max(max_gain(root.left), 0);
        //right tree sum
        int right_gain = Math.max(max_gain(root.right), 0);
        
        //whole path sum
        int max_path = root.data + left_gain + right_gain;
        
        //max sum
        max_sum = Math.max(max_sum, max_path);
        
        return root.data + Math.max(left_gain, right_gain);
    }
}

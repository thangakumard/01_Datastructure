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

    int maxSum = Integer.MIN_VALUE;

    /**
     * Time Complexity: O(N), where N is the total number of nodes in the binary tree. Post-order DFS visits each node exactly once.
     * Space Complexity: O(H), where H is the height of the tree, corresponding to the call stack size O(H) on balanced trees, O(H) in worst-case degenerate trees).
     */
    public int maxPathSum(TreeNode root) {
        dfs(root);
        return maxSum;
    }
    
    private int dfs(TreeNode root){
        if (root == null) {
            return 0;
        }

        // Recursively compute the maximum contribution from left and right branches.
        // Math.max(0, ...) ignores negative subtree sums.
        int leftGain = Math.max(0, dfs(root.left));
        int rightGain = Math.max(0, dfs(root.right));

        // Path sum with current node as the highest point (root of the path)
        int currentPathSum = root.val + leftGain + rightGain;

        // Update the global max path sum
        maxSum = Math.max(maxSum, currentPathSum);

        // Return the max single-branch path sum to the parent
        return root.val + Math.max(leftGain, rightGain);
    }
}

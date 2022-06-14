package algorithms.tree.BinarySearchTree;

import java.util.Stack;

import algorithms.tree.TreeNode;

/***
 * https://leetcode.com/problems/range-sum-of-bst/ 
 * Given the root node of a
 * binary search tree, return the sum of values of all nodes with a value in the
 * range [low, high].
 * 
 * 
 * 
 * Example 1:
 * Input: root = [10,5,15,3,7,null,18], low = 7, high = 15 Output: 32  (10+15+7)
 * 
 * Example 2:
 * 
 * 
 * Input: root = [10,5,15,3,7,13,18,1,null,6], low = 6, high = 10 Output: 23 (10,7,6)
 * 
 * 
 * Constraints:
 * 
 * The number of nodes in the tree is in the range [1, 2 * 104]. 1 <= Node.val
 * <= 105 1 <= low <= high <= 105 All Node.val are unique.
 *
 */

public class BST10_RangeSumBST {

	public int rangeSumBST_solution_01(TreeNode root, int low, int high) {
		if (root == null)
			return 0;
		if (root.data > high)
			return rangeSumBST_solution_01(root.left, low, high);
		if (root.data < low)
			return rangeSumBST_solution_01(root.right, low, high);

		return (root.data + rangeSumBST_solution_01(root.left, low, high) + rangeSumBST_solution_01(root.right, low, high));
	}
	
	public int rangeSumBST_solution_02(TreeNode root, int low, int high) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        int result = 0;
       while(true){
           if(root != null){
               stack.push(root);
               root = root.left;
           }else{
               if(stack.isEmpty()) break;
               
               TreeNode temp = stack.pop();
               root = temp.right;
               
               if(low <= temp.data && temp.data <= high){
                   result+= temp.data;
               }
           }
       }
       return result;
   }
}

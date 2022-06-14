package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/***

  https://leetcode.com/problems/delete-nodes-and-return-forest/submissions/

 * Given the root of a binary tree, each node in the tree has a distinct value.

After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).

Return the roots of the trees in the remaining forest. You may return the result in any order.

 

Example 1:


Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
Output: [[1,2,null,4],[6],[7]]
Example 2:

Input: root = [1,2,4,null,3], to_delete = [3]
Output: [[1,2,4]]
 

Constraints:

The number of nodes in the given tree is at most 1000.
Each node has a distinct value between 1 and 1000.
to_delete.length <= 1000
to_delete contains distinct values between 1 and 1000.
 *
 */
public class BinaryTree39_DeleteNodesAndReturnForest {
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				1
    		2				3
    	4		5		6		7
    				
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		tree.root.right.left = new TreeNode(6);
		tree.root.right.right = new TreeNode(7);
		
		int[] to_delete = new int[] {3,5};
		List<TreeNode> ans = delNodes(tree.root, to_delete);
		for(TreeNode node: ans) {
			System.out.println(node.data);
		}
	}
	
	HashSet<Integer> deleteSet;
    List<TreeNode> result;
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        deleteSet = new HashSet<Integer>();
        for(int i: to_delete){
            deleteSet.add(i);
        }
        result = new ArrayList<>();
        helper(root, true);
        return result;
    }
    
    private TreeNode helper(TreeNode root, boolean isRoot){
        if(root == null) return null;
        Boolean isDeleted = deleteSet.contains(root.data);
        if(isRoot && !isDeleted){
            result.add(root);
        }
        root.left = helper(root.left, isDeleted);
        root.right = helper(root.right, isDeleted);
        return isDeleted ? null : root;
    }

}

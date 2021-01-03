package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/path-sum-ii/
 * 
 * Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.

Note: A leaf is a node with no children.

Example:

Given the below binary tree and sum = 22,

      5
     / \
    4   8
   /   / \
  11  13  4
 /  \    / \
7    2  5   1
Return:

[
   [5,4,11,2],
   [5,8,4,5]
]

 */
public class BinaryTree22_PathSum_II {
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	      5
	     / \
	    4   8
	   /   / \
	  11  13  4
	 /  \      \
	7    2      1
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(5);
		tree.root.left = new TreeNode(4);
		tree.root.right = new TreeNode(8);
		tree.root.left.left = new TreeNode(11);
		tree.root.left.left.left = new TreeNode(7);
		tree.root.left.left.right = new TreeNode(2);

		tree.root.right.left = new TreeNode(13);
		tree.root.right.right = new TreeNode(4);
		tree.root.right.right.left = new TreeNode(5);
		tree.root.right.right.right = new TreeNode(1);
 
		System.out.println(pathSum(tree.root, 22));
	}
	

	 public List<List<Integer>> pathSum(TreeNode root, int sum) {
	        List<List<Integer>> paths = new ArrayList<>();
	        
	        findPaths(root, sum, new ArrayList<>(), paths);
	        return paths;
	    }
	    
	    private void findPaths(TreeNode root, int sum, List<Integer> current,List<List<Integer>> paths){
	        if(root == null) return;
	        
	        current.add(root.data);
	        if(root.data == sum && root.left == null && root.right == null){
	            paths.add(current);
	            return;
	        }
	        
	        findPaths(root.left, sum - root.data , new ArrayList<Integer>(current), paths);
	        findPaths(root.right, sum - root.data , new ArrayList<Integer>(current), paths);
	    }
}

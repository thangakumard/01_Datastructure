package algorithms.tree.BinarySearchTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * 
 * https://leetcode.com/problems/search-in-a-binary-search-tree/
 */

public class BST11_SearchInBST {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				1
    		2				3
    	4		5
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(15);
		tree.root.left.left = new TreeNode(3);
		tree.root.left.right = new TreeNode(7);
		tree.root.right.right = new TreeNode(18);
		System.out.println(searchInBST(tree.root,7).data);
	}
	
	private TreeNode searchInBST(TreeNode root, int val) {
		if(root == null) return null;
        if(root.data == val) {
            return root;
        }
        if(root.data > val) { 
            return searchInBST(root.left, val);
        }
        if(root.data < val) { 
            return searchInBST(root.right, val);
        }
        return null;
	}
}

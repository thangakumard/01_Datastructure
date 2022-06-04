package algorithms.tree.binaryTree;
import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;
import algorithms.tree.BinarySearchTree.BST02_BSTFromSortedArray;

/*
The height of a tree is the number of edges on the longest downward path between the root and a leaf. 
So the height of a tree is the height of its root.

https://leetcode.com/problems/maximum-depth-of-binary-tree
*/

public class BinaryTree01_HeightOfBinaryTree {

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
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		tree.root.left.right.right = new TreeNode(7);
		BST02_BSTFromSortedArray bstFromArray = new BST02_BSTFromSortedArray();
		int input[] = {300,400,425,450,475,500,550,600};
		
		
		int height = heightOfBST(bstFromArray.buildBST(input, 0, input.length-1));
		System.out.println("Height is : " + height);
	}
	
	int heightOfBST(TreeNode root){
		if(root == null)
			return 0;
		
		int leftHeight = heightOfBST(root.left);
		int rightHeight = heightOfBST(root.right);
		return 1 + Math.max(leftHeight, rightHeight);
	}
}

package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://www.educative.io/module/lesson/data-structures-in-java/YVPy5nwwPMY
 * 
 * deleteZeroSumSubtree([-3, 5, -2, 7, 6])	
 * 
 * Expected Output: [7, 6]
 */
public class BinaryTree17_DeleteZeroSumSubtree {
	
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				7
	    		5					6
	    	-3		-2       
	    			
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(7);
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(6);
		tree.root.left.left = new TreeNode(-3);
		tree.root.left.right = new TreeNode(-2);

		deleteZeroSumSubtree(tree);
		printInOrder(tree.root);
	}

	public void deleteZeroSumSubtree(BinaryTree tree) {
	    if(tree == null || tree.root == null) return;
	    if(0 == deleteZeroSum(tree.root)) tree.root = null;
	  }

	  private int deleteZeroSum(TreeNode currentNode){
	    if(currentNode == null) return 0;

	    int sum_left = deleteZeroSum(currentNode.left);
	    int sum_right = deleteZeroSum(currentNode.right);

	    if(sum_left == 0){
	      currentNode.left = null;
	    } 

	    if(sum_right == 0){
	      currentNode.right = null;
	    } 

	    return currentNode.data + sum_left + sum_right;

	  }
	  
	  private void printInOrder(TreeNode root) {
		  if(root.left != null) printInOrder(root.left);
		  System.out.println(root.data);
		  if(root.right != null) printInOrder(root.right);
	  }
}

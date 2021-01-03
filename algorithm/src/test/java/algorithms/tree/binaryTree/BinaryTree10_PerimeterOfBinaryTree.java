package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree10_PerimeterOfBinaryTree {
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				100
	    		50					200
	    	20		70       150			300
	    				80							400
	    			75       85
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(100);
		tree.root.left = new TreeNode(50);
		tree.root.right = new TreeNode(200);
		tree.root.left.left = new TreeNode(20);
		tree.root.left.right = new TreeNode(70);
		tree.root.left.left.right = new TreeNode(30);
		tree.root.left.right.right = new TreeNode(80);
		tree.root.left.right.right.left = new TreeNode(75);
		tree.root.left.right.right.right = new TreeNode(85);
		tree.root.right.left = new TreeNode(150);
		tree.root.right.right = new TreeNode(300);
		tree.root.right.right.right = new TreeNode(400);
 
		System.out.println(boundaryOfBinaryTree(tree.root));
	}

	List<Integer> nodes = new ArrayList<>(1000);
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {
	    
	    if(root == null) return nodes;

	    nodes.add(root.data);
	    leftBoundary(root.left);
	    leaves(root.left);
	    leaves(root.right);
	    rightBoundary(root.right);
	    
	    return nodes;
	}
	public void leftBoundary(TreeNode root) {
	    if(root == null || (root.left == null && root.right == null)) return;
	    nodes.add(root.data);
	    if(root.left == null) leftBoundary(root.right);
	    else leftBoundary(root.left);
	}
	public void rightBoundary(TreeNode root) {
	    if(root == null || (root.right == null && root.left == null)) return;
	    if(root.right == null)rightBoundary(root.left);
	    else rightBoundary(root.right);
	    nodes.add(root.data); // add after child visit(reverse)
	}
	public void leaves(TreeNode root) {
	    if(root == null) return;
	    if(root.left == null && root.right == null) {
	        nodes.add(root.data);
	        return;
	    }
	    leaves(root.left);
	    leaves(root.right);
	}
}

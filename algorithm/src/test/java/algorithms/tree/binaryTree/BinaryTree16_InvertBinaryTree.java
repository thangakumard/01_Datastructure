package algorithms.tree.binaryTree;
import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;
/***
https://leetcode.com/problems/invert-binary-tree/
***/

public class BinaryTree16_InvertBinaryTree {

	@Test
	public void invertTree(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		
		printInorder(invertTreeRecursive(t1.root));
		printInorder(invertTreeIterative(t1.root));
	}
	
	/*
	 * Time complexity O(n)
	 * Space complexity O(n)
	 */
	private TreeNode invertTreeRecursive(TreeNode root){
		if(root == null)
			return null;
		TreeNode temp = root.left;
		root.left = invertTreeRecursive(root.right);
		root.right = invertTreeRecursive(temp);
		
		return root;
	}
	
	/*
	 * Time complexity O(n)
	 * Space complexity O(n)
	 */
	private TreeNode invertTreeIterative(TreeNode root) {
        if(root == null)
            return null;
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        
        while(!queue.isEmpty()){
            TreeNode currentTreeNode = queue.remove();
        	
            TreeNode temp = currentTreeNode.left;
            currentTreeNode.left = currentTreeNode.right;
            currentTreeNode.right = temp;
            
            if(currentTreeNode.left != null) queue.add(currentTreeNode.left);
            if(currentTreeNode.right != null) queue.add(currentTreeNode.right);                
        }
        
        return root;
    }
	
	private void printInorder(TreeNode root){
		if(root == null)
			return;
		printInorder(root.left);
		System.out.print(root.data + ",");
		printInorder(root.right);
	}
}

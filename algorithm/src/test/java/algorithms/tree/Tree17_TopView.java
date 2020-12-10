package algorithms.tree;

import java.util.*;

import org.testng.annotations.Test;

public class Tree17_TopView {
	HashSet<Integer> set = new HashSet<Integer>();
	ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
	ArrayDeque<Integer> hdQueue = new ArrayDeque<Integer>(); 
	
	/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		5				15
    	2		8       13		20
    				9				25
		*/

	@Test
	public void topView(){
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(15);
		tree.root.left.left = new TreeNode(2);
		tree.root.left.right = new TreeNode(8);
		tree.root.left.right.right = new TreeNode(9);
		tree.root.right.left = new TreeNode(13);
		tree.root.right.right = new TreeNode(20);
		tree.root.right.right.right = new TreeNode(25);
		queue.offerFirst(tree.root);
		hdQueue.offerFirst(0);
		printTopView(tree.root);
		
		tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.right = new TreeNode(4);
		tree.root.left.right.right = new TreeNode(5);
		tree.root.left.right.right.right = new TreeNode(6);
		queue = new  ArrayDeque<TreeNode>();
		hdQueue = new ArrayDeque<Integer>();
		set = new HashSet<Integer>();
		queue.offerFirst(tree.root);
		hdQueue.offerFirst(0);
		printTopView(tree.root);
	}
	
	//Best Approach
	void topView(TreeNode root)
	{
	    left_view(root.left);
	    System.out.print(root.data + " ");
	    right_view(root.right);
	}

	void left_view(TreeNode root) {
	    if (root == null) return;
	    left_view(root.left);
	    System.out.print(root.data + " ");
	}

	void right_view(TreeNode root) {
	    if (root == null) return;
	    System.out.print(root.data + " ");
	    right_view(root.right);
	}

	
	//Approach 1
	    private void printTopView(TreeNode TreeNode){       
	         if(TreeNode == null){
	            return;
	          }
	           TreeNode currentTreeNode = null;
	           int hd = 0;
	           queue.push(TreeNode);
	           while(!queue.isEmpty()){
	        	hd = hdQueue.pollFirst();
	            currentTreeNode = queue.pollFirst();    
	               if(!set.contains(hd)){
	                   set.add(hd);
	                   System.out.print(currentTreeNode.data + " ");
	               }

	               if(currentTreeNode.left != null){
	            	   hdQueue.offerLast(hd -1);
	            	   queue.offerLast(currentTreeNode.left);
	               }
	               if(currentTreeNode.right != null){
	            	   hdQueue.offerLast(hd + 1);
	            	   queue.offerLast(currentTreeNode.right);
	               }
	           }
	    }

}

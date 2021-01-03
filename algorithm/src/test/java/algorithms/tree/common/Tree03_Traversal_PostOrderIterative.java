package algorithms.tree.common;

import java.util.*;
import java.util.Stack;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

//https://www.youtube.com/watch?v=qT65HltK2uE&t=181s
//https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/TreeTraversals.java#L120
/*
 * https://leetcode.com/problems/binary-tree-postorder-traversal/
 */
public class Tree03_Traversal_PostOrderIterative {

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
		postOrder_usingTwoStack(tree.root);
		postOrder_usingSingleStack(tree.root);
	}
	
	
	void postOrder_usingSingleStack(TreeNode root) {
		if(root == null)return;
		
		ArrayDeque<TreeNode> stack1 = new ArrayDeque<TreeNode>();
		TreeNode current = root;
		while(current != null || !stack1.isEmpty()) {
			if(current != null) {
				stack1.addFirst(current);
				current = current.left;
			}else {
				TreeNode temp = stack1.peek().right;
				if(temp == null) {
					temp = stack1.pop();
					System.out.println(temp.data + " ");
					while(!stack1.isEmpty() && temp == stack1.peek().right) {
						temp = stack1.pop();
						System.out.println(temp.data + " ");
					}
					
				}else {
					current = temp;
				}
			}
		}
		
	}

	
	void postOrder_usingTwoStack(TreeNode root){
		if(root == null)
			return;
		
		Deque<TreeNode> stack1 = new ArrayDeque<TreeNode>();
		Deque<TreeNode> stack2 = new ArrayDeque<TreeNode>();
		
		stack1.add(root);
		while(!stack1.isEmpty()){
			root = stack1.pollFirst();
			if(root.left != null) {
				stack1.addFirst(root.left);
			}
			if(root.right != null) {
				stack1.addFirst(root.right);
			}
			stack2.addFirst(root);
		}
		
		while(!stack2.isEmpty()){
			System.out.println(stack2.pop().data);
		}
	}
}

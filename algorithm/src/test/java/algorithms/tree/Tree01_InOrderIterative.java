package algorithms.tree;

import java.util.Stack;

import org.testng.annotations.Test;


//http://www.geeksforgeeks.org/inorder-tree-traversal-without-recursion/
//https://www.youtube.com/watch?v=nzmtCFNae9k
//https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/TreeTraversals.java

public class Tree01_InOrderIterative {

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
		inorderIterative(tree.root);
	}

	public void inorderIterative(TreeNode root) {
		if(root == null) return;
		
		Stack<TreeNode> stack = new Stack<TreeNode>();
		
		while(true){
			if(root != null){
				stack.push(root);
				root = root.left;
			}else{
				if(stack.isEmpty()) break;
				
				root = stack.pop();
				System.out.print(root.data + " ");
				root = root.right;
			}
		}
	}
}

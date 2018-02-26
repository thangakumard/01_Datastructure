package tree;

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
    the nodes 
    				1
    		2				3
    	4		5
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
		inorderIterative(tree.root);
	}

	void inorderIterative(Node root) {
		if(root == null) return;
		
		Stack<Node> stack = new Stack<Node>();
		
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

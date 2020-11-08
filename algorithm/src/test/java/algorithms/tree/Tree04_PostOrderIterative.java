package algorithms.tree;

import java.util.Stack;

import org.testng.annotations.Test;

//https://www.youtube.com/watch?v=qT65HltK2uE&t=181s
//https://github.com/mission-peace/interview/blob/master/src/com/interview/tree/TreeTraversals.java#L120
public class Tree04_PostOrderIterative {

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
		postOrderIterative(tree.root);
	}
	
	
	void postOrderIterative(Node root){
		if(root == null)
			return;
		
		Stack<Node> stack1 = new Stack<Node>();
		Stack<Node> stack2 = new Stack<Node>();
		
		stack1.add(root);
		while(!stack1.isEmpty()){
			root = stack1.pop();
			if(root.left != null){
				stack1.add(root.left);
			}
			if(root.right != null){
				stack1.add(root.right);
			}
			stack2.add(root);
		}
		
		while(!stack2.isEmpty()){
			System.out.println(stack2.pop().data);
		}
	}
}

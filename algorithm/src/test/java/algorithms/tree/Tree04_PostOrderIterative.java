package algorithms.tree;

import java.util.*;
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
		postOrder_usingTwoStack(tree.root);
		postOrder_usingSingleStack(tree.root);
	}
	
	
	void postOrder_usingSingleStack(Node root) {
		if(root == null)return;
		
		ArrayDeque<Node> stack1 = new ArrayDeque<Node>();
		Node current = root;
		while(current != null || !stack1.isEmpty()) {
			if(current != null) {
				stack1.addFirst(current);
				current = current.left;
			}else {
				Node temp = stack1.peek().right;
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

	
	void postOrder_usingTwoStack(Node root){
		if(root == null)
			return;
		
		Deque<Node> stack1 = new ArrayDeque<Node>();
		Deque<Node> stack2 = new ArrayDeque<Node>();
		
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

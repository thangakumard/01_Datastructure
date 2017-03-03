package tree;

import java.util.Stack;

import org.testng.annotations.Test;

public class Tree05_PostOrderSingleStack {

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
		postOrderInSingleStack(tree.root);
	}

	void postOrderInSingleStack(Node root){

		Node currentNode = root;
		Stack<Node> stack1 = new Stack<Node>();

		while(currentNode != null || !stack1.isEmpty()){

			if(currentNode != null){
				stack1.push(currentNode);
				currentNode = currentNode.left;
			}else{

				Node temp = stack1.peek().right;
				if(temp == null){
					temp = stack1.pop();
					System.out.print(temp.data + " ");
					while(!stack1.isEmpty() && temp ==stack1.peek().right){
						temp = stack1.pop();
						System.out.print(temp.data + " ");
					}
				}else{
					currentNode = temp;
				}
			}
		}

	}

}

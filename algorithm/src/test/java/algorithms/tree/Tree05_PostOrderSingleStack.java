package algorithms.tree;

import java.util.Stack;

import org.testng.annotations.Test;

public class Tree05_PostOrderSingleStack {

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
		postOrderInSingleStack(tree.root);
	}

	void postOrderInSingleStack(TreeNode root){

		TreeNode currentTreeNode = root;
		Stack<TreeNode> stack1 = new Stack<TreeNode>();

		while(currentTreeNode != null || !stack1.isEmpty()){
			if(currentTreeNode != null){
				stack1.push(currentTreeNode);
				currentTreeNode = currentTreeNode.left;
			}else{
				TreeNode temp = stack1.peek().right;
				if(temp == null){
					temp = stack1.pop();
					System.out.print(temp.data + " ");
					while(!stack1.isEmpty() && temp ==stack1.peek().right){
						temp = stack1.pop();
						System.out.print(temp.data + " ");
					}
				}else{
					currentTreeNode = temp;
				}
			}
		}

	}

}

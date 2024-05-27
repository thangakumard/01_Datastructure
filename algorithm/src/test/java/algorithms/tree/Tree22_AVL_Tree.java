package algorithms.tree;

import org.testng.annotations.Test;

import algorithms.tree.traversal.Tree01_Traversal_InOrderIterative;

public class Tree22_AVL_Tree {
	
	@Test
	public void test(){
		
		TreeNode root = null;
		root = insertIntoAVL(root, -10);
		root = insertIntoAVL(root, 2);
		root = insertIntoAVL(root, 13);
		root = insertIntoAVL(root, -13);
		root = insertIntoAVL(root, -15);
		root = insertIntoAVL(root, 15);
		root = insertIntoAVL(root, 17);
		root = insertIntoAVL(root, 20);
		
		//inOrder(root);
		Tree01_Traversal_InOrderIterative traverse = new Tree01_Traversal_InOrderIterative();
		traverse.inorderIterative(root);	
	}
	

	private TreeNode insertIntoAVL(TreeNode root, int value)
	{
		if(root == null)
			return new TreeNode(value);
		
		if(root.data >= value){
			root.left = insertIntoAVL(root.left, value);
		}
		else{
			root.right = insertIntoAVL(root.right, value);
		}
		
		int balanceFactor = balanceFactor(root);
		if(balanceFactor > 1){
			if(balanceFactor(root.left.left) >= balanceFactor(root.left.right)){
				root= rightRotate(root);
			}
			else{
				root.left = leftRotate(root.left);
				root = rightRotate(root);
			}
		}
		else if(balanceFactor < -1){
			if(balanceFactor(root.right.right) > balanceFactor(root.right.left)){
				root = leftRotate(root);
			}
			else{
				root.right = rightRotate(root.right);
				root = leftRotate(root);
			}
		}
		
		return root;
	}
	
	private TreeNode leftRotate(TreeNode root){
		TreeNode newRoot = root.right;
		root.right = newRoot.left;
		newRoot.left = root;
		
		return newRoot;
	}
	
	private TreeNode rightRotate(TreeNode root){
		TreeNode newRoot = root.left;
		root.left = newRoot.right;
		newRoot.right = root;
		
		return newRoot;
	}
	
	
	private int balanceFactor(TreeNode root){
		if(root == null)
			return 0;
		
		int l = balanceFactor(root.left);
		int m = balanceFactor(root.right);
		
		return l-m;
	}
}

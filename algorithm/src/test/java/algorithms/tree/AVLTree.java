package algorithms.tree;

import org.testng.annotations.Test;

import algorithms.tree.traversal.depthFirstSearch.Tree01_Traversal_InOrderIterative;

/****************
 * Bainary search search is a special kind of binaray tree where every TreeNodes in the left subtree is less than or equal to root and 
 * every TreeNode in the right subtree is greater than or equal to root. This is recursively true for every TreeNode.
 * 
 * AVL tree is special kind of binary search tree but the difference of the height of the left subtree and right subtree will never be greater than 1
 * To Insert a TreeNode into an AVL tree, we need to understand 4 simple cases
 * 	1.  LEFT LEFT case
 *  2.	LEFT RIGHT case
 *  3.	RIGHT LEFT case
 *  4.  RIGHT RIGHT case
 *
 */

public class AVLTree {


	private TreeNode leftRotate(TreeNode root){

		TreeNode newRoot = root.right;
		root.right = root.right.left;
		newRoot.left = root;

		newRoot.height = setHeight(newRoot);
		root.height = setHeight(root);
		
		return newRoot;
	}

	private TreeNode rightRotate(TreeNode root){

		TreeNode newRoot = root.left;
		root.left = root.left.right;
		newRoot.right = root;

		newRoot.height = setHeight(newRoot);
		root.height = setHeight(root);
		
		return newRoot;
	}

	private int setHeight(TreeNode root){
		if(root == null)
			return 0;
		return 1 + Math.max((root.left != null ? root.left.height : 0) , (root.right != null ? root.right.height : 0));
	}
	
	private int balance(TreeNode rootLeft, TreeNode rootRight){
		return height(rootLeft) - height(rootRight);
	}
	
	private int height(TreeNode root){
		if(root == null){
			return 0;
		}
		else{
			return root.height;
		}
	}

	private TreeNode insertIntoAVL(TreeNode root, int data){
		
		if(root == null){
			return new TreeNode(data);
		}
		if(root.data <= data){
			root.right = insertIntoAVL(root.right, data);
		}
		else{
			root.left = insertIntoAVL(root.left, data);
		}
		int balance = balance(root.left, root.right);
		if(balance > 1){
			if(height(root.left.left) >= height(root.left.right)){
				root = rightRotate(root);
			}else{
				root.left = leftRotate(root.left);
				root = rightRotate(root);
			}
		}else if(balance < -1){
			if(height(root.right.right) >= height(root.right.left)){
				root = leftRotate(root);
			}else{
				root.right = rightRotate(root.right);
				root = leftRotate(root);
			}
			
		}else{
			root.height = setHeight(root);			
		}
		return root;
	}
	
	private void inOrder(TreeNode TreeNode){
		if(TreeNode == null)
			return;
		if(TreeNode.left != null)
			inOrder(TreeNode.left);
		if(TreeNode.right != null)
			inOrder(TreeNode.right);
		
		System.out.println(TreeNode.data);
			
		
	}
	
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

}

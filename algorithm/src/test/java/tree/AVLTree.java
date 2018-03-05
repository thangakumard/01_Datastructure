package tree;

import org.testng.annotations.Test;

public class AVLTree {


	private Node leftRotate(Node root){

		Node newRoot = root.right;
		root.right = root.right.left;
		newRoot.left = root;

		newRoot.height = setHeight(newRoot);
		root.height = setHeight(root);
		
		return newRoot;
	}

	private Node rightRotate(Node root){

		Node newRoot = root.left;
		root.left = root.left.right;
		newRoot.right = root;

		newRoot.height = setHeight(newRoot);
		root.height = setHeight(root);
		
		return newRoot;
	}

	private int setHeight(Node root){
		if(root == null)
			return 0;
		return 1 + Math.max((root.left != null ? root.left.height : 0) , (root.right != null ? root.right.height : 0));
	}
	
	private int balance(Node rootLeft, Node rootRight){
		return height(rootLeft) - height(rootRight);
	}
	
	private int height(Node root){
		if(root == null){
			return 0;
		}
		else{
			return root.height;
		}
	}

	private Node insertIntoAVL(Node root, int data){
		
		if(root == null){
			return new Node(data);
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
	
	private void inOrder(Node node){
		if(node == null)
			return;
		if(node.left != null)
			inOrder(node.left);
		if(node.right != null)
			inOrder(node.right);
		
		System.out.println(node.data);
			
		
	}
	
	@Test
	public void test(){
		
		Node root = null;
		root = insertIntoAVL(root, -10);
		root = insertIntoAVL(root, 2);
		root = insertIntoAVL(root, 13);
		root = insertIntoAVL(root, -13);
		root = insertIntoAVL(root, -15);
		root = insertIntoAVL(root, 15);
		root = insertIntoAVL(root, 17);
		root = insertIntoAVL(root, 20);
		
		//inOrder(root);
		Tree01_InOrderIterative traverse = new Tree01_InOrderIterative();
		traverse.inorderIterative(root);	
	}

}

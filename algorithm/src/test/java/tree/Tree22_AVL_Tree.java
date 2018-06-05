package tree;

import org.testng.annotations.Test;

public class Tree22_AVL_Tree {
	
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
	

	private Node insertIntoAVL(Node root, int value)
	{
		if(root == null)
			return new Node(value);
		
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
	
	private Node leftRotate(Node root){
		Node newRoot = root.right;
		root.right = newRoot.left;
		newRoot.left = root;
		
		return newRoot;
	}
	
	private Node rightRotate(Node root){
		Node newRoot = root.left;
		root.left = newRoot.right;
		newRoot.right = root;
		
		return newRoot;
	}
	
	
	private int balanceFactor(Node root){
		if(root == null)
			return 0;
		
		int l = balanceFactor(root.left);
		int m = balanceFactor(root.right);
		
		return l-m;
	}
}

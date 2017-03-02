package tree;



public class AVLTree {

	
	private void leftRotate(Node root){
		
		Node newRoot = root.right;
		root.right = root.right.left;
		newRoot.left = root;
		
		newRoot.height = setHeight(newRoot);
		root.height = setHeight(root);
	}
	
	private void rightRotate(Node root){
	
		Node newRoot = root.left;
		root.left = root.left.right;
		newRoot.right = root;
		
		newRoot.height = setHeight(newRoot);
		root.height = setHeight(root);
	}
	
	private int setHeight(Node root){
		if(root == null)
			return 0;
		
		return 1 + Math.max((root.left != null ? root.left.height : 0) , (root.right != null ? root.right.height : 0));
	}
	
	private Node insertIntoAVL(Node root, int data){
		
		if(root == null){
			return new Node(data);
		}
		if(root.data <= data){
			
		}
		
		
		return new Node(10);
		
	}
	
}

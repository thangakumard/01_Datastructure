package algorithms.tree;

import org.testng.annotations.Test;

public class Tree28_Inorder_Successor_BST {

	@Test
	public void successor(){
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		//inOrderTraversal(tree.root);
		
		Node p = new Node(7);
		System.out.println(inOrderSuccessor(tree.root, p).data);
	}
	
	private Node buildBST(int[] input,int start, int end){
		if(start > end) return null;
		
		int mid = (start+end)/2;
		Node root = new Node(input[mid]);
		root.left = buildBST(input, start, mid-1);
		root.right = buildBST(input, mid+1, end);
		return root;
	}
	
	private void inOrderTraversal(Node root){
		if(root == null)
			return;
		inOrderTraversal(root.left);
		System.out.println(root.data);
		inOrderTraversal(root.right);
	}
	
	private Node inOrderSuccessor(Node root, Node p){
		if(root == null)
			return null;
		if(root.data <= p.data){
			return inOrderSuccessor(root.right, p);
		}
		else{
			Node left = inOrderSuccessor(root.left, p);
			return left != null ? left : root;
		}
	}
	
}

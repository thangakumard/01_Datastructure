package tree;

import org.testng.annotations.Test;

public class Tree23_MergeTwoBST {
	
	@Test
	public void mergeBST(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new Node(1);
		t1.root.left = new Node(2);
		t1.root.right = new Node(3);
		t1.root.left.left = new Node(4);
		t1.root.left.right = new Node(5);
		
		BinaryTree t2 = new BinaryTree();
		t2.root = new Node(1);
		t2.root.left = new Node(2);
		t2.root.right = new Node(3);
		t2.root.left.left = new Node(4);
		t2.root.left.right = new Node(5);
		
		Node root = mergeTwoTrees(t1.root, t2.root);
		inorderTraversal(root);
	}
	
	private Node mergeTwoTrees(Node t1, Node t2){
		if(t1 == null)
			return t2;
		if(t2 == null)
			return t1;
		
		t1.data += t2.data;
		t1.left = mergeTwoTrees(t1.left, t2.left);
		t1.right = mergeTwoTrees(t1.right, t2.right);
		
		return t1;
	}
	
	private void inorderTraversal(Node root){
		if(root == null)
			return;
		inorderTraversal(root.left);
		System.out.print(root.data + ",");
		inorderTraversal(root.right);
	}
}

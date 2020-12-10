package algorithms.tree;

import org.testng.annotations.Test;

public class Tree29_Inorder_Predecessor_BST {
	@Test
	public void predecessor(){
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		//inOrderTraversal(tree.root);
		
		TreeNode p = new TreeNode(7);
		System.out.println(inOrderPredecessor(tree.root, p).data);
	}
	
	private TreeNode buildBST(int[] input,int start, int end){
		if(start > end) return null;
		
		int mid = (start+end)/2;
		TreeNode root = new TreeNode(input[mid]);
		root.left = buildBST(input, start, mid-1);
		root.right = buildBST(input, mid+1, end);
		return root;
	}
	
	private void inOrderTraversal(TreeNode root){
		if(root == null)
			return;
		inOrderTraversal(root.left);
		System.out.println(root.data);
		inOrderTraversal(root.right);
	}
	
	private TreeNode inOrderPredecessor(TreeNode root, TreeNode p){
		if(root == null)
			return null;
		if(root.data >= p.data){
			return inOrderPredecessor(root.left, p);
		}
		else{
			TreeNode right = inOrderPredecessor(root.right, p);
			return right!= null ? right : root;
		}
	}
	
}

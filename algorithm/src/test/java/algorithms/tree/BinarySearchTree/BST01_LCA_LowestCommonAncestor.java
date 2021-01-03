package algorithms.tree.BinarySearchTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

//https://www.youtube.com/watch?v=TIoCCStdiFo
public class BST01_LCA_LowestCommonAncestor {

	@Test
	public void test(){
	
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		inOrderTraversal(tree.root);
		
		TreeNode lca = lcAOfTreeNodes(tree.root, 6,10);		
		System.out.println("lcAOfTreeNodes(tree.root, 6,10) :" + lca.data);
		
		TreeNode lca1 = lcAOfTreeNodes(tree.root, 8,10);		
		System.out.println(" lcAOfTreeNodes(tree.root, 8,10) :" + lca1.data);
	}
	
	/**
	 * Find the middle number and keep it as root TreeNode
	 * Repeat that for left array
	 * Repeat that for right array	
	 */
	TreeNode buildBST(int[] input, int left, int right){
		
		if(left > right)
			return null;
		
		int middle = (left + right)/2;
		
		TreeNode TreeNode = new TreeNode(input[middle]);
		
		TreeNode.left = buildBST(input, left, middle-1);
		TreeNode.right = buildBST(input, middle+1, right);
		
		return TreeNode;		
	}
	
	void inOrderTraversal(TreeNode TreeNode){
		if(TreeNode == null)
			return;
		inOrderTraversal(TreeNode.left);
		System.out.print(TreeNode.data +" ");
		inOrderTraversal(TreeNode.right);
	}
	
	TreeNode lcAOfTreeNodes(TreeNode root,int n1, int n2){
		
		if(root == null)
			return null;
		
		if(root.data > Math.max(n1, n2))
			return lcAOfTreeNodes(root.left, n1, n2);
		
		if( root.data < Math.min(n1, n2))
			return lcAOfTreeNodes(root.right, n1, n2);
		
		return root;
	}
	
}

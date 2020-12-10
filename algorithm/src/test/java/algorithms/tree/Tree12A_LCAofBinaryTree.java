package algorithms.tree;

import org.testng.annotations.Test;
import java.util.Deque;
import java.util.ArrayDeque;

public class Tree12A_LCAofBinaryTree {

	@Test
	public void test(){
	
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		System.out.println("");
		inOrderTraversal(tree.root);
		System.out.println("");
		TreeNode lca = LCAOfBinaryTree(tree.root, 6,10);		
		System.out.println("LCAOfBinaryTree(tree.root, 6,10) :" + lca.data);
		
		TreeNode lca1 = LCAOfBinaryTree(tree.root, 8,10);		
		System.out.println("LCAOfBinaryTree(tree.root, 8,10) :" + lca1.data);
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
	
	private TreeNode LCAOfBinaryTree(TreeNode TreeNode, int x, int y){		
		if(TreeNode == null)
			return TreeNode;
		
		if(TreeNode.data == x || TreeNode.data == y)
			return TreeNode;
		
		TreeNode.left = LCAOfBinaryTree(TreeNode.left, x, y);
		TreeNode.right = LCAOfBinaryTree(TreeNode.right, x, y);
		
		if(TreeNode.left != null && TreeNode.right != null)
			return TreeNode;
		
		return (TreeNode.left != null ? TreeNode.left : TreeNode.right); 
		
	}
	
	private void inOrderTraversal(TreeNode TreeNode){
		
		Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
		TreeNode currentTreeNode = TreeNode;
		
		while(currentTreeNode != null || !stack.isEmpty()){
			if(currentTreeNode != null){
				stack.addFirst(currentTreeNode);
				currentTreeNode = currentTreeNode.left; 
			}else{
				if(stack.isEmpty())
					break;
				currentTreeNode = stack.removeFirst();
				System.out.print(currentTreeNode.data + " ");
				currentTreeNode = currentTreeNode.right;
			}			
		}
	}

}

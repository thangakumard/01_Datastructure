package algorithms.tree;

import java.util.Arrays;
import java.util.Stack;

import org.testng.annotations.Test;


public class Tree21_01_BuildTreeFromPostAndInOrder {

	@Test
	public void buildBinaryTree(){
		BinaryTree tree = new BinaryTree();
		int[] inorder = new int[]{9,3,15,20,7};
		int[] postorder = new int[]{9,15,7,20,3};
		
		TreeNode root = buildTree(inorder, postorder);
		inorderIterative(root);
	}
	
//	int pIndex= 0;
//	private int getIndex(){
//		return pIndex;
//	}
//	
//	private int setIndex(int index){
//		pIndex = index;
//		return pIndex;
//	}

	public void inorderIterative(TreeNode root) {
		if(root == null) return;
		
		Stack<TreeNode> stack = new Stack<TreeNode>();
		
		while(true){
			if(root != null){
				stack.push(root);
				root = root.left;
			}else{
				if(stack.isEmpty()) break;
				
				root = stack.pop();
				System.out.print(root.data + " ");
				root = root.right;
			}
		}
	}
	
	 public TreeNode buildTree(int[] inorder, int[] postorder) {	        
		 TreeNode root = null;
		 if(postorder.length > 0){
//			 setIndex(postorder.length-1);			 
			 root = buildTree(inorder,postorder,0,inorder.length-1,postorder.length-1);
		 }		 
		 return root;		 
	  }
	 
	 private TreeNode buildTree(int[] inorder,int[] postorder,int start,int end,int postorderIndex){
		 if(start > end){return null;}
			 int rootValue = postorder[postorderIndex];			 
			 TreeNode currentTreeNode = new TreeNode(rootValue);
			 if(start == end) return currentTreeNode;
			 
			 int rootIndex = findIndex(inorder,start, end, rootValue);
			 
			 currentTreeNode.right= buildTree(inorder, postorder, rootIndex+1, end, postorderIndex-1);
			 currentTreeNode.left = buildTree(inorder,postorder,start,rootIndex-1,postorderIndex-1);
		 return currentTreeNode;
	 }
	 
	 private int findIndex(int[] inorder,int start, int end, int value){
		 int index = -1;
		 for(int i=start; i <= end; i++){
			 if(inorder[i] == value){
				 index = i;
				 break;
			 }
		 }
		 return index;
	 }
}

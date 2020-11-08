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
		
		Node root = buildTree(inorder, postorder);
		inorderIterative(root);
	}
	
	int pIndex= 0;
//	private int getIndex(){
//		return pIndex;
//	}
//	
//	private int setIndex(int index){
//		pIndex = index;
//		return pIndex;
//	}

	public void inorderIterative(Node root) {
		if(root == null) return;
		
		Stack<Node> stack = new Stack<Node>();
		
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
	
	 public Node buildTree(int[] inorder, int[] postorder) {	        
		 Node root = null;
		 if(postorder.length > 0){
//			 setIndex(postorder.length-1);			 
			 root = buildTree(inorder,postorder,0,inorder.length-1,postorder.length-1);
		 }		 
		 return root;		 
	  }
	 
	 private Node buildTree(int[] inorder,int[] postorder,int start,int end,int postorderIndex){
		 if(start > end){return null;}
			 int rootValue = postorder[postorderIndex];			 
			 Node currentNode = new Node(rootValue);
			 if(start == end) return currentNode;
			 int rootIndex = findIndex(inorder,start, end, rootValue);
			 currentNode.right= buildTree(inorder, postorder, rootIndex+1, end, postorderIndex-1);
			 currentNode.left = buildTree(inorder,postorder,start,rootIndex-1,postorderIndex-1);
		 return currentNode;
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

package algorithms.tree;

import org.testng.annotations.Test;

public class Tree21_02_BuildTreeFromPreAndInOrder {

	@Test
	public void buildTree(){
		int[] preorder = new int[]{3,9,20,15,7};
		int[] inorder = new int[]{9,3,15,20,7};
		Node root = buildTree(preorder,inorder);
		printInOrder(root);
	}
	
	private void printInOrder(Node root){
		if(root == null)
			return;
		printInOrder(root.left);
		System.out.println(root.data);
		printInOrder(root.right);
	}
	
	int preIndex = 0;
	   
    private int getPIndex(){
        return preIndex;
    }
   
   private void setPIndex(int value){
       preIndex = value;
   }
   
   public Node buildTree(int[] preorder, int[] inorder) {
       return buildBinaryTree(preorder, inorder, 0, inorder.length-1, 0);
   }
   
   public Node buildBinaryTree(int[] pre, int[] in, int s, int e, int preIndex){
       if(s > e)
           return null;
       System.out.println("s :" + s + "e :" + e);
       Node root = new Node(pre[preIndex]);
       int index = findIndex(in, s, e, pre[preIndex]);
       setPIndex(preIndex+1);        
       root.left = buildBinaryTree(pre, in, s, index-1, getPIndex());
       root.right = buildBinaryTree(pre, in, index+1, e, getPIndex());
       return root;
   }
   
   private int findIndex(int[] inOrder, int s, int e, int value){
       for(int i=s; i <= e; i++){
           if(inOrder[i] == value){
               return i;
           }
       }
       return -1;
   }
}

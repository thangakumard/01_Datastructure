package algorithms.tree;

import org.testng.annotations.Test;

public class Tree21_02_BuildTreeFromPreAndInOrder {

	@Test
	public void buildTree(){
		int[] preorder = new int[]{3,9,20,15,7};
		int[] inorder = new int[]{9,3,15,20,7};
		TreeNode root = buildTree(preorder,inorder);
		printInOrder(root);
	}
	
	private void printInOrder(TreeNode root){
		if(root == null)
			return;
		printInOrder(root.left);
		System.out.println(root.data);
		printInOrder(root.right);
	}
	

   
   public TreeNode buildTree(int[] preorder, int[] inorder) {
       return helper(preorder, inorder, 0, inorder.length-1, 0);
   }
   
   private TreeNode helper(int[] preorder, int[] inorder, int start, int end, int preIndex){
       if(start > end) return null;
       
       TreeNode treeNode = new TreeNode(preorder[preIndex]);
       int rootIndex = 0;
       
       for(int i=start; i<= end; i++){
           if(inorder[i] == preorder[preIndex]){
               rootIndex = i;
           }
       }
               
       treeNode.left = helper(preorder, inorder, start,rootIndex-1, preIndex+1);
       treeNode.right = helper(preorder, inorder, rootIndex+1, end, preIndex+1+ (rootIndex-start));
       
       return treeNode;
   }
   
//	int preOrderIndex = 0;
	   
//  private int getPIndex(){
//      return preIndex;
//  }
// 
// private void setPIndex(int value){
//     preIndex = value;
// }
   
   
//   public TreeNode buildBinaryTree(int[] pre, int[] in, int s, int e, int preIndex){
//       if(s > e)
//           return null;
//       TreeNode root = new TreeNode(pre[preIndex]);
//       
//       int rootIndex = findIndexFromInOrder(in, s, e, pre[preIndex]);
//       preOrderIndex = (preIndex+1);        
//       
//       root.left = buildBinaryTree(pre, in, s, rootIndex-1, preOrderIndex);
//       root.right = buildBinaryTree(pre, in, rootIndex+1, e, preOrderIndex);
//       return root;
//   }
//   
//   private int findIndexFromInOrder(int[] inOrder, int s, int e, int value){
//       for(int i=s; i <= e; i++){
//           if(inOrder[i] == value){
//               return i;
//           }
//       }
//       return -1;
//   }
}

package algorithms.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Test;

public class Tree27_ZigzagLevelOrder {

	@Test
	public void ZigzagLevelOrder(){
		
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		System.out.println(ZigzagTreeTraversal(t1.root));
	}
	
	private List<List<Integer>> ZigzagTreeTraversal(TreeNode root){
		
		 if(root == null) return new ArrayList<>();
	        boolean zig = true;
	      
	        List<List<Integer>> lstResult = new ArrayList<>();
	        Deque<TreeNode> TreeNodeQue = new ArrayDeque<TreeNode>();
	        TreeNodeQue.offerLast(root);
	      
	      while(!TreeNodeQue.isEmpty()){
	        int size = TreeNodeQue.size();
	        List<Integer> levelTreeNodes = new ArrayList<>();
	        for(int i=0; i< size; i++){
	          
	        	TreeNode head = TreeNodeQue.pollFirst();
	          levelTreeNodes.add(head.data);
	          
	          if(head.left != null){
	            TreeNodeQue.offerLast(head.left);
	          }
	          if(head.right != null){
	            TreeNodeQue.offerLast(head.right);
	          }
	          
	        }
	        
	        if(zig){
	          lstResult.add(levelTreeNodes);
	        }
	        else{
	            Collections.reverse(levelTreeNodes);
	            lstResult.add(levelTreeNodes);
	        }
	        
	        zig = !zig;
	      }
	      
	      return lstResult;
	}
	
}

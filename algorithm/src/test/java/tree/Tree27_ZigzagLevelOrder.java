package tree;

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
		t1.root = new Node(1);
		t1.root.left = new Node(2);
		t1.root.right = new Node(3);
		t1.root.left.left = new Node(4);
		t1.root.left.right = new Node(5);
		System.out.println(ZigzagTreeTraversal(t1.root));
	}
	
	private List<List<Integer>> ZigzagTreeTraversal(Node root){
		
		 if(root == null) return new ArrayList<>();
	        boolean zig = true;
	      
	        List<List<Integer>> lstResult = new ArrayList<>();
	        Deque<Node> nodeQue = new ArrayDeque<Node>();
	        nodeQue.offerLast(root);
	      
	      while(!nodeQue.isEmpty()){
	        int size = nodeQue.size();
	        List<Integer> levelNodes = new ArrayList<>();
	        for(int i=0; i< size; i++){
	          
	        	Node head = nodeQue.pollFirst();
	          levelNodes.add(head.data);
	          
	          if(head.left != null){
	            nodeQue.offerLast(head.left);
	          }
	          if(head.right != null){
	            nodeQue.offerLast(head.right);
	          }
	          
	        }
	        
	        if(zig){
	          lstResult.add(levelNodes);
	        }
	        else{
	            Collections.reverse(levelNodes);
	            lstResult.add(levelNodes);
	        }
	        
	        zig = !zig;
	      }
	      
	      return lstResult;
	}
	
}

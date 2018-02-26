package tree;

import java.util.*;

import org.testng.annotations.Test;

public class Tree17_TopView {
	HashSet<Integer> set = new HashSet<Integer>();
	ArrayDeque<Node> queue = new ArrayDeque<Node>();
	ArrayDeque<Integer> hdQueue = new ArrayDeque<Integer>(); 
	
	/* creating a binary tree and entering 
    the nodes 
    				10
    		5				15
    	2		8       13		20
    				9				25
		*/

	@Test
	public void topView(){
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(10);
		tree.root.left = new Node(5);
		tree.root.right = new Node(15);
		tree.root.left.left = new Node(2);
		tree.root.left.right = new Node(8);
		tree.root.left.right.right = new Node(9);
		tree.root.right.left = new Node(13);
		tree.root.right.right = new Node(20);
		tree.root.right.right.right = new Node(25);
		queue.offerFirst(tree.root);
		hdQueue.offerFirst(0);
		printTopView(tree.root);
		
		tree = new BinaryTree();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.right = new Node(4);
		tree.root.left.right.right = new Node(5);
		tree.root.left.right.right.right = new Node(6);
		queue = new  ArrayDeque<Node>();
		hdQueue = new ArrayDeque<Integer>();
		set = new HashSet<Integer>();
		queue.offerFirst(tree.root);
		hdQueue.offerFirst(0);
		printTopView(tree.root);
	}
	
	//Best Approach
	void topView(Node root)
	{
	    left_view(root.left);
	    System.out.print(root.data + " ");
	    right_view(root.right);
	}

	void left_view(Node root) {
	    if (root == null) return;
	    left_view(root.left);
	    System.out.print(root.data + " ");
	}

	void right_view(Node root) {
	    if (root == null) return;
	    System.out.print(root.data + " ");
	    right_view(root.right);
	}

	
	//Approach 1
	    private void printTopView(Node node){       
	         if(node == null){
	            return;
	          }
	           Node currentNode = null;
	           int hd = 0;
	           queue.push(node);
	           while(!queue.isEmpty()){
	        	hd = hdQueue.pollFirst();
	            currentNode = queue.pollFirst();    
	               if(!set.contains(hd)){
	                   set.add(hd);
	                   System.out.print(currentNode.data + " ");
	               }

	               if(currentNode.left != null){
	            	   hdQueue.offerLast(hd -1);
	            	   queue.offerLast(currentNode.left);
	               }
	               if(currentNode.right != null){
	            	   hdQueue.offerLast(hd + 1);
	            	   queue.offerLast(currentNode.right);
	               }
	           }
	    }

}

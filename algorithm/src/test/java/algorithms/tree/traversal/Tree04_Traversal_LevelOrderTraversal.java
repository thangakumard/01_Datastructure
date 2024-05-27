package algorithms.tree.traversal;

import java.util.LinkedList;
import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 * 
 * printLevelorder(tree)
1) Create an empty queue q
2) temp_TreeNode = root //start from root
3) Loop while temp_TreeNode is not NULL
    a) print temp_TreeNode->data.
    b) Enqueue temp_TreeNode�s children (first left then right children) to q
    c) Dequeue a TreeNode from q and assign it�s value to temp_TreeNode
 */
public class Tree04_Traversal_LevelOrderTraversal {
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				100
	    		50					200
	    	20		70       150			300
	    		60		80							400
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(100);
		tree.root.left = new TreeNode(50);
		tree.root.right = new TreeNode(200);
		tree.root.left.left = new TreeNode(20);
		tree.root.left.right = new TreeNode(70);
		tree.root.left.right.right = new TreeNode(80);
		tree.root.left.right.right.left = new TreeNode(75);
		tree.root.left.right.right.right = new TreeNode(85);
		tree.root.right.left = new TreeNode(150);
		tree.root.right.right = new TreeNode(300);
		tree.root.right.right.right = new TreeNode(400);
 
		LevelTraversal(tree.root);
		System.out.println(LevelTraversal_print_ArrayList(tree.root));
	}
	
	private void LevelTraversal(TreeNode root){
		
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		
		while(!queue.isEmpty()){
			
			/* poll() removes the present head.
            For more information on poll() visit 
            http://www.tutorialspoint.com/java/util/linkedlist_poll.htm */
			TreeNode tempTreeNode = queue.poll();
			System.out.println(tempTreeNode.data);
			
			/*Enqueue left child */
			if(tempTreeNode.left != null){
				queue.add(tempTreeNode.left);
			}
			
			/*Enqueue right child */
			if(tempTreeNode.right != null){
				queue.add(tempTreeNode.right);
			}
			
		}	
		
	}
	
	private List<List<Integer>> LevelTraversal_print_ArrayList(TreeNode root){
		
		List<List<Integer>> result = new ArrayList<>();
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(root);
		List<Integer> currentLevel;
		
		while(!queue.isEmpty()){
			
			int size =  queue.size();
			currentLevel = new ArrayList<>();

			for(int i=0; i< size; i++) {
				
				/* poll() removes the present head.
	            For more information on poll() visit 
	            http://www.tutorialspoint.com/java/util/linkedlist_poll.htm */
				TreeNode tempTreeNode = queue.poll();
				currentLevel.add(tempTreeNode.data);
				
				/*Enqueue left child */
				if(tempTreeNode.left != null){
					queue.add(tempTreeNode.left);
				}
				
				/*Enqueue right child */
				if(tempTreeNode.right != null){
					queue.add(tempTreeNode.right);
				}
			}
			result.add(currentLevel);
		}	
		return result;
	}
}

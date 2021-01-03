package algorithms.tree;

import java.util.*;

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
	
	public static List<List<Integer>> ZigzagTreeTraversal(TreeNode root) {
	    List<List<Integer>> result = new ArrayList<List<Integer>>();
	    
	    Queue<TreeNode> queueNodes = new LinkedList<>();
	    queueNodes.add(root);
	    boolean leftToRight = false;

	    while(!queueNodes.isEmpty()){
	        leftToRight = !leftToRight;
	        int size = queueNodes.size();
	        List<Integer> levelValues = new ArrayList<>();

	        for(int i=0; i < size; i++){
	          TreeNode currentNode = queueNodes.poll();
	          if(leftToRight)
	            levelValues.add(currentNode.data);
	          else{
	            levelValues.add(0,currentNode.data);
	          }

	          if(currentNode.left != null)queueNodes.add(currentNode.left);
	          if(currentNode.right != null)queueNodes.add(currentNode.right);
	          
	        }
	        result.add(levelValues);
	    }

	    return result;
	  }
	
}

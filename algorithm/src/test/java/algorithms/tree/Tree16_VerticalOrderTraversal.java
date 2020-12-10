package algorithms.tree;

import java.util.*;

import org.testng.annotations.Test;

public class Tree16_VerticalOrderTraversal {

	@Test
	public void vertical_Traversal(){	
		
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				10
	    		5				15
	    	2		8       13		20
	    				9				25
			*/
			BinaryTree tree = new BinaryTree();
			tree.root = new TreeNode(10);
			tree.root.left = new TreeNode(5);
			tree.root.right = new TreeNode(15);
			tree.root.left.left = new TreeNode(2);
			tree.root.left.right = new TreeNode(8);
			tree.root.left.right.right = new TreeNode(9);
			tree.root.right.left = new TreeNode(13);
			tree.root.right.right = new TreeNode(20);
			tree.root.right.right.right = new TreeNode(25);
			
		   List<List<Integer>> result= verticalTraversal(tree.root);
		   for(List<Integer> i : result){
			   for(Integer j: i){
				   System.out.println(j);
			   }
		   }
	}
	
public List<List<Integer>> verticalTraversal(TreeNode root) {
        
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Deque<TreeNode> TreeNodeQueue = new ArrayDeque<TreeNode>();
        Deque<Integer> orderQueue = new ArrayDeque<Integer>();
        HashMap<Integer,List<Integer>> verticalMap = new HashMap<Integer, List<Integer>>();
        
        TreeNodeQueue.offerFirst(root);
        orderQueue.offerFirst(0);
        TreeNode currentTreeNode;
        int vertical = 0, minValue =0, maxValue = 0;
        while(!TreeNodeQueue.isEmpty()) {
        	
        	currentTreeNode = TreeNodeQueue.pollFirst();
        	vertical = orderQueue.pollFirst();
        	minValue = Math.min(minValue, vertical);
        	maxValue = Math.max(maxValue, vertical);
        	
        	if(!verticalMap.containsKey(vertical)) {
        		verticalMap.put(vertical, new ArrayList<Integer>());
        	}
        	verticalMap.get(vertical).add(currentTreeNode.data);

        	if(currentTreeNode.left != null) {
        		TreeNodeQueue.offerLast(currentTreeNode.left);
        		orderQueue.offerLast(vertical -1);
        	}
        	
        	if(currentTreeNode.right != null) {
        		TreeNodeQueue.offerLast(currentTreeNode.right);
        		orderQueue.offerLast(vertical + 1);
        	}
        	
        }
        
        for(int i=minValue; i <= maxValue; i++) {
        	result.add(verticalMap.get(i));
        }
        
        return result;
}
	
	
	    public List<List<Integer>> verticalOrder_01(TreeNode root) {
	        if (root == null) {
	            return new ArrayList<>();
	        }
	        int minVal = 0;
	        int maxVal = 0;
	        Map<Integer, List<Integer>> map = new HashMap<>();

	        Deque<TreeNode> queue = new LinkedList<>();
	        Deque<Integer> verticalQueue = new LinkedList<>();

	        queue.offerFirst(root);
	        verticalQueue.offerFirst(0);
	        int vertical;
	        while (!queue.isEmpty()) {
	            root = queue.pollFirst();
	            vertical = verticalQueue.pollFirst();
	            minVal = Math.min(minVal, vertical);
	            maxVal = Math.max(maxVal, vertical);

	            List<Integer> r = map.get(vertical);
	            if (r == null) {
	                r = new ArrayList<>();
	                map.put(vertical, r);
	            }
	            r.add(root.data);

	            if (root.left != null) {
	                queue.offerLast(root.left);
	                verticalQueue.offerLast(vertical - 1);
	            }

	            if (root.right != null) {
	                queue.offerLast(root.right);
	                verticalQueue.offerLast(vertical + 1);
	            }
	        }

	        List<List<Integer>> result = new ArrayList<>();
	        for (int i = minVal; i <= maxVal; i++) {
	            List<Integer> r = map.get(i);
	            result.add(r);
	        }
	        return result;
	    }
}

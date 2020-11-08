package algorithms.tree;

import java.util.*;

import org.testng.annotations.Test;

public class Tree16_VerticalOrderTraversal {

	@Test
	public void vertical_Traversal(){	
		
		/* creating a binary tree and entering 
	    the nodes 
	    				10
	    		5				15
	    	2		8       13		20
	    				9				25
			*/
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
			
		   List<List<Integer>> result= verticalOrder(tree.root);
		   for(List<Integer> i : result){
			   for(Integer j: i){
				   System.out.println(j);
			   }
		   }
	}
	
	    public List<List<Integer>> verticalOrder(Node root) {
	        if (root == null) {
	            return new ArrayList<>();
	        }
	        int minVal = 0;
	        int maxVal = 0;
	        Map<Integer, List<Integer>> map = new HashMap<>();

	        Deque<Node> queue = new LinkedList<>();
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

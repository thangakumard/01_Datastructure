package algorithms.tree.binaryTree;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree20_MinimumDepthBinaryTree {

	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				100
	    		50					200
	    	20		70       150			
	    				80							
	    			75       85
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(100);
		tree.root.left = new TreeNode(50);
		tree.root.right = new TreeNode(200);
		tree.root.left.left = new TreeNode(20);
		tree.root.left.right = new TreeNode(70);
		tree.root.left.left.right = new TreeNode(30);
		tree.root.left.right.right = new TreeNode(80);
		tree.root.left.right.right.left = new TreeNode(75);
		tree.root.left.right.right.right = new TreeNode(85);
		tree.root.right.left = new TreeNode(150);

 
		System.out.println(minDepth(tree.root));
	}
	
	public int minDepth(TreeNode root) {
        if(root == null) return 0;
        
        if((root.left == null) && (root.right == null)){
            return 1;
        }
        
        int min_depth = Integer.MAX_VALUE;
        if(root.left != null){
            min_depth = Math.min(minDepth(root.left), min_depth);
        }
        if(root.right != null){
            min_depth = Math.min(minDepth(root.right), min_depth);
        }
        
        return min_depth+1;
    }
}

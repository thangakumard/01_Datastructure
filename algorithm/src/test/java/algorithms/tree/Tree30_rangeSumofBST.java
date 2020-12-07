package algorithms.tree;

import org.testng.annotations.Test;

public class Tree30_rangeSumofBST {
	
	/*
	 * https://leetcode.com/problems/range-sum-of-bst/
	 */
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the nodes 
    				1
    		2				3
    	4		5
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(10);
		tree.root.left = new Node(5);
		tree.root.right = new Node(15);
		tree.root.left.left = new Node(3);
		tree.root.left.right = new Node(7);
		tree.root.right.right = new Node(18);
		System.out.println(rangeSumBST(tree.root,7, 15));
	}

    public int rangeSumBST(Node root, int low, int high) {
        if(root == null) return 0;
        if(root.data > high) return rangeSumBST(root.left, low, high);
        if(root.data < low) return rangeSumBST(root.right, low, high);
        return root.data + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
    }
}

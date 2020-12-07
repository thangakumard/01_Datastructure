package algorithms.tree;

import org.testng.annotations.Test;

/*
 * 
 * https://leetcode.com/problems/search-in-a-binary-search-tree/
 */

public class Tree31_SearchInBST {

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
		System.out.println(searchInBST(tree.root,7).data);
	}
	
	private Node searchInBST(Node root, int val) {
		if(root == null) return null;
        if(root.data == val) {
            return root;
        }
        if(root.data > val) { 
            return searchInBST(root.left, val);
        }
        if(root.data < val) { 
            return searchInBST(root.right, val);
        }
        return null;
	}
}

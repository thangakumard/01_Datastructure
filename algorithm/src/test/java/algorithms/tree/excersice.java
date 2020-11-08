package algorithms.tree;

import org.testng.annotations.Test;

public class excersice {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the nodes 
    				10
    		20				30
    	4		5
		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(10);
		tree.root.left = new Node(20);
		tree.root.right = new Node(30);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
		int level = LCA(tree.root , 5,4);
		System.out.println("LCA is : " + level);
	}

	private int LCA(Node root, int i, int j) {
		
		if(root == null) return -1;
		
		if(root.data < i && root.data < j){
			return LCA(root.right, i , j);
		}
		else if(root.data > i && root.data > j){
			return LCA(root.left, i, j);
		}
		else{
			return root.data;
		}
		
	}

	
}

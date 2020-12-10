package algorithms.tree;

import org.testng.annotations.Test;

public class excersice {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		20				30
    	4		5
		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		tree.root.left = new TreeNode(20);
		tree.root.right = new TreeNode(30);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		int level = LCA(tree.root , 5,4);
		System.out.println("LCA is : " + level);
	}

	private int LCA(TreeNode root, int i, int j) {
		
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

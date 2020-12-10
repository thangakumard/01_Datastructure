package algorithms.tree;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/get-level-of-a-TreeNode-in-a-binary-tree/
public class Tree11_LevelOfNodeInBST {

	@Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				1
    		2				3
    	4		5
		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
		int level = findLevel(tree.root , 5,1);
		System.out.println("TreeNode's Level is : " + level);
	}

	int findLevel(TreeNode root, int value, int level){
		if(root == null){ 
			return 0;
		}
		if(root.data == value){
			return level;
		}
		int currentLevel = findLevel(root.left, value, level+1);
		if(currentLevel != 0)
			return currentLevel;

		currentLevel = findLevel(root.right, value, level+1);

		return currentLevel;
	}
}

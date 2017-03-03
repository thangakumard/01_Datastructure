package tree;

import org.testng.annotations.Test;

//http://www.geeksforgeeks.org/get-level-of-a-node-in-a-binary-tree/
public class Tree11_LevelOfNodeInBST {

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
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
		int level = findLevel(tree.root , 5,1);
		System.out.println("Node's Level is : " + level);
	}

	int findLevel(Node root, int value, int level){
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

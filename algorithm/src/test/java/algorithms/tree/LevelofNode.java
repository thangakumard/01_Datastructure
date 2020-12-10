package algorithms.tree;

import org.testng.annotations.Test;

public class LevelofNode {

	@Test
	public void test(){
		int[] input = {1,2,3,4,5,6,7,8,9,10};

		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		//inOrderTraversal(tree.root);
		
		System.out.println("Level of 7 :" + getLevel(tree.root, 7, 1));


	}

	int getLevel(TreeNode TreeNode,int data,int level){
			
		int downlevel = 0;
		if(TreeNode == null)
			return 0;
		
		if(TreeNode.data == data)
			return level;

		downlevel = getLevel(TreeNode.left, data, level+1);
		if(downlevel != 0)
			return downlevel;

		downlevel = getLevel(TreeNode.right, data, level+1);
		return downlevel;
	}

	TreeNode buildBST(int[] input, int left, int right){

		if(left > right)
			return null;

		int middle = (left + right)/2;

		TreeNode TreeNode = new TreeNode(input[middle]);

		TreeNode.left = buildBST(input, left, middle-1);
		TreeNode.right = buildBST(input, middle+1, right);

		return TreeNode;		
	}

	void inOrderTraversal(TreeNode TreeNode){
		if(TreeNode == null)
			return;

		System.out.println(TreeNode.data);
		inOrderTraversal(TreeNode.left);
		inOrderTraversal(TreeNode.right);
	}

}

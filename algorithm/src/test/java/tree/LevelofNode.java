package tree;

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

	int getLevel(Node node,int data,int level){
			
		int downlevel = 0;
		if(node == null)
			return 0;
		
		if(node.key == data)
			return level;

		downlevel = getLevel(node.left, data, level+1);
		if(downlevel != 0)
			return downlevel;

		downlevel = getLevel(node.right, data, level+1);
		return downlevel;
	}

	Node buildBST(int[] input, int left, int right){

		if(left > right)
			return null;

		int middle = (left + right)/2;

		Node node = new Node(input[middle]);

		node.left = buildBST(input, left, middle-1);
		node.right = buildBST(input, middle+1, right);

		return node;		
	}

	void inOrderTraversal(Node node){
		if(node == null)
			return;

		System.out.println(node.key);
		inOrderTraversal(node.left);
		inOrderTraversal(node.right);
	}

}

package tree;

import org.testng.annotations.Test;

public class Tree13_BSTFromSortedArray {

	/**************
	 * -------Approach-------
	 * 1. Find the middle element and set that as root node
	 * 2. Repeat that for left half of the array and keep that left of root node in the step 1
	 * 3. Repeat that for right half of the array and keep that right of root node in the step 1
	 */
	
	@Test
	public void test(){
		
		int[] inputs = {1,2,3,4,5,6,7,8,9,10};
		BinaryTree tree = new BinaryTree();
		
		tree.root = buildBST(inputs, 0 , inputs.length-1);		
		inOrderTraversal(tree.root);
	}
	
	
	Node buildBST(int[] input, int left, int right){
		if(left > right)
			return null;
		int middle = (left+right)/2;
		Node node = new Node(input[middle]);
		
		node.left = buildBST(input, left, middle-1);
		node.right = buildBST(input, middle+1, right);
		
		return node;
	}
	
	void inOrderTraversal(Node node){
		if(node == null)
			return;
		
		System.out.println(node.data);
		inOrderTraversal(node.left);
		inOrderTraversal(node.right);
	}
}

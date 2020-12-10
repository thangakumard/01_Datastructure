package algorithms.tree;

import org.testng.annotations.Test;

public class Tree13_BSTFromSortedArray {

	/**************
	 * -------Approach-------
	 * 1. Find the middle element and set that as root TreeNode
	 * 2. Repeat that for left half of the array and keep that left of root TreeNode in the step 1
	 * 3. Repeat that for right half of the array and keep that right of root TreeNode in the step 1
	 */
	
	@Test
	public void test(){
		
		int[] inputs = {1,2,3,4,5,6,7,8,9,10};
		BinaryTree tree = new BinaryTree();
		
		tree.root = buildBST(inputs, 0 , inputs.length-1);		
		inOrderTraversal(tree.root);
	}
	
	
	public TreeNode buildBST(int[] input, int left, int right){
		if(left > right)
			return null;
		int middle = (left+right)/2;
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

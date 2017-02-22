package tree;

import org.testng.annotations.Test;

public class LCAOfBST {

	@Test
	public void test(){
	
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		inOrderTraversal(tree.root);
		
		Node lca = lcAOfNodes(tree.root, 6,10);
		
		System.out.println(lca.key);
	}
	
	/**
	 * Find the middle number and keep it as root node
	 * Repeat that for left array
	 * Repeat that for right array
	 * @param input
	 * @param left
	 * @param right
	 * @return
	 */
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
	
	Node lcAOfNodes(Node node,int n1, int n2){
		
		if(node == null)
			return null;
		
		if(node.key > n1 && node.key > n2)
			return lcAOfNodes(node.left, n1, n2);
		
		if( node.key < n1 && node.key < n2)
			return lcAOfNodes(node.right, n1, n2);
		
		return node;
	}
	
}

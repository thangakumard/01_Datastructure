package algorithms.tree;

import org.testng.annotations.Test;
import java.util.Deque;
import java.util.ArrayDeque;

public class Tree12A_LCAofBinaryTree {

	@Test
	public void test(){
	
		int[] input = {1,2,3,4,5,6,7,8,9,10};
		
		BinaryTree tree = new BinaryTree();
		tree.root = buildBST(input, 0, input.length-1);
		System.out.println("");
		inOrderTraversal(tree.root);
		System.out.println("");
		Node lca = LCAOfBinaryTree(tree.root, 6,10);		
		System.out.println("LCAOfBinaryTree(tree.root, 6,10) :" + lca.data);
		
		Node lca1 = LCAOfBinaryTree(tree.root, 8,10);		
		System.out.println("LCAOfBinaryTree(tree.root, 8,10) :" + lca1.data);
	}
	
	/**
	 * Find the middle number and keep it as root node
	 * Repeat that for left array
	 * Repeat that for right array	
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
	
	private Node LCAOfBinaryTree(Node node, int x, int y){		
		if(node == null)
			return node;
		
		if(node.data == x || node.data == y)
			return node;
		
		node.left = LCAOfBinaryTree(node.left, x, y);
		node.right = LCAOfBinaryTree(node.right, x, y);
		
		if(node.left != null && node.right != null)
			return node;
		
		return (node.left != null ? node.left : node.right); 
		
	}
	
	private void inOrderTraversal(Node node){
		
		Deque<Node> stack = new ArrayDeque<Node>();
		Node currentNode = node;
		
		while(currentNode != null || !stack.isEmpty()){
			if(currentNode != null){
				stack.addFirst(currentNode);
				currentNode = currentNode.left; 
			}else{
				if(stack.isEmpty())
					break;
				currentNode = stack.removeFirst();
				System.out.print(currentNode.data + " ");
				currentNode = currentNode.right;
			}			
		}
	}

}

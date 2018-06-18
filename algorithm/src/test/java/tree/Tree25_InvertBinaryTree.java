package tree;

import java.util.ArrayDeque;

import org.testng.annotations.Test;

public class Tree25_InvertBinaryTree {

	@Test
	public void invertTree(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new Node(1);
		t1.root.left = new Node(2);
		t1.root.right = new Node(3);
		t1.root.left.left = new Node(4);
		t1.root.left.right = new Node(5);
		
		printInorder(invertTreeRecursive(t1.root));
		printInorder(invertTreeIterative(t1.root));
	}
	
	private Node invertTreeRecursive(Node root){
		if(root == null)
			return null;
		Node temp = root.left;
		root.left = invertTreeRecursive(root.right);
		root.right = invertTreeRecursive(temp);
		
		return root;
	}
	
	private Node invertTreeIterative(Node root) {
        if(root == null)
            return null;
        ArrayDeque<Node> queue = new ArrayDeque<Node>();
        queue.addLast(root);
        
        while(!queue.isEmpty()){
        	Node currentNode = queue.removeFirst();
        	Node temp = currentNode.left;
            currentNode.left = currentNode.right;
            currentNode.right = temp;
            if(currentNode.left != null) queue.addLast(currentNode.left);
            if(currentNode.right != null) queue.addLast(currentNode.right);                
        }
        
        return root;
    }
	
	private void printInorder(Node root){
		if(root == null)
			return;
		printInorder(root.left);
		System.out.print(root.data + ",");
		printInorder(root.right);
	}
}

package tree;

import org.junit.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.*;

public class Tree24_IsSameTree {

	@Test
	public void isSameTree(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new Node(1);
		t1.root.left = new Node(2);
		t1.root.right = new Node(3);
		t1.root.left.left = new Node(4);
		t1.root.left.right = new Node(5);
		
		BinaryTree t2 = new BinaryTree();
		t2.root = new Node(1);
		t2.root.left = new Node(2);
		t2.root.right = new Node(3);
		t2.root.left.left = new Node(4);
		t2.root.left.right = new Node(5);
		
		Assert.assertTrue(checkTreeNodes(t1.root,t2.root));
	}
	
	private boolean checkTreeNodes(Node t1, Node t2){
		if(t1 == null && t2 == null)
			return true;
		if(t1 == null || t2 == null){
			return false;
		}
		
		if(t1.data == t2.data)
			return checkTreeNodes(t1.left, t2.left) && checkTreeNodes(t1.right, t2.right);
		return false;
	}
}

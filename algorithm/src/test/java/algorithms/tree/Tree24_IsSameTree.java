package algorithms.tree;

import org.junit.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.*;

public class Tree24_IsSameTree {

	@Test
	public void isSameTree(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		
		BinaryTree t2 = new BinaryTree();
		t2.root = new TreeNode(1);
		t2.root.left = new TreeNode(2);
		t2.root.right = new TreeNode(3);
		t2.root.left.left = new TreeNode(4);
		t2.root.left.right = new TreeNode(5);
		
		Assert.assertTrue(checkTreeTreeNodes(t1.root,t2.root));
	}
	
	private boolean checkTreeTreeNodes(TreeNode t1, TreeNode t2){
		if(t1 == null && t2 == null)
			return true;
		if(t1 == null || t2 == null){
			return false;
		}
		
		if(t1.data == t2.data)
			return checkTreeTreeNodes(t1.left, t2.left) && checkTreeTreeNodes(t1.right, t2.right);
		return false;
	}
}

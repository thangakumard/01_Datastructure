package algorithms.tree;
import org.junit.Assert;
import org.testng.annotations.Test;
/******
 * a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
 *https://leetcode.com/problems/balanced-binary-tree/description/
 */
public class Tree26_IsBalanced {

	boolean result = true;
	
	@Test
	public void isBalanced(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new Node(1);
		t1.root.left = new Node(2);
		t1.root.right = new Node(3);
		t1.root.left.left = new Node(4);
		t1.root.left.right = new Node(5);
		isBalancedTree(t1.root);
		Assert.assertTrue(result);
	}
	
	private int isBalancedTree(Node root){
		if(root == null)
			return 0;
		int l = isBalancedTree(root.left);
		int r = isBalancedTree(root.right);
		
		if(Math.abs(l-r) > 1){
			result = false;
		}
		
		return 1+Math.max(l, r);
	}
}

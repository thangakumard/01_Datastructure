package algorithms.tree.binaryTree;

/***
 * 
 * https://leetcode.com/problems/same-tree/

Given the roots of two binary trees p and q, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

 

Example 1:


Input: p = [1,2,3], q = [1,2,3]
Output: true
Example 2:


Input: p = [1,2], q = [1,null,2]
Output: false
Example 3:


Input: p = [1,2,1], q = [1,1,2]
Output: false
 

Constraints:

The number of nodes in both trees is in the range [0, 100].
-104 <= Node.val <= 104

 */
import org.junit.Assert;
import org.testng.annotations.Test;
import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree31_IsSameTree {

	@Test
	private void test() {
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

		Assert.assertTrue(isSameTree(t1.root, t2.root));
	}

	public boolean isSameTree(TreeNode p, TreeNode q) {

		if (p == null && q == null)
			return true;

		if (p != null && q != null) {
			return p.data == q.data && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
		}
		return false;

	}
}

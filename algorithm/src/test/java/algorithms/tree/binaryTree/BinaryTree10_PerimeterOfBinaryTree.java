package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/***
 * https://leetcode.com/problems/boundary-of-binary-tree/
 *
 * The boundary of a binary tree is the concatenation of the root, the left boundary, the leaves ordered from left-to-right, and the reverse order of the right boundary.
 *
 * The left boundary is the set of nodes defined by the following:
 *
 * The root node's left child is in the left boundary. If the root does not have a left child, then the left boundary is empty.
 * If a node in the left boundary and has a left child, then the left child is in the left boundary.
 * If a node is in the left boundary, has no left child, but has a right child, then the right child is in the left boundary.
 * The leftmost leaf is not in the left boundary.
 * The right boundary is similar to the left boundary, except it is the right side of the root's right subtree. Again, the leaf is not part of the right boundary, and the right boundary is empty if the root does not have a right child.
 *
 * The leaves are nodes that do not have any children. For this problem, the root is not a leaf.
 *
 * Given the root of a binary tree, return the values of its boundary.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: root = [1,null,2,3,4]
 * Output: [1,3,4,2]
 * Explanation:
 * - The left boundary is empty because the root does not have a left child.
 * - The right boundary follows the path starting from the root's right child 2 -> 4.
 *   4 is a leaf, so the right boundary is [2].
 * - The leaves from left to right are [3,4].
 * Concatenating everything results in [1] + [] + [3,4] + [2] = [1,3,4,2].
 * Example 2:
 *
 *
 * Input: root = [1,2,3,4,5,6,null,null,null,7,8,9,10]
 * Output: [1,2,4,7,8,9,10,6,3]
 * Explanation:
 * - The left boundary follows the path starting from the root's left child 2 -> 4.
 *   4 is a leaf, so the left boundary is [2].
 * - The right boundary follows the path starting from the root's right child 3 -> 6 -> 10.
 *   10 is a leaf, so the right boundary is [3,6], and in reverse order is [6,3].
 * - The leaves from left to right are [4,7,8,9,10].
 * Concatenating everything results in [1] + [2] + [4,7,8,9,10] + [6,3] = [1,2,4,7,8,9,10,6,3].
 *
 *
 * Constraints:
 *
 * The number of nodes in the tree is in the range [1, 104].
 * -1000 <= Node.val <= 1000
 */

public class BinaryTree10_PerimeterOfBinaryTree {
	
	@Test
	public void test()
	{
		/* creating a binary tree and entering 
	    the TreeNodes 
	    				100
	    		50					200
	    	20		70       150			300
	    				80							400
	    			75       85
			*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(100);
		tree.root.left = new TreeNode(50);
		tree.root.right = new TreeNode(200);
		tree.root.left.left = new TreeNode(20);
		tree.root.left.right = new TreeNode(70);
		tree.root.left.left.right = new TreeNode(30);
		tree.root.left.right.right = new TreeNode(80);
		tree.root.left.right.right.left = new TreeNode(75);
		tree.root.left.right.right.right = new TreeNode(85);
		tree.root.right.left = new TreeNode(150);
		tree.root.right.right = new TreeNode(300);
		tree.root.right.right.right = new TreeNode(400);
 
		System.out.println(boundaryOfBinaryTree(tree.root));
	}

	List<Integer> nodes = new ArrayList<>(1000);
	public List<Integer> boundaryOfBinaryTree(TreeNode root) {
	    
	    if(root == null) return null;

	    nodes.add(root.data);
	    leftBoundary(root.left);
	    leaves(root.left);
	    leaves(root.right);
	    rightBoundary(root.right);
	    
	    return nodes;
	}
	public void leftBoundary(TreeNode root) {
	    if(root == null || (root.left == null && root.right == null)) return;
	    nodes.add(root.data);
	    if(root.left == null) leftBoundary(root.right);
	    else leftBoundary(root.left);
	}
	public void rightBoundary(TreeNode root) {
	    if(root == null || (root.right == null && root.left == null)) return;
	    if(root.right == null)rightBoundary(root.left);
	    else rightBoundary(root.right);
	    nodes.add(root.data); // add after child visit(reverse) ******** IMPORTANT
	}
	public void leaves(TreeNode root) {
	    if(root == null) return;
	    if(root.left == null && root.right == null) {
	        nodes.add(root.data);
	        return;
	    }
	    leaves(root.left);
	    leaves(root.right);
	}
}

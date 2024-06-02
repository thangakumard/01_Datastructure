package algorithms.tree.BinarySearchTree;

import java.util.Stack;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 * 
 * Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

 

Example 1:

Input: root = [3,1,4,null,2], k = 1
   3
  / \
 1   4
  \
   2
Output: 1
Example 2:

Input: root = [5,3,6,2,4,null,null,1], k = 3
       5
      / \
     3   6
    / \
   2   4
  /
 1
Output: 3
Follow up:
What if the BST is modified (insert/delete operations) often and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?

 

Constraints:

The number of elements of the BST is between 1 to 10^4.
You may assume k is always valid, 1 ≤ k ≤ BST's total elements.

 */
public class BST14_KthSmallestInBST {
	
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
		Assertions.assertThat(kthSmallest_01(tree.root, 1)).isEqualTo(20);
		Assertions.assertThat(kthSmallest_02(tree.root, 1)).isEqualTo(20);

	}
	
	public int kthSmallest_02(TreeNode root, int k) {
		if (root == null)
			return -1;
		Stack<TreeNode> stackNodes = new Stack<>();
		while (true) {
			if (root != null) {
				stackNodes.add(root);
				root = root.left;
			} else {
				if (stackNodes.isEmpty())
					break;
				TreeNode temp = stackNodes.pop();
				k--;
				if (k == 0)
					return temp.data;
				root = temp.right;
			}
		}
		return -1;
	}
	
    public int kthSmallest_01(TreeNode root, int k) {
    	int[] i = new int[2];
    	inorder_Traversal(root,k, i);
        return i[1];
    }
    
    private void inorder_Traversal(TreeNode node, int k, int[] i){
        if(node == null)
            return;
        inorder_Traversal(node.left,k, i);
        
        if(++i[0] == k)
        {
            i[1] = node.data;
            return;
        }
        inorder_Traversal(node.right,k, i);
    }

	
}

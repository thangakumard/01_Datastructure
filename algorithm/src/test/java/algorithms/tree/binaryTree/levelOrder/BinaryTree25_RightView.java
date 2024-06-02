 package algorithms.tree.binaryTree.levelOrder;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/******
 * 
 * https://leetcode.com/problems/binary-tree-right-side-view/

Given a binary tree, imagine yourself standing on the right side of it, 
return the values of the nodes you can see ordered from top to bottom.

Example:

Input: [1,2,3,null,5,null,4]
Output: [1, 3, 4]
Explanation:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
 *
 */
public class BinaryTree25_RightView {
	
	/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		5				15
    	2		8       13		20
    				9				25
		*/

	@Test
	public void rightView(){
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(15);
		tree.root.left.left = new TreeNode(2);
		tree.root.left.right = new TreeNode(8);
		tree.root.left.right.right = new TreeNode(9);
		tree.root.right.left = new TreeNode(13);
		tree.root.right.right = new TreeNode(20);
		tree.root.right.right.right = new TreeNode(25);
		
		List<Integer> result = rightSideView(tree.root);
		System.out.println(result);
	}

	public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;
        Queue<TreeNode> queueNode = new LinkedList<TreeNode>();
        queueNode.add(root);
        
        while(!queueNode.isEmpty()){
            int size = queueNode.size();
            
            for(int i=0; i<size; i++){
                TreeNode currentNode = queueNode.poll();
                
                if(i == size-1) result.add(currentNode.data);
                if(currentNode.left != null)queueNode.add(currentNode.left);
                if(currentNode.right != null)queueNode.add(currentNode.right);
            }
        }
        
        return result;
    }
}

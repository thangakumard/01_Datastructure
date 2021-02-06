package algorithms.tree.binaryTree;

import java.util.*;

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
public class BinaryTree24_RightView {

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

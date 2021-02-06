package algorithms.tree.binaryTree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import algorithms.tree.TreeNode;

/*****
 * https://leetcode.com/problems/maximum-width-of-binary-tree/
 Given a binary tree, write a function to get the maximum width of the given tree. The maximum width of a tree is the maximum width among all levels.

The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level, where the null nodes between the end-nodes are also counted into the length calculation.

It is guaranteed that the answer will in the range of 32-bit signed integer.

Example 1:

Input: 

           1
         /   \
        3     2
       / \     \  
      5   3     9 

Output: 4
Explanation: The maximum width existing in the third level with the length 4 (5,3,null,9).
Example 2:

Input: 

          1
         /  
        3    
       / \       
      5   3     

Output: 2
Explanation: The maximum width existing in the third level with the length 2 (5,3).
Example 3:

Input: 

          1
         / \
        3   2 
       /        
      5      

Output: 2
Explanation: The maximum width existing in the second level with the length 2 (3,2).
Example 4:

Input: 

          1
         / \
        3   2
       /     \  
      5       9 
     /         \
    6           7
Output: 8
Explanation:The maximum width existing in the fourth level with the length 8 (6,null,null,null,null,null,null,7).
 *
 */

public class BinaryTree32_WidthOfBinaryTree {

	
	public int widthOfBinaryTree(TreeNode root) {
        Queue<TreeNode> queueNode = new LinkedList<TreeNode>();
        HashMap<TreeNode, Integer> map = new HashMap<TreeNode, Integer>();
        int start=0, end=0, currentWidth=0, maxWidth=0;

        queueNode.add(root);
        map.put(root,1);
        
        while(!queueNode.isEmpty()){
            int size = queueNode.size();
            for(int i=0; i<size; i++){
                TreeNode CurrentNode = queueNode.poll();
                if(i == 0) start = map.get(CurrentNode);
                if(i == size -1) end = map.get(CurrentNode);
                
                if(CurrentNode.left != null){
                    queueNode.add(CurrentNode.left);
                    map.put(CurrentNode.left, map.get(CurrentNode) * 2);
                }
                if(CurrentNode.right != null){
                    queueNode.add(CurrentNode.right);
                    map.put(CurrentNode.right, map.get(CurrentNode) * 2 + 1);
                }
            }
            currentWidth = end - start + 1;
            maxWidth = Math.max(maxWidth, currentWidth);
            
        }
        
        return maxWidth;
    }
}

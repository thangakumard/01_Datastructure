package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.*;
import algorithms.tree.BinarySearchTree.BST02_BSTFromSortedArray;

/**
 * 
 * https://leetcode.com/problems/find-duplicate-subtrees/
 * 
Given the root of a binary tree, return all duplicate subtrees.

For each kind of duplicate subtrees, you only need to return the root node of any one of them.

Two trees are duplicate if they have the same structure with the same node values.

 

Example 1:


Input: root = [1,2,3,4,null,2,4,null,null,4]
Output: [[2,4],[4]]
Example 2:


Input: root = [2,1,1]
Output: [[1]]
Example 3:


Input: root = [2,2,2,3,null,3,null]
Output: [[2,3],[3]]
 

Constraints:

The number of the nodes in the tree will be in the range [1, 10^4]
-200 <= Node.val <= 200
 *
 */
public class BinaryTree38_FindDuplicateSubtrees {

	
    
    @Test
	public void test()
	{
		/* creating a binary tree and entering 
    the TreeNodes 
    				1
    		2				3
    	4				2		4
    				4
    	
		*/
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.right.left = new TreeNode(2);
		tree.root.right.right = new TreeNode(4);
		tree.root.right.left.left = new TreeNode(4);
		
		
		List<TreeNode> ans = findDuplicateSubtrees(tree.root);
		for(TreeNode node: ans) {
			System.out.println(node.data);
		}
	}
    

    
    /******* SOLUTION *******/
    HashMap<String,Integer> mapPath;
    List<TreeNode> result;
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        mapPath = new HashMap<>();
        result = new ArrayList<>();
        collect(root);
        return result;
    }
    
    private String collect(TreeNode node){
        if(node == null) return "#";
        String path = node.data + "," + 
            collect(node.left) + "," + 
            collect(node.right);
        mapPath.put(path,mapPath.getOrDefault(path,0)+1);
        if(mapPath.get(path) == 2){
            result.add(node);
        }
        return path;
    }
}

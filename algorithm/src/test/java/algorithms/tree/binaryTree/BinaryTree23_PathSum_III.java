package algorithms.tree.binaryTree;

import java.util.HashMap;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/******
 * 
 * https://leetcode.com/problems/path-sum-iii/
 * 
 * You are given a binary tree in which each node contains an integer value.

Find the number of paths that sum to a given value.

The path does not need to start or end at the root or a leaf, but it must go downwards (traveling only from parent nodes to child nodes).

The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

Example:

root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

Return 3. The paths that sum to 8 are:

1.  5 -> 3
2.  5 -> 2 -> 1
3. -3 -> 11
 *
 */
public class BinaryTree23_PathSum_III {
	
	@Test
	public void test(){
			
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(10);
		
		tree.root.left = new TreeNode(5);
		tree.root.right = new TreeNode(-3);
		tree.root.right.right = new TreeNode(11);
		
		tree.root.left.left = new TreeNode(3);
		tree.root.left.right = new TreeNode(2);
		
		tree.root.left.left.left = new TreeNode(3);
		tree.root.left.left.right = new TreeNode(-2);
		tree.root.left.right.right = new TreeNode(1);

		
		System.out.println("Path Sum Count :" + pathSum(tree.root, 8));
	}

	public int pathSum(TreeNode root, int target) {
        HashMap<Long, Integer> map = new HashMap<>();
        map.put(0L,1);
        return countPaths(root, target, 0, map);
    }
    
   /**** Big O(n) Solution ****/
    private int countPaths(TreeNode root, int target, long currentSum, HashMap<Long, Integer> map){
        if(root == null) return 0;
        
        currentSum += root.data;
        //Get the matching count from the map
        int count = map.getOrDefault((currentSum - target), 0);

        //Increment the currentSum count the map
        map.put(currentSum , map.getOrDefault(currentSum, 0) + 1);
        count += countPaths(root.left, target, currentSum,map) +
                countPaths(root.right, target, currentSum,map);
        //Decrement the currentSum count the map
        map.put(currentSum , map.get(currentSum) - 1);
        return count;
    }
	
   /**** Big O(n ^ 2) Solution ****/
     @Test
     public int pathSum_BigO_N2(TreeNode root, int targetSum) {
        if(root == null) return 0;
        return getPathCount(root, targetSum) + 
            pathSum_BigO_N2(root.left, targetSum) + 
            pathSum_BigO_N2(root.right, targetSum);
    }
   
    private int getPathCount(TreeNode root, long targetSum){
        if(root == null) return 0;
        
        int count = 0;
        if(root.data == targetSum){
            count = 1;
        }
        
        targetSum = targetSum - root.data;
        count += getPathCount(root.left, targetSum);
        count += getPathCount(root.right, targetSum);
        
        return count;
    }

}

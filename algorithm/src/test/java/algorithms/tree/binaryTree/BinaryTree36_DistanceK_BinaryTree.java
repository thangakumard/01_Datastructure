package algorithms.tree.binaryTree;

import java.util.*;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

/*****
	https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
	This solution is copied from: https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/discuss/143798/1ms-beat-100-simple-Java-dfs-with(without)-hashmap-including-explanation
*****/

public class BinaryTree36_DistanceK_BinaryTree {

	@Test
	public void test()
	{
		/* 		
		creating a binary tree and entering the TreeNodes 
    				1
    		2				3
    	4		5
		 */
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(3);
		tree.root.left = new TreeNode(5);
		
		tree.root.left.left = new TreeNode(6);
		tree.root.left.right = new TreeNode(2);
		tree.root.left.right.left = new TreeNode(7);
		tree.root.left.right.right = new TreeNode(4);
		
		tree.root.right = new TreeNode(1);
		tree.root.right.left = new TreeNode(0);
		tree.root.right.right = new TreeNode(8);
		
		List<Integer> result = distanceK(tree.root , tree.root.left,2);
		System.out.println("distanceK from the target node : " + result);
	}
	
	public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new LinkedList<>();
        if (K == 0) {
            res.add(target.data);
        } else {
            dfs(res, root, target.data, K ,0);
        }
        return res;
    }
    
    private int dfs(List<Integer> res, TreeNode node, int target, int K, int depth) {
        if (node == null) return 0;
        
        if (depth == K) {
            res.add(node.data);
            return 0;
        }
        
        int left, right;
        if (node.data == target || depth > 0) {
            left = dfs(res, node.left, target, K, depth + 1);
            right = dfs(res, node.right, target, K, depth + 1);
        } else {
            left = dfs(res, node.left, target, K, depth);
            right = dfs(res, node.right, target, K, depth);
        }
        
        if (node.data == target) return 1;
        
        if (left == K || right == K) {
            res.add(node.data);
            return 0;
        }
        
        if (left > 0) {
            dfs(res, node.right, target, K, left + 1);
            return left + 1;
        }
        
        if (right > 0) {
            dfs(res, node.left, target, K, right + 1);
            return right + 1;
        }
        
        return 0;
    }
}

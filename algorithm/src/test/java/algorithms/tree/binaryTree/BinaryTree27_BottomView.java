package algorithms.tree.binaryTree;

import java.util.HashMap;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree27_BottomView {

	/* creating a binary tree and entering 
    the TreeNodes 
    				10 (0)
    		5(-1)				15(1)
    	2(-2)		    8(0)            13(0)			20(2)
    				           9(1)						       25(3)
		*/

	@Test
	public void bottomView(){
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
		
		printbottomView(tree.root);
	}
	
	int start = 0, end = 0;
	private void printbottomView(TreeNode node) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		buildHorizondalDistanceMap(node, 0, map);
		while(start <= end) {
			System.out.print(map.get(start) + " ");
			start++;
		}
	}
	
	private void buildHorizondalDistanceMap(TreeNode node, int hd, HashMap<Integer, Integer> map) {
		map.put(hd, node.data);
		
		start = Math.min(start, hd);
		end = Math.max(end, hd);
		
		if(node.left != null)
			buildHorizondalDistanceMap(node.left, hd-1, map);
		if(node.right != null)
			buildHorizondalDistanceMap(node.right, hd+1, map);
		
	}
}

package algorithms.tree.binaryTree.bfs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.testng.annotations.Test;

import algorithms.tree.BinaryTree;
import algorithms.tree.TreeNode;

public class BinaryTree26_LeftView {
	
	/* creating a binary tree and entering 
    the TreeNodes 
    				10
    		5				15
    	2		8       13		20
    				9				25
		*/

	@Test
	public void leftView(){
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
		
		List<Integer> result = leftSideView(tree.root);
		System.out.println(result);
	}

	public List<Integer> leftSideView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if(root == null) return result;
        Queue<TreeNode> queueNode = new LinkedList<TreeNode>();
        queueNode.add(root);
        
        while(!queueNode.isEmpty()){
            int size = queueNode.size();
            
            for(int i=0; i<size; i++){
                TreeNode currentNode = queueNode.poll();
                
                if(i == 0) result.add(currentNode.data);
                if(currentNode.left != null)queueNode.add(currentNode.left);
                if(currentNode.right != null)queueNode.add(currentNode.right);
            }
        }
        
        return result;
    }
}

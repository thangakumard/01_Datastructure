package algorithms.tree;

import java.util.ArrayDeque;

import org.testng.annotations.Test;

public class Tree25_InvertBinaryTree {

	@Test
	public void invertTree(){
		BinaryTree t1 = new BinaryTree();
		t1.root = new TreeNode(1);
		t1.root.left = new TreeNode(2);
		t1.root.right = new TreeNode(3);
		t1.root.left.left = new TreeNode(4);
		t1.root.left.right = new TreeNode(5);
		
		printInorder(invertTreeRecursive(t1.root));
		printInorder(invertTreeIterative(t1.root));
	}
	
	private TreeNode invertTreeRecursive(TreeNode root){
		if(root == null)
			return null;
		TreeNode temp = root.left;
		root.left = invertTreeRecursive(root.right);
		root.right = invertTreeRecursive(temp);
		
		return root;
	}
	
	private TreeNode invertTreeIterative(TreeNode root) {
        if(root == null)
            return null;
        ArrayDeque<TreeNode> queue = new ArrayDeque<TreeNode>();
        queue.addLast(root);
        
        while(!queue.isEmpty()){
        	TreeNode currentTreeNode = queue.removeFirst();
        	TreeNode temp = currentTreeNode.left;
            currentTreeNode.left = currentTreeNode.right;
            currentTreeNode.right = temp;
            if(currentTreeNode.left != null) queue.addLast(currentTreeNode.left);
            if(currentTreeNode.right != null) queue.addLast(currentTreeNode.right);                
        }
        
        return root;
    }
	
	private void printInorder(TreeNode root){
		if(root == null)
			return;
		printInorder(root.left);
		System.out.print(root.data + ",");
		printInorder(root.right);
	}
}

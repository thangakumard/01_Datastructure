package algorithms.tree;

import java.util.LinkedList;
import java.util.Queue;

import org.testng.annotations.Test;

/*
 * printLevelorder(tree)
1) Create an empty queue q
2) temp_TreeNode = root //start from root
3) Loop while temp_TreeNode is not NULL
    a) print temp_TreeNode->data.
    b) Enqueue temp_TreeNode�s children (first left then right children) to q
    c) Dequeue a TreeNode from q and assign it�s value to temp_TreeNode
 */
public class Tree08_LevelOrderTraversal {
	
	@Test
	public void test()
	{
		BinaryTree tree = new BinaryTree();
		tree.root = new TreeNode(1);
		tree.root.left = new TreeNode(2);
		tree.root.right = new TreeNode(3);
		tree.root.left.left = new TreeNode(4);
		tree.root.left.right = new TreeNode(5);
 
		LevelTraversal(tree.root);
	}
	
	private void LevelTraversal(TreeNode root){
		
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		
		while(!queue.isEmpty()){
			
			/* poll() removes the present head.
            For more information on poll() visit 
            http://www.tutorialspoint.com/java/util/linkedlist_poll.htm */
			TreeNode tempTreeNode = queue.poll();
			System.out.println(tempTreeNode.data);
			
			/*Enqueue left child */
			if(tempTreeNode.left != null){
				queue.add(tempTreeNode.left);
			}
			
			/*Enqueue right child */
			if(tempTreeNode.right != null){
				queue.add(tempTreeNode.right);
			}
			
		}	
		
	}
}

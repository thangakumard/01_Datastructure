package tree;

import java.util.LinkedList;
import java.util.Queue;

import org.testng.annotations.Test;

/*
 * printLevelorder(tree)
1) Create an empty queue q
2) temp_node = root //start from root
3) Loop while temp_node is not NULL
    a) print temp_node->data.
    b) Enqueue temp_node’s children (first left then right children) to q
    c) Dequeue a node from q and assign it’s value to temp_node
 */
public class LevelOrderTraversal {
	
	@Test
	public void test()
	{
		BinaryTree tree = new BinaryTree();
		tree.root = new Node(1);
		tree.root.left = new Node(2);
		tree.root.right = new Node(3);
		tree.root.left.left = new Node(4);
		tree.root.left.right = new Node(5);
 
		LevelTraversal(tree.root);
	}
	
	private void LevelTraversal(Node root){
		
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(root);
		
		while(!queue.isEmpty()){
			
			/* poll() removes the present head.
            For more information on poll() visit 
            http://www.tutorialspoint.com/java/util/linkedlist_poll.htm */
			Node tempNode = queue.poll();
			System.out.println(tempNode.key);
			
			/*Enqueue left child */
			if(tempNode.left != null){
				queue.add(tempNode.left);
			}
			
			/*Enqueue right child */
			if(tempNode.right != null){
				queue.add(tempNode.right);
			}
			
		}	
		
	}
}
